package cz.cvut.fit.mi_mdw.entities;

import com.google.appengine.api.datastore.Key;
import cz.cvut.fit.mi_mdw.util.Validator;
import java.util.HashSet;
import java.util.Set;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class Dealer {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key id;
    @Persistent
    private String name;
    @Persistent
    private String address;
    @Persistent
    private String email;
    @Persistent
    private String phone;
    @Persistent
    private Set<Key> cars;
    @Persistent(mappedBy = "dealer")
    private Set<Order> orders;

    public Dealer(String name, String address, String email, String phone, Set<Key> cars, Set<Order> orders) {
        validate(name, address, email, phone);

        this.name = name;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.cars = cars;
        this.orders = orders;
    }

    public Key getID() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        validate(name, address, email, phone);

        this.address = address;
    }

    public Set<Key> getCars() {
        return cars;
    }

    public void setCars(Set<Key> cars) {
        this.cars = new HashSet<Key>(cars);
    }

    public void addCar(Car car) {
        cars.add(car.getID());
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        validate(name, address, email, phone);

        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        validate(name, address, email, phone);

        this.name = name;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = new HashSet<Order>(orders);
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        validate(name, address, email, phone);

        this.phone = phone;
    }

    private void validate(String name, String address, String email, String phone) throws IllegalArgumentException {
        if (name == null || address == null || email == null || phone == null) {
            throw new IllegalArgumentException();
        }

        boolean result;

        result = Validator.regularText(name);
        result = result && Validator.length(name, 1, 100);
        result = result && Validator.regularText(address);
        result = result && Validator.length(address, 1, 500);
        result = result && Validator.email(email);
        result = result && Validator.length(email, 1, 100);
        result = result && Validator.phone(phone);
        result = result && Validator.length(phone, 1, 15);

        if (!result) {
            throw new IllegalArgumentException();
        }
    }
}
