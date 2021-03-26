package org.manufacture.Ontology.concepts.domain.domainImpl;

import jade.util.leap.ArrayList;
import jade.util.leap.Iterator;
import jade.util.leap.List;
import org.manufacture.Ontology.concepts.domain.ManufactureJournal;
import org.manufacture.Ontology.concepts.domain.Order;
import org.manufacture.Ontology.concepts.domain.Plan;
import org.manufacture.Ontology.concepts.domain.Product;

/**
* Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#Order
* @author OntologyBeanGenerator v4.1
* @version 2021/03/25, 22:27:12
*/
public class DefaultOrder implements Order {

  private static final long serialVersionUID = 5298226161551650755L;

  private String _internalInstanceName = null;

  public DefaultOrder() {
    this._internalInstanceName = "";
  }

  public DefaultOrder(String instance_name) {
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
   * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#quantity
   */
   private int quantity;
   public void setQuantity(int value) { 
    this.quantity=value;
   }
   public int getQuantity() {
     return this.quantity;
   }

   /**
   * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#formedOnProduct
   */
   private Product formedOnProduct;
   public void setFormedOnProduct(Product value) { 
    this.formedOnProduct=value;
   }
   public Product getFormedOnProduct() {
     return this.formedOnProduct;
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
   * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#dueDate
   */
   private String dueDate;
   public void setDueDate(String value) { 
    this.dueDate=value;
   }
   public String getDueDate() {
     return this.dueDate;
   }

   /**
   * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#hasManufactureJournals
   */
   private List hasManufactureJournals = new ArrayList();
   public void addHasManufactureJournals(ManufactureJournal elem) {
     hasManufactureJournals.add(elem);
   }
   public boolean removeHasManufactureJournals(ManufactureJournal elem) {
     boolean result = hasManufactureJournals.remove(elem);
     return result;
   }
   public void clearAllHasManufactureJournals() {
     hasManufactureJournals.clear();
   }
   public Iterator getAllHasManufactureJournals() {return hasManufactureJournals.iterator(); }
   public List getHasManufactureJournals() {return hasManufactureJournals; }
   public void setHasManufactureJournals(List l) {hasManufactureJournals = l; }

   /**
   * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#executedByPlan
   */
   private Plan executedByPlan;
   public void setExecutedByPlan(Plan value) { 
    this.executedByPlan=value;
   }
   public Plan getExecutedByPlan() {
     return this.executedByPlan;
   }

}
