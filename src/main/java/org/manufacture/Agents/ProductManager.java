package org.manufacture.Agents;


import jade.content.Concept;
import jade.content.ContentElement;
import jade.content.lang.Codec;
import jade.content.onto.OntologyException;
import jade.content.onto.basic.Action;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.FSMBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.FIPAException;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import org.manufacture.API.QueryExecutorService;
import org.manufacture.Ontology.actions.SendOperationJournals;
import org.manufacture.Ontology.actions.SendOrder;
import org.manufacture.Ontology.actions.SendTasks;
import org.manufacture.Ontology.actions.actionsImpl.DefaultSendTasks;
import org.manufacture.Ontology.concepts.domain.*;
import org.manufacture.Ontology.concepts.domain.domainImpl.DefaultManufactureJournal;
import org.manufacture.constants.Constants;
import org.manufacture.dbConnection.QueryExecutor;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ProductManager extends ResourceAgent {

    private boolean isWorking = false;
    private String productName;
    private Plan plan;
    private Order order;
    private boolean isDone = false;
    private boolean isFailed = false;
    private ManufactureJournal manufactureJournal = new DefaultManufactureJournal();

    @Override
    protected void setup() {
        super.setup();
        addBehaviour(new GetOfferRequests());
        addBehaviour(new ApplyOffer());
    }

    private class GetOfferRequests extends CyclicBehaviour {

        @Override
        public void action() {
            //только CFP message
            MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.CFP);
            ACLMessage msg = myAgent.receive(mt);
            if (msg != null) {
                System.out.println("[" + getLocalName() +
                        "] Принял CFP сообщение от " + msg.getSender().getLocalName());
                ACLMessage reply = msg.createReply();
                if (isWorking) {
                    reply.setPerformative(ACLMessage.REFUSE);
                    System.out.println("[" + getLocalName() +
                            "] Не готов принять заказ");
                    reply.setContent("No");
                } else {
                    reply.setPerformative(ACLMessage.PROPOSE);
                    System.out.println("[" + getLocalName() +
                            "] Готов принять заказ");
                    reply.setContent("Yes");
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
                processContent(msg);
                manageProduct();
                addBehaviour(new SendInform(msg.createReply()));
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
            if (content != null) {
                Concept action = ((Action) content).getAction();
                if (action instanceof SendOrder) {
                    SendOrder sendOrder = (SendOrder) action;
                    order = sendOrder.getOrder();
                    plan = order.getExecutedByPlan();
                    productName = order.getFormedOnProduct().getName();
                }
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

                send(reply);
                isWorking = false;
                isDone = false;
            }
        }
    }

    private class SendRequest extends Behaviour {
        int step = 0;
        private AID worker;
        MessageTemplate mt;
        boolean isFailed = false;
        private jade.util.leap.List operations;
        //private jade.util.leap.List newOperations;
        private String manufacturerType;
        private int workerId = 0;

        public SendRequest(List<Operation> operations, String manufacturerType) {
            this.operations = castToJadeList(operations);
            this.manufacturerType = manufacturerType;
        }

        //Вынести
        private jade.util.leap.List castToJadeList(List<Operation> operations) {
            jade.util.leap.List jadeList = new jade.util.leap.ArrayList();
            operations.forEach(jadeList::add);
            return jadeList;
        }

        @Override
        public void action() {
            switch (step) {
                case 0:
                    try {
                        worker = findServices(manufacturerType).get(workerId);
                    } catch (FIPAException e) {
                        e.printStackTrace();
                    }
                    System.out.println("[" + getLocalName() +
                            "] отправляю операции");
                    //вынести
                    ACLMessage request = new ACLMessage(ACLMessage.REQUEST);
                    request.addReceiver(worker);
                    request.setConversationId(Constants.MANAGER_MANUFACTURER);
                    request.setReplyWith("request" + System.currentTimeMillis());
                    request.setLanguage(getCodec().getName());
                    request.setOntology(getOntology().getName());
                    request.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);
                    SendTasks sendTasks = new DefaultSendTasks();
                    sendTasks.setHasOperations(operations);

                    try {
                        getContentManager().fillContent(request, new Action(getAID(), sendTasks));
                    } catch (Codec.CodecException | OntologyException codecException) {
                        codecException.printStackTrace();
                    }

                    mt = MessageTemplate.and(MessageTemplate.MatchConversationId(Constants.MANAGER_MANUFACTURER),
                            MessageTemplate.MatchInReplyTo(request.getReplyWith()));
                    send(request);
                    step = 1;
                case 1:
                    ACLMessage firstReply = myAgent.receive(mt);
                    if (firstReply != null) {
                        if (firstReply.getPerformative() == ACLMessage.AGREE) {
                            System.out.println("[" + getLocalName() +
                                    "] ожидаю результатов от " + firstReply.getSender().getLocalName());
                            step = 2;
                        } else {
                            System.out.println("[" + getLocalName() +
                                    "] октаз от " + firstReply.getSender().getLocalName());
                            step = 0;
                            workerId++;
                            System.out.println("[" + getLocalName() +
                                    "] поиск другого исполнителя");
                            this.restart();
                        }
                    } else {
                        block();
                    }
                case 2:
                    ACLMessage secondReply = myAgent.receive(mt);
                    if (secondReply != null) {
                        processContent(secondReply);
                        if (secondReply.getPerformative() == ACLMessage.INFORM) {
                            System.out.println("[" + getLocalName() +
                                    "] принял успешный результат от " + secondReply.getSender().getLocalName());
                        } else if (secondReply.getPerformative() == ACLMessage.FAILURE) {
                            System.out.println("[" + getLocalName() +
                                    "] принял отрицательный результат от " + secondReply.getSender().getLocalName());
                            isFailed = true;
                        }
                        step = 3;
                    } else {
                        block();
                    }
            }
        }

        private void processContent(ACLMessage msg) {
            ContentElement content = null;
            try {
                content = getContentManager().extractContent(msg);
            } catch (Codec.CodecException | OntologyException e) {
                e.printStackTrace();
            }
            if (content != null) {
                Concept action = ((Action) content).getAction();
                if (action instanceof SendOperationJournals) {
                    SendOperationJournals sendOperationJournals = (SendOperationJournals) action;
                    jade.util.leap.List operationJournals = sendOperationJournals.getOperationJournals();
                    for (int i = 0; i < operationJournals.size(); i++) {
                        manufactureJournal.addHasOperationJournals((OperationJournal) operationJournals.get(i));
                    }
                }
            }
        }

        @Override
        public boolean done() {
            return step == 3;
        }

        // разбить на  варианта. Агент может отказаться
        @Override
        public int onEnd() {
            return isFailed ? 4 : 2;
        }

    }

    private void manageProduct() {

        FSMBehaviour fsmB = new FSMBehaviour(this) {
            @Override
            public int onEnd() {
                isDone = true;
                System.out.println("[" + getLocalName() +
                        "] Закончил работу над продуктом:" + productName);
                Date eDate = new Date();
                manufactureJournal.setEndDate(eDate);
                manufactureJournal.setStatus(Constants.STATUS_DONE);
                for (int i = 0; i < manufactureJournal.getHasOperationJournals().size(); i++) {
                    OperationJournal oJ = (OperationJournal) manufactureJournal.getHasOperationJournals().get(i);
                    if (oJ.getStatus().equals(Constants.STATUS_FAIL)) {
                        manufactureJournal.setStatus(Constants.STATUS_FAIL);
                        break;
                    }
                }
                QueryExecutorService QES = QueryExecutor.getQueryExecutor();
                try {
                    QES.insertOJ(manufactureJournal, plan.getId(), order.getId());
                } catch (SQLException throwable) {
                    throwable.printStackTrace();
                }
                return 0;
            }
        };

        fsmB.registerFirstState(new OneShotBehaviour(this) {
            @Override
            public void action() {
                System.out.println("[" + getLocalName() +
                        "] Начинаю производство продукта: " + productName);
                Date date = new Date();
                manufactureJournal.setStartDate(date);
            }

            @Override
            public int onEnd() {
                return 1;
            }
        }, "A");

        fsmB.registerLastState(new OneShotBehaviour(this) {
            @Override
            public void action() {
                System.out.println("[" + getLocalName() +
                        "] Контроль не пройден");
                isFailed = true;
            }

            @Override
            public int onEnd() {
                return 3;
            }
        }, "K");
        //List<String> uniqOperations = List.of("Предварительная сборка", "Общая сборка", "Конечная сборка", "Контроль", "Комлпектовка");

        //todo зафорить
        List<Operation> PAOperations = getOperations("Предварительная сборка");
        fsmB.registerState(new SendRequest(PAOperations, Constants.PA_PRODUCT_MANUFACTURER_TYPE), "B");

        List<Operation> GAOperations = getOperations("Общая сборка");
        fsmB.registerState(new SendRequest(GAOperations, Constants.GA_PRODUCT_MANUFACTURER_TYPE), "C");

        List<Operation> FAOperations = getOperations("Конечная сборка");
        fsmB.registerState(new SendRequest(FAOperations, Constants.FA_PRODUCT_MANUFACTURER_TYPE), "D");

        List<Operation> VOperations = getOperations("Контроль");
        fsmB.registerState(new SendRequest(VOperations, Constants.VERIFIER_TYPE), "E");

        List<Operation> POperations = getOperations("Комлпектовка");
        fsmB.registerLastState(new SendRequest(POperations, Constants.PRODUCT_PACKER_TYPE), "F");

        fsmB.registerTransition("A", "B", 1);
        fsmB.registerTransition("B", "C", 2);
        fsmB.registerTransition("C", "D", 2);
        fsmB.registerTransition("D", "E", 2);
        fsmB.registerTransition("E", "F", 2);

        fsmB.registerTransition("B", "K", 4);

        addBehaviour(fsmB);
    }

    private List<Operation> getOperations(String s) {
        List<Operation> operations = new ArrayList<>();

        for (int i = 0; i < plan.getHasOperations().size(); i++) {
            Operation operation = (Operation) plan.getHasOperations().get(i);
            if (operation.getPerformedOnStation().getName().contains(s))
                operations.add(operation);
        }
        return operations;
    }
}
