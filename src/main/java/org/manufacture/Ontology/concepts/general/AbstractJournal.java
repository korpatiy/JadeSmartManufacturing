package org.manufacture.Ontology.concepts.general;


import jade.content.Concept;

/**
 * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#AbsctractJournal
 *
 * @author OntologyBeanGenerator v4.1
 * @version 2021/03/9, 22:46:25
 */
public abstract class AbstractJournal implements Concept {

    private static final long serialVersionUID = -1311739020448369233L;

    private String _internalInstanceName = null;

    public AbstractJournal() {
        this._internalInstanceName = "";
    }

    public AbstractJournal(String instance_name) {
        this._internalInstanceName = instance_name;
    }

    public String toString() {
        return _internalInstanceName;
    }

    /**
     * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#endDate
     */
    private String endDate;

    public void setEndDate(String value) {
        this.endDate = value;
    }

    public String getEndDate() {
        return this.endDate;
    }

    /**
     * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#startDate
     */
    private String startDate;

    public void setStartDate(String value) {
        this.startDate = value;
    }

    public String getStartDate() {
        return this.startDate;
    }

    /**
     * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#status
     */
    private String status;

    public void setStatus(String value) {
        this.status = value;
    }

    public String getStatus() {
        return this.status;
    }

}
