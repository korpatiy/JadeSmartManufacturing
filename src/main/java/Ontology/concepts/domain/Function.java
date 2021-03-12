package Ontology.concepts.domain;


import Ontology.concepts.general.AbstractItem;

/**
* Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#Function
* @author OntologyBeanGenerator v4.1
* @version 2021/03/9, 22:46:25
*/
public class Function extends AbstractItem {

  private static final long serialVersionUID = -1311739020448369233L;

  private String _internalInstanceName = null;

  public Function() {
    this._internalInstanceName = "";
  }

  public Function(String instance_name) {
    this._internalInstanceName = instance_name;
  }

  public String toString() {
    return _internalInstanceName;
  }


   /**
   * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#type
   */
   private String type;
   public void setType(String value) { 
    this.type=value;
   }
   public String getType() {
     return this.type;
   }

}
