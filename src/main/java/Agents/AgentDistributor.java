package Agents;

import API.Constants;
import Ontology.actions.SendOperationJournal;
import Ontology.actions.SendTask;
import Ontology.concepts.domain.*;
import jade.content.lang.Codec;
import jade.content.onto.OntologyException;
import jade.content.onto.basic.Action;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.domain.AMSService;
import jade.domain.FIPAAgentManagement.AMSAgentDescription;
import jade.domain.FIPAAgentManagement.SearchConstraints;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.Arrays;
import java.util.stream.Collectors;

public class AgentDistributor extends AbstractAgent {

    private String product;
    private java.util.List<AID> managerAgents = null;
    private MessageTemplate mt;
    private AID manager;
    private int replyCnt = 0;

    public String getAgentName() {
        return getLocalName();
    }

    @Override
    protected void setup() {
        super.setup();
        Object[] args = getArguments();

        if (args != null && args.length > 0) {
            product = (String) args[0];
        } else {
            System.out.println("No product title specified");
            doDelete();
        }
        addBehaviour(new sendStartMessage());
    }

    private class sendStartMessage extends Behaviour {

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
                    findManagers();
                    ACLMessage cfp = new ACLMessage(ACLMessage.CFP);
                    managerAgents.forEach(cfp::addReceiver);
                    cfp.setConversationId(Constants.DISTRIBUTOR_MANAGER);
                    cfp.setReplyWith("msg" + System.currentTimeMillis());
                    cfp.setContent(product);

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
                    x.setName("oper1");
                    x.addMaterials(m);
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

    @Override
    protected void takeDown() {
        System.out.println("Distributor-Agent " + getAID().getName() + " terminating.");
    }
}
