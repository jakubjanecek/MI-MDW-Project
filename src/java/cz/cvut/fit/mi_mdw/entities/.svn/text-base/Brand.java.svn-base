package cz.cvut.fit.mi_mdw.entities;

import com.google.appengine.api.datastore.Key;
import cz.cvut.fit.mi_mdw.util.Validator;
import java.util.HashSet;
import java.util.Set;
import javax.jdo.annotations.Element;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@PersistenceCapable
@XmlRootElement
public class Brand {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key id;
    @Persistent
    private String name;
    @Persistent
    private String description;
    @Persistent(mappedBy = "brand")
    @Element(dependent = "true")
    private Set<Model> models;

    public Brand(String name, String description, Set<Model> models) {
        validate(name, description);

        this.name = name;
        this.description = description;
        this.models = models;
    }

    @XmlTransient
    public Key getID() {
        return id;
    }

    @XmlElement(name = "id")
    public long getIDXml() {
        return id.getId();
    }

    @XmlElement
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        validate(name, description);

        this.description = description;
    }

    @XmlElement
    public String getName() {
        return name;
    }

    public void setName(String name) {
        validate(name, description);

        this.name = name;
    }

    public void addModel(Model model) {
        model.setBrand(this);
        models.add(model);
    }

    @XmlTransient
    public Set<Model> getModels() {
        return models;
    }

    public void setModels(Set<Model> models) {
        this.models = new HashSet<Model>(models);
    }

    private void validate(String name, String description) throws IllegalArgumentException {
        if (name == null || description == null) {
            throw new IllegalArgumentException();
        }

        boolean result;

        result = Validator.regularText(name);
        result = result && Validator.length(name, 1, 100);
        result = result && Validator.regularText(description);
        result = result && Validator.length(description, 1, 500);

        if (!result) {
            throw new IllegalArgumentException();
        }
    }
}
