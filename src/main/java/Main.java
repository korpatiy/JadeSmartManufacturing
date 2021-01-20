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
        anotherProfile.setParameter(Profile.CONTAINER_NAME,"Manufacture-Container");
        AgentContainer anotherContainer = runtime.createAgentContainer(anotherProfile);

        String[] args = new String[]{"ClassicTable"};
        AgentController AgentDb = anotherContainer.createNewAgent("DistributorAgent-1",
                "Agents.Distributors.AgentDb", args);
        AgentDb.start();
        AgentController testAgent = anotherContainer.createNewAgent("DistributorAgent-2",
                "Agents.Distributors.AgentDb1", null);
        testAgent.start();
    }

    public static void main(String[] args) throws StaleProxyException {
        runtime = Runtime.instance();
        startMainContainer();
        startMyContainer();
    }
}
