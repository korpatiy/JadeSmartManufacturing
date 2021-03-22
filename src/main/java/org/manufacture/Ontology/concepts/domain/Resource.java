package org.manufacture.Ontology.concepts.domain;


import jade.content.Concept;
import org.manufacture.Ontology.concepts.general.AbstractItem;
import jade.util.leap.ArrayList;
import jade.util.leap.Iterator;
import jade.util.leap.List;

/**
 * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#Resourse
 *
 * @author OntologyBeanGenerator v4.1
 * @version 2021/03/9, 22:46:25
 */
public class Resource extends AbstractItem {

    private static final long serialVersionUID = -1311739020448369233L;

    private String _internalInstanceName = null;

    public Resource() {
        this._internalInstanceName = "";
    }

    public Resource(String instance_name) {
        this._internalInstanceName = instance_name;
    }

    public String toString() {
        return _internalInstanceName;
    }



    /**
     * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#type
     */
    private String type;

    public void setType(String value) {
        this.type = value;
    }

    public String getType() {
        return this.type;
    }

    /**
     * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#stations
     */
    private Station station;

    public void setStation(Station value) {
        this.station = value;
    }

    public Station getStation() {
        return this.station;
    }


    /**
     * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#tools
     */
    private List tools = new ArrayList();

    public void addTools(Tool elem) {
        tools.add(elem);
    }

    public boolean removeTools(Tool elem) {
        boolean result = tools.remove(elem);
        return result;
    }

    public void clearAllTools() {
        tools.clear();
    }

    public Iterator getAllTools() {
        return tools.iterator();
    }

    public List getTools() {
        return tools;
    }

    public void setTools(List l) {
        tools = l;
    }

}
