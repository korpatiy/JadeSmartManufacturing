package Agents;

import ManufactureOntology.ManufactureOntology;
import jade.content.ContentManager;
import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.core.Agent;

public abstract class AbstractAgent extends Agent {

    protected final Codec codec = new SLCodec();
    protected final Ontology ontology = ManufactureOntology.getInstance();
    protected final ContentManager contentManager = getContentManager();
}
