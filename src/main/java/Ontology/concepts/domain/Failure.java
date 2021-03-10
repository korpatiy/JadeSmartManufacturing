package Ontology.concepts.domain;


import Ontology.concepts.general.AbstractItem;

/**
* Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#Failure
* @author OntologyBeanGenerator v4.1
* @version 2021/03/9, 22:46:25
*/
public class Failure extends AbstractItem {

  private static final long serialVersionUID = -1311739020448369233L;

  private String _internalInstanceName = null;

  public Failure() {
    this._internalInstanceName = "";
  }

  public Failure(String instance_name) {
    this._internalInstanceName = instance_name;
  }

  public String toString() {
    return _internalInstanceName;
  }

   /**
   * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#occurrenceDate
   */
   private String occurrenceDate;
   public void setOccurrenceDate(String value) { 
    this.occurrenceDate=value;
   }
   public String getOccurrenceDate() {
     return this.occurrenceDate;
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

   /**
   * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#status
   */
   private String status;
   public void setStatus(String value) { 
    this.status=value;
   }
   public String getStatus() {
     return this.status;
   }

}
