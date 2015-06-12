package com.jda.gateway.persistence;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.eclipse.persistence.config.PersistenceUnitProperties;
import org.junit.Ignore;
import org.junit.Test;

public class TestDropAndCreateDatabase {

    EntityManager em;

    @Ignore
    @Test
    public void testDDLGeneration() {
        Map<String, String> persistProperties = new HashMap<String, String>();
        persistProperties.put(PersistenceUnitProperties.DDL_GENERATION, "drop-and-create-tables");
        persistProperties.put(PersistenceUnitProperties.DDL_GENERATION_MODE, "database");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory( "sms-gateway", persistProperties);
        em = emf.createEntityManager();
    }
    
    @Test
    public void testDMLLoadData() {
      Map<String, String> persistProperties = new HashMap<String, String>();
      persistProperties.put(PersistenceUnitProperties.SCHEMA_GENERATION_DATABASE_ACTION, 
          PersistenceUnitProperties.SCHEMA_GENERATION_DROP_AND_CREATE_ACTION);
      persistProperties.put(PersistenceUnitProperties.SCHEMA_GENERATION_CREATE_SOURCE,
          PersistenceUnitProperties.SCHEMA_GENERATION_METADATA_SOURCE);
      persistProperties.put(PersistenceUnitProperties.SCHEMA_GENERATION_DROP_SOURCE,
          PersistenceUnitProperties.SCHEMA_GENERATION_METADATA_SOURCE);
      persistProperties.put(PersistenceUnitProperties.SCHEMA_GENERATION_SQL_LOAD_SCRIPT_SOURCE,
          "META-INF" + File.separatorChar + "loadData.sql");
    ////      persistProperties.put(PersistenceUnitProperties.DDL_GENERATION_MODE, "database");
      EntityManagerFactory emf = Persistence.createEntityManagerFactory( "sms-gateway", persistProperties);
      em = emf.createEntityManager();
      assertNotNull("Entity Manager has no properties", em.getProperties());
    }

}
