package org.manufacture.Agents;

import jade.content.lang.Codec;
import jade.content.onto.OntologyException;
import jade.content.onto.basic.Action;
import jade.core.AID;
import jade.core.Agent;
import jade.domain.FIPAException;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.ContractNetInitiator;
import jade.proto.ContractNetResponder;
import org.manufacture.API.QueryExecutorService;
import org.manufacture.Ontology.actions.SendOrder;
import org.manufacture.Ontology.actions.actionsImpl.DefaultSendOrder;
import org.manufacture.Ontology.concepts.domain.Order;
import org.manufacture.Ontology.concepts.domain.Plan;
import org.manufacture.Ontology.concepts.domain.Product;
import org.manufacture.Ontology.concepts.domain.domainImpl.DefaultOrder;
import org.manufacture.Ontology.concepts.domain.domainImpl.DefaultPlan;
import org.manufacture.Ontology.concepts.domain.domainImpl.DefaultProduct;
import org.manufacture.constants.Constants;
import org.manufacture.dbConnection.QueryExecutor;

import java.sql.SQLException;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

public class AgentDistributor extends ResourceAgent {

    private java.util.List<AID> managerAgents;
    private List<AID> manufacturerAgents;
    private MessageTemplate mt;
    private AID manager;
    private Order order;
    private List<AID> worker;

    @Override
    protected void setup() {
        super.setup();
        try {
            createOrder();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            managerAgents = findServices(Constants.PRODUCT_MANAGER_TYPE);
        } catch (FIPAException e) {
            e.printStackTrace();
        }
        orderHandler();
    }

    private void createOrder() throws SQLException {
        Object[] arguments = getArguments();
        String productName = (String) arguments[0];
        String dueDate = String.valueOf(arguments[1]);
        int qty = (int) arguments[2];
        order = new DefaultOrder();
        QueryExecutorService queryExecutor = QueryExecutor.getQueryExecutor();
        Plan plan = queryExecutor.seekPlan(productName);

        Product product = new DefaultProduct();
        product.setName(productName);
        order.setFormedOnProduct(product);
        order.setQuantity(qty);
        order.setDueDate(dueDate);
        order.setExecutedByPlan(plan);
    }

    private void orderHandler() {
        ACLMessage msg = new ACLMessage(ACLMessage.CFP);
        //Время, к которому ответ должен быть получен
        //msg.setReplyByDate(new Date((System.currentTimeMillis() + 1000)));
        managerAgents.forEach(msg::addReceiver);
        msg.setConversationId(Constants.DISTRIBUTOR_MANAGER);
        msg.setProtocol(FIPANames.InteractionProtocol.FIPA_CONTRACT_NET);
        msg.setReplyWith("msg" + System.currentTimeMillis());
        addBehaviour(new ContractNetInitiator(this, msg) {

            @Override
            protected void handlePropose(ACLMessage propose, Vector acceptances) {
                System.out.println("[" + getLocalName() +
                        "] Принял ответ от " + propose.getSender().getLocalName());
            }

            @Override
            protected void handleRefuse(ACLMessage refuse) {
                System.out.println("[" + getLocalName() +
                        "] Принял отказ от " + refuse.getSender().getLocalName());
            }

            @Override
            protected void handleFailure(ACLMessage failure) {
                super.handleFailure(failure);
            }

            @Override
            protected void handleAllResponses(Vector responses, Vector acceptances) {
                String bestProposal = "Yes";
                AID bestProposer = null;
                ACLMessage accept = null;
                @SuppressWarnings("rawtypes")
                Enumeration e = responses.elements();
                while (e.hasMoreElements()) {
                    ACLMessage msg = (ACLMessage) e.nextElement();
                    if (msg.getPerformative() == ACLMessage.PROPOSE) {
                        ACLMessage reply = msg.createReply();
                        reply.setPerformative(ACLMessage.REJECT_PROPOSAL);
                        //noinspection unchecked
                        acceptances.addElement(reply);
                        String proposal = msg.getContent();
                        if (proposal.equals("Yes")) {
                            bestProposer = msg.getSender();
                            accept = reply;
                        }
                    }
                }
                if (accept != null) {
                    System.out.println("[" + getLocalName() +
                            "] Принял предложение " + bestProposal + " от " + bestProposer.getLocalName());
                    accept.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
                    // Формирование сообщения с использованием jade.content.ContentManager
                    SendOrder sendOrder = new DefaultSendOrder();
                    sendOrder.setOrder(order);
                    accept.setLanguage(getCodec().getName());
                    accept.setOntology(getOntology().getName());
                    try {
                        getContentManager().fillContent(accept, new Action(getAID(), sendOrder));
                    } catch (Codec.CodecException | OntologyException codecException) {
                        codecException.printStackTrace();
                    }
                }
            }

            @Override
            protected void handleInform(ACLMessage inform) {
                System.out.println("[" + getLocalName() +
                        "] Принял отчет от " + inform.getSender().getLocalName());
                doDelete();
            }
        });
    }
}
