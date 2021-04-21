package org.manufacture.API;

import org.manufacture.Ontology.concepts.domain.ManufactureJournal;
import org.manufacture.Ontology.concepts.domain.Order;
import org.manufacture.Ontology.concepts.domain.ProductionResource;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public interface QueryExecutorService {

    Order processOrder(String productName, Date date, int qty) throws SQLException;

    List<ProductionResource> seekAgents() throws SQLException;

    void insertOJ(ManufactureJournal manufactureJournal, int id, int orderId) throws SQLException;

    // int seekPlanId(String productName) throws SQLException;

    // Plan seekPlan(String productName) throws SQLException;
    // T seekEntity(String entity, String entityName, Class<T extends AbstractItem> type) throws SQLException;
}
