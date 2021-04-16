package org.manufacture.Ontology.concepts.general;


import java.util.Date;

/**
* Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#Journal
* @author OntologyBeanGenerator v4.1
* @version 2021/04/16, 20:40:39
*/
public interface Journal extends jade.content.Concept {

   /**
   * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#endDate
   */
   public void setEndDate(Date value);
   public Date getEndDate();

   /**
   * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#startDate
   */
   public void setStartDate(Date value);
   public Date getStartDate();

   /**
   * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#status
   */
   public void setStatus(String value);
   public String getStatus();

}
