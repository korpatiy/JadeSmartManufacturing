package ManufactureOntology.Predicates;

import ManufactureOntology.Concepts.Product;
import jade.content.Predicate;
import jade.util.leap.List;

public class HasMaterial implements Predicate {

    public List materials = null;
    public Product product = null;


    public List getMaterials() {
        return materials;
    }

    public void setMaterials(List materials) {
        this.materials = materials;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
