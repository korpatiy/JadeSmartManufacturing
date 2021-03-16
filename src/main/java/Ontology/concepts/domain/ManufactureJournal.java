package Ontology.concepts.domain;


import Ontology.concepts.general.AbstractJournal;
import jade.util.leap.ArrayList;
import jade.util.leap.Iterator;
import jade.util.leap.List;

/**
 * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#ManufactureJournal
 *
 * @author OntologyBeanGenerator v4.1
 * @version 2021/03/9, 22:46:25
 */
public class ManufactureJournal extends AbstractJournal {

    private static final long serialVersionUID = -1311739020448369233L;

    private String _internalInstanceName = null;

    public ManufactureJournal() {
        this._internalInstanceName = "";
    }

    public ManufactureJournal(String instance_name) {
        this._internalInstanceName = instance_name;
    }

    public String toString() {
        return _internalInstanceName;
    }

    /**
     * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#operationJournals
     */
    private List operationJournals = new ArrayList();

    public void addOperationJournals(OperationJournal elem) {
        operationJournals.add(elem);
    }

    public boolean removeOperationJournals(OperationJournal elem) {
        return operationJournals.remove(elem);
    }

    public void clearAllOperationJournals() {
        operationJournals.clear();
    }

    public Iterator getAllOperationJournals() {
        return operationJournals.iterator();
    }

    public List getOperationJournals() {
        return operationJournals;
    }

    public void setOperationJournals(List l) {
        operationJournals = l;
    }
}
