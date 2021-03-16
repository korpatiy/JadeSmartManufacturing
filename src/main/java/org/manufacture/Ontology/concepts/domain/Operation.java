package org.manufacture.Ontology.concepts.domain;


import org.manufacture.Ontology.concepts.general.AbstractItem;
import jade.util.leap.ArrayList;
import jade.util.leap.Iterator;
import jade.util.leap.List;

/**
 * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#Operation
 *
 * @author OntologyBeanGenerator v4.1
 * @version 2021/03/9, 22:46:25
 */
public class Operation extends AbstractItem {

    private static final long serialVersionUID = -1311739020448369233L;

    private String _internalInstanceName = null;

    public Operation() {
        this._internalInstanceName = "";
    }

    public Operation(String instance_name) {
        this._internalInstanceName = instance_name;
    }

    public String toString() {
        return _internalInstanceName;
    }

    /**
     * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#funtion
     */
    private Function function;

    public void setFunction(Function value) {
        this.function = value;
    }

    public Function getFunction() {
        return this.function;
    }

    /**
     * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#materials
     */
    private List materials = new ArrayList();

    public void addMaterials(Material elem) {
        materials.add(elem);
    }

    public boolean removeMaterials(Material elem) {
        return materials.remove(elem);
    }

    public void clearAllMaterials() {
        materials.clear();
    }

    public Iterator getAllMaterials() {
        return materials.iterator();
    }

    public List getMaterials() {
        return materials;
    }

    public void setMaterials(List l) {
        materials = l;
    }

    /**
     * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#setup
     */
    private Setup setup;

    public void setSetup(Setup value) {
        this.setup = value;
    }

    public Setup getSetup() {
        return this.setup;
    }

    /**
     * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#duration
     */
    private String duration;

    public void setDuration(String value) {
        this.duration = value;
    }

    public String getDuration() {
        return this.duration;
    }
}
