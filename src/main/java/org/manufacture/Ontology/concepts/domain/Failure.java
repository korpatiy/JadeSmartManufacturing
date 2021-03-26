package org.manufacture.Ontology.concepts.domain;


import jade.util.leap.Iterator;
import jade.util.leap.List;
import org.manufacture.Ontology.concepts.general.Item;

/**
* Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#Failure
* @author OntologyBeanGenerator v4.1
* @version 2021/03/25, 22:27:12
*/
public interface Failure extends Item {

   /**
   * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#occurrenceDate
   */
   public void setOccurrenceDate(String value);
   public String getOccurrenceDate();

   /**
   * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#type
   */
   public void setType(String value);
   public String getType();

   /**
   * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#status
   */
   public void addStatus(String elem);
   public boolean removeStatus(String elem);
   public void clearAllStatus();
   public Iterator getAllStatus();
   public List getStatus();
   public void setStatus(List l);

}
