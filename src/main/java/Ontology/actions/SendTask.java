package Ontology.actions;


import Ontology.concepts.domain.Operation;
import jade.content.AgentAction;

/**
 * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#SendTask
 *
 * @author OntologyBeanGenerator v4.1
 * @version 2021/03/9, 22:46:25
 */
public class SendTask implements AgentAction {

    private static final long serialVersionUID = -1311739020448369233L;

    private String _internalInstanceName = null;

    public SendTask() {
        this._internalInstanceName = "";
    }

    public SendTask(String instance_name) {
        this._internalInstanceName = instance_name;
    }

    public String toString() {
        return _internalInstanceName;
    }

    /**
     * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#operation
     */
    private Operation operation;

    public void setOperation(Operation value) {
        this.operation = value;
    }

    public Operation getOperation() {
        return this.operation;
    }

}
