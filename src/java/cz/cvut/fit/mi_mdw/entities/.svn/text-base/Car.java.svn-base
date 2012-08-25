package cz.cvut.fit.mi_mdw.entities;

import com.google.appengine.api.datastore.Key;
import cz.cvut.fit.mi_mdw.util.Validator;
import java.math.BigDecimal;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@PersistenceCapable
@XmlRootElement
public class Car {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key id;
    @Persistent
    private Model model;
    @Persistent
    private Engine engine;
    @Persistent
    private Fuel fuel;
    @Persistent
    private BigDecimal price;
    @Persistent
    private Integer power;
    @Persistent
    private BigDecimal consumption;
    @Persistent
    private String description;
    @Persistent
    private Key dealerKey;

    public Car(Model model, Engine engine, Fuel fuel, String price, String power, String consumption, String description, Key dealer) {
        validate(price, power, consumption, description);

        this.model = model;
        this.engine = engine;
        this.fuel = fuel;
        this.price = new BigDecimal(price);
        this.power = Integer.valueOf(power);
        this.consumption = new BigDecimal(consumption);
        this.description = description;
        this.dealerKey = dealer;
    }

    @XmlTransient
    public Key getID() {
        return id;
    }

    @XmlAttribute(name = "id")
    @Transient
    public String getIDXml() {
        return String.format("%d-%d-%d", model.getBrand().getID().getId(), model.getID().getId(), id.getId());
    }

    public void setID(Key key) {
        this.id = key;
    }

    @XmlElement
    public BigDecimal getConsumption() {
        return consumption;
    }

    public void setConsumption(String consumption) {
        validate(price.toEngineeringString(), power.toString(), consumption, description);

        this.consumption = new BigDecimal(consumption);
    }

    @XmlElement
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        validate(price.toEngineeringString(), power.toString(), consumption.toEngineeringString(), description);

        this.description = description;
    }

    @XmlElement
    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    @XmlElement
    public Fuel getFuel() {
        return fuel;
    }

    public void setFuel(Fuel fuel) {
        this.fuel = fuel;
    }

    @XmlTransient
    public Model getModel() {
        return model;
    }

    @XmlElement(name = "model")
    @Transient
    public String getModelXml() {
        return model.getName();
    }

    @XmlElement(name = "brand")
    @Transient
    public String getBrandXml() {
        return model.getBrand().getName();
    }

    public void setModel(Model model) {
        this.model = model;
    }

    @XmlElement
    public Integer getPower() {
        return power;
    }

    public void setPower(String power) {
        validate(price.toEngineeringString(), power, consumption.toEngineeringString(), description);

        this.power = Integer.valueOf(power);
    }

    @XmlElement
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(String price) {
        validate(price, power.toString(), consumption.toEngineeringString(), description);

        this.price = new BigDecimal(price);
    }

    @XmlTransient
    public Key getDealer() {
        return dealerKey;
    }

    @XmlElement(name = "dealerID")
    @Transient
    public long getDealerXml() {
        return dealerKey.getId();
    }

    public void setDealer(Key dealerKey) {
        this.dealerKey = dealerKey;
    }

    private void validate(String price, String power, String consumption, String description) throws IllegalArgumentException {
        if (price == null || power == null || consumption == null || description == null) {
            throw new IllegalArgumentException();
        }

        BigDecimal priceConverted;
        BigDecimal consumptionConverted;
        Integer powerConverted;
        try {
            priceConverted = new BigDecimal(price);
            consumptionConverted = new BigDecimal(consumption);
            powerConverted = Integer.valueOf(power);
        }
        catch (NumberFormatException ex) {
            throw new IllegalArgumentException();
        }

        boolean result;

        result = Validator.range(powerConverted, 1, 2000);
        result = result && Validator.regularText(description);
        result = result && Validator.length(description, 1, 500);
        result = result && Validator.range(priceConverted.intValue(), 0, 100000000);
        result = result && Validator.range(consumptionConverted.intValue(), 1, 200);

        if (!result) {
            throw new IllegalArgumentException();
        }
    }
}
