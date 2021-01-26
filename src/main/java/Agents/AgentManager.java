package Agents;

import ManufactureOntology.ManufactureOntology;
import ManufactureOntology.Predicates.HasMaterial;
import jade.content.ContentElement;
import jade.content.ContentManager;
import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.content.onto.OntologyException;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.util.leap.ArrayList;
import jade.util.leap.List;

public class AgentManager extends Agent {

    private String product;
    private ContentManager contentManager = getContentManager();
    private Codec codec = new SLCodec();
    private Ontology ontology = ManufactureOntology.getInstance();
    private List orders = new ArrayList();

    @Override
    protected void setup() {
        System.out.println("Manager-Agent " + getAID().getName() + " is ready.");
        contentManager.registerLanguage(codec);
        contentManager.registerOntology(ontology);
        addBehaviour(new OfferRequests());
        addBehaviour(new ApplyOffer());
    }

    private class OfferRequests extends CyclicBehaviour {

        @Override
        public void action() {
            //только CFP message
            MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.CFP);
            ACLMessage msg = myAgent.receive(mt);
            if (msg != null) {
                System.out.println("Manager-Agent: Принял CFP сообщение от дистрб");
                ACLMessage reply = msg.createReply();
                if (orders.size() > 3) {
                    reply.setPerformative(ACLMessage.REFUSE);
                    reply.setContent("Стек агента менеджера переполнен");
                } else {
                    reply.setPerformative(ACLMessage.PROPOSE);
                    System.out.println("Manager-Agent: Propose");
                }
                send(reply);
            } else {
                block();
            }
        }
    }

    private class ApplyOffer extends CyclicBehaviour {

        @Override
        public void action() {
            //Только ACCEPT message
            MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL);
            ACLMessage msg = receive(mt);
            if (msg != null) {
                System.out.println("Manager-Agent: Принял ACCEPT сообщение от дистрб");
                ContentElement p = null;
                try {
                    p = contentManager.extractContent(msg);
                } catch (Codec.CodecException | OntologyException e) {
                    e.printStackTrace();
                }
                if (p instanceof HasMaterial) {
                    HasMaterial hasMaterial = (HasMaterial) p;
                    System.out.println("[" + getLocalName() +
                            "] Receiver inform message: information stored.");
                }
            } else {
                block();
            }
        }
    }

    @Override
    protected void takeDown() {
        System.out.println("Manager-Agent " + getAID().getName() + " terminating.");
    }
}
