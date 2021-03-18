package org.manufacture.dbConnection;

import org.manufacture.Ontology.concepts.domain.Resource;
import org.manufacture.Ontology.concepts.domain.Station;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbQueryExecutor {

    private static DbQueryExecutor dbQueryExecutor;
    DBConnection dbConnection = DBConnection.getConnection();

    private DbQueryExecutor() {
    }

    public static DbQueryExecutor getDbQueryExecutor() {
        if (dbQueryExecutor == null) {
            return new DbQueryExecutor();
        }
        return dbQueryExecutor;
    }

    public List<Resource> seekAgents() throws SQLException {
        Connection connection = DriverManager.getConnection(ConnectConstants.DB_URL, ConnectConstants.USER, ConnectConstants.PASSWORD);
        PreparedStatement getAgents = connection.prepareStatement("SELECT r.name as rname, s.name as sname, r.description, r.type from resource r JOIN station s ON r.station_id = s.id");
        ResultSet agents = getAgents.executeQuery();
        System.out.println(agents);
        List<Resource> resources = new ArrayList<>();
        while (agents.next()) {
            Resource agent = new Resource();
            agent.setName(agents.getString("rname"));
            agent.setType(agents.getString("type"));
            //agent.setDescription(agents.getString("description")); упущенно в рамках примера
            Station station = new Station();
            station.setName(agents.getString("sname"));
            //station description упущенно в рамках примера
            agent.setStation(station);
            resources.add(agent);
        }
        connection.close();
        return resources;
    }
}
