package org.manufacture.Ontology.concepts.domain.domainImpl;


import jade.util.leap.ArrayList;
import jade.util.leap.Iterator;
import jade.util.leap.List;
import org.manufacture.Ontology.concepts.domain.*;

/**
 * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#Order
 *
 * @author OntologyBeanGenerator v4.1
 * @version 2021/04/16, 20:40:39
 */
public class DefaultOrder implements Order {

    private static final long serialVersionUID = 3647375170601921857L;

    private String _internalInstanceName = null;

    public DefaultOrder() {
        this._internalInstanceName = "";
    }

    public DefaultOrder(String instance_name) {
        this._internalInstanceName = instance_name;
    }

    public String toString() {
        return _internalInstanceName;
    }

    /**
     * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#quantity
     */
    private int quantity;

    public void setQuantity(int value) {
        this.quantity = value;
    }

    public int getQuantity() {
        return this.quantity;
    }

    /**
     * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#formedOnProduct
     */
    private Product formedOnProduct;

    public void setFormedOnProduct(Product value) {
        this.formedOnProduct = value;
    }

    public Product getFormedOnProduct() {
        return this.formedOnProduct;
    }

    /**
     * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#dueDate
     */
    private String dueDate;

    public void setDueDate(String value) {
        this.dueDate = value;
    }

    public String getDueDate() {
        return this.dueDate;
    }

    /**
     * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#executedByPlan
     */
    private Plan executedByPlan;

    public void setExecutedByPlan(Plan value) {
        this.executedByPlan = value;
    }

    public Plan getExecutedByPlan() {
        return this.executedByPlan;
    }
}
