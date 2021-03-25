package org.manufacture.Agents;

import jade.content.lang.Codec;
import jade.content.onto.OntologyException;
import jade.content.onto.basic.Action;
import jade.core.AID;
import jade.domain.FIPAException;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.ContractNetInitiator;
import org.manufacture.Ontology.actions.SendOrder;
import org.manufacture.Ontology.concepts.domain.*;
import org.manufacture.constants.Constants;

import java.sql.SQLException;
import java.util.*;

public class AgentDistributor extends ResourceAgent {

    private java.util.List<AID> managerAgents;
    private List<AID> manufacturerAgents;
    private MessageTemplate mt;
    private AID manager;
    private int replyCnt = 0;
    private Order order;

    @Override
    protected void setup() {
        super.setup();
        try {
            createOrder();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        try {
            managerAgents = findServices(Constants.PRODUCT_MANAGER_TYPE);
        } catch (FIPAException e) {
            e.printStackTrace();
        }
        /*try {
            Thread.sleep(40000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        orderHandler();
        //addBehaviour(new SendingOrder());
    }

    private void createOrder() throws SQLException {
        Object[] arguments = getArguments();
        String productName = (String) arguments[0];
        String planName = (String) arguments[1];
        String dueDate = (String) arguments[2];
        order = new Order();
        //QueryExecutorService queryExecutor = QueryExecutor.getQueryExecutor();
        //Product product = queryExecutor.seekEntity("product", productName, Product.class);
        //Plan plan = queryExecutor.seekEntity("plan", planName, Plan.class);
        order.setName("Order1");
        Product product = new Product();
        product.setName("Машина стиральная стандартная");
        order.setProduct(product);
        Plan plan = new Plan();
        plan.setName("Стандартный");
        order.setPlan(plan);
        order.setDueDate(dueDate);
    }

    private void orderHandler() {
        ACLMessage msg = new ACLMessage(ACLMessage.CFP);
        //Время, к которому ответ должен быть получен
        //msg.setReplyByDate(new Date((System.currentTimeMillis() + 1000)));
        managerAgents.forEach(msg::addReceiver);
        msg.setConversationId(Constants.DISTRIBUTOR_MANAGER);
        msg.setProtocol(FIPANames.InteractionProtocol.FIPA_CONTRACT_NET);
        msg.setReplyWith("msg" + System.currentTimeMillis());
        msg.setContent("Машинка стиральная");
        addBehaviour(new ContractNetInitiator(this, msg) {

            @Override
            protected void handlePropose(ACLMessage propose, Vector acceptances) {
                System.out.println("Get propose from " + propose.getSender().getLocalName());
            }

            @Override
            protected void handleRefuse(ACLMessage refuse) {
                System.out.println("Get refuse from " + refuse.getSender().getLocalName());
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
                    System.out.println("Accepting proposal " + bestProposal + " from responder " + bestProposer.getLocalName());
                    accept.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
                    SendOrder sendOrder = new SendOrder();
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
                System.out.println("Принял отчет от " + inform.getSender().getLocalName());
                doDelete();
            }
        });
    }
}
