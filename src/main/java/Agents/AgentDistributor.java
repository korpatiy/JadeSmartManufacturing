package Agents;

import ManufactureOntology.Concepts.Material;
import ManufactureOntology.Concepts.Product;
import ManufactureOntology.ManufactureOntology;
import ManufactureOntology.Predicates.HasMaterial;
import jade.content.ContentManager;
import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.content.onto.OntologyException;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.AMSService;
import jade.domain.FIPAAgentManagement.AMSAgentDescription;
import jade.domain.FIPAAgentManagement.SearchConstraints;
import jade.lang.acl.ACLMessage;
import jade.util.leap.ArrayList;
import jade.util.leap.List;

import java.util.Arrays;
import java.util.stream.Collectors;

public class AgentDistributor extends Agent {

    private String product;
    private ContentManager contentManager = getContentManager();
    private Codec codec = new SLCodec();
    private Ontology ontology = ManufactureOntology.getInstance();
    private java.util.List<AMSAgentDescription> managerAgents = null;

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
        contentManager.registerLanguage(codec);
        contentManager.registerOntology(ontology);
        addBehaviour(new sendStartMessage());
    }

    private class sendStartMessage extends OneShotBehaviour {
        @Override
        public void action() {
            ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
            managerAgents.forEach(agent -> msg.addReceiver(agent.getName()));

            msg.setLanguage(codec.getName());
            msg.setOntology(ontology.getName());

            Product table = new Product();
            Material wood = new Material();
            Material wood1 = new Material();
            table.setId(1);
            table.setName("Classic table");
            wood.setId(1);
            wood.setName("Wood");
            wood1.setId(2);
            wood1.setName("Wood2");

            HasMaterial hasMaterial = new HasMaterial();
            hasMaterial.setProduct(table);
            List materials = new ArrayList();
            materials.add(wood);
            materials.add(wood1);
            hasMaterial.setMaterials(materials);

            try {
                contentManager.fillContent(msg, hasMaterial);
            } catch (Codec.CodecException e) {
                e.printStackTrace();
            } catch (OntologyException e) {
                e.printStackTrace();
            }

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
