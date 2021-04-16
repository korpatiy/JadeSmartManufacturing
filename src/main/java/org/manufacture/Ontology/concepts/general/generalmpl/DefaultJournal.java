package org.manufacture.Ontology.concepts.general.generalmpl;


import org.manufacture.Ontology.concepts.general.Journal;

import java.util.Date;

/**
* Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#Journal
* @author OntologyBeanGenerator v4.1
* @version 2021/04/16, 20:40:39
*/
public class DefaultJournal implements Journal {

  private static final long serialVersionUID = 3647375170601921857L;

  private String _internalInstanceName = null;

  public DefaultJournal() {
    this._internalInstanceName = "";
  }

  public DefaultJournal(String instance_name) {
    this._internalInstanceName = instance_name;
  }

  public String toString() {
    return _internalInstanceName;
  }

   /**
   * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#endDate
   */
   private Date endDate;
   public void setEndDate(Date value) {
    this.endDate=value;
   }
   public Date getEndDate() {
     return this.endDate;
   }

   /**
   * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#startDate
   */
   private Date startDate;
   public void setStartDate(Date value) {
    this.startDate=value;
   }
   public Date getStartDate() {
     return this.startDate;
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
