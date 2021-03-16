package org.manufacture.Ontology.actions;


import org.manufacture.Ontology.concepts.domain.OperationJournal;
import jade.content.AgentAction;

/**
 * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#SendOperationJournal
 *
 * @author OntologyBeanGenerator v4.1
 * @version 2021/03/9, 22:46:25
 */
public class SendOperationJournal implements AgentAction {

    private static final long serialVersionUID = -1311739020448369233L;

    private String _internalInstanceName = null;

    public SendOperationJournal() {
        this._internalInstanceName = "";
    }

    public SendOperationJournal(String instance_name) {
        this._internalInstanceName = instance_name;
    }

    public String toString() {
        return _internalInstanceName;
    }

    /**
     * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#operationJournal
     */
    private OperationJournal operationJournal;

    public void setOperationJournal(OperationJournal value) {
        this.operationJournal = value;
    }

    public OperationJournal getOperationJournal() {
        return this.operationJournal;
    }

}
