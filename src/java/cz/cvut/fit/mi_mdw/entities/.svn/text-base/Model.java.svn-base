package cz.cvut.fit.mi_mdw.entities;

import com.google.appengine.api.datastore.Key;
import cz.cvut.fit.mi_mdw.util.Validator;
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
public class Model {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key id;
    @Persistent
    private String name;
    @Persistent
    private String description;
    @Persistent
    private Brand brand;
    @Persistent(mappedBy = "model")
    @Element(dependent = "true")
    private Set<Car> cars;

    public Model(String name, String description, Brand brand, Set<Car> cars) {
        validate(name, description);

        this.name = name;
        this.description = description;
        this.brand = brand;
        this.cars = cars;
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

    @XmlTransient
    public Brand getBrand() {
        return brand;
    }

    @XmlElement(name = "brand")
    public long getBrandXml() {
        return brand.getID().getId();
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    @XmlTransient
    public Set<Car> getCars() {
        return cars;
    }

    public void setCars(Set<Car> cars) {
        this.cars = cars;
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
