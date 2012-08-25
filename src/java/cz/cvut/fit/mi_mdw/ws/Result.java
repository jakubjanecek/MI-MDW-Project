package cz.cvut.fit.mi_mdw.ws;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "result")
public class Result {

    private boolean result;
    private String value;

    public Result() {
    }

    public Result(boolean result) {
        this.result = result;
    }

    @XmlElement(name = "value")
    public boolean getResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    @XmlElement(name = "info")
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
