package Agents;

import ManufactureOntology.ManufactureOntology;
import jade.content.ContentManager;
import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.core.Agent;

public abstract class AbstractAgent extends Agent {

    protected ContentManager myContentManager = getContentManager();
    protected Codec codec = new SLCodec();
    protected Ontology ontology = ManufactureOntology.getInstance();
}
