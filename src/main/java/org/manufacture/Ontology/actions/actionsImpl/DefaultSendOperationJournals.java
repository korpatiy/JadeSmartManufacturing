package org.manufacture.Ontology.actions.actionsImpl;


import jade.util.leap.ArrayList;
import jade.util.leap.Iterator;
import jade.util.leap.List;
import org.manufacture.Ontology.actions.SendOperationJournals;
import org.manufacture.Ontology.concepts.domain.OperationJournal;

/**
 * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#SendOperationJournals
 *
 * @author OntologyBeanGenerator v4.1
 * @version 2021/03/25, 22:27:12
 */
public class DefaultSendOperationJournals implements SendOperationJournals {

    private static final long serialVersionUID = 5298226161551650755L;

    private String _internalInstanceName = null;

    public DefaultSendOperationJournals() {
        this._internalInstanceName = "";
    }

    public DefaultSendOperationJournals(String instance_name) {
        this._internalInstanceName = instance_name;
    }

    public String toString() {
        return _internalInstanceName;
    }

    /**
     * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#operationJournal
     */
    private List operationJournal = new ArrayList();

    public void addOperationJournal(OperationJournal elem) {
        operationJournal.add(elem);
    }

    public boolean removeOperationJournal(OperationJournal elem) {
        boolean result = operationJournal.remove(elem);
        return result;
    }

    public void clearAllOperationJournal() {
        operationJournal.clear();
    }

    public Iterator getAllOperationJournal() {
        return operationJournal.iterator();
    }

    public List getOperationJournal() {
        return operationJournal;
    }

    public void setOperationJournal(List l) {
        operationJournal = l;
    }

}
