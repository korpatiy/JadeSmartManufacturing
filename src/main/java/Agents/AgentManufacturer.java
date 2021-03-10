package Agents;

import API.Constants;
import jade.core.behaviours.*;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class AgentManufacturer extends AbstractAgent {

    private boolean isWorking = false;

    @Override
    protected void setup() {
        super.setup();
        dfd.setName(getAID());
        sd.setType(Constants.MANUFACTURER_TYPE);
        sd.setName(getLocalName());
        dfd.addServices(sd);
        try {
            DFService.register(this, dfd);
        } catch (FIPAException e) {
            e.printStackTrace();
        }
        //startWorking();
        addBehaviour(new GetOfferRequest());
    }


    private class GetOfferRequest extends CyclicBehaviour {

        @Override
        public void action() {
            MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
            ACLMessage msg = myAgent.receive(mt);
            if (msg != null) {
                ACLMessage reply = msg.createReply();
                if (!isWorking) {
                    reply.setPerformative(ACLMessage.AGREE);
                    reply.setContent("ok");
                    //send(reply);
                    System.out.println("[" + getLocalName() +
                            "] принял " + msg.getContent());
                } else {
                    reply.setPerformative(ACLMessage.REFUSE);
                    System.out.println("[" + getLocalName() +
                            "] отказ");
                    //send(reply);
                }
                send(reply);
                isWorking = true;
                addBehaviour(new WakerBehaviour(myAgent, 10000) {
                    @Override
                    protected void onWake() {
                        isWorking = false;
                        ACLMessage reply = msg.createReply();
                        reply.setPerformative(ACLMessage.INFORM);
                        reply.setContent("ok");
                        send(reply);
                    }
                });
            } else {
                block();
            }
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
