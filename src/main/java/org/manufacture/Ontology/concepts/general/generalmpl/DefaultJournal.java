package org.manufacture.Ontology.concepts.general.generalmpl;


import jade.util.leap.ArrayList;
import jade.util.leap.Iterator;
import jade.util.leap.List;
import org.manufacture.Ontology.concepts.general.Journal;

import java.util.Date;

/**
* Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#Journal
* @author OntologyBeanGenerator v4.1
* @version 2021/03/25, 22:27:12
*/
public class DefaultJournal implements Journal {

  private static final long serialVersionUID = 5298226161551650755L;

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
