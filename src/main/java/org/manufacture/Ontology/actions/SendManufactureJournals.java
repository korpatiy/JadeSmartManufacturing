package org.manufacture.Ontology.actions;


import jade.util.leap.Iterator;
import jade.util.leap.List;
import org.manufacture.Ontology.concepts.domain.ManufactureJournal;

/**
 * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#SendManufactureJournals
 *
 * @author OntologyBeanGenerator v4.1
 * @version 2021/03/25, 22:27:12
 */
public interface SendManufactureJournals extends jade.content.AgentAction {

    /**
     * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#hasManufactureJournals
     */
    public void addHasManufactureJournals(ManufactureJournal elem);

    public boolean removeHasManufactureJournals(ManufactureJournal elem);

    public void clearAllHasManufactureJournals();

    public Iterator getAllHasManufactureJournals();

    public List getHasManufactureJournals();

    public void setHasManufactureJournals(List l);

}
