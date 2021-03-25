package org.manufacture.API;

import org.manufacture.dbConnection.QueryExecutor;

@Deprecated
public interface QueryExecutorBuilder {
    /**
     * @return экземпляр {@link QueryExecutor}
     */
    default QueryExecutorService build() {
        return QueryExecutor.getQueryExecutor();
    }
}
