package org.manufacture.Ontology.concepts.domain.domainImpl;


import org.manufacture.Ontology.concepts.domain.Function;
import org.manufacture.Ontology.concepts.domain.Material;
import org.manufacture.Ontology.concepts.domain.Operation;
import org.manufacture.Ontology.concepts.domain.Setup;

/**
 * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#Operation
 *
 * @author OntologyBeanGenerator v4.1
 * @version 2021/03/25, 22:27:12
 */
public class DefaultOperation implements Operation {

    private static final long serialVersionUID = 5298226161551650755L;

    private String _internalInstanceName = null;

    public DefaultOperation() {
        this._internalInstanceName = "";
    }

    public DefaultOperation(String instance_name) {
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
     * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#hasFunction
     */
    private Function hasFunction;

    public void setHasFunction(Function value) {
        this.hasFunction = value;
    }

    public Function getHasFunction() {
        return this.hasFunction;
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
     * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#requiresSetup
     */
    private Setup requiresSetup;

    public void setRequiresSetup(Setup value) {
        this.requiresSetup = value;
    }

    public Setup getRequiresSetup() {
        return this.requiresSetup;
    }

}
