package Ontology.concepts.domain;


import Ontology.concepts.general.AbstractItem;
import jade.util.leap.ArrayList;
import jade.util.leap.Iterator;
import jade.util.leap.List;

/**
 * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#Plan
 *
 * @author OntologyBeanGenerator v4.1
 * @version 2021/03/9, 22:46:25
 */
public class Plan extends AbstractItem {

    private static final long serialVersionUID = -1311739020448369233L;

    private String _internalInstanceName = null;

    public Plan() {
        this._internalInstanceName = "";
    }

    public Plan(String instance_name) {
        this._internalInstanceName = instance_name;
    }

    public String toString() {
        return _internalInstanceName;
    }

    /**
     * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#operations
     */
    private List operations = new ArrayList();

    public void addOperations(Operation elem) {
        operations.add(elem);
    }

    public boolean removeOperations(Operation elem) {
        return operations.remove(elem);
    }

    public void clearAllOperations() {
        operations.clear();
    }

    public Iterator getAllOperations() {
        return operations.iterator();
    }

    public List getOperations() {
        return operations;
    }

    public void setOperations(List l) {
        operations = l;
    }
}
