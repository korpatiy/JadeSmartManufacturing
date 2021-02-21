package Agents;

import API.Constants;
import ManufactureOntology.Concepts.Material;
import ManufactureOntology.Concepts.Product;
import ManufactureOntology.Predicates.HasMaterial;
import jade.content.lang.Codec;
import jade.content.onto.OntologyException;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.core.behaviours.WakerBehaviour;
import jade.domain.AMSService;
import jade.domain.FIPAAgentManagement.AMSAgentDescription;
import jade.domain.FIPAAgentManagement.SearchConstraints;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.ContractNetInitiator;
import jade.util.leap.ArrayList;
import jade.util.leap.List;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.Vector;
import java.util.stream.Collectors;

public class AgentDistributor extends AbstractAgent {

    private String product;
    private java.util.List<AID> managerAgents = null;
    private MessageTemplate mt;
    private AID manager;
    private int replyCnt = 0;
    private java.util.List<AID> managers = new java.util.ArrayList<>();

    @Override
    protected void setup() {
        System.out.println("Distributor-Agent " + getAID().getName() + " is ready.");
        Object[] args = getArguments();

        if (args != null && args.length > 0) {
            product = (String) args[0];
        } else {
            System.out.println("No product title specified");
            doDelete();
        }

        contentManager.registerLanguage(codec);
        contentManager.registerOntology(ontology);

        //addBehaviour(new sendStartMessage());
        findManagers();
        ACLMessage cfp = new ACLMessage(ACLMessage.CFP);
        managerAgents.forEach(cfp::addReceiver);
        cfp.setConversationId(Constants.DISTRIBUTOR_MANAGER);
        cfp.setReplyWith("msg" + System.currentTimeMillis());
        cfp.setContent(product);
        addBehaviour(new TickerBehaviour(this, 7000) {
            @Override
            protected void onTick() {
                addBehaviour(new ContractNetInitiator(myAgent, cfp) {

                    private AID manager;

                    @Override
                    protected void handlePropose(ACLMessage propose, Vector acceptances) {
                        System.out.println("get propose from " + propose.getSender().getLocalName());
                        manager = propose.getSender();
                    }

                    @Override
                    protected void handleRefuse(ACLMessage refuse) {
                        System.out.println("refuse from" + refuse.getSender().getLocalName());
                    }

                    @Override
                    protected void handleAllResponses(Vector responses, Vector acceptances) {
                        //ACLMessage reply = msg.createReply();
                        Enumeration e = responses.elements();

                        int iter = 0;
                        while (e.hasMoreElements()) {
                            ACLMessage msg = (ACLMessage) e.nextElement();
                            ACLMessage reply = msg.createReply();
                            if (msg.getPerformative() == ACLMessage.PROPOSE) {
                                reply.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
                                reply.setLanguage(codec.getName());
                                reply.setOntology(ontology.getName());
                                Product table = new Product();
                                Material wood = new Material();
                                Material wood1 = new Material();
                                table.setId(1);
                                table.setName("Classic table");
                                wood.setId(1);
                                wood.setName("Wood");
                                wood1.setId(2);
                                wood1.setName("Wood2");

                                HasMaterial hasMaterial = new HasMaterial();
                                hasMaterial.setProduct(table);
                                List materials = new ArrayList();
                                materials.add(wood);
                                materials.add(wood1);
                                hasMaterial.setMaterials(materials);

                                try {
                                    contentManager.fillContent(reply, hasMaterial);
                                } catch (Codec.CodecException | OntologyException exception) {
                                    exception.printStackTrace();
                                }

                                System.out.println("высылаю проопасл" + msg.getSender().getLocalName());
                                acceptances.addElement(reply);
                            } else {
                                reply.setPerformative(ACLMessage.REJECT_PROPOSAL);
                                System.out.println("реджект" + msg.getSender().getLocalName());
                                acceptances.addElement(reply);
                            }
                        }
                    }

                    @Override
                    protected void handleInform(ACLMessage inform) {
                        System.out.println("отчет от " + inform.getSender().getLocalName());
                    }
                });
            }
        });

    }

    private class sendStartMessage extends Behaviour {

        private int step = 0;

        @Override
        public void action() {
            switch (step) {
                //посылка сообщения
                case 0:
                   /* try {
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

                    Product table = new Product();
                    Material wood = new Material();
                    Material wood1 = new Material();
                    table.setId(1);
                    table.setName("Classic table");
                    wood.setId(1);
                    wood.setName("Wood");
                    wood1.setId(2);
                    wood1.setName("Wood2");

                    HasMaterial hasMaterial = new HasMaterial();
                    hasMaterial.setProduct(table);
                    List materials = new ArrayList();
                    materials.add(wood);
                    materials.add(wood1);
                    hasMaterial.setMaterials(materials);

                    try {
                        contentManager.fillContent(order, hasMaterial);
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
