import jade.Boot;
import jade.core.Agent;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

public class Main {

    private static ContainerController myContainer;

    public static void createAgent() {
        try {
            AgentController rma = myContainer.createNewAgent("rma", "jade.tools.rma.rma", null);
            rma.start();
            String[] args = new String[]{"ClassicTable"};
            AgentController AgentDb = myContainer.createNewAgent("DistributorAgent-1",
                    "Agents.Distributors.AgentDb", args);
            AgentDb.start();
            AgentController AgentDb1 = myContainer.createNewAgent("DistributorAgent-2",
                    "Agents.Distributors.AgentDb1", null);
            //  AgentDb1.start();
        } catch (StaleProxyException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Runtime myRuntime = Runtime.instance();
        Profile myProfile = new ProfileImpl();
        myContainer = myRuntime.createAgentContainer(myProfile);
        createAgent();
    }
}
