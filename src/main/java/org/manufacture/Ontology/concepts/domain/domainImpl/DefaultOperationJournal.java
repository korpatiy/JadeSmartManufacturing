package org.manufacture.Ontology.concepts.domain.domainImpl;


import jade.util.leap.ArrayList;
import jade.util.leap.Iterator;
import jade.util.leap.List;
import org.manufacture.Ontology.concepts.domain.Failure;
import org.manufacture.Ontology.concepts.domain.Operation;
import org.manufacture.Ontology.concepts.domain.OperationJournal;
import org.manufacture.Ontology.concepts.general.Resource;

import java.util.Date;

/**
* Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#OperationJournal
* @author OntologyBeanGenerator v4.1
* @version 2021/04/16, 20:40:39
*/
public class DefaultOperationJournal implements OperationJournal {

  private static final long serialVersionUID = 3647375170601921857L;

  private String _internalInstanceName = null;

  public DefaultOperationJournal() {
    this._internalInstanceName = "";
  }

  public DefaultOperationJournal(String instance_name) {
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

   /**
   * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#hasResource
   */
   private Resource hasResource;
   public void setHasResource(Resource value) { 
    this.hasResource=value;
   }
   public Resource getHasResource() {
     return this.hasResource;
   }

   /**
   * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#describesOperation
   */
   private Operation describesOperation;
   public void setDescribesOperation(Operation value) { 
    this.describesOperation=value;
   }
   public Operation getDescribesOperation() {
     return this.describesOperation;
   }

   /**
   * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#hasFailures
   */
   private List hasFailures = new ArrayList();
   public void addHasFailures(Failure elem) {
     hasFailures.add(elem);
   }
   public boolean removeHasFailures(Failure elem) {
     boolean result = hasFailures.remove(elem);
     return result;
   }
   public void clearAllHasFailures() {
     hasFailures.clear();
   }
   public Iterator getAllHasFailures() {return hasFailures.iterator(); }
   public List getHasFailures() {return hasFailures; }
   public void setHasFailures(List l) {hasFailures = l; }

}
