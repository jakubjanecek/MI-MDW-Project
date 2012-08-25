package cz.cvut.fit.mi_mdw.entities;

import com.google.appengine.api.datastore.Key;
import cz.cvut.fit.mi_mdw.util.PMF;
import java.util.Date;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@PersistenceCapable
@XmlRootElement
public class Order {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key id;
    @Persistent
    private Date datetime;
    @Persistent
    private Key car;
    @Persistent
    private Dealer dealer;
    @Persistent
    private Key user;

    public Order(Date datetime, Key car, Dealer dealer, Key user) {
        validate(datetime);

        this.datetime = datetime;
        this.car = car;
        this.dealer = dealer;
        this.user = user;
    }

    public Key getID() {
        return id;
    }

    @XmlAttribute(name = "id")
    public long getIDXml() {
        return id.getId();
    }

    @XmlTransient
    public Key getCar() {
        return car;
    }

    @XmlElement(name = "carID")
    public long getCarXml() {
        return car.getId();
    }

    public void setCar(Car car) {
        this.car = car.getID();
    }

    @XmlElement
    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        validate(datetime);

        this.datetime = datetime;
    }

    @XmlTransient
    public Dealer getDealer() {
        return dealer;
    }

    @XmlElement(name = "dealer")
    public long getDealerXml() {
        return dealer.getID().getId();
    }

    public void setDealer(Dealer dealer) {
        this.dealer = dealer;
    }

    @XmlTransient
    public Key getUser() {
        return user;
    }

    @XmlElement(name = "user")
    public User getUserXml() {
        return PMF.getPM().getObjectById(User.class, user.getId());
    }

    public void setUser(User user) {
        this.user = user.getID();
    }

    private void validate(Date date) throws IllegalArgumentException {
        boolean result;

        result = date != null;

        if (!result) {
            throw new IllegalArgumentException();
        }
    }
}
