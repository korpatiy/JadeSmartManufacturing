package org.manufacture.Ontology.concepts.domain;


import jade.util.leap.Iterator;
import jade.util.leap.List;
import org.manufacture.Ontology.concepts.general.Item;

/**
* Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#Order
* @author OntologyBeanGenerator v4.1
* @version 2021/03/25, 22:27:12
*/
public interface Order extends Item {

   /**
   * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#quantity
   */
   public void setQuantity(int value);
   public int getQuantity();

   /**
   * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#formedOnProduct
   */
   public void setFormedOnProduct(Product value);
   public Product getFormedOnProduct();

   /**
   * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#dueDate
   */
   public void setDueDate(String value);
   public String getDueDate();

   /**
   * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#hasManufactureJournals
   */
   public void addHasManufactureJournals(ManufactureJournal elem);
   public boolean removeHasManufactureJournals(ManufactureJournal elem);
   public void clearAllHasManufactureJournals();
   public Iterator getAllHasManufactureJournals();
   public List getHasManufactureJournals();
   public void setHasManufactureJournals(List l);

   /**
   * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#executedByPlan
   */
   public void setExecutedByPlan(Plan value);
   public Plan getExecutedByPlan();

}
