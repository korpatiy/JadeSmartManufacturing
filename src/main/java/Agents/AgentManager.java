package Agents;

import API.Constants;
import ManufactureOntology.Predicates.HasMaterial;
import jade.content.ContentElement;
import jade.content.lang.Codec;
import jade.content.onto.OntologyException;
import jade.core.AID;
import jade.core.behaviours.*;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.*;
import jade.domain.FIPAException;
import jade.domain.RequestFIPAServiceBehaviour;
import jade.domain.RequestManagementBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.ContractNetInitiator;
import jade.proto.ContractNetResponder;
import jade.proto.FIPAProtocolNames;
import jade.util.leap.ArrayList;
import jade.util.leap.List;

import java.util.Arrays;
import java.util.Date;
import java.util.Vector;
import java.util.stream.Collectors;

import static jade.core.behaviours.ParallelBehaviour.WHEN_ALL;

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
        MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.CFP);
        addBehaviour(new ContractNetResponder(this, mt) {
            @Override
            protected ACLMessage handleCfp(ACLMessage cfp) throws RefuseException, FailureException, NotUnderstoodException {
                ACLMessage reply = cfp.createReply();
                if (isWorking) {
                    reply.setPerformative(ACLMessage.REFUSE);
                    System.out.println("[" + getLocalName() +
                            "] Не готов принять заказ");
                    reply.setContent("Не найден продукт");
                } else {
                    reply.setPerformative(ACLMessage.PROPOSE);
                    System.out.println("[" + getLocalName() +
                            "] Готов Принять заказ");
                }
                return reply;
            }

            @Override
            protected ACLMessage handleAcceptProposal(ACLMessage cfp, ACLMessage propose, ACLMessage accept) throws FailureException {
                isWorking = true;
                ACLMessage reply = accept.createReply();
                addBehaviour(new WakerBehaviour(myAgent, 10000) {
                    @Override
                    protected void onWake() {
                        System.out.println(myAgent.getLocalName() + " im wake up!");
                        isWorking = false;
                        ACLMessage reply = accept.createReply();
                        reply.setPerformative(ACLMessage.INFORM);
                        try {
                            finServices();
                        } catch (FIPAException e) {
                            e.printStackTrace();
                        }
                    }
                });
                return reply;
            }

            @Override
            protected void handleRejectProposal(ACLMessage cfp, ACLMessage propose, ACLMessage reject) {
                System.out.println("reject");
            }
        });

        //addBehaviour(new GetOfferRequests());
        // addBehaviour(new ApplyOffer());
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
                if (isWorking) {
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

                //РЕАЛИЗАЦИЯ ОСНОВГО РАБОЫТ МЕНЕДЕЖДРА.
                //ФОРМИРОВАНИЕ ОТЧЕТА ДИСТРИБЬЮТОРУ

                /*ACLMessage reply = msg.createReply();
                reply.setPerformative(ACLMessage.INFORM);

                send(reply);
                try {
                    finServices();
                } catch (FIPAException e) {
                    e.printStackTrace();
                }*/
                isWorking = true;
                addBehaviour(new WakerBehaviour(myAgent, 10000) {
                    @Override
                    protected void onWake() {
                        System.out.println(myAgent.getLocalName() + " im wake up!");
                        isWorking = false;
                        ACLMessage reply = msg.createReply();
                        reply.setPerformative(ACLMessage.INFORM);

                        send(reply);
                        try {
                            finServices();
                        } catch (FIPAException e) {
                            e.printStackTrace();
                        }
                    }
                });
                //startManage();
            } else {
                block();
            }
        }
    }

    private class Send extends Behaviour {

        int step = 0;
        private AID worker;

        @Override
        public void action() {
            switch (step) {
                case 0:
                    ACLMessage request = new ACLMessage(ACLMessage.REQUEST);
                    manufacturerAgents.forEach(request::addReceiver);
                    request.setConversationId(Constants.MANAGER_MANUFACTURER);
                    request.setReplyWith("msg" + System.currentTimeMillis());
                    request.setContent("detail");
                    mt = MessageTemplate.and(MessageTemplate.MatchConversationId(Constants.MANAGER_MANUFACTURER),
                            MessageTemplate.MatchInReplyTo(request.getReplyWith()));
                    send(request);
                    step = 1;
                    break;
                case 1:
                    ACLMessage reply = myAgent.receive(mt);
                    if (reply != null) {
                        if (reply.getPerformative() == ACLMessage.AGREE) {
                            worker = reply.getSender();
                            System.out.println("ok agree");
                        }
                        step = 2;
                    } else {
                        block();
                    }
                    break;
                case 2:
                    ACLMessage reply1 = myAgent.receive(mt);
                    if (reply1 != null) {
                        if (reply1.getPerformative() == ACLMessage.INFORM)
                            System.out.println("ok inform");
                        step = 3;
                    } else {
                        block();
                    }
                    break;
            }
        }

        @Override
        public boolean done() {
            return step == 3;
        }
    }

    private void startManage() {

        System.out.println("[" + getLocalName() +
                "] начал менеджинг");

        SequentialBehaviour seqB = new SequentialBehaviour();
        addBehaviour(seqB);

        ParallelBehaviour parB = new ParallelBehaviour(this, WHEN_ALL) {
            @Override
            public int onEnd() {
                System.out.println("завершил работу");
                return 0;
            }
        };
        seqB.addSubBehaviour(parB);

        //new RequestFIPAServiceBehaviour()

        /*for (int i = 0; i < 4; i++) {
            System.out.println("iter" + i);
            ACLMessage request = new ACLMessage(ACLMessage.REQUEST);
            manufacturerAgents.forEach(request::addReceiver);
            request.setConversationId(Constants.MANAGER_MANUFACTURER);
            request.setReplyWith("msg" + System.currentTimeMillis());
            parB.addSubBehaviour(new ContractNetInitiator(this, request)
            {
                private AID worker;
                @Override
                protected void handlePropose(ACLMessage propose, Vector acceptances) {
                    worker = propose.getSender();
                }

                @Override
                protected void handleAllResponses(Vector responses, Vector acceptances) {

                }

            });
        }*/

        seqB.addSubBehaviour(new OneShotBehaviour() {
            @Override
            public void action() {
                System.out.println("end");
            }
        });
    }

    //обработка
       /* parallelBehaviour.addSubBehaviour(new SimpleBehaviour() {

            @Override
            public void action() {
                if(t)
                System.out.println("te");
                t=false;
            }

            @Override
            public boolean done() {
                return t;
            }
        });*/

    //проверка
        /*parallelBehaviour.addSubBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {

            }
        });*/


    @Override
    protected void takeDown() {
        System.out.println("Manager-Agent " + getAID().getName() + " terminating.");
    }
}
