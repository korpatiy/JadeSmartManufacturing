package org.manufacture.Ontology.actions;


import jade.util.leap.Iterator;
import jade.util.leap.List;
import org.manufacture.Ontology.concepts.domain.OperationJournal;

/**
* Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#SendOperationJournals
* @author OntologyBeanGenerator v4.1
* @version 2021/04/16, 20:40:39
*/
public interface SendOperationJournals extends jade.content.AgentAction {

   /**
   * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#operationJournals
   */
   public void addOperationJournals(OperationJournal elem);
   public boolean removeOperationJournals(OperationJournal elem);
   public void clearAllOperationJournals();
   public Iterator getAllOperationJournals();
   public List getOperationJournals();
   public void setOperationJournals(List l);

}
