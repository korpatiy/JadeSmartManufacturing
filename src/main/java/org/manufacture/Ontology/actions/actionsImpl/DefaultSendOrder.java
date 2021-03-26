package org.manufacture.Ontology.actions.actionsImpl;


import org.manufacture.Ontology.actions.SendOrder;
import org.manufacture.Ontology.concepts.domain.Order;

/**
 * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#SendOrder
 *
 * @author OntologyBeanGenerator v4.1
 * @version 2021/03/25, 22:27:12
 */
public class DefaultSendOrder implements SendOrder {

    private static final long serialVersionUID = 5298226161551650755L;

    private String _internalInstanceName = null;

    public DefaultSendOrder() {
        this._internalInstanceName = "";
    }

    public DefaultSendOrder(String instance_name) {
        this._internalInstanceName = instance_name;
    }

    public String toString() {
        return _internalInstanceName;
    }

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
