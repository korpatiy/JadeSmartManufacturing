package org.manufacture.Agents;

import jade.content.Concept;
import jade.content.ContentElement;
import jade.content.lang.Codec;
import jade.content.onto.OntologyException;
import jade.content.onto.basic.Action;
import jade.util.leap.List;
import org.manufacture.Ontology.actions.SendTasks;
import org.manufacture.Ontology.concepts.domain.*;
import org.manufacture.constants.Constants;
import jade.core.behaviours.*;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.Date;

public class AgentManufacturer extends ResourceAgent {

    private boolean isWorking = false;
    private List operations;
    private boolean isDone = false;
    private String station;
    private java.util.List<Tool> toolList;

    @Override
    protected void setup() {
        super.setup();
        addBehaviour(new GetOfferRequest());
    }

    protected void setFields() {
        Object[] args = getArguments();
        if (args != null && args.length > 0) {
            setType((String) args[0]);
            this.station = (String) args[1];
        } else {
            System.out.println("No arguments");
            doDelete();
        }
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
                    System.out.println("[" + getLocalName() +
                            "] принял операции");
                    processContent(msg);
                    isWorking = true;
                    startWorking();
                    addBehaviour(new SendInform(msg.createReply()));
                } else {
                    reply.setPerformative(ACLMessage.REFUSE);
                    System.out.println("[" + getLocalName() +
                            "] отказ");
                }
                send(reply);
            } else {
                block();
            }
        }

        private void processContent(ACLMessage msg) {
            ContentElement content = null;
            try {
                content = getContentManager().extractContent(msg);
            } catch (Codec.CodecException | OntologyException e) {
                e.printStackTrace();
            }
            assert content != null;
            Concept action = ((Action) content).getAction();
            if (action instanceof SendTasks) {
                SendTasks sendTasks = (SendTasks) action;
                operations = sendTasks.getOperations();
            }
        }
    }

    private class SendInform extends CyclicBehaviour {

        private ACLMessage reply;

        public SendInform(ACLMessage reply) {
            this.reply = reply;
        }

        @Override
        public void action() {
            if (isDone) {
                reply.setPerformative(ACLMessage.INFORM);
                // отчет

                send(reply);
                isWorking = false;
                isDone = false;
            }
        }
    }

    private void startWorking() {

        SequentialBehaviour seqBehaviour = new SequentialBehaviour(this) {
            public int onEnd() {
                isDone = true;
                System.out.println("[" + getLocalName() + "] Закончил работу над операциями");
                return 0;
            }
        };

        for (int i = 0; i < operations.size(); i++) {
            Operation operation = (Operation) operations.get(i);
            //Положение Setup
            Date startDateO = new Date();
            seqBehaviour.addSubBehaviour(new WakerBehaviour(this, Long.parseLong(operation.getDuration())) {
                @Override
                protected void onWake() {
                    System.out.println("[" + getLocalName() +
                            "] выполняется " + operation.getName() + " " + operation.getMaterial().getName());
                    //createJournal();
                }

               /* private void createJournal() {
                    Date endDateO = new Date();
                    Resource resource = new Resource();
                    resource.setName(getName());
                    resource.setType(getType());
                    OperationJournal operationJournal = new OperationJournal();
                    operationJournal.setOperation(operation);
                    operationJournal.setStatus(Constants.STATUS_DONE);
                    operationJournal.setStartDate(startDateO);
                    operationJournal.setEndDate(endDateO);
                    operationJournal.setResource(resource);
                    stationJournal.addOperationJournals(operationJournal);
                    //need
                    //operationJournal.setFailures();
                }*/
            });
        }
        addBehaviour(seqBehaviour);
    }


}
