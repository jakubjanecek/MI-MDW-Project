package cz.cvut.fit.mi_mdw.util;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.PersistenceManager;

public final class PMF {

    private static final PersistenceManagerFactory instance = JDOHelper.getPersistenceManagerFactory("transactions-optional");

    private PMF() {
    }

    public static PersistenceManagerFactory get() {
        return instance;
    }

    public static PersistenceManager getPM() {
        return instance.getPersistenceManager();
    }
}
