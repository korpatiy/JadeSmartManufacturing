package Agents;

import Ontology.ManufactureOntology;
import jade.content.ContentManager;
import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.core.Agent;

public abstract class AbstractAgent extends Agent {

    protected Codec codec = new SLCodec();
    protected Ontology ontology = ManufactureOntology.getInstance();
    //protected ContentManager contentManager = getContentManager();

    public abstract String getAgentName();

    @Override
    protected void setup() {

        System.out.println("Agent " + getAgentName() + " is ready.");
        getContentManager().registerLanguage(codec);
        getContentManager().registerOntology(ontology);
    }
}
