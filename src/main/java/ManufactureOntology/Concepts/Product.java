package ManufactureOntology.Concepts;

import jade.content.Concept;
import jade.util.leap.ArrayList;
import jade.util.leap.List;

public class Product implements Concept {

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

    private List details = new ArrayList();
    public void addDetails(Detail elem) {
        details.add(elem);
    }
    public boolean removeDetails(Detail elem) {
        return details.remove(elem);
    }
    public void clearAllDetails() {
        details.clear();
    }
    public List getDetails() {return details; }
    public void setDetails(List l) {details = l; }
}
