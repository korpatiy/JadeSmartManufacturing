package org.manufacture.Ontology.actions;

import jade.content.AgentAction;
import org.manufacture.Ontology.concepts.domain.Order;

public class SendOrder implements AgentAction {

    /**
     * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#order
     */
    private Order order;

    public void setOrder(Order value) {
        this.order = value;
    }

    public Order getOrder() {
        return this.order;
    }

}
