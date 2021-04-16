package org.manufacture.Ontology.concepts.domain;


import jade.util.leap.Iterator;
import jade.util.leap.List;
import org.manufacture.Ontology.concepts.general.Resource;

/**
* Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#ProductionResource
* @author OntologyBeanGenerator v4.1
* @version 2021/04/16, 20:40:39
*/
public interface ProductionResource extends Resource {

   /**
   * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#useTools
   */
   public void addUseTools(Tool elem);
   public boolean removeUseTools(Tool elem);
   public void clearAllUseTools();
   public Iterator getAllUseTools();
   public List getUseTools();
   public void setUseTools(List l);

   /**
   * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#locatedOnStation
   */
   public void setLocatedOnStation(Station value);
   public Station getLocatedOnStation();

}
