package org.manufacture.Ontology.concepts.domain.domainImpl;


import org.manufacture.Ontology.concepts.domain.Function;
import org.manufacture.Ontology.concepts.domain.Material;

/**
 * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#Function
 *
 * @author OntologyBeanGenerator v4.1
 * @version 2021/04/16, 20:40:39
 */
public class DefaultFunction implements Function {

    private static final long serialVersionUID = 3647375170601921857L;

    private String _internalInstanceName = null;

    public DefaultFunction() {
        this._internalInstanceName = "";
    }

    public DefaultFunction(String instance_name) {
        this._internalInstanceName = instance_name;
    }

    @Override
    public String toString() {
        return "DefaultFunction{" +
                ", name='" + name + '\'' +
                ", performedOverMaterial=" + performedOverMaterial +
                ", id=" + id +
                '}';
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
     * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#performedOverMaterial
     */
    private Material performedOverMaterial;

    public void setPerformedOverMaterial(Material value) {
        this.performedOverMaterial = value;
    }

    public Material getPerformedOverMaterial() {
        return this.performedOverMaterial;
    }

    /**
     * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#id
     */
    private int id;

    public void setId(int value) {
        this.id = value;
    }

    public int getId() {
        return this.id;
    }

}
