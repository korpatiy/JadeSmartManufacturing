package Agents;

import ManufactureOntology.ManufactureOntology;
import jade.content.ContentManager;
import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;

public class AgentManufacturer extends Agent {

    private ContentManager contentManager = getContentManager();
    private Codec codec = new SLCodec();
    private Ontology ontology = ManufactureOntology.getInstance();
    private boolean isWorking = false;

    @Override
    protected void setup() {
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType("manager-manufacturer");
        sd.setName("Product-manufacturer");
        //sd.addOntologies(ManufactureOntology.getInstance().getName());
        dfd.addServices(sd);
        //dfd.addOntologies(ManufactureOntology.getInstance().getName());
        this.contentManager.registerOntology(ontology);
        this.contentManager.registerLanguage(codec);
        try {
            DFService.register(this, dfd);
        } catch (FIPAException e) {
            e.printStackTrace();
        }
    }

    private class working extends CyclicBehaviour {

        @Override
        public void action() {

        }
    }

    @Override
    protected void takeDown() {
        try {
            DFService.deregister(this);
        } catch (FIPAException e) {
            e.printStackTrace();
        }
        System.out.println(getAID().getName() + " terminating.");
    }
}
