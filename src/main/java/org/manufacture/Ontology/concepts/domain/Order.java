package org.manufacture.Ontology.concepts.domain;


import jade.util.leap.ArrayList;
import jade.util.leap.Iterator;
import jade.util.leap.List;
import org.manufacture.Ontology.concepts.general.AbstractItem;

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
     * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#product
     */
    private Product product;

    public void setProduct(Product value) {
        this.product = value;
    }

    public Product getProduct() {
        return this.product;
    }

    /**
     * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#plan
     */
    private Plan plan;

    public void setPlan(Plan value) {
        this.plan = value;
    }

    public Plan getPlan() {
        return this.plan;
    }

    /**
     * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#manufactureJournals
     */
    private List manufactureJournals = new ArrayList();

    public void addManufactureJournals(ManufactureJournal elem) {
        manufactureJournals.add(elem);
    }

    public boolean removeManufactureJournals(ManufactureJournal elem) {
        boolean result = manufactureJournals.remove(elem);
        return result;
    }

    public void clearAllManufactureJournals() {
        manufactureJournals.clear();
    }

    public Iterator getAllManufactureJournals() {
        return manufactureJournals.iterator();
    }

    public List getManufactureJournals() {
        return manufactureJournals;
    }

    public void setManufactureJournals(List l) {
        manufactureJournals = l;
    }
}
