package org.manufacture.Ontology.actions;


import org.manufacture.Ontology.concepts.domain.ManufactureJournal;
import jade.content.AgentAction;

/**
 * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#SendManufactureJournal
 *
 * @author OntologyBeanGenerator v4.1
 * @version 2021/03/9, 22:46:25
 */
public class SendManufactureJournal implements AgentAction {

    private static final long serialVersionUID = -1311739020448369233L;

    private String _internalInstanceName = null;

    public SendManufactureJournal() {
        this._internalInstanceName = "";
    }

    public SendManufactureJournal(String instance_name) {
        this._internalInstanceName = instance_name;
    }

    public String toString() {
        return _internalInstanceName;
    }

    /**
     * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#manufactureJournal
     */
    private ManufactureJournal manufactureJournal;

    public void setManufactureJournal(ManufactureJournal value) {
        this.manufactureJournal = value;
    }

    public ManufactureJournal getManufactureJournal() {
        return this.manufactureJournal;
    }

}
