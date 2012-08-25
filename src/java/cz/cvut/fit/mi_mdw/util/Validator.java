package cz.cvut.fit.mi_mdw.util;

public final class Validator {

    private Validator() {
    }

    public static boolean length(String val, int min, int max) {
        return (val.length() >= min && val.length() <= max);
    }

    public static boolean range(int val, int min, int max) {
        return (val >= min && val <= max);
    }

    public static boolean regularText(String val) {
        return val.matches("^[a-zA-Z0-9\\. /_-]*$");
    }

    public static boolean username(String val) {
        return val.matches("^[a-z0-9]*$");
    }

    public static boolean email(String val) {
        return val.matches("^[a-zA-Z0-9\\._-]+@[a-zA-Z0-9\\._-]+\\.[a-z]{2,4}$");
    }

    public static boolean phone(String val) {
        return val.matches("^((00|\\+)[0-9]{1,3})?[0-9]{9}$");
    }
}
