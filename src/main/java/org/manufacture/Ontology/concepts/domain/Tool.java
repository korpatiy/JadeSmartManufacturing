package org.manufacture.Ontology.concepts.domain;


import org.manufacture.Ontology.concepts.general.AbstractItem;

/**
 * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#Tool
 *
 * @author OntologyBeanGenerator v4.1
 * @version 2021/03/9, 22:46:25
 */
public class Tool extends AbstractItem {

    private static final long serialVersionUID = -1311739020448369233L;

    private String _internalInstanceName = null;

    public Tool() {
        this._internalInstanceName = "";
    }

    public Tool(String instance_name) {
        this._internalInstanceName = instance_name;
    }

    public String toString() {
        return _internalInstanceName;
    }
}
