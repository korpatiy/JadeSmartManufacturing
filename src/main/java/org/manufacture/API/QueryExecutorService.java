package org.manufacture.API;

import org.manufacture.Ontology.concepts.domain.Plan;
import org.manufacture.Ontology.concepts.domain.ProductionResource;

import java.sql.SQLException;
import java.util.List;

public interface QueryExecutorService {

    Plan seekPlan(String productName) throws SQLException;

    List<ProductionResource> seekAgents() throws SQLException;

    // int seekPlanId(String productName) throws SQLException;

    // Plan seekPlan(String productName) throws SQLException;
    // T seekEntity(String entity, String entityName, Class<T extends AbstractItem> type) throws SQLException;
}
