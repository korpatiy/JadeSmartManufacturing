package org.manufacture.Ontology.concepts.domain.domainImpl;


import org.manufacture.Ontology.concepts.domain.Setup;
import org.manufacture.Ontology.concepts.domain.Tool;

/**
* Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#Setup
* @author OntologyBeanGenerator v4.1
* @version 2021/04/16, 20:40:39
*/
public class DefaultSetup implements Setup {

  private static final long serialVersionUID = 3647375170601921857L;

  private String _internalInstanceName = null;

  public DefaultSetup() {
    this._internalInstanceName = "";
  }

  public DefaultSetup(String instance_name) {
    this._internalInstanceName = instance_name;
  }

  public String toString() {
    return _internalInstanceName;
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
   * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#requiresTool
   */
   private Tool requiresTool;
   public void setRequiresTool(Tool value) { 
    this.requiresTool=value;
   }
   public Tool getRequiresTool() {
     return this.requiresTool;
   }

   /**
   * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#id
   */
   private int id;
   public void setId(int value) { 
    this.id=value;
   }
   public int getId() {
     return this.id;
   }

}
