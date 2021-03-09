package ManufactureOntology;

import ManufactureOntology.Concepts.Detail;
import ManufactureOntology.Concepts.Material;
import ManufactureOntology.Concepts.Product;
import ManufactureOntology.Predicates.HasMaterial;
import jade.content.onto.BasicOntology;
import jade.content.onto.Ontology;
import jade.content.onto.OntologyException;
import jade.content.schema.*;

public class ManufactureOntology extends Ontology {

    private static final String ONTOLOGY_NAME = "MANUFACTURE_ONTOLOGY";

    private final String MATERIAL = "MATERIAL";
    private final String PRODUCT = "PRODUCT";

    private final String ID = "ID";
    private final String NAME = "NAME";

    private final String MATERIALS = "MATERIALS";


    private final String HAS_MATERIAL = "HAS_MATERIAL";

    public static Ontology instance;

    static {
        try {
            instance = new ManufactureOntology();
        } catch (OntologyException e) {
            e.printStackTrace();
        }
    }

    public static Ontology getInstance() {
        return instance;
    }

    public ManufactureOntology() throws OntologyException {
        super(ONTOLOGY_NAME, BasicOntology.getInstance());

        PrimitiveSchema stringSchema = (PrimitiveSchema) getSchema(BasicOntology.STRING);
        PrimitiveSchema integerSchema = (PrimitiveSchema) getSchema(BasicOntology.INTEGER);

        ConceptSchema productSchema = new ConceptSchema(PRODUCT);
        productSchema.add(NAME, stringSchema, ObjectSchema.OPTIONAL);
        productSchema.add(ID, integerSchema, ObjectSchema.OPTIONAL);

        ConceptSchema materialSchema = new ConceptSchema(MATERIAL);
        materialSchema.add(NAME, stringSchema, ObjectSchema.OPTIONAL);
        materialSchema.add(ID, integerSchema, ObjectSchema.OPTIONAL);

        ConceptSchema detailSchema = new ConceptSchema("DETAIL");
        detailSchema.add(NAME, stringSchema, ObjectSchema.OPTIONAL);
        detailSchema.add(ID, integerSchema, ObjectSchema.OPTIONAL);
        productSchema.add("DETAILS", detailSchema, 0, ObjectSchema.UNLIMITED);

        add(detailSchema, Detail.class);
        add(productSchema, Product.class);
        add(materialSchema, Material.class);

        AggregateSchema materialsSchema = new AggregateSchema(BasicOntology.SEQUENCE);

        PredicateSchema hasMaterialSchema = new PredicateSchema(HAS_MATERIAL);

        //hasMaterialSchema.add(PRODUCT, productSchema);
        hasMaterialSchema.add("Object", ObjectSchema.getBaseSchema());
        hasMaterialSchema.add(MATERIALS, materialsSchema);

        add(hasMaterialSchema, HasMaterial.class);
    }
}
