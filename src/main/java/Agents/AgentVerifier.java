package Agents;

import API.Constants;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;

public class AgentVerifier extends AbstractAgent {

    @Override
    protected void setup() {
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType(Constants.VERIFIER_TYPE);
        sd.setName(getLocalName());
        dfd.addServices(sd);
        try {
            DFService.register(this, dfd);
        } catch (FIPAException e) {
            e.printStackTrace();
        }
        contentManager.registerOntology(ontology);
        contentManager.registerLanguage(codec);
       // addBehaviour(new Verify());
    }


    private class Verify extends CyclicBehaviour {

        @Override
        public void action() {

        }
    }

    @Override
    protected void takeDown() {
        super.takeDown();
    }
}
