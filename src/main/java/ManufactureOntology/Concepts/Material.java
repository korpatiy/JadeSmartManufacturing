package ManufactureOntology.Concepts;

import jade.content.Concept;

public class Material implements Concept {

    public int id;
    public String name = null;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
