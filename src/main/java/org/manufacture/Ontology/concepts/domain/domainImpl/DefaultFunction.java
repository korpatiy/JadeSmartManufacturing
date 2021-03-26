package org.manufacture.Ontology.concepts.domain.domainImpl;

import org.manufacture.Ontology.concepts.domain.Function;
import org.manufacture.Ontology.concepts.domain.Material;

/**
* Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#Function
* @author OntologyBeanGenerator v4.1
* @version 2021/03/25, 22:27:12
*/
public class DefaultFunction implements Function {

  private static final long serialVersionUID = 5298226161551650755L;

  private String _internalInstanceName = null;

  public DefaultFunction() {
    this._internalInstanceName = "";
  }

  public DefaultFunction(String instance_name) {
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
   * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#executedOnMaterial
   */
   private Material executedOnMaterial;
   public void setExecutedOnMaterial(Material value) { 
    this.executedOnMaterial=value;
   }
   public Material getExecutedOnMaterial() {
     return this.executedOnMaterial;
   }

}
