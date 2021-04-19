package org.manufacture.Agents;

import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.util.leap.List;
import org.manufacture.Ontology.ManufactureOntology;

import java.util.Arrays;
import java.util.stream.Collectors;

public abstract class ResourceAgent extends Agent {

    private final Codec codec = new SLCodec();
    private final Ontology ontology = ManufactureOntology.getInstance();
    private String type;
    private final DFAgentDescription dfd = new DFAgentDescription();
    private final ServiceDescription sd = new ServiceDescription();
    /*private boolean isWorking = false;
    private boolean isDone = false;

    public boolean isWorking() {
        return isWorking;
    }

    public void setWorking(boolean working) {
        isWorking = working;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }*/

    public Codec getCodec() {
        return codec;
    }

    public Ontology getOntology() {
        return ontology;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    protected void setup() {
        if (!getLocalName().contains("Distributor")) {
            setFields();
            registerService();
        }
        System.out.println("Agent " + getLocalName() + " is ready.");
        getContentManager().registerLanguage(codec);
        getContentManager().registerOntology(ontology);
    }

    /**
     * Выполняет поиск сервиса
     *
     * @param type тип искомого сервиса
     * @return возвращает список агентов, предоставляющих сервис
     * @throws FIPAException
     */
    protected java.util.List<AID> findServices(String type) throws FIPAException {
        DFAgentDescription dfd = new DFAgentDescription();
        ServiceDescription sd = new ServiceDescription();
        sd.setType(type);
        dfd.addServices(sd);
        java.util.List<AID> list = Arrays.stream(DFService.search(this, dfd))
                .map(DFAgentDescription::getName)
                .collect(Collectors.toList());
        dfd.removeServices(sd);
        return list;
    }

    /**
     * Выполняет регистрацию сервиса
     */
    private void registerService() {
        dfd.setName(getAID());
        sd.setType(type);
        sd.setName(getLocalName());
        dfd.addServices(sd);
        try {
            DFService.register(this, dfd);
        } catch (FIPAException e) {
            e.printStackTrace();
        }
    }

    protected void setFields() {
        Object[] args = getArguments();
        if (args != null && args.length > 0) {
            this.type = (String) args[0];
        } else {
            System.out.println("No arguments");
            doDelete();
        }
    }

    @Override
    protected void takeDown() {
        System.out.println("Agent " + getLocalName() + " terminating.");
        if (!getLocalName().contains("Distributor")) {
            try {
                DFService.deregister(this);
            } catch (FIPAException e) {
                e.printStackTrace();
            }
        }
    }

  /*  private class SendInform extends CyclicBehaviour {

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
    }*/
}
