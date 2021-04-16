package org.manufacture.Ontology.actions.actionsImpl;


import jade.util.leap.ArrayList;
import jade.util.leap.Iterator;
import jade.util.leap.List;
import org.manufacture.Ontology.actions.SendOperationJournals;
import org.manufacture.Ontology.concepts.domain.OperationJournal;

/**
* Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#SendOperationJournals
* @author OntologyBeanGenerator v4.1
* @version 2021/04/16, 20:40:39
*/
public class DefaultSendOperationJournals implements SendOperationJournals {

  private static final long serialVersionUID = 3647375170601921857L;

  private String _internalInstanceName = null;

  public DefaultSendOperationJournals() {
    this._internalInstanceName = "";
  }

  public DefaultSendOperationJournals(String instance_name) {
    this._internalInstanceName = instance_name;
  }

  public String toString() {
    return _internalInstanceName;
  }

   /**
   * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#operationJournals
   */
   private List operationJournals = new ArrayList();
   public void addOperationJournals(OperationJournal elem) {
     operationJournals.add(elem);
   }
   public boolean removeOperationJournals(OperationJournal elem) {
     boolean result = operationJournals.remove(elem);
     return result;
   }
   public void clearAllOperationJournals() {
     operationJournals.clear();
   }
   public Iterator getAllOperationJournals() {return operationJournals.iterator(); }
   public List getOperationJournals() {return operationJournals; }
   public void setOperationJournals(List l) {operationJournals = l; }

}
