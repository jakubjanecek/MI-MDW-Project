package cz.cvut.fit.mi_mdw.entities;

import com.google.appengine.api.datastore.Key;
import cz.cvut.fit.mi_mdw.util.Validator;
import java.util.Set;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@PersistenceCapable
@XmlRootElement
public class User {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key id;
    @Persistent
    private String username;
    @Persistent
    private String password;
    @Persistent
    private String name;
    @Persistent
    private String address;
    @Persistent
    private String email;
    @Persistent
    private Set<Key> orders;

    public User(String username, String password, String name, String address, String email, Set<Key> orders) {
        validate(username, password, name, address, email);

        this.username = username;
        this.password = password;
        this.name = name;
        this.address = address;
        this.email = email;
        this.orders = orders;
    }

    @XmlTransient
    public Key getID() {
        return id;
    }

    @XmlElement
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        validate(username, password, name, address, email);

        this.address = address;
    }

    @XmlElement
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        validate(username, password, name, address, email);

        this.email = email;
    }

    @XmlElement
    public String getName() {
        return name;
    }

    public void setName(String name) {
        validate(username, password, name, address, email);

        this.name = name;
    }

    @XmlTransient
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        validate(username, password, name, address, email);

        this.password = password;
    }

    @XmlElement
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        validate(username, password, name, address, email);

        this.username = username;
    }

    @XmlTransient
    public Set<Key> getOrders() {
        return orders;
    }

    public void setOrders(Set<Key> orders) {
        this.orders = orders;
    }

    private void validate(String username, String password, String name, String address, String email) throws IllegalArgumentException {
        if (username == null || password == null || name == null || address == null || email == null) {
            throw new IllegalArgumentException();
        }

        boolean result;

        result = Validator.length(username, 3, 20);
        result = result && Validator.username(username);
        result = result && Validator.length(password, 5, 20);
        result = result && Validator.regularText(name);
        result = result && Validator.length(name, 1, 100);
        result = result && Validator.regularText(address);
        result = result && Validator.length(address, 1, 500);
        result = result && Validator.email(email);
        result = result && Validator.length(email, 1, 100);

        if (!result) {
            throw new IllegalArgumentException();
        }
    }
}
