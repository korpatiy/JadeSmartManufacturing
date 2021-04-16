package org.manufacture.Ontology.actions;


import jade.util.leap.Iterator;
import jade.util.leap.List;
import org.manufacture.Ontology.concepts.domain.Operation;

/**
* Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#SendTasks
* @author OntologyBeanGenerator v4.1
* @version 2021/04/16, 20:40:39
*/
public interface SendTasks extends jade.content.AgentAction {

   /**
    * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#hasOperations
    */
   public void addHasOperations(Operation elem);
   public boolean removeHasOperations(Operation elem);
   public void clearAllHasOperations();
   public Iterator getAllHasOperations();
   public List getHasOperations();
   public void setHasOperations(List l);
}
