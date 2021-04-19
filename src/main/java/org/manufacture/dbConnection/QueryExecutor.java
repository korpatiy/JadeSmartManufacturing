package org.manufacture.dbConnection;

import org.manufacture.API.QueryExecutorService;
import org.manufacture.Ontology.concepts.domain.*;
import org.manufacture.Ontology.concepts.domain.domainImpl.*;
import org.manufacture.Ontology.concepts.general.Resource;
import org.manufacture.Ontology.concepts.general.generalmpl.DefaultResource;

import java.sql.*;
import java.util.*;

public class QueryExecutor implements QueryExecutorService {

    private static QueryExecutor queryExecutor;
    private Connection connection;

    private QueryExecutor() {
    }

    public static QueryExecutor getQueryExecutor() {
        if (queryExecutor == null) {
            return new QueryExecutor();
        }
        return queryExecutor;
    }

    @Override
    public List<Resource> seekAgents() throws SQLException {
        connection = DriverManager.getConnection(ConnectConstants.DB_URL, ConnectConstants.USER, ConnectConstants.PASSWORD);
        PreparedStatement getAgents = connection.prepareStatement("SELECT r.name as rname, s.name as sname, r.description, r.type from resource r JOIN station s ON r.station_id = s.id");
        ResultSet resultSet = getAgents.executeQuery();
        List<Resource> resources = new ArrayList<>();
        while (resultSet.next()) {
            Resource agent = new DefaultResource();
            agent.setName(resultSet.getString("rname"));
            agent.setType(resultSet.getString("type"));
            //Optional<String> x = Optional.ofNullable(resultSet.getString("type")).if;
            //agent.setDescription(agents.getString("description")); упущенно в рамках примера
            Station station = new DefaultStation();
            station.setName(resultSet.getString("sname"));
            //station description упущенно в рамках примера
            //agent.(station);
            resources.add(agent);
        }

        //ВЕРНУТЬ OBJECT И ПОТОМ ИСКЛЮЧИТЬ ТУПО
        connection.close();
        return resources;
    }

  /*  @Override
    public int seekPlanId() throws SQLException {
        connection = DriverManager.getConnection(ConnectConstants.DB_URL, ConnectConstants.USER, ConnectConstants.PASSWORD);
        PreparedStatement getAgents = connection.prepareStatement("SELECT r.name as rname, s.name as sname, r.description, r.type from resource r JOIN station s ON r.station_id = s.id");


        return 0;
    }*/

    @Override
    public Plan seekPlan(String productName) throws SQLException {
        Plan plan = new DefaultPlan();
        connection = DriverManager.getConnection(ConnectConstants.DB_URL, ConnectConstants.USER, ConnectConstants.PASSWORD);
        PreparedStatement getPlanId = connection
                .prepareStatement("select plan_id from product where name = ?");
        getPlanId.setString(1, productName);
        ResultSet resultSet = getPlanId.executeQuery();
        while (resultSet.next()) {
            plan.setId(resultSet.getInt("plan_id"));
        }
        PreparedStatement getOperations = connection
                .prepareStatement("select s.name as s_name, o.name as o_name, o.duration as duration, s2.name as s2_name, m.name as m_name, tool.name as t_name \n" +
                        "from plan_station_operation \n" +
                        "join station s on s.id = plan_station_operation.station_id\n" +
                        "join operation o on o.id = plan_station_operation.operation_id\n" +
                        "join setup s2 on s2.id = o.setup_id\n" +
                        "join tool on s2.tool_id = tool.id\n" +
                        "join material m on m.id = o.material_id\n" +
                        "where plan_id = ?");
        getOperations.setInt(1, plan.getId());
        resultSet = getOperations.executeQuery();
        while (resultSet.next()) {
            Tool tool = new DefaultTool();
            tool.setName(resultSet.getString("t_name"));

            Material material = new DefaultMaterial();
            material.setName(resultSet.getString("m_name"));

            Setup setup = new DefaultSetup();
            setup.setName(resultSet.getString("s2_name"));
            setup.setRequiresTool(tool);

            Station station = new DefaultStation();
            station.setName(resultSet.getString("s_name"));


            Operation operation = new DefaultOperation();
            operation.setName(resultSet.getString("o_name"));
            operation.setRequiresSetup(setup);
            operation.setPerformedOverMaterial(material);
            operation.setDuration(resultSet.getInt("duration"));
            operation.setPerfomedOnStation(station);
            plan.addHasOperations(operation);
        }
        connection.close();
        return plan;
    }
}
