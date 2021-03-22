package org.manufacture.Agents;

import jade.core.behaviours.*;
import jade.domain.FIPAException;
import org.manufacture.API.QueryExecutorService;
import org.manufacture.Ontology.actions.SendOrder;
import org.manufacture.Ontology.actions.SendTasks;
import org.manufacture.Ontology.concepts.domain.Operation;
import org.manufacture.Ontology.concepts.domain.Station;
import org.manufacture.constants.Constants;
import jade.content.Concept;
import jade.content.ContentElement;
import jade.content.lang.Codec;
import jade.content.onto.OntologyException;
import jade.content.onto.basic.Action;
import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import org.manufacture.dbConnection.QueryExecutor;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProductManager extends AbstractAgent {

    private Map<Station, Operation> stationOperationMap;
    private boolean isWorking = false;

    @Override
    protected void setup() {
        super.setup();
        addBehaviour(new GetOfferRequests());
        addBehaviour(new ApplyOffer());
    }

    private void getStationOperation(String planName) {
        QueryExecutorService queryExecutor = QueryExecutor.getQueryExecutor();
        try {
            stationOperationMap = queryExecutor.seekPlan(planName);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    private class GetOfferRequests extends CyclicBehaviour {

        @Override
        public void action() {
            //только CFP message
            MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.CFP);
            ACLMessage msg = myAgent.receive(mt);
            if (msg != null) {
                System.out.println("[" + getLocalName() +
                        "] Принял CFP сообщение от дистрибютора");
                ACLMessage reply = msg.createReply();
                if (isWorking) {
                    reply.setPerformative(ACLMessage.REFUSE);
                    System.out.println("[" + getLocalName() +
                            "] Не готов принять заказ");
                    reply.setContent("No");
                } else {
                    reply.setPerformative(ACLMessage.PROPOSE);
                    System.out.println("[" + getLocalName() +
                            "] Готов Принять заказ");
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
                ContentElement content = null;
                try {
                    content = getContentManager().extractContent(msg);
                } catch (Codec.CodecException | OntologyException e) {
                    e.printStackTrace();
                }
                assert content != null;
                Concept action = ((Action) content).getAction();
                String planName = null;
                String productName = null;
                if (action instanceof SendOrder) {
                    SendOrder sendOrder = (SendOrder) action;
                    planName = sendOrder.getOrder().getPlan().getName();
                    productName = sendOrder.getOrder().getProduct().getName();
                }
                //закостылено. Поиск не по плану
                getStationOperation(planName);

                //РЕАЛИЗАЦИЯ ОСНОВГО РАБОТЫ МЕНЕДЕЖДРА.
                //ФОРМИРОВАНИЕ ОТЧЕТА ДИСТРИБЬЮТОРУ
                manageProduct();
                //addBehaviour(new Ti);
                
                ACLMessage reply = msg.createReply();
                reply.setPerformative(ACLMessage.INFORM);
                send(reply);
                //doDelete();
            } else {
                block();
            }
        }
    }

    private class SendRequest extends Behaviour {
        int step = 0;
        private AID worker;
        MessageTemplate mt;
        boolean isAccept = false;
        private List<Operation> operations;
        private String manufacturerType;

        public SendRequest(List<Operation> operations, String manufacturerType) {
            this.operations = operations;
            this.manufacturerType = manufacturerType;
        }

       /* private jade.util.leap.List castToJadeList() {
            jade.util.leap.List operation = new jade.util.leap.ArrayList();

        }*/

        @Override
        public void action() {
            switch (step) {
                case 0 -> {
                    try {
                        worker = findServices(manufacturerType).get(0);
                    } catch (FIPAException e) {
                        e.printStackTrace();
                    }
                    System.out.println("[" + getLocalName() +
                            "] отправляю операции");
                    ACLMessage request = new ACLMessage(ACLMessage.REQUEST);
                    request.addReceiver(worker);
                    request.setConversationId(Constants.MANAGER_MANUFACTURER);
                    request.setReplyWith("request" + System.currentTimeMillis());
                   // SendTasks sendTasks = new SendTasks();
                   // sendTasks.setOperations((jade.util.leap.List) operations);

                    mt = MessageTemplate.and(MessageTemplate.MatchConversationId(Constants.MANAGER_MANUFACTURER),
                            MessageTemplate.MatchInReplyTo(request.getReplyWith()));
                    send(request);
                    step = 1;
                }
                case 1 -> {
                    ACLMessage reply = myAgent.receive(mt);
                    if (reply != null) {
                        if (reply.getPerformative() == ACLMessage.AGREE) {
                            //worker = reply.getSender();
                            System.out.println("[" + getLocalName() +
                                    "] ожидаю результатов от" + reply.getSender().getLocalName());
                            step = 2;
                        } else {
                            System.out.println("отказ");
                            isAccept = true;
                            step = 3;
                        }
                    } else {
                        block();
                    }
                }
                case 2 -> {
                    ACLMessage reply1 = myAgent.blockingReceive(mt);
                    if (reply1 != null) {
                        if (reply1.getPerformative() == ACLMessage.INFORM)
                            System.out.println("[" + getLocalName() +
                                    "] принял успешный результат от" + reply1.getSender().getLocalName());
                        else {
                            System.out.println("[" + getLocalName() +
                                    "] принял отрицательный результат от" + reply1.getSender().getLocalName());
                        }
                        step = 3;
                    } else {
                        block();
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
            return isAccept ? 4 : 2;
        }
    }

    private void manageProduct() {

        FSMBehaviour fsmB = new FSMBehaviour(this) {
            @Override
            public int onEnd() {
                System.out.println("Закончил работу над деталью");
                return 0;
            }
        };

        List<Operation> ManufactureOperations = new ArrayList<>();
        getOperations("Изготовление");
        fsmB.registerFirstState(new OneShotBehaviour() {
            @Override
            public void action() {
                System.out.println("Начинаю производство продукта:");
            }

            @Override
            public int onEnd() {
                return 1;
            }
        }, "A");

        List<Operation> PAOperations = getOperations("Предварительная сборка");
        fsmB.registerState(new SendRequest(PAOperations, Constants.PA_PRODUCT_MANUFACTURER_TYPE), "B");

        fsmB.registerLastState(new OneShotBehaviour() {
            @Override
            public void action() {
                System.out.println("кц");
            }
        }, "C");

        fsmB.registerTransition("A", "B", 1);
        fsmB.registerTransition("B", "C", 2);
        //fsmB.registerTransition("B", "B", 4);
        //fsmB.registerTransition("B", "C", 2);
        addBehaviour(fsmB);
    }

    private List<Operation> getOperations(String s) {
        List<Operation> operations = new ArrayList<>();
        stationOperationMap.forEach((key, value) ->
        {
            if (key.getName().contains(s))
                operations.add(value);
        });
        return operations;
    }
}
