package Agents;

import ManufactureOntology.ManufactureOntology;
import ManufactureOntology.Predicates.HasMaterial;
import jade.content.Concept;
import jade.content.ContentElement;
import jade.content.ContentManager;
import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.content.onto.OntologyException;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class AgentManager extends Agent {

    private String product;
    private ContentManager contentManager = getContentManager();
    private Codec codec = new SLCodec();
    private Ontology ontology = ManufactureOntology.getInstance();

    @Override
    protected void setup() {
        contentManager.registerLanguage(codec);
        contentManager.registerOntology(ontology);
        addBehaviour(new OfferRequests());
    }

    private class OfferRequests extends SimpleBehaviour {

        //Признак завершения агент
        public boolean finished = false;

        @Override
        public boolean done() {
            return finished;
        }

        @Override
        public void action() {
            System.out.println("get message");
            //MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.CFP);
            try {
                ACLMessage msg = blockingReceive();
                if (msg != null)
                    switch (msg.getPerformative()) {
                        case ACLMessage.INFORM:
                            ContentElement p = contentManager.extractContent(msg);
                            if (p instanceof HasMaterial) {
                                HasMaterial hasMaterial = (HasMaterial) p;
                                System.out.println("[" + getLocalName() +
                                        "] Receiver inform message: information stored.");
                                break;
                            }
                    }
            } catch (Exception e) {
                e.printStackTrace();
            }

            finished = true;
            /*String title;
            ACLMessage reply = null;
            if (msg != null) {
                title = msg.getContent();
                reply = msg.createReply();
            } else {
                reply.setPerformative(ACLMessage.REFUSE);
                reply.setContent("123");
            }
            send(reply);*/
        }

    }

    @Override
    protected void takeDown() {
        System.out.println("Manager-Agent " + getAID().getName() + " terminating.");
    }
}
