package Agents;

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
        this.contentManager.registerLanguage(codec);
        this.contentManager.registerOntology(ontology);

        addBehaviour(new OfferRequests());
        addBehaviour(new ApplyOffer());
        //working();
    }

    private void findServices(Agent myAgent) {
        DFAgentDescription template = new DFAgentDescription();
        ServiceDescription sd = new ServiceDescription();
        sd.setType("manager-manufacturer");
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
                //working();
                //working(myAgent);
                //addBehaviour(new test());
            } else {
                block();
            }
            // working();
        }
    }

    private void startManage() {
        FSMBehaviour fsmBehaviour = new FSMBehaviour(this) {
            @Override
            public int onEnd() {
                System.out.println("[" + getLocalName() +
                        "]: завершил работу над продуктом...");
                //переработать завершение -> согласовать с выше стоящими поведениями
                return 0;
            }
        };

        fsmBehaviour.registerFirstState(new OneShotBehaviour() {
            @Override
            public void action() {
                System.out.println("[" + getLocalName() +
                        "]: Приступил к работе над продуктом...");
            }
        }, "A");

        //распределение
        fsmBehaviour.registerState(new Behaviour() {

            int step = 0;
            int dcount = 4;

            @Override
            public void action() {
                switch (step) {
                    case 0:
                        ACLMessage cfp = new ACLMessage(ACLMessage.CFP);
                        for (int i = 0; i < manufacturerAgents.length; i++) {
                            cfp.addReceiver(manufacturerAgents[i]);
                        }
                        cfp.setConversationId("manager-manufacturer");
                        cfp.setReplyWith("msg" + System.currentTimeMillis());
                        send(cfp);
                        step = 1;
                        break;
                    case 1:

                        break;
                }
            }

            @Override
            public boolean done() {
                return step == 4;
            }
        }, "B");



    }

    private void working() {

        FSMBehaviour compFSM = new FSMBehaviour(this) {
            @Override
            public int onEnd() {
                System.out.println("FSM Behavior completed successfully!");
                return 0;
            }
        };

        compFSM.registerFirstState(new OneShotBehaviour(this) {
            int c = 0;

            @Override
            public void action() {
                System.out.println("complement all the X Behavior");
                c++;
            }

            @Override
            public int onEnd() {
                return (c > 4 ? 1 : 0);
            }
        }, "X");

        compFSM.registerState(new OneShotBehaviour(this) {

            public void action() {
                System.out.println("complement all of Z Behavior");
            }

            public int onEnd() {
                return 2;
            }
        }, "Z");
        compFSM.registerLastState(new OneShotBehaviour(this) {

            public void action() {
                System.out.println("Running my last behavior.");
            }
        }, "Y");
        compFSM.registerTransition("X", "Z", 0);
        compFSM.registerTransition("X", "Y", 1);
        compFSM.registerDefaultTransition("Z", "X", new String[]{"X", "Z"});
        addBehaviour(compFSM);
    }

    /*private class ManageProduct extends SequentialBehaviour {

        private int step = 0;

        @Override
        public void action() {
            findServices(myAgent);
            System.out.println("[" + getLocalName() + "] Приступил к выполнению");
            switch (step) {
                case 0:


                    step = 1;
                    break;

            }
        }

        @Override
        public boolean done() {
            return step == 2;
        }
    }*/

    @Override
    protected void takeDown() {
        System.out.println("Manager-Agent " + getAID().getName() + " terminating.");
    }
}
