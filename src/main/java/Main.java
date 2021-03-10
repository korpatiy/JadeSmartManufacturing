import Agents.AgentManufacturer;
import jade.Boot;
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

    public static void startMyContainer() throws StaleProxyException, InterruptedException {
        Profile anotherProfile = new ProfileImpl(false);
        anotherProfile.setParameter(Profile.CONTAINER_NAME, "Manufacture-Container");
        AgentContainer anotherContainer = runtime.createAgentContainer(anotherProfile);

        String[] args = new String[]{"ClassicTable"};
        String[] args2 = new String[]{"ClassicChair"};

        AgentController agentManufacturer = anotherContainer.createNewAgent("AgentManufacturer", "Agents.AgentManufacturer", null);
        agentManufacturer.start();


        AgentController agentM = anotherContainer.createNewAgent("AgentManager", "Agents.AgentManager", args);
        agentM.start();

        AgentController agentDistributor = anotherContainer.createNewAgent("DistributorAgent",
                "Agents.AgentDistributor", args);
        agentDistributor.start();

    }

    public static void main(String[] args) throws StaleProxyException, InterruptedException {
        runtime = Runtime.instance();
        startMainContainer();
        startMyContainer();
    }
}
