package org.manufacture.Ontology.concepts.domain;


import org.manufacture.Ontology.concepts.general.Item;

/**
* Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#Setup
* @author OntologyBeanGenerator v4.1
* @version 2021/03/25, 22:27:12
*/
public interface Setup extends Item {

   /**
   * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#requiresTool
   */
   public void setRequiresTool(Tool value);
   public Tool getRequiresTool();

}
