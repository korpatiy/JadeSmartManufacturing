package org.manufacture.Ontology.concepts.domain.domainImpl;


import jade.util.leap.ArrayList;
import jade.util.leap.Iterator;
import jade.util.leap.List;
import org.manufacture.Ontology.concepts.domain.Material;
import org.manufacture.Ontology.concepts.domain.Product;

/**
 * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#Product
 *
 * @author OntologyBeanGenerator v4.1
 * @version 2021/03/25, 22:27:12
 */
public class DefaultProduct implements Product {

    private static final long serialVersionUID = 5298226161551650755L;

    private String _internalInstanceName = null;

    public DefaultProduct() {
        this._internalInstanceName = "";
    }

    public DefaultProduct(String instance_name) {
        this._internalInstanceName = instance_name;
    }

    public String toString() {
        return _internalInstanceName;
    }

    /**
     * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#description
     */
    private String description;

    public void setDescription(String value) {
        this.description = value;
    }

    public String getDescription() {
        return this.description;
    }

    /**
     * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#name
     */
    private String name;

    public void setName(String value) {
        this.name = value;
    }

    public String getName() {
        return this.name;
    }

    /**
     * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#hasMaterials
     */
    private List hasMaterials = new ArrayList();

    public void addHasMaterials(Material elem) {
        hasMaterials.add(elem);
    }

    public boolean removeHasMaterials(Material elem) {
        boolean result = hasMaterials.remove(elem);
        return result;
    }

    public void clearAllHasMaterials() {
        hasMaterials.clear();
    }

    public Iterator getAllHasMaterials() {
        return hasMaterials.iterator();
    }

    public List getHasMaterials() {
        return hasMaterials;
    }

    public void setHasMaterials(List l) {
        hasMaterials = l;
    }

}
