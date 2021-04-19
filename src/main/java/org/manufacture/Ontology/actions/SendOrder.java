package org.manufacture.Ontology.actions;


import org.manufacture.Ontology.concepts.domain.Order;

/**
* Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#SendOrder
* @author OntologyBeanGenerator v4.1
* @version 2021/04/16, 20:40:39
*/
public interface SendOrder extends jade.content.AgentAction {

   /**
   * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#order
   */
   public void setOrder(Order value);
   public Order getOrder();
}
