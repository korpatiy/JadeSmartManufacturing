package Ontology.concepts.domain;

import Ontology.concepts.general.AbstractItem;
import jade.util.leap.ArrayList;
import jade.util.leap.Iterator;
import jade.util.leap.List;

/**
 * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#Product
 *
 * @author OntologyBeanGenerator v4.1
 * @version 2021/03/9, 22:46:25
 */
public class Product extends AbstractItem {

    private static final long serialVersionUID = -1311739020448369233L;

    private String _internalInstanceName = null;

    public Product() {
        this._internalInstanceName = "";
    }

    public Product(String instance_name) {
        this._internalInstanceName = instance_name;
    }

    public String toString() {
        return _internalInstanceName;
    }

    /**
     * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#plan
     */
    private Plan plan;

    public void setPlan(Plan value) {
        this.plan = value;
    }

    public Plan getPlan() {
        return this.plan;
    }

    /**
     * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#materials
     */
    private List materials = new ArrayList();

    public void addMaterials(Material elem) {
        materials.add(elem);
    }

    public boolean removeMaterials(Material elem) {
        boolean result = materials.remove(elem);
        return result;
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

}
