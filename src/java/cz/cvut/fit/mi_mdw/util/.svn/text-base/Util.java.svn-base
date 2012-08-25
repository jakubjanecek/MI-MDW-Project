package cz.cvut.fit.mi_mdw.util;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import cz.cvut.fit.mi_mdw.entities.Brand;
import cz.cvut.fit.mi_mdw.entities.Car;
import cz.cvut.fit.mi_mdw.entities.Model;

public final class Util {

    private Util() {
    }

    public static Key carKeyFromString(String string) {
        String[] IDs = string.split("-");
        return KeyFactory.createKey(KeyFactory.createKey(KeyFactory.createKey(Brand.class.getSimpleName(), Long.valueOf(IDs[0])), Model.class.getSimpleName(), Long.valueOf(IDs[1])), Car.class.getSimpleName(), Long.valueOf(IDs[2]));
    }

    public static Key modelKeyFromString(String string) {
        String[] IDs = string.split("-");
        return KeyFactory.createKey(KeyFactory.createKey(Brand.class.getSimpleName(), Long.valueOf(IDs[0])), Model.class.getSimpleName(), Long.valueOf(IDs[1]));
    }
}
