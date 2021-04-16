package org.manufacture.Ontology.concepts.domain;


import jade.util.leap.Iterator;
import jade.util.leap.List;
import org.manufacture.Ontology.concepts.general.Journal;

/**
* Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#ManufactureJournal
* @author OntologyBeanGenerator v4.1
* @version 2021/04/16, 20:40:39
*/
public interface ManufactureJournal extends Journal {

   /**
   * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#hasOperationJournals
   */
   public void addHasOperationJournals(OperationJournal elem);
   public boolean removeHasOperationJournals(OperationJournal elem);
   public void clearAllHasOperationJournals();
   public Iterator getAllHasOperationJournals();
   public List getHasOperationJournals();
   public void setHasOperationJournals(List l);

}
