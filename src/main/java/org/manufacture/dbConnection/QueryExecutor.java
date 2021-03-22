package org.manufacture.dbConnection;

import org.manufacture.API.QueryExecutorService;
import org.manufacture.Ontology.concepts.domain.*;

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


    /*@Override
    public <T extends AbstractItem> T seekEntity(String entity, String entityName, Class<T extends AbstractItem> type) throws SQLException {
        connection = DriverManager.getConnection(ConnectConstants.DB_URL, ConnectConstants.USER, ConnectConstants.PASSWORD);
        PreparedStatement getEntity = connection.prepareStatement("SELECT * FROM product");
        //getEntity.setString(1, entity);
        //getEntity.setString(2, entityName);
        ResultSet resultSet = getEntity.executeQuery();

        connection.close();

    }*/

    @Override
    public List<Resource> seekAgents() throws SQLException {
        connection = DriverManager.getConnection(ConnectConstants.DB_URL, ConnectConstants.USER, ConnectConstants.PASSWORD);
        PreparedStatement getAgents = connection.prepareStatement("SELECT r.name as rname, s.name as sname, r.description, r.type from resource r JOIN station s ON r.station_id = s.id");
        ResultSet resultSet = getAgents.executeQuery();
        System.out.println(resultSet);
        List<Resource> resources = new ArrayList<>();
        while (resultSet.next()) {
            Resource agent = new Resource();
            agent.setName(resultSet.getString("rname"));
            agent.setType(resultSet.getString("type"));
            //Optional<String> x = Optional.ofNullable(resultSet.getString("type")).if;
            //agent.setDescription(agents.getString("description")); упущенно в рамках примера
            Station station = new Station();
            station.setName(resultSet.getString("sname"));
            //station description упущенно в рамках примера
            agent.setStation(station);
            resources.add(agent);
        }
        connection.close();
        return resources;
    }

    @Override
    public Map<Station, Operation> seekPlan(String planName) throws SQLException {
        connection = DriverManager.getConnection(ConnectConstants.DB_URL, ConnectConstants.USER, ConnectConstants.PASSWORD);
        PreparedStatement getStations = connection
                .prepareStatement("select s.name as s_name, o.name as o_name, s2.name as s2_name, m.name as m_name, tool.name as t_name \n" +
                        "from plan_station_operation \n" +
                        "join station s on s.id = plan_station_operation.station_id\n" +
                        "join operation o on o.id = plan_station_operation.operation_id\n" +
                        "join setup s2 on s2.id = o.setup_id\n" +
                        "join tool on s2.tool_id = tool.id\n" +
                        "join material m on m.id = o.material_id");
        ResultSet resultSet = getStations.executeQuery();
        Map<Station, Operation> stationOperationMap = new LinkedHashMap<>();
        while (resultSet.next()) {
            Tool tool = new Tool();
            tool.setName(resultSet.getString("t_name"));
            Material material = new Material();
            material.setName(resultSet.getString("m_name"));
            Setup setup = new Setup();
            setup.setName(resultSet.getString("s2_name"));
            Operation operation = new Operation();
            operation.setName(resultSet.getString("o_name"));
            operation.setSetup(setup);
            operation.setMaterial(material);
            Station station = new Station();
            station.setName(resultSet.getString("s_name"));
            stationOperationMap.put(station, operation);
        }
        connection.close();
        return stationOperationMap;
    }
}
