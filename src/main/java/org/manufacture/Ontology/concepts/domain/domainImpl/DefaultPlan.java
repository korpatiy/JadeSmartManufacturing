package org.manufacture.Ontology.concepts.domain.domainImpl;


import jade.util.leap.ArrayList;
import jade.util.leap.Iterator;
import jade.util.leap.List;
import org.manufacture.Ontology.concepts.domain.Operation;
import org.manufacture.Ontology.concepts.domain.Plan;

/**
* Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#Plan
* @author OntologyBeanGenerator v4.1
* @version 2021/04/16, 20:40:39
*/
public class DefaultPlan implements Plan {

  private static final long serialVersionUID = 3647375170601921857L;

  private String _internalInstanceName = null;

  public DefaultPlan() {
    this._internalInstanceName = "";
  }

  public DefaultPlan(String instance_name) {
    this._internalInstanceName = instance_name;
  }

  public String toString() {
    return _internalInstanceName;
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

    /**
     * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#hasOperations
     */
    private List hasOperations = new ArrayList();

    public void addHasOperations(Operation elem) {
        hasOperations.add(elem);
    }

    public boolean removeHasOperations(Operation elem) {
        boolean result = hasOperations.remove(elem);
        return result;
    }

    public void clearAllHasOperations() {
        hasOperations.clear();
    }

    public Iterator getAllHasOperations() {
        return hasOperations.iterator();
    }

    public List getHasOperations() {
        return hasOperations;
    }

    public void setHasOperations(List l) {
        hasOperations = l;
    }

}
