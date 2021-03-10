package Ontology.concepts.domain;


import Ontology.concepts.general.AbstractJournal;
import jade.util.leap.ArrayList;
import jade.util.leap.Iterator;
import jade.util.leap.List;

/**
* Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#OperationJournal
* @author OntologyBeanGenerator v4.1
* @version 2021/03/9, 22:46:25
*/
public class OperationJournal extends AbstractJournal {

  private static final long serialVersionUID = -1311739020448369233L;

  private String _internalInstanceName = null;

  public OperationJournal() {
    this._internalInstanceName = "";
  }

  public OperationJournal(String instance_name) {
    this._internalInstanceName = instance_name;
  }

  public String toString() {
    return _internalInstanceName;
  }

   /**
   * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#failures
   */
   private List failures = new ArrayList();
   public void addFailures(Failure elem) { 
     failures.add(elem);
   }
   public boolean removeFailures(Failure elem) {
     boolean result = failures.remove(elem);
     return result;
   }
   public void clearAllFailures() {
     failures.clear();
   }
   public Iterator getAllFailures() {return failures.iterator(); }
   public List getFailures() {return failures; }
   public void setFailures(List l) {failures = l; }

   /**
   * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#operation
   */
   private Operation operation;
   public void setOperation(Operation value) { 
    this.operation=value;
   }
   public Operation getOperation() {
     return this.operation;
   }

}
