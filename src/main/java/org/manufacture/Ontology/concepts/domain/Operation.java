package org.manufacture.Ontology.concepts.domain;


import org.manufacture.Ontology.concepts.general.Item;

/**
* Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#Operation
* @author OntologyBeanGenerator v4.1
* @version 2021/03/25, 22:27:12
*/
public interface Operation extends Item {

   /**
   * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#hasFunction
   */
   public void setHasFunction(Function value);
   public Function getHasFunction();

   /**
   * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#duration
   */
   public void setDuration(String value);
   public String getDuration();

   /**
   * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#performedOverMaterial
   */
   public void setPerformedOverMaterial(Material value);
   public Material getPerformedOverMaterial();

   /**
   * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#requiresSetup
   */
   public void setRequiresSetup(Setup value);
   public Setup getRequiresSetup();

}
