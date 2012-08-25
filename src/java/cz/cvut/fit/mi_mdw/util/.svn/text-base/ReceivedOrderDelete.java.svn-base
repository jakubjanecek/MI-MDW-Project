/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.fit.mi_mdw.util;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Martin
 */
@XmlRootElement(name="order")
public class ReceivedOrderDelete {
    private String username;
    private String passwd;
    private String orderID;

    public ReceivedOrderDelete() {
    }

    public ReceivedOrderDelete(String username, String passwd, String orderID) {
        this.username = username;
        this.passwd = passwd;
        this.orderID = orderID;
    }

    @XmlElement(name="id")
    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    @XmlAttribute(name="passwd")
    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    @XmlAttribute(name="username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }



}
