package org.manufacture.Ontology.concepts.domain;


import org.manufacture.Ontology.concepts.general.AbstractJournal;
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
     * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#stationJournals
     */
    private List stationJournals = new ArrayList();

    public void addStationJournals(StationJournal elem) {
        stationJournals.add(elem);
    }

    public boolean removeStationJournals(StationJournal elem) {
        boolean result = stationJournals.remove(elem);
        return result;
    }

    public void clearAllStationJournals() {
        stationJournals.clear();
    }

    public Iterator getAllStationJournals() {
        return stationJournals.iterator();
    }

    public List getStationJournals() {
        return stationJournals;
    }

    public void setStationJournals(List l) {
        stationJournals = l;
    }
}
