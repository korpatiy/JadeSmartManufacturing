package org.manufacture.Ontology.concepts.domain.domainImpl;


import org.manufacture.Ontology.concepts.domain.Station;

/**
* Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#Station
* @author OntologyBeanGenerator v4.1
* @version 2021/04/16, 20:40:39
*/
public class DefaultStation implements Station {

  private static final long serialVersionUID = 3647375170601921857L;

  private String _internalInstanceName = null;

  public DefaultStation() {
    this._internalInstanceName = "";
  }

  public DefaultStation(String instance_name) {
    this._internalInstanceName = instance_name;
  }

  public String toString() {
    return _internalInstanceName;
  }

    /**
     * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#id
     */
    private int id;
    public void setId(int value) {
        this.id=value;
    }
    public int getId() {
        return this.id;
    }
   /**
   * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#description
   */
   private String description;
   public void setDescription(String value) { 
    this.description=value;
   }
   public String getDescription() {
     return this.description;
   }

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

}
