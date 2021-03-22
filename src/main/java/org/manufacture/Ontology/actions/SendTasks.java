package org.manufacture.Ontology.actions;

import jade.content.AgentAction;
import jade.util.leap.ArrayList;
import jade.util.leap.Iterator;
import jade.util.leap.List;
import org.manufacture.Ontology.concepts.domain.Operation;

public class SendTasks implements AgentAction {

    private List operations = new ArrayList();

    public void addOperation(Operation elem) {
        operations.add(elem);
    }

    public boolean removeOperation(Operation elem) {
        return operations.remove(elem);
    }

    public void clearAllSOperations() {
        operations.clear();
    }

    public Iterator getAllOperations() {
        return operations.iterator();
    }

    public List getOperations() {
        return operations;
    }

    public void setOperations(List l) {
        operations = l;
    }
}
