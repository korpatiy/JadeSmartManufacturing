package org.manufacture.Agents;

import org.manufacture.constants.Constants;
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

        FSMBehaviour fsmB = new FSMBehaviour(this) {
            @Override
            public int onEnd() {
                System.out.println("Закончил работу над материалом");
                return 0;
            }
        };


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
