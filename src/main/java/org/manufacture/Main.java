package org.manufacture;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;
import org.manufacture.API.QueryExecutorBuilder;
import org.manufacture.API.QueryExecutorService;
import org.manufacture.Ontology.concepts.domain.Operation;
import org.manufacture.Ontology.concepts.domain.Resource;
import org.manufacture.Ontology.concepts.domain.Station;
import org.manufacture.dbConnection.QueryExecutor;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

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

        QueryExecutorService queryExecutor = QueryExecutor.getQueryExecutor();

        //need interface resource
        List<Resource> resources = queryExecutor.seekAgents();

        resources.forEach(resource -> {
            Object[] args = new Object[]{resource.getType(), resource.getStation().getName()};
            String className = "org.manufacture.Agents.";
            if (resource.getName().contains("Manufacturer") || resource.getName().contains("Verifier") || resource.getName().contains("Packer")) {
                createAgent(manufactureContainer, resource.getName(), args, className + "AgentManufacturer");
            }
            if (resource.getName().contains("Verifier")) {
                createAgent(manufactureContainer, resource.getName(), args, className + "AgentVerifier");
            }
            if (resource.getName().contains("ProductManager")) {
                createAgent(manufactureContainer, resource.getName(), args, className + "ProductManager");
            }
        });

        Object[] args = new Object[]{"Машина стиральная стандартная", "Стандартный", "today"};
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
