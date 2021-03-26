package org.manufacture.Ontology.actions.actionsImpl;


import jade.util.leap.ArrayList;
import jade.util.leap.Iterator;
import jade.util.leap.List;
import org.manufacture.Ontology.actions.SendTasks;
import org.manufacture.Ontology.concepts.domain.Operation;

/**
 * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#SendTasks
 *
 * @author OntologyBeanGenerator v4.1
 * @version 2021/03/25, 22:27:12
 */
public class DefaultSendTasks implements SendTasks {

    private static final long serialVersionUID = 5298226161551650755L;

    private String _internalInstanceName = null;

    public DefaultSendTasks() {
        this._internalInstanceName = "";
    }

    public DefaultSendTasks(String instance_name) {
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
        boolean result = operations.remove(elem);
        return result;
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
