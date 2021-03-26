package org.manufacture.Ontology.concepts.domain;


import jade.util.leap.Iterator;
import jade.util.leap.List;
import org.manufacture.Ontology.ManufactureOntology;
import org.manufacture.Ontology.concepts.general.Journal;

/**
 * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#OperationJournal
 *
 * @author OntologyBeanGenerator v4.1
 * @version 2021/03/25, 22:27:12
 */
public interface OperationJournal extends Journal {

    /**
     * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#describesOperation
     */
    public void setDescribesOperation(Operation value);

    public Operation getDescribesOperation();

    /**
     * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#hasFailures
     */
    public void addHasFailures(Failure elem);

    public boolean removeHasFailures(Failure elem);

    public void clearAllHasFailures();

    public Iterator getAllHasFailures();

    public List getHasFailures();

    public void setHasFailures(List l);

    /**
     * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#hasResource
     */
    public void setHasResource(Resource value);

    public Resource getHasResource();

}
