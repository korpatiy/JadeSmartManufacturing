package org.manufacture.dbConnection;

import org.manufacture.Ontology.concepts.domain.Resource;

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

    public void seekAgents() throws SQLException {
        Connection conn = DriverManager.getConnection(ConnectConstants.DB_URL, ConnectConstants.USER, ConnectConstants.PASSWORD);
        PreparedStatement getAgents = conn.prepareStatement("SELECT * FROM resource");
        ResultSet agents = getAgents.executeQuery();
        List<Resource> resources = new ArrayList<>();
        while (agents.next()) {
            Resource agent = new Resource();
            agent.setName(agents.getString("name"));
            agent.setDescription(agents.getString("description"));
            agent.setLocation(agents.getString("location"));
            agent.setType(agents.getString("type"));
            resources.add(agent);
        }
        conn.close();
        int x = 5;
    }
}
