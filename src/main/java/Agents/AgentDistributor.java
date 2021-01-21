package Agents;

import jade.content.ContentManager;
import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.AMSService;
import jade.domain.FIPAAgentManagement.AMSAgentDescription;
import jade.domain.FIPAAgentManagement.SearchConstraints;
import jade.lang.acl.ACLMessage;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AgentDistributor extends Agent {

    private String product;
    private ContentManager contentManager = getContentManager();
    private Codec codec = new SLCodec();
    private Ontology ontology;
    private List<AMSAgentDescription> managerAgents = null;

    @Override
    protected void setup() {
        System.out.println("Distributor-Agent " + getAID().getName() + " is ready.");
        Object[] args = getArguments();

        if (args != null && args.length > 0) {
            product = (String) args[0];
        } else {
            System.out.println("No product title specified");
            doDelete();
        }

        findManagers();
        /*contentManager.registerLanguage(codec);
        contentManager.registerOntology(ontology);*/
        addBehaviour(new sendStartMessage());
    }

    private class sendStartMessage extends OneShotBehaviour {
        @Override
        public void action() {
            ACLMessage msg = new ACLMessage(ACLMessage.CFP);
            managerAgents.forEach(agent -> msg.addReceiver(agent.getName()));
            msg.setContent(product);
            send(msg);
            System.out.println("Message send to Manager");
            doDelete();
        }
    }

    private void findManagers() {
        AMSAgentDescription[] agents = null;
        try {
            SearchConstraints c = new SearchConstraints();
            c.setMaxResults(new Long(-1));
            agents = AMSService.search(this, new AMSAgentDescription(), c);
        } catch (Exception e) {
            System.out.println("Problem searching AMS: " + e);
            e.printStackTrace();
        }
        managerAgents = Arrays.stream(agents)
                .filter(agent -> agent.getName().getLocalName().contains("Manager"))
                .collect(Collectors.toList());
    }

    @Override
    protected void takeDown() {
        System.out.println("Distributor-Agent " + getAID().getName() + " terminating.");
    }
}
