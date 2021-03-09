package Agents;

import API.Constants;
import ManufactureOntology.Predicates.HasMaterial;
import jade.content.ContentElement;
import jade.content.lang.Codec;
import jade.content.onto.OntologyException;
import jade.core.AID;
import jade.core.behaviours.*;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.util.leap.ArrayList;
import jade.util.leap.List;

import java.util.Arrays;
import java.util.stream.Collectors;

public class AgentManager extends AbstractAgent {

    private String product;
    boolean t = false;
    private List orders = new ArrayList();
    private java.util.List<AID> manufacturerAgents;
    private java.util.List<AID> verifierAgents;
    private MessageTemplate mt;
    private final DFAgentDescription template = new DFAgentDescription();
    private final ServiceDescription sd = new ServiceDescription();
    private boolean isWorking = false;
    private boolean end = false;

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

        addBehaviour(new GetOfferRequests());
        addBehaviour(new ApplyOffer());
    }

    private void finServices() throws FIPAException {
        manufacturerAgents = serviceSearcher(Constants.MANUFACTURER_TYPE);
        verifierAgents = serviceSearcher(Constants.VERIFIER_TYPE);

    }

    private java.util.List<AID> serviceSearcher(String type) throws FIPAException {
        sd.setType(type);
        template.addServices(sd);
        java.util.List<AID> list = Arrays.stream(DFService.search(this, template))
                .map(DFAgentDescription::getName)
                .collect(Collectors.toList());
        template.removeServices(sd);
        return list;
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

                //РЕАЛИЗАЦИЯ ОСНОВГО РАБОТЫ МЕНЕДЕЖДРА.
                //ФОРМИРОВАНИЕ ОТЧЕТА ДИСТРИБЬЮТОРУ
                try {
                    finServices();
                } catch (FIPAException e) {
                    e.printStackTrace();
                }
                manageDetail();
                //addBehaviour(new SendDetail());
                ACLMessage reply = msg.createReply();
                reply.setPerformative(ACLMessage.INFORM);
                send(reply);

            } else {
                block();
            }
        }
    }


    private class SendDetail extends Behaviour {
        int step = 0;
        private AID worker;
        MessageTemplate mmt;

        @Override
        public void action() {
            switch (step) {
                case 0 -> {
                    System.out.println("[" + getLocalName() +
                            "] отправил деталь");
                    ACLMessage request = new ACLMessage(ACLMessage.REQUEST);
                    manufacturerAgents.forEach(request::addReceiver);
                    request.setConversationId(Constants.MANAGER_MANUFACTURER);
                    request.setReplyWith("msg" + System.currentTimeMillis());
                    request.setContent("detail");
                    mmt = MessageTemplate.and(MessageTemplate.MatchConversationId(Constants.MANAGER_MANUFACTURER),
                            MessageTemplate.MatchInReplyTo(request.getReplyWith()));
                    send(request);
                    step = 1;
                }
                case 1 -> {
                    ACLMessage reply = myAgent.receive(mmt);
                    if (reply != null) {
                        if (reply.getPerformative() == ACLMessage.AGREE) {
                            //worker = reply.getSender();
                            System.out.println("[" + getLocalName() +
                                    "] ожидаю результатов от" + reply.getSender().getLocalName());
                            step = 2;
                        } else {
                            System.out.println("отказ");
                            step = 3;
                        }
                    } else {
                        block();
                    }
                }
                case 2 -> {
                    ACLMessage reply1 = myAgent.blockingReceive(mmt);
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

        // разбить на 2 варианта. Агент может отказаться
        @Override
        public int onEnd() {
            return 1;
        }
    }


    private void manageDetail() {

        FSMBehaviour fsmB = new FSMBehaviour(this) {
            @Override
            public int onEnd() {
                System.out.println("Закончил работу над деталью");
                return 0;
            }
        };
        //резка
        fsmB.registerFirstState(new SendDetail(), "A");

        fsmB.registerState(new OneShotBehaviour() {
            @Override
            public void action() {
                System.out.println("new");
            }

            @Override
            public int onEnd() {
                return 2;
            }
        }, "B");

        fsmB.registerLastState(new OneShotBehaviour() {
            @Override
            public void action() {
                System.out.println("кц");
            }
        }, "C");

        fsmB.registerTransition("A", "B", 1);
        fsmB.registerTransition("B", "C", 2);
        addBehaviour(fsmB);
    }

    @Override
    protected void takeDown() {
        System.out.println("Manager-Agent " + getAID().getName() + " terminating.");
    }
}
