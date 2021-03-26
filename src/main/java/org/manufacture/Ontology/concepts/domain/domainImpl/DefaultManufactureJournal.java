package org.manufacture.Ontology.concepts.domain.domainImpl;


import jade.util.leap.ArrayList;
import jade.util.leap.Iterator;
import jade.util.leap.List;
import org.manufacture.Ontology.concepts.domain.ManufactureJournal;
import org.manufacture.Ontology.concepts.domain.StationJournal;

import java.util.Date;

/**
 * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#ManufactureJournal
 *
 * @author OntologyBeanGenerator v4.1
 * @version 2021/03/25, 22:27:12
 */
public class DefaultManufactureJournal implements ManufactureJournal {

    private static final long serialVersionUID = 5298226161551650755L;

    private String _internalInstanceName = null;

    public DefaultManufactureJournal() {
        this._internalInstanceName = "";
    }

    public DefaultManufactureJournal(String instance_name) {
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
     * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#hasStationJournals
     */
    private List hasStationJournals = new ArrayList();

    public void addHasStationJournals(StationJournal elem) {
        hasStationJournals.add(elem);
    }

    public boolean removeHasStationJournals(StationJournal elem) {
        boolean result = hasStationJournals.remove(elem);
        return result;
    }

    public void clearAllHasStationJournals() {
        hasStationJournals.clear();
    }

    public Iterator getAllHasStationJournals() {
        return hasStationJournals.iterator();
    }

    public List getHasStationJournals() {
        return hasStationJournals;
    }

    public void setHasStationJournals(List l) {
        hasStationJournals = l;
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
