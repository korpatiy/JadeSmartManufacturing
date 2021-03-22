package org.manufacture.Agents;

import jade.content.lang.Codec;
import jade.content.onto.OntologyException;
import jade.content.onto.basic.Action;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.domain.AMSService;
import jade.domain.FIPAAgentManagement.AMSAgentDescription;
import jade.domain.FIPAAgentManagement.SearchConstraints;
import jade.domain.FIPAException;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.ContractNetInitiator;
import org.manufacture.API.QueryExecutorService;
import org.manufacture.Ontology.actions.SendOrder;
import org.manufacture.Ontology.actions.SendTask;
import org.manufacture.Ontology.concepts.domain.*;
import org.manufacture.constants.Constants;
import org.manufacture.dbConnection.QueryExecutor;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class AgentDistributor extends AbstractAgent {

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
        try {
            manufacturerAgents = findServices(Constants.PRODUCT_MANUFACTURER_TYPE);
        } catch (FIPAException e) {
            e.printStackTrace();
        }
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
                    accept.setLanguage(codec.getName());
                    accept.setOntology(ontology.getName());
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

    @Deprecated
    private class SendingOrder extends Behaviour {

        private int step = 0;

        @Override
        public void action() {
            switch (step) {
                //посылка сообщения
                case 0:
                    /*try {
                        Thread.sleep(20000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }*/
                    ACLMessage cfp = new ACLMessage(ACLMessage.CFP);
                    managerAgents.forEach(cfp::addReceiver);
                    cfp.setConversationId(Constants.DISTRIBUTOR_MANAGER);
                    cfp.setReplyWith("msg" + System.currentTimeMillis());
                    cfp.setContent("product");

                    //cfp.setReplyByDate(new Date(System.currentTimeMillis() + 10000));
                    myAgent.send(cfp);
                    mt = MessageTemplate.and(MessageTemplate.MatchConversationId(Constants.DISTRIBUTOR_MANAGER),
                            MessageTemplate.MatchInReplyTo(cfp.getReplyWith()));
                    step = 1;
                    System.out.println("[" + getLocalName() +
                            "] Отправка предложения менеджерам");
                    break;
                //получение ответа
                case 1:
                    ACLMessage reply = myAgent.receive(mt);
                    if (reply != null) {
                        if (reply.getPerformative() == ACLMessage.PROPOSE) {
                            manager = reply.getSender();
                            //managers.add(reply.getSender());
                        } else
                            System.out.println("[" + getLocalName() +
                                    "] отказ от менеджера " + reply.getSender().getLocalName());
                        replyCnt++;
                        if (replyCnt >= managerAgents.size())
                            step = 2;
                    } else {
                        block();
                    }
                    break;
                //отправка заказа
                case 2:
                    // ЗДЕСЬ!!!!Проверка на пустой список или отсутвие менеджера вообще
                    ACLMessage order = new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);
                    //managers.forEach(order::addReceiver);
                    order.addReceiver(manager);
                    order.setLanguage(codec.getName());
                    order.setOntology(ontology.getName());
                    order.setConversationId(Constants.DISTRIBUTOR_MANAGER);
                    order.setReplyWith("order" + System.currentTimeMillis());
                    /*
                    OperationJournal x = new OperationJournal();
                    x.setStatus("ok");
                    SendOperationJournal y = new SendOperationJournal();
                    y.setOperationJournal(x);*/
                    Material m = new Material();
                    m.setName("m1");
                    Operation x = new Operation();
                    SendTask y = new SendTask();
                    y.setOperation(x);


                    try {
                        getContentManager().fillContent(order, new Action(getAID(), y));
                    } catch (Codec.CodecException | OntologyException e) {
                        e.printStackTrace();
                    }
                    send(order);
                    mt = MessageTemplate.and(MessageTemplate.MatchConversationId(Constants.DISTRIBUTOR_MANAGER),
                            MessageTemplate.MatchInReplyTo(order.getReplyWith()));
                    step = 3;
                    System.out.println("[" + getLocalName() +
                            "]: Заказ отправлен менеджеру" + manager.getLocalName());

                    break;
                case 3:
                    reply = receive(mt);
                    if (reply != null) {
                        if (reply.getPerformative() == ACLMessage.INFORM) {
                            System.out.println("[" + getLocalName() +
                                    "] Получил отчет от " + reply.getSender().getLocalName());
                            //doDelete();
                        }
                        // step = 4;
                    } else {
                        block();
                    }
                    break;
            }
        }

        @Override
        public boolean done() {
            return step == 4;
        }
    }

    @Deprecated
    private void findManagers() {
        AMSAgentDescription[] agents = null;
        try {
            SearchConstraints c = new SearchConstraints();
            c.setMaxResults(-1L);
            agents = AMSService.search(this, new AMSAgentDescription(), c);
        } catch (Exception e) {
            System.out.println("Problem searching AMS: " + e);
            e.printStackTrace();
        }
        assert agents != null;
        managerAgents = Arrays.stream(agents)
                .map(AMSAgentDescription::getName)
                .filter(name -> name.getLocalName().contains("Manager")).collect(Collectors.toList());
    }
}
