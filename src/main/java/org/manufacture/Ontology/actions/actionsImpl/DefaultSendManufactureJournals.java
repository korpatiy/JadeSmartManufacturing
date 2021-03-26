package org.manufacture.Ontology.actions.actionsImpl;


import jade.util.leap.ArrayList;
import jade.util.leap.Iterator;
import jade.util.leap.List;
import org.manufacture.Ontology.actions.SendManufactureJournals;
import org.manufacture.Ontology.concepts.domain.ManufactureJournal;

/**
* Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#SendManufactureJournals
* @author OntologyBeanGenerator v4.1
* @version 2021/03/25, 22:27:12
*/
public class DefaultSendManufactureJournals implements SendManufactureJournals {

  private static final long serialVersionUID = 5298226161551650755L;

  private String _internalInstanceName = null;

  public DefaultSendManufactureJournals() {
    this._internalInstanceName = "";
  }

  public DefaultSendManufactureJournals(String instance_name) {
    this._internalInstanceName = instance_name;
  }

  public String toString() {
    return _internalInstanceName;
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

}
