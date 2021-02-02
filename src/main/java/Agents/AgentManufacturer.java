package Agents;

import API.Constants;
import ManufactureOntology.ManufactureOntology;
import jade.content.ContentManager;
import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.core.Agent;
import jade.core.behaviours.*;
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
        sd.setType(Constants.MANUFACTURER_TYPE);
        sd.setName(getLocalName());
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
        startWorking();
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

        fsmBehaviour.registerFirstState(new WakerBehaviour(this, 1000) {
            @Override
            protected void handleElapsedTimeout() {
                System.out.println("lel");
            }

            @Override
            public int onEnd() {
                return 5;
            }
        }, "L");

        fsmBehaviour.registerState(new SimpleBehaviour(this) {

            int step = 0;

            @Override
            public void action() {
                System.out.println("[" + getLocalName() +
                        "]: Отправляю на проверку...");
                ACLMessage msg = new ACLMessage(ACLMessage.QUERY_IF);
                step++;
            }



            @Override
            public boolean done() {
                return true;
            }

            @Override
            public int onEnd() {
                return (step > 4 ? 1 : 0);
            }
        }, "A");

        fsmBehaviour.registerState(new OneShotBehaviour(this) {

            @Override
            public void action() {
                System.out.println("[" + getLocalName() +
                        "]: Не удалось сделать...");
            }

            @Override
            public int onEnd() {
                return 2;
            }
        }, "C");

        fsmBehaviour.registerLastState(new OneShotBehaviour(this) {
            @Override
            public void action() {
                System.out.println("[" + getLocalName() +
                        "]: Оповещаю менеджера...");
            }
        }, "B");

        fsmBehaviour.registerTransition("L", "A", 5);
        fsmBehaviour.registerTransition("A", "C", 0);
        fsmBehaviour.registerTransition("A", "B", 1);
        fsmBehaviour.registerDefaultTransition("C", "L", new String[]{"L", "C"});
        addBehaviour(fsmBehaviour);
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
