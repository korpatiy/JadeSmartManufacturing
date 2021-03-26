package org.manufacture.Ontology.concepts.domain;


import org.manufacture.Ontology.concepts.general.Item;

/**
* Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#Function
* @author OntologyBeanGenerator v4.1
* @version 2021/03/25, 22:27:12
*/
public interface Function extends Item {

   /**
   * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#executedOnMaterial
   */
   public void setExecutedOnMaterial(Material value);
   public Material getExecutedOnMaterial();

}
