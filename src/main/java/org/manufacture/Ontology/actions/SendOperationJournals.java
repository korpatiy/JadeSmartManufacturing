package org.manufacture.Ontology.actions;


import jade.util.leap.Iterator;
import jade.util.leap.List;
import org.manufacture.Ontology.concepts.domain.OperationJournal;

/**
* Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#SendOperationJournals
* @author OntologyBeanGenerator v4.1
* @version 2021/03/25, 22:27:12
*/
public interface SendOperationJournals extends jade.content.AgentAction {

   /**
   * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#operationJournal
   */
   public void addOperationJournal(OperationJournal elem);
   public boolean removeOperationJournal(OperationJournal elem);
   public void clearAllOperationJournal();
   public Iterator getAllOperationJournal();
   public List getOperationJournal();
   public void setOperationJournal(List l);

}
