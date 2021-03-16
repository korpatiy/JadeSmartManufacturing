package org.manufacture.Ontology.actions;


import jade.util.leap.ArrayList;
import jade.util.leap.Iterator;
import jade.util.leap.List;
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
     * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#manufactureJournals
     */
    private List manufactureJournals = new ArrayList();
    public void addManufactureJournals(ManufactureJournal elem) {
        manufactureJournals.add(elem);
    }
    public boolean removeManufactureJournals(ManufactureJournal elem) {
        boolean result = manufactureJournals.remove(elem);
        return result;
    }
    public void clearAllManufactureJournals() {
        manufactureJournals.clear();
    }
    public Iterator getAllManufactureJournals() {return manufactureJournals.iterator(); }
    public List getManufactureJournals() {return manufactureJournals; }
    public void setManufactureJournals(List l) {manufactureJournals = l; }

}
