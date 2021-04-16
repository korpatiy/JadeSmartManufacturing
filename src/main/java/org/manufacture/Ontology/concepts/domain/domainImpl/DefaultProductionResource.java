package org.manufacture.Ontology.concepts.domain.domainImpl;


import jade.util.leap.ArrayList;
import jade.util.leap.Iterator;
import jade.util.leap.List;
import org.manufacture.Ontology.concepts.domain.ProductionResource;
import org.manufacture.Ontology.concepts.domain.Station;
import org.manufacture.Ontology.concepts.domain.Tool;

/**
* Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#ProductionResource
* @author OntologyBeanGenerator v4.1
* @version 2021/04/16, 20:40:39
*/
public class DefaultProductionResource implements ProductionResource {

  private static final long serialVersionUID = 3647375170601921857L;

  private String _internalInstanceName = null;

  public DefaultProductionResource() {
    this._internalInstanceName = "";
  }

  public DefaultProductionResource(String instance_name) {
    this._internalInstanceName = instance_name;
  }

  public String toString() {
    return _internalInstanceName;
  }

   /**
   * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#useTools
   */
   private List useTools = new ArrayList();
   public void addUseTools(Tool elem) {
     useTools.add(elem);
   }
   public boolean removeUseTools(Tool elem) {
     boolean result = useTools.remove(elem);
     return result;
   }
   public void clearAllUseTools() {
     useTools.clear();
   }
   public Iterator getAllUseTools() {return useTools.iterator(); }
   public List getUseTools() {return useTools; }
   public void setUseTools(List l) {useTools = l; }

   /**
   * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#name
   */
   private String name;
   public void setName(String value) { 
    this.name=value;
   }
   public String getName() {
     return this.name;
   }

   /**
   * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#type
   */
   private String type;
   public void setType(String value) { 
    this.type=value;
   }
   public String getType() {
     return this.type;
   }

   /**
   * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#locatedOnStation
   */
   private Station locatedOnStation;
   public void setLocatedOnStation(Station value) { 
    this.locatedOnStation=value;
   }
   public Station getLocatedOnStation() {
     return this.locatedOnStation;
   }

}
