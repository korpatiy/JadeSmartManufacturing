package org.manufacture.Ontology.concepts.domain;

import jade.util.leap.ArrayList;
import jade.util.leap.Iterator;
import jade.util.leap.List;
import org.manufacture.Ontology.concepts.general.AbstractJournal;

public class StationJournal extends AbstractJournal {
    /**
     * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#operationJournals
     */
    private List operationJournals = new ArrayList();

    public void addOperationJournals(OperationJournal elem) {
        operationJournals.add(elem);
    }

    public boolean removeOperationJournals(OperationJournal elem) {
        boolean result = operationJournals.remove(elem);
        return result;
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
