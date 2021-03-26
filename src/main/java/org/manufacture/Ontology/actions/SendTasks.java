package org.manufacture.Ontology.actions;


import jade.util.leap.Iterator;
import jade.util.leap.List;
import org.manufacture.Ontology.concepts.domain.Operation;

/**
* Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#SendTasks
* @author OntologyBeanGenerator v4.1
* @version 2021/03/25, 22:27:12
*/
public interface SendTasks extends jade.content.AgentAction {

   /**
   * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#operations
   */
   public void addOperations(Operation elem);
   public boolean removeOperations(Operation elem);
   public void clearAllOperations();
   public Iterator getAllOperations();
   public List getOperations();
   public void setOperations(List l);

}
