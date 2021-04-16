package org.manufacture.Ontology.actions.actionsImpl;


import jade.util.leap.ArrayList;
import jade.util.leap.Iterator;
import jade.util.leap.List;
import org.manufacture.Ontology.actions.SendReport;
import org.manufacture.Ontology.concepts.domain.ManufactureJournal;

/**
* Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#SendReport
* @author OntologyBeanGenerator v4.1
* @version 2021/04/16, 20:40:39
*/
public class DefaultSendReport implements SendReport {

  private static final long serialVersionUID = 3647375170601921857L;

  private String _internalInstanceName = null;

  public DefaultSendReport() {
    this._internalInstanceName = "";
  }

  public DefaultSendReport(String instance_name) {
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
