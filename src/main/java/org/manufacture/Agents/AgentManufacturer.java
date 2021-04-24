package org.manufacture.Agents;

import jade.content.Concept;
import jade.content.ContentElement;
import jade.content.lang.Codec;
import jade.content.onto.OntologyException;
import jade.content.onto.basic.Action;
import jade.util.leap.List;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import org.manufacture.Ontology.actions.SendOperationJournals;
import org.manufacture.Ontology.actions.SendTasks;
import org.manufacture.Ontology.actions.actionsImpl.DefaultSendOperationJournals;
import org.manufacture.Ontology.concepts.domain.Operation;
import org.manufacture.Ontology.concepts.domain.OperationJournal;
import org.manufacture.Ontology.concepts.domain.ProductionResource;
import org.manufacture.Ontology.concepts.domain.Tool;
import org.manufacture.Ontology.concepts.domain.domainImpl.DefaultOperationJournal;
import org.manufacture.Ontology.concepts.domain.domainImpl.DefaultProductionResource;
import org.manufacture.Ontology.concepts.general.Resource;
import org.manufacture.Ontology.concepts.general.generalmpl.DefaultResource;
import org.manufacture.constants.Constants;

import java.util.Date;

public class AgentManufacturer extends ResourceAgent {

    private boolean isWorking = false;
    private List operations;
    private boolean isDone = false;
    private String station;
    private java.util.List<Tool> toolList;
    private SendOperationJournals sendJournals;
    private boolean isFailed = false;
    int cycle = 0;

    @Override
    protected void setup() {
        super.setup();
        if (getLocalName().equals("PAProductManufacturer"))
            isWorking = true;
        addBehaviour(new GetOfferRequest());
    }

    protected void setFields() {
        Object[] args = getArguments();
        if (args != null && args.length > 0) {
            setId((int) args[0]);
            setType((String) args[1]);
            //this.station = (String) args[1];
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
                    send(reply);
                    processContent(msg);
                    startWorking();
                    addBehaviour(new SendInform(msg.createReply()));
                    addBehaviour(new SendFail(msg.createReply()));
                } else {
                    reply.setPerformative(ACLMessage.REFUSE);
                    System.out.println("[" + getLocalName() +
                            "] отказ");
                    send(reply);
                }
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
                operations = sendTasks.getHasOperations();
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
            if (isDone && !isFailed) {
                reply.setPerformative(ACLMessage.INFORM);
                try {
                    getContentManager().fillContent(reply, new Action(getAID(), sendJournals));
                } catch (Codec.CodecException | OntologyException codecException) {
                    codecException.printStackTrace();
                }
                send(reply);
                isWorking = false;
                isDone = false;
            }
        }
    }

    private class SendFail extends CyclicBehaviour {
        private ACLMessage reply;

        public SendFail(ACLMessage reply) {
            this.reply = reply;
        }

        @Override
        public void action() {
            if (isFailed && isDone) {
                reply.setPerformative(ACLMessage.FAILURE);
                try {
                    getContentManager().fillContent(reply, new Action(getAID(), sendJournals));
                } catch (Codec.CodecException | OntologyException codecException) {
                    codecException.printStackTrace();
                }
                send(reply);
                isWorking = false;
                isFailed = false;
                isDone = false;
            }
        }
    }

    private void startWorking() {

        sendJournals = new DefaultSendOperationJournals();
        isWorking = true;
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
            Date startDate = new Date();
            seqBehaviour.addSubBehaviour(new WakerBehaviour(this, operation.getDuration() - 2000) {

                @Override
                protected void onWake() {
                    System.out.println("[" + getLocalName() +
                            "] принял положение: " + operation.getRequiresSetup().getName() + ", инструмент: " + operation.getRequiresSetup().getRequiresTool().getName());

                    String baseOp = "[" + getLocalName() +
                            "] выполняется " + operation.getName() + " " + operation.getPerformedOverMaterial().getName();
                    if (operation.getHasFunction() != null)
                        baseOp += " " + operation.getHasFunction().getName() + " " + operation.getHasFunction().getPerformedOverMaterial().getName();
                    if (cycle == 0) {
                        if (operation.getName().equals("Установка") && operation.getPerformedOverMaterial().getName().equals("Барабан")) {
                            isFailed = true;
                            //baseOp += " ОШИБКА";
                            //createJournal(Constants.STATUS_FAIL);
                            cycle++;
                        }
                    }
                    if (isFailed) {
                        baseOp += " ОШИБКА";
                        createJournal(Constants.STATUS_FAIL);
                    } else
                        createJournal(Constants.STATUS_DONE);
                    //createJournal(Constants.STATUS_DONE);

                    System.out.println(baseOp);
                }

                private void createJournal(String fail) {
                    Date endDate = new Date();
                    ProductionResource resource = new DefaultProductionResource();
                    resource.setName(getLocalName());
                    resource.setType(getType());
                    resource.setId(getId());
                    OperationJournal operationJournal = new DefaultOperationJournal();
                    operationJournal.setDescribesOperation(operation);
                    operationJournal.setStatus(fail);
                    operationJournal.setStartDate(startDate);
                    operationJournal.setEndDate(endDate);
                    operationJournal.setHasResource(resource);
                    sendJournals.addOperationJournals(operationJournal);
                }
            });
        }
        addBehaviour(seqBehaviour);
    }
}
