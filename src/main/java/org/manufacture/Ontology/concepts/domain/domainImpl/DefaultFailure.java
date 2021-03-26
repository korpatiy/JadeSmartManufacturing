package org.manufacture.Ontology.concepts.domain.domainImpl;


import jade.util.leap.ArrayList;
import jade.util.leap.Iterator;
import jade.util.leap.List;
import org.manufacture.Ontology.concepts.domain.Failure;

/**
* Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#Failure
* @author OntologyBeanGenerator v4.1
* @version 2021/03/25, 22:27:12
*/
public class DefaultFailure implements Failure {

  private static final long serialVersionUID = 5298226161551650755L;

  private String _internalInstanceName = null;

  public DefaultFailure() {
    this._internalInstanceName = "";
  }

  public DefaultFailure(String instance_name) {
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
   private List status = new ArrayList();
   public void addStatus(String elem) { 
     status.add(elem);
   }
   public boolean removeStatus(String elem) {
     boolean result = status.remove(elem);
     return result;
   }
   public void clearAllStatus() {
     status.clear();
   }
   public Iterator getAllStatus() {return status.iterator(); }
   public List getStatus() {return status; }
   public void setStatus(List l) {status = l; }

}
