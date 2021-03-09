package ManufactureOntology.Predicates;

import ManufactureOntology.Concepts.Product;
import jade.content.Predicate;
import jade.util.leap.List;

public class HasMaterial implements Predicate {

    public List materials = null;
    public Object product = null;


    public List getMaterials() {
        return materials;
    }

    public void setMaterials(List materials) {
        this.materials = materials;
    }

    public Object getObject() {
        return product;
    }

    public void setObject(Object product) {
        this.product = product;
    }
}
