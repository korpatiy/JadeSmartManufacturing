package org.manufacture.Ontology.actions.actionsImpl;


import org.manufacture.Ontology.actions.SendOrder;
import org.manufacture.Ontology.concepts.domain.Order;

/**
* Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#SendOrder
* @author OntologyBeanGenerator v4.1
* @version 2021/04/16, 20:40:39
*/
public class DefaultSendOrder implements SendOrder {

  private static final long serialVersionUID = 3647375170601921857L;

  private String _internalInstanceName = null;

  public DefaultSendOrder() {
    this._internalInstanceName = "";
  }

  public DefaultSendOrder(String instance_name) {
    this._internalInstanceName = instance_name;
  }

    @Override
    public String toString() {
        return "DefaultSendOrder{" +
                ", order=" + order +
                '}';
    }

    /**
   * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#order
   */
   private Order order;
   public void setOrder(Order value) { 
    this.order=value;
   }
   public Order getOrder() {
     return this.order;
   }
}
