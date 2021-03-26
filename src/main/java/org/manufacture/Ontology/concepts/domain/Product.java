package org.manufacture.Ontology.concepts.domain;


import jade.util.leap.Iterator;
import jade.util.leap.List;
import org.manufacture.Ontology.concepts.general.Item;

/**
* Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#Product
* @author OntologyBeanGenerator v4.1
* @version 2021/03/25, 22:27:12
*/
public interface Product extends Item {

   /**
   * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#hasMaterials
   */
   public void addHasMaterials(Material elem);
   public boolean removeHasMaterials(Material elem);
   public void clearAllHasMaterials();
   public Iterator getAllHasMaterials();
   public List getHasMaterials();
   public void setHasMaterials(List l);

}
