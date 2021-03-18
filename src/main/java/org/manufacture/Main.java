package org.manufacture;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;
import org.manufacture.Ontology.concepts.domain.Resource;
import org.manufacture.dbConnection.DbQueryExecutor;

import java.sql.SQLException;
import java.util.List;

public class Main {

    private static Runtime runtime;

    //в класс
    public static void startMainContainer() throws StaleProxyException {
        Profile myProfile = new ProfileImpl();
        AgentContainer mainContainer = runtime.createMainContainer(myProfile);
        AgentController rma = mainContainer.createNewAgent("rma", "jade.tools.rma.rma", null);
        rma.start();
    }

    //в класс
    public static void startManufactureContainer() throws SQLException, StaleProxyException {
        Profile anotherProfile = new ProfileImpl(false);
        anotherProfile.setParameter(Profile.CONTAINER_NAME, "Manufacture-Container");
        AgentContainer manufactureContainer = runtime.createAgentContainer(anotherProfile);

        // УБРАТЬ ОТСЮДА
        DbQueryExecutor dbQueryExecutor = DbQueryExecutor.getDbQueryExecutor();

        List<Resource> resources = dbQueryExecutor.seekAgents();
        resources.forEach(resource -> {
            Object[] args = new Object[]{resource.getType(), resource.getStation().getName()};
            String className = "org.manufacture.Agents.";
            if (resource.getName().contains("ProductManager")) {
                createAgent(manufactureContainer, resource.getName(), args, className + "ProductManager");
            }
            if (resource.getName().contains("Manufacturer")) {
                createAgent(manufactureContainer, resource.getName(), args, className + "AgentManufacturer");
            }
            if (resource.getName().contains("Verifier")) {
                createAgent(manufactureContainer, resource.getName(), args, className + "AgentVerifier");
            }
        });

        /*AgentController agentDistributor = manufactureContainer.createNewAgent("DistributorAgent",
                "org.manufacture.Agents.AgentDistributor", null);
        agentDistributor.start();*/
    }

    private static void createAgent(AgentContainer manufactureContainer, String name, Object[] args, String className) {
        AgentController agent;
        try {
            agent = manufactureContainer.createNewAgent(name, className, args);
            assert agent != null;
            agent.start();
        } catch (StaleProxyException e) {
            e.printStackTrace();
        }
    }

    /**
     * Старт программы
     *
     * @param args
     * @throws StaleProxyException
     * @throws InterruptedException
     * @throws SQLException
     */
    public static void main(String[] args) throws StaleProxyException, SQLException {
        runtime = Runtime.instance();
        startMainContainer();
        startManufactureContainer();
    }
}
