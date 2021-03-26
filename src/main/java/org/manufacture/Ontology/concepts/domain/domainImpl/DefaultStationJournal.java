package org.manufacture.Ontology.concepts.domain.domainImpl;


import jade.util.leap.ArrayList;
import jade.util.leap.Iterator;
import jade.util.leap.List;
import org.manufacture.Ontology.concepts.domain.OperationJournal;
import org.manufacture.Ontology.concepts.domain.StationJournal;

import java.util.Date;

/**
 * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#StationJournal
 *
 * @author OntologyBeanGenerator v4.1
 * @version 2021/03/25, 22:27:12
 */
public class DefaultStationJournal implements StationJournal {

    private static final long serialVersionUID = 5298226161551650755L;

    private String _internalInstanceName = null;

    public DefaultStationJournal() {
        this._internalInstanceName = "";
    }

    public DefaultStationJournal(String instance_name) {
        this._internalInstanceName = instance_name;
    }

    public String toString() {
        return _internalInstanceName;
    }

    /**
     * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#endDate
     */
    private Date endDate;

    public void setEndDate(Date value) {
        this.endDate = value;
    }

    public Date getEndDate() {
        return this.endDate;
    }

    /**
     * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#hasOperationJournals
     */
    private List hasOperationJournals = new ArrayList();

    public void addHasOperationJournals(OperationJournal elem) {
        hasOperationJournals.add(elem);
    }

    public boolean removeHasOperationJournals(OperationJournal elem) {
        boolean result = hasOperationJournals.remove(elem);
        return result;
    }

    public void clearAllHasOperationJournals() {
        hasOperationJournals.clear();
    }

    public Iterator getAllHasOperationJournals() {
        return hasOperationJournals.iterator();
    }

    public List getHasOperationJournals() {
        return hasOperationJournals;
    }

    public void setHasOperationJournals(List l) {
        hasOperationJournals = l;
    }

    /**
     * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#startDate
     */
    private Date startDate;

    public void setStartDate(Date value) {
        this.startDate = value;
    }

    public Date getStartDate() {
        return this.startDate;
    }

    /**
     * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#status
     */
    private List status = new ArrayList();

    public void addStatus(String elem) {
        status.add(elem);
    }

    public boolean removeStatus(String elem) {
        boolean result = status.remove(elem);
        return result;
    }

    public void clearAllStatus() {
        status.clear();
    }

    public Iterator getAllStatus() {
        return status.iterator();
    }

    public List getStatus() {
        return status;
    }

    public void setStatus(List l) {
        status = l;
    }

}
