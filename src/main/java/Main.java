import Agents.AgentManufacturer;
import dbConnection.DBConnection;
import dbConnection.DbQueryExecutor;
import jade.Boot;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

import java.sql.SQLException;

public class Main {

    private static Runtime runtime;

    public static void startMainContainer() throws StaleProxyException {
        Profile myProfile = new ProfileImpl();
        AgentContainer mainContainer = runtime.createMainContainer(myProfile);
        AgentController rma = mainContainer.createNewAgent("rma", "jade.tools.rma.rma", null);
        rma.start();
    }

    public static void startMyContainer() throws StaleProxyException, InterruptedException, SQLException {
        Profile anotherProfile = new ProfileImpl(false);
        anotherProfile.setParameter(Profile.CONTAINER_NAME, "Manufacture-Container");
        AgentContainer anotherContainer = runtime.createAgentContainer(anotherProfile);

        String[] args = new String[]{"ClassicTable"};
        String[] args2 = new String[]{"ClassicChair"};

        /*DBConnection dbConnection = DBConnection.getConnection();
        if (!dbConnection.tryConnect()) {
            System.out.println("Не удалось подключиться к БД");
        }*/
        //DbQueryExecutor dbQueryExecutor = DbQueryExecutor.getDbQueryExecutor();
        //dbQueryExecutor.seekAgents();

        AgentController agentManufacturer = anotherContainer.createNewAgent("AgentManufacturer", "Agents.AgentManufacturer", args);
        agentManufacturer.start();

        AgentController agentM = anotherContainer.createNewAgent("AgentManager", "Agents.AgentManager", args);
        agentM.start();

        AgentController agentDistributor = anotherContainer.createNewAgent("DistributorAgent",
                "Agents.AgentDistributor", args);
        agentDistributor.start();
    }

    public static void main(String[] args) throws StaleProxyException, InterruptedException, SQLException {
        runtime = Runtime.instance();
        startMainContainer();
        startMyContainer();
    }
}
