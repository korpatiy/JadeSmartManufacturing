import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

public class ControllerTest {

    public static ContainerController myContainer;

    public static void createAgent() {
        try {
            AgentController rma = myContainer.createNewAgent("rma", "jade.tools.rma.rma", null);
            rma.start();
            AgentController agent2 = myContainer.createNewAgent("receiver", "TestAgent", null);
            agent2.start();
        } catch(StaleProxyException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Runtime myRuntime = Runtime.instance();
        Profile myProfile = new ProfileImpl();
        myContainer = myRuntime.createMainContainer(myProfile);
        createAgent();
    }
}
