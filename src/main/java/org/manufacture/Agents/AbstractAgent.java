package org.manufacture.Agents;

import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import org.manufacture.Ontology.ManufactureOntology;

import java.util.Arrays;
import java.util.stream.Collectors;

public abstract class AbstractAgent extends Agent {

    //to private
    protected Codec codec = new SLCodec();
    protected Ontology ontology = ManufactureOntology.getInstance();
    private String type;
    private String station;
    //to private
    protected DFAgentDescription dfd = new DFAgentDescription();
    protected ServiceDescription sd = new ServiceDescription();

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
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

    private void setFields() {
        Object[] args = getArguments();
        if (args != null && args.length > 0) {
            this.type = (String) args[0];
            this.station = (String) args[1];
        } else {
            System.out.println("No arguments");
            //doDelete();
        }
    }

    @Override
    protected void takeDown() {
        System.out.println("Agent " + getLocalName() + " terminating.");
    }
}
