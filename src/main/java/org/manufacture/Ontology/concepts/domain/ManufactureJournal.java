package org.manufacture.Ontology.concepts.domain;


import jade.util.leap.Iterator;
import jade.util.leap.List;
import org.manufacture.Ontology.concepts.general.Journal;

/**
* Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#ManufactureJournal
* @author OntologyBeanGenerator v4.1
* @version 2021/03/25, 22:27:12
*/
public interface ManufactureJournal extends Journal {

   /**
   * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#hasStationJournals
   */
   public void addHasStationJournals(StationJournal elem);
   public boolean removeHasStationJournals(StationJournal elem);
   public void clearAllHasStationJournals();
   public Iterator getAllHasStationJournals();
   public List getHasStationJournals();
   public void setHasStationJournals(List l);

}
