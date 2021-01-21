import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

public class Main {

    private static Runtime runtime;

    public static void startMainContainer() throws StaleProxyException {
        Profile myProfile = new ProfileImpl();
        AgentContainer mainContainer = runtime.createMainContainer(myProfile);
        AgentController rma = mainContainer.createNewAgent("rma", "jade.tools.rma.rma", null);
        rma.start();
    }

    public static void startMyContainer() throws StaleProxyException {
        Profile anotherProfile = new ProfileImpl(false);
        anotherProfile.setParameter(Profile.CONTAINER_NAME, "Manufacture-Container");
        AgentContainer anotherContainer = runtime.createAgentContainer(anotherProfile);

        String[] args = new String[]{"ClassicTable"};
        AgentController agentDb = anotherContainer.createNewAgent("DistributorAgent",
                "Agents.AgentDistributor", args);
        agentDb.start();

        AgentController agentM = anotherContainer.createNewAgent("AgentManager","Agents.AgentManager", null);
        AgentController agentM1 = anotherContainer.createNewAgent("AgentManager1","Agents.AgentManager", null);
        agentM.start();
        agentM1.start();
    }

    public static void main(String[] args) throws StaleProxyException {
        runtime = Runtime.instance();
        startMainContainer();
        startMyContainer();

    }
}
