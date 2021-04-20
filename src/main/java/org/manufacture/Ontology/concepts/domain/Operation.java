package org.manufacture.Ontology.concepts.domain;


import org.manufacture.Ontology.concepts.general.Item;

/**
* Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#Operation
* @author OntologyBeanGenerator v4.1
* @version 2021/04/16, 20:40:39
*/
public interface Operation extends Item {

   /**
   * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#perfomedOnStation
   */
   public void setPerformedOnStation(Station value);
   public Station getPerformedOnStation();

   /**
   * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#hasFunction
   */
   public void setHasFunction(Function value);
   public Function getHasFunction();

   /**
   * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#duration
   */
   public void setDuration(int value);
   public int getDuration();

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
