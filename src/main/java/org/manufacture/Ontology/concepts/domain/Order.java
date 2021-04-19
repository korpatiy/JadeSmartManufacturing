package org.manufacture.Ontology.concepts.domain;


import jade.util.leap.Iterator;
import jade.util.leap.List;

/**
* Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#Order
* @author OntologyBeanGenerator v4.1
* @version 2021/04/16, 20:40:39
*/
public interface Order extends jade.content.Concept {

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
   * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#executedByPlan
   */
   public void setExecutedByPlan(Plan value);
   public Plan getExecutedByPlan();

}
