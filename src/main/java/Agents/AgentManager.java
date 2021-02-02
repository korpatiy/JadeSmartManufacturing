package Agents;

import API.Constants;
import ManufactureOntology.ManufactureOntology;
import ManufactureOntology.Predicates.HasMaterial;
import jade.content.ContentElement;
import jade.content.ContentManager;
import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.content.onto.OntologyException;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.*;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
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
    private AID[] manufacturerAgents;

    @Override
    protected void setup() {

        Object[] args = getArguments();

        if (args != null && args.length > 0) {
            product = (String) args[0];
        } else {
            System.out.println("No product title specified");
            doDelete();
        }

        System.out.println("Manager-Agent " + getAID().getName() + " is ready.");
        contentManager.registerLanguage(codec);
        contentManager.registerOntology(ontology);

        addBehaviour(new OfferRequests());
        addBehaviour(new ApplyOffer());
        //working();
    }

    private void findServices(Agent myAgent) {
        DFAgentDescription template = new DFAgentDescription();
        ServiceDescription sd = new ServiceDescription();
        sd.setType(Constants.MANUFACTURER_TYPE);
        //ServiceDescription sd1 = new ServiceDescription();
        //sd1.setType("manager-collector");

        template.addServices(sd);
        try {
            DFAgentDescription[] result = DFService.search(myAgent, template);
            manufacturerAgents = new AID[result.length];
            for (int i = 0; i < result.length; i++) {
                manufacturerAgents[i] = result[i].getName();
            }
        } catch (FIPAException e) {
            e.printStackTrace();
        }

    }

    private class OfferRequests extends CyclicBehaviour {

        @Override
        public void action() {
            //только CFP message
            MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.CFP);
            ACLMessage msg = myAgent.receive(mt);
            if (msg != null) {
                System.out.println("[" + getLocalName() +
                        "] Принял CFP сообщение от дистрибютора");
                ACLMessage reply = msg.createReply();
                if (!product.equals(msg.getContent())) {
                    reply.setPerformative(ACLMessage.REFUSE);
                    System.out.println("[" + getLocalName() +
                            "] Не готов принять заказ");
                    reply.setContent("Не найден продукт");
                } else {
                    reply.setPerformative(ACLMessage.PROPOSE);
                    System.out.println("[" + getLocalName() +
                            "] Готов Принять заказ");
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
            ACLMessage msg = myAgent.receive(mt);
            if (msg != null) {
                System.out.println("[" + getLocalName() +
                        "] Принял ACCEPT сообщение от дистрб");
                ContentElement p = null;
                try {
                    p = contentManager.extractContent(msg);
                } catch (Codec.CodecException | OntologyException e) {
                    e.printStackTrace();
                }
                HasMaterial hasMaterial = null;
                if (p instanceof HasMaterial) {
                    hasMaterial = (HasMaterial) p;
                }
                ACLMessage reply = msg.createReply();
                reply.setPerformative(ACLMessage.INFORM);

                send(reply);
                findServices(myAgent);
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
