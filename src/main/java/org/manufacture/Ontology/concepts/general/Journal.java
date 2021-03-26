package org.manufacture.Ontology.concepts.general;


import jade.util.leap.Iterator;
import jade.util.leap.List;

import javax.xml.crypto.Data;
import java.util.Date;

/**
* Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#Journal
* @author OntologyBeanGenerator v4.1
* @version 2021/03/25, 22:27:12
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
   public void addStatus(String elem);
   public boolean removeStatus(String elem);
   public void clearAllStatus();
   public Iterator getAllStatus();
   public List getStatus();
   public void setStatus(List l);

}
