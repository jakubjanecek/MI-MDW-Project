/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.fit.mi_mdw.util;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Martin
 */
@XmlRootElement
public class ResponseConverter {

    private int state;

    public ResponseConverter() {
    }

    

    public ResponseConverter(int state) {
        this.state = state;
    }

    @XmlElement
    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    

}
