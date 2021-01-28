package Agents;

import API.Constants;
import ManufactureOntology.Concepts.Material;
import ManufactureOntology.Concepts.Product;
import ManufactureOntology.ManufactureOntology;
import ManufactureOntology.Predicates.HasMaterial;
import jade.content.ContentManager;
import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.content.onto.OntologyException;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.domain.AMSService;
import jade.domain.FIPAAgentManagement.AMSAgentDescription;
import jade.domain.FIPAAgentManagement.SearchConstraints;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.util.leap.ArrayList;
import jade.util.leap.List;

import java.util.Arrays;
import java.util.stream.Collectors;

public class AgentDistributor extends Agent {

    private String product;
    private ContentManager contentManager = getContentManager();
    private Codec codec = new SLCodec();
    private Ontology ontology = ManufactureOntology.getInstance();
    private java.util.List<AMSAgentDescription> managerAgents = null;
    private MessageTemplate mt;
    private AID manager;
    private int replyCnt = 0;

    @Override
    protected void setup() {
        System.out.println("Distributor-Agent " + getAID().getName() + " is ready.");
        Object[] args = getArguments();

       /* if (args != null && args.length > 0) {
            product = (String) args[0];
        } else {
            System.out.println("No product title specified");
            doDelete();
        }*/

        findManagers();
        this.contentManager.registerLanguage(codec);
        this.contentManager.registerOntology(ontology);
        addBehaviour(new sendStartMessage());
    }

    private class sendStartMessage extends Behaviour {

        private int step = 0;

        @Override
        public void action() {
            switch (step) {
                //посылка сообщения
                case 0:
                    ACLMessage cfp = new ACLMessage(ACLMessage.CFP);
                    managerAgents.forEach(agent -> cfp.addReceiver(agent.getName()));
                    cfp.setConversationId("distributor-manager");
                    cfp.setReplyWith("msg" + System.currentTimeMillis());
                    cfp.setContent("ClassicTable");
                    myAgent.send(cfp);
                    //System.out.println("Message send to Manager");
                    mt = MessageTemplate.and(MessageTemplate.MatchConversationId("distributor-manager"),
                            MessageTemplate.MatchInReplyTo(cfp.getReplyWith()));
                    step = 1;
                    System.out.println("[" + getLocalName() +
                            "] Отправка предложения менеджерам");
                    break;
                //получение ответа
                case 1:
                    ACLMessage reply = myAgent.receive(mt);
                    if (reply != null) {
                        if (reply.getPerformative() == ACLMessage.PROPOSE)
                            manager = reply.getSender();
                        else
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
                    ACLMessage order = new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);

                    order.addReceiver(manager);
                    order.setLanguage(codec.getName());
                    order.setOntology(ontology.getName());
                    order.setConversationId(Constants.CONVERSATION_ID);
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
                    mt = MessageTemplate.and(MessageTemplate.MatchConversationId(Constants.CONVERSATION_ID),
                            MessageTemplate.MatchInReplyTo(order.getReplyWith()));
                    step = 3;
                    System.out.println("[" + getLocalName() +
                            "]: Заказ отправлен менеджеру");
                    break;
                case 3:
                    reply = receive(mt);
                    if (reply != null) {
                        if (reply.getPerformative() == ACLMessage.INFORM) {
                            System.out.println("[" + getLocalName() +
                                    "] Заказ принят менеджером " + reply.getSender().getLocalName());
                            doDelete();
                        }
                        step = 4;
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
            c.setMaxResults(new Long(-1));
            agents = AMSService.search(this, new AMSAgentDescription(), c);
        } catch (Exception e) {
            System.out.println("Problem searching AMS: " + e);
            e.printStackTrace();
        }
        managerAgents = Arrays.stream(agents)
                .filter(agent -> agent.getName().getLocalName().contains("Manager"))
                .collect(Collectors.toList());
    }

    @Override
    protected void takeDown() {
        System.out.println("Distributor-Agent " + getAID().getName() + " terminating.");
    }
}
