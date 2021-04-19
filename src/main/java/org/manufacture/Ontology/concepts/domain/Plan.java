package org.manufacture.Ontology.concepts.domain;


import jade.util.leap.Iterator;
import jade.util.leap.List;

/**
* Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#Plan
* @author OntologyBeanGenerator v4.1
* @version 2021/04/16, 20:40:39
*/
public interface Plan extends jade.content.Concept {

   /**
   * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#id
   */
   public void setId(int value);
   public int getId();

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
