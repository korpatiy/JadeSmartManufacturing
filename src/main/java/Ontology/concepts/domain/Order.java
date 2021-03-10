package Ontology.concepts.domain;


import Ontology.concepts.general.AbstractItem;

/**
 * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#Order
 *
 * @author OntologyBeanGenerator v4.1
 * @version 2021/03/9, 22:46:25
 */
public class Order extends AbstractItem {

    private static final long serialVersionUID = -1311739020448369233L;

    private String _internalInstanceName = null;

    public Order() {
        this._internalInstanceName = "";
    }

    public Order(String instance_name) {
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
     * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#startDate
     */
    private String startDate;

    public void setStartDate(String value) {
        this.startDate = value;
    }

    public String getStartDate() {
        return this.startDate;
    }

    /**
     * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#product
     */
    private Product product;

    public void setProduct(Product value) {
        this.product = value;
    }

    public Product getProduct() {
        return this.product;
    }

}
