package org.manufacture.Ontology.concepts.domain;



/**
* Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#Setup
* @author OntologyBeanGenerator v4.1
* @version 2021/04/16, 20:40:39
*/
public interface Setup extends jade.content.Concept {

   /**
   * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#name
   */
   public void setName(String value);
   public String getName();

   /**
   * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#requiresTool
   */
   public void setRequiresTool(Tool value);
   public Tool getRequiresTool();

   /**
   * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#id
   */
   public void setId(int value);
   public int getId();

}
