package org.manufacture.dbConnection;

import org.manufacture.API.QueryExecutorService;
import org.manufacture.Ontology.concepts.domain.*;
import org.manufacture.Ontology.concepts.domain.domainImpl.*;

import java.sql.*;
import java.util.*;
import java.util.Date;

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
    public List<ProductionResource> seekAgents() throws SQLException {
        connection = DriverManager.getConnection(ConnectConstants.DB_URL, ConnectConstants.USER, ConnectConstants.PASSWORD);
        PreparedStatement getAgents = connection.prepareStatement("SELECT r.id as r_id, r.name as rname, s.name as sname, r.type from resource r JOIN station s ON r.station_id = s.id");
        ResultSet resultSet = getAgents.executeQuery();
        List<ProductionResource> resources = new ArrayList<>();
        while (resultSet.next()) {
            ProductionResource agent = new DefaultProductionResource();
            agent.setName(resultSet.getString("rname"));
            agent.setType(resultSet.getString("type"));
            agent.setId(resultSet.getInt("r_id"));
            //Optional<String> x = Optional.ofNullable(resultSet.getString("type")).if;
            //agent.setDescription(agents.getString("description")); упущенно в рамках примера
            Station station = new DefaultStation();
            station.setName(resultSet.getString("sname"));

            agent.setLocatedOnStation(station);
            resources.add(agent);
        }

        //ВЕРНУТЬ OBJECT И ПОТОМ ИСКЛЮЧИТЬ ТУПО
        connection.close();
        return resources;
    }


    public void Test() throws SQLException {
        connection = DriverManager.getConnection(ConnectConstants.DB_URL, ConnectConstants.USER, ConnectConstants.PASSWORD);

        PreparedStatement statement = connection
                .prepareStatement("insert into manufacture_journal(end_date, start_date, status, product_order_id )\n" +
                        "VALUES (?,?,?, (select id from product_order where plan_id = ?));", Statement.RETURN_GENERATED_KEYS);

        Date x = new Date();

        statement.setTimestamp(1, new Timestamp(x.getTime()));
        statement.setTimestamp(2, new Timestamp(x.getTime()));
        statement.setString(3, "done");
        statement.setInt(4, 1);

        int affectedRows = statement.executeUpdate();
        int manufactureJID = 0;
        if (affectedRows == 0) {
            throw new SQLException("Creating failed, no rows affected.");
        }
        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                manufactureJID = generatedKeys.getInt(1);
            } else {
                throw new SQLException("Creating failed, no ID obtained.");
            }
        }
        connection.close();
    }

    @Override
    public void insertOJ(ManufactureJournal manufactureJournal, int planId, int orderId) throws SQLException {
        connection = DriverManager.getConnection(ConnectConstants.DB_URL, ConnectConstants.USER, ConnectConstants.PASSWORD);

        int manufactureJID = insertMJ(manufactureJournal, orderId);
        PreparedStatement statement;
        int affectedRows;

        statement = connection
                .prepareStatement("insert into operation_journal (end_date, start_date, status, resource_id, operation_id, manufacture_journal_id) " +
                        "VALUES (?,?,?,?,?,?);");

        for (int i = 0; i < manufactureJournal.getHasOperationJournals().size(); i++) {
            OperationJournal oJ = (OperationJournal) manufactureJournal.getHasOperationJournals().get(i);
            statement.setTimestamp(1, new Timestamp(oJ.getEndDate().getTime()));
            statement.setTimestamp(2, new Timestamp(oJ.getStartDate().getTime()));
            statement.setString(3, oJ.getStatus());
            statement.setInt(4, oJ.getHasResource().getId());
            statement.setInt(5, oJ.getDescribesOperation().getId());
            statement.setInt(6, manufactureJID);
            affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating failed, no rows affected.");
            }
        }

        connection.close();
    }

    private int insertMJ(ManufactureJournal manufactureJournal, int orderId) throws SQLException {
        PreparedStatement statement = connection
                .prepareStatement("insert into manufacture_journal(end_date, start_date, status, product_order_id )\n" +
                        "VALUES (?,?,?,?);", Statement.RETURN_GENERATED_KEYS);

        statement.setTimestamp(1, new Timestamp(manufactureJournal.getEndDate().getTime()));
        statement.setTimestamp(2, new Timestamp(manufactureJournal.getStartDate().getTime()));
        statement.setString(3, manufactureJournal.getStatus());
        statement.setInt(4, orderId);

        int affectedRows = statement.executeUpdate();
        int manufactureJID = 0;
        if (affectedRows == 0) {
            throw new SQLException("Creating failed, no rows affected.");
        }
        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                manufactureJID = generatedKeys.getInt(1);
            } else {
                throw new SQLException("Creating failed, no ID obtained.");
            }
        }
        return manufactureJID;
    }

    @Override
    public Order processOrder(String productName, Date date, int qty) throws SQLException {
        connection = DriverManager.getConnection(ConnectConstants.DB_URL, ConnectConstants.USER, ConnectConstants.PASSWORD);
        Plan plan = seekPlan(productName);
        Order order = new DefaultOrder();
        Product product = new DefaultProduct();
        product.setName(productName);
        order.setFormedOnProduct(product);
        order.setQuantity(qty);
        order.setDueDate(String.valueOf(date));
        order.setExecutedByPlan(plan);
        //insertOrder(order, productName, date);
        connection.close();
        return order;
    }

    private void insertOrder(Order order, String productName, Date date) throws SQLException {
        int productId = seekID(productName);
        PreparedStatement statement = connection
                .prepareStatement("insert into product_order (product_id, due_date, quantity, plan_id)" +
                        " VALUES (?,?,?,?);", Statement.RETURN_GENERATED_KEYS);
        java.sql.Date x = new java.sql.Date(date.getTime());
        statement.setInt(1, productId);
        statement.setTimestamp(2, new Timestamp(date.getTime()));
        statement.setInt(3, order.getQuantity());
        statement.setInt(4, order.getExecutedByPlan().getId());
        // statement.execute();
        int affectedRows = statement.executeUpdate();
        if (affectedRows == 0) {
            throw new SQLException("Creating failed, no rows affected.");
        }
        int orderId = 0;
        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                orderId = generatedKeys.getInt(1);
            } else {
                throw new SQLException("Creating failed, no ID obtained.");
            }
        }
        order.setId(orderId);
    }

    private int seekID(String productName) throws SQLException {
        int productId = 0;
        PreparedStatement getId = connection
                .prepareStatement("select id from product where name = ?;");
        getId.setString(1, productName);
        ResultSet resultSet = getId.executeQuery();
        while (resultSet.next()) {
            productId = resultSet.getInt("id");
        }
        return productId;
    }

    private Plan seekPlan(String productName) throws SQLException {
        Plan plan = new DefaultPlan();
        PreparedStatement getPlanId = connection
                .prepareStatement("select plan_id from product where name = ?;");
        getPlanId.setString(1, productName);
        ResultSet resultSet = getPlanId.executeQuery();
        while (resultSet.next()) {
            plan.setId(resultSet.getInt("plan_id"));
        }
        PreparedStatement getOperations = connection
                .prepareStatement("select s.name as s_name, o.id as o_id,o.name as o_name, o.duration, s2.name as s2_name, m.name as m_name, tool.name as t_name, f.name as f_name, m2.name as m2_name\n" +
                        "from plan_station_operation\n" +
                        "         join station s on s.id = plan_station_operation.station_id\n" +
                        "         join operation o on o.id = plan_station_operation.operation_id\n" +
                        "         join setup s2 on s2.id = o.setup_id\n" +
                        "         join tool on s2.tool_id = tool.id\n" +
                        "         join material m on m.id = o.material_id\n" +
                        "full join function f on o.function_id = f.id\n" +
                        "full join material m2 on m2.id = f.material_id\n" +
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
            if (resultSet.getString("m2_name") != null) {
                Material material1 = new DefaultMaterial();
                material1.setName(resultSet.getString("m2_name"));

                Function function = new DefaultFunction();
                function.setName(resultSet.getString("f_name"));
                function.setPerformedOverMaterial(material1);

                operation.setHasFunction(function);
            }

            operation.setId(resultSet.getInt("o_id"));
            operation.setName(resultSet.getString("o_name"));
            operation.setRequiresSetup(setup);
            operation.setPerformedOverMaterial(material);
            operation.setDuration(resultSet.getInt("duration"));
            operation.setPerformedOnStation(station);
            plan.addHasOperations(operation);
        }
        return plan;
    }
}
