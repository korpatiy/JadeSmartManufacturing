package org.manufacture.Ontology.concepts.domain;


import org.manufacture.Ontology.concepts.general.AbstractItem;
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
     * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#stations
     */
    private List stations = new ArrayList();
    public void addStations(Station elem) {
        stations.add(elem);
    }
    public boolean removeStations(Station elem) {
        boolean result = stations.remove(elem);
        return result;
    }
    public void clearAllStations() {
        stations.clear();
    }
    public Iterator getAllStations() {return stations.iterator(); }
    public List getStations() {return stations; }
    public void setStations(List l) {stations = l; }

}
