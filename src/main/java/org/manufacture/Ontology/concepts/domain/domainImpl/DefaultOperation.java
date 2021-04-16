package org.manufacture.Ontology.concepts.domain.domainImpl;


import org.manufacture.Ontology.concepts.domain.*;

/**
* Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#Operation
* @author OntologyBeanGenerator v4.1
* @version 2021/04/16, 20:40:39
*/
public class DefaultOperation implements Operation {

  private static final long serialVersionUID = 3647375170601921857L;

  private String _internalInstanceName = null;

  public DefaultOperation() {
    this._internalInstanceName = "";
  }

  public DefaultOperation(String instance_name) {
    this._internalInstanceName = instance_name;
  }

  public String toString() {
    return _internalInstanceName;
  }

   /**
   * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#description
   */
   private String description;
   public void setDescription(String value) { 
    this.description=value;
   }
   public String getDescription() {
     return this.description;
   }

   /**
   * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#perfomedOnStation
   */
   private Station perfomedOnStation;
   public void setPerfomedOnStation(Station value) { 
    this.perfomedOnStation=value;
   }
   public Station getPerfomedOnStation() {
     return this.perfomedOnStation;
   }

   /**
   * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#name
   */
   private String name;
   public void setName(String value) { 
    this.name=value;
   }
   public String getName() {
     return this.name;
   }

   /**
   * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#hasFunction
   */
   private Function hasFunction;
   public void setHasFunction(Function value) { 
    this.hasFunction=value;
   }
   public Function getHasFunction() {
     return this.hasFunction;
   }

   /**
   * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#duration
   */
   private int duration;
   public void setDuration(int value) { 
    this.duration=value;
   }
   public int getDuration() {
     return this.duration;
   }

   /**
   * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#performedOverMaterial
   */
   private Material performedOverMaterial;
   public void setPerformedOverMaterial(Material value) { 
    this.performedOverMaterial=value;
   }
   public Material getPerformedOverMaterial() {
     return this.performedOverMaterial;
   }

   /**
   * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#requiresSetup
   */
   private Setup requiresSetup;
   public void setRequiresSetup(Setup value) { 
    this.requiresSetup=value;
   }
   public Setup getRequiresSetup() {
     return this.requiresSetup;
   }

}
