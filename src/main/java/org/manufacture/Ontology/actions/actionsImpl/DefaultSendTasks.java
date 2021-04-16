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
 * @version 2021/04/16, 20:40:39
 */
public class DefaultSendTasks implements SendTasks {

    private static final long serialVersionUID = 84152853867242224L;

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
     * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#hasOperations
     */
    private List hasOperations = new ArrayList();

    public void addHasOperations(Operation elem) {
        hasOperations.add(elem);
    }

    public boolean removeHasOperations(Operation elem) {
        boolean result = hasOperations.remove(elem);
        return result;
    }

    public void clearAllHasOperations() {
        hasOperations.clear();
    }

    public Iterator getAllHasOperations() {
        return hasOperations.iterator();
    }

    public List getHasOperations() {
        return hasOperations;
    }

    public void setHasOperations(List l) {
        hasOperations = l;
    }
}
