package Agents;

import Ontology.ManufactureOntology;
import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.core.Agent;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public abstract class AbstractAgent extends Agent {

    protected Codec codec = new SLCodec();
    protected Ontology ontology = ManufactureOntology.getInstance();
    protected String product;
    protected String location;
    protected DFAgentDescription dfd = new DFAgentDescription();
    protected ServiceDescription sd = new ServiceDescription();
    protected Object[] args;

    @Override
    protected void setup() {
        args = getArguments();
        if (args != null && args.length > 0) {
            this.product = (String) args[0];
        } else {
            System.out.println("No arguments");
            doDelete();
        }
        System.out.println("Agent " + getLocalName() + " is ready.");
        getContentManager().registerLanguage(codec);
        getContentManager().registerOntology(ontology);
    }

    @Override
    protected void takeDown() {
        System.out.println("Agent " + getLocalName() + " terminating.");
    }
}
