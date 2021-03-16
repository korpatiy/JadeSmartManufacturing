package org.manufacture.Ontology.concepts.general;

import jade.content.Concept;

/**
 * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#AbstractItem
 *
 * @author OntologyBeanGenerator v4.1
 * @version 2021/03/9, 22:46:25
 */
public abstract class AbstractItem implements Concept {

    private static final long serialVersionUID = -1311739020448369233L;

    private String _internalInstanceName = null;

    public AbstractItem() {
        this._internalInstanceName = "";
    }

    public AbstractItem(String instance_name) {
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

}
