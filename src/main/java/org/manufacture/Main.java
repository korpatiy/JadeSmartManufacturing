package org.manufacture;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;
import org.manufacture.API.QueryExecutorService;
import org.manufacture.Ontology.concepts.domain.ProductionResource;
import org.manufacture.dbConnection.QueryExecutor;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class Main {

    private static Runtime runtime;

    //в класс
    public static void startMainContainer() throws StaleProxyException {
        Profile mainProfile = new ProfileImpl();
        AgentContainer mainContainer = runtime.createMainContainer(mainProfile);
        AgentController rma = mainContainer.createNewAgent("rma", "jade.tools.rma.rma", null);
        rma.start();
    }

    //в класс
    public static void startManufactureContainer() throws SQLException, StaleProxyException {
        Profile manufactureProfile = new ProfileImpl(false);
        manufactureProfile.setParameter(Profile.CONTAINER_NAME, "Manufacture-Container");
        AgentContainer manufactureContainer = runtime.createAgentContainer(manufactureProfile);

        QueryExecutorService queryExecutor = QueryExecutor.getQueryExecutor();

        //need interface resource
        List<ProductionResource> resources = queryExecutor.seekAgents();

        resources.forEach(resource -> {
            Object[] args = new Object[]{resource.getType()};
            String className = "org.manufacture.Agents.";
            if (resource.getName().contains("Manufacturer") || resource.getName().contains("Verifier") || resource.getName().contains("Packer")) {
                createAgent(manufactureContainer, resource.getName(), args, className + "AgentManufacturer");
            }
            if (resource.getName().contains("ProductManager")) {
                createAgent(manufactureContainer, resource.getName(), args, className + "ProductManager");
            }
        });

        Date date = new Date();
        Object[] args = new Object[]{"Машина стиральная стандартная", date, 1};
        AgentController agentDistributor = manufactureContainer.createNewAgent("DistributorAgent",
                "org.manufacture.Agents.AgentDistributor", args);
        agentDistributor.start();
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
