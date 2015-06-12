package com.jda.gateway.persistence;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.eclipse.persistence.config.PersistenceUnitProperties;
import org.junit.Test;

public class TestSchemaGeneration {

    EntityManager em;

    @Test
    public void testDDLGeneration() {
        Map<String, String> persistProperties = new HashMap<String, String>();
        persistProperties.put(PersistenceUnitProperties.DDL_GENERATION, "drop-and-create-tables");
        persistProperties.put(PersistenceUnitProperties.DDL_GENERATION_MODE, "sql-script");
        persistProperties.put(PersistenceUnitProperties.APP_LOCATION, System.getProperty("user.dir"));
        persistProperties.put(PersistenceUnitProperties.CREATE_JDBC_DDL_FILE, "createDDL.sql");
        persistProperties.put(PersistenceUnitProperties.DROP_JDBC_DDL_FILE, "dropDDL.sql");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory( "sms-gateway", persistProperties);
        em = emf.createEntityManager();
    }

}
