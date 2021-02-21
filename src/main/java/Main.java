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

        AgentController agentF = anotherContainer.createNewAgent("Veriefer", "Agents.AgentVerifier", null);
        agentF.start();
        AgentController agentF1 = anotherContainer.createNewAgent("Veriefer1", "Agents.AgentVerifier", null);
        agentF1.start();
        AgentController agentManuf = anotherContainer.createNewAgent("AgentManuf", "Agents.AgentManufacturer", null);
        agentManuf.start();
        AgentController agentManuf1 = anotherContainer.createNewAgent("AgentManuf1", "Agents.AgentManufacturer", null);
        agentManuf1.start();
        /*AgentController agentManuf2 = anotherContainer.createNewAgent("AgentManuf2", "Agents.AgentManufacturer", null);
        agentManuf2.start();
        AgentController agentManuf3 = anotherContainer.createNewAgent("AgentManuf3", "Agents.AgentManufacturer", null);
        agentManuf3.start();*/

        AgentController agentM = anotherContainer.createNewAgent("AgentManager", "Agents.AgentManager", args);
        AgentController agentM1 = anotherContainer.createNewAgent("AgentManager1", "Agents.AgentManager", args);
        agentM.start();
        agentM1.start();

        AgentController agentDb = anotherContainer.createNewAgent("DistributorAgent",
                "Agents.AgentDistributor", args);
        agentDb.start();

    }

    public static void main(String[] args) throws StaleProxyException, InterruptedException {
        runtime = Runtime.instance();
        startMainContainer();
        startMyContainer();
    }
}
