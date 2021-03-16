package org.manufacture.Ontology.actions;

import jade.content.AgentAction;
import jade.util.leap.ArrayList;
import jade.util.leap.Iterator;
import jade.util.leap.List;
import org.manufacture.Ontology.concepts.domain.Material;

public class SendSupply implements AgentAction {
    /**
     * Protege name: http://www.semanticweb.org/slava/ontologies/2021/1/ManufactureOntology#materials
     */
    private List materials = new ArrayList();

    public void addMaterials(Material elem) {
        materials.add(elem);
    }

    public boolean removeMaterials(Material elem) {
        boolean result = materials.remove(elem);
        return result;
    }

    public void clearAllMaterials() {
        materials.clear();
    }

    public Iterator getAllMaterials() {
        return materials.iterator();
    }

    public List getMaterials() {
        return materials;
    }

    public void setMaterials(List l) {
        materials = l;
    }

}
