package org.manufacture.API;

import org.manufacture.Ontology.concepts.domain.Operation;
import org.manufacture.Ontology.concepts.domain.Resource;
import org.manufacture.Ontology.concepts.domain.Station;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface QueryExecutorService {

    Map<Station, Operation> seekPlan(String planName) throws SQLException;

    List<Resource> seekAgents() throws SQLException;

    // T seekEntity(String entity, String entityName, Class<T extends AbstractItem> type) throws SQLException;
}
