package Agents;

import ManufactureOntology.ManufactureOntology;
import jade.content.ContentManager;
import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.FSMBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.SimpleBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class AgentManufacturer extends Agent {

    private ContentManager contentManager = getContentManager();
    private Codec codec = new SLCodec();
    private Ontology ontology = ManufactureOntology.getInstance();
    private boolean isWorking = false;

    @Override
    protected void setup() {
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType("manager-manufacturer");
        sd.setName("Product-manufacturer");
        //sd.addOntologies(ManufactureOntology.getInstance().getName());
        dfd.addServices(sd);
        //dfd.addOntologies(ManufactureOntology.getInstance().getName());
        this.contentManager.registerOntology(ontology);
        this.contentManager.registerLanguage(codec);
        try {
            DFService.register(this, dfd);
        } catch (FIPAException e) {
            e.printStackTrace();
        }
    }

    private void startWorking() {

        System.out.println("[" + getLocalName() +
                "]: начал производство детали...");

        FSMBehaviour fsmBehaviour = new FSMBehaviour(this) {
            @Override
            public int onEnd() {
                System.out.println("FSM Behavior completed successfully!");
                return 0;
            }
        };

        fsmBehaviour.registerFirstState(new OneShotBehaviour() {
            @Override
            public void action() {
                System.out.println("[" + getLocalName() +
                        "]: Получил ресурсы...");
            }
        }, "A");

        /*fsmBehaviour.registerFirstState(new OneShotBehaviour() {
            @Override
            public void action() {
                System.out.println("[" + getLocalName() +
                        "]: Начал изготовку...");
            }
        }, "B");

        fsmBehaviour.registerFirstState(new OneShotBehaviour() {
            @Override
            public void action() {
                System.out.println("[" + getLocalName() +
                        "]: Сделал обработку...");
            }
        }, "С");*/

        fsmBehaviour.registerState(new SimpleBehaviour() {
            @Override
            public void action() {
                System.out.println("[" + getLocalName() +
                        "]: Отправляю на проверку...");
                ACLMessage msg = new ACLMessage(ACLMessage.QUERY_IF);
            }

            @Override
            public boolean done() {
                return false;
            }
        }, "B");
    }

    private class Working extends CyclicBehaviour {

        @Override
        public void action() {
            MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.CFP);
            ACLMessage msg = myAgent.receive(mt);
            if (msg != null) {
                ACLMessage reply = msg.createReply();
                ACLMessage t = new ACLMessage(ACLMessage.SUBSCRIBE);
            } else {
                block();
            }
        }
    }

    @Override
    protected void takeDown() {
        try {
            DFService.deregister(this);
        } catch (FIPAException e) {
            e.printStackTrace();
        }
        System.out.println(getAID().getName() + " terminating.");
    }
}
