package com.jda.gateway.persistence;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.Ignore;
import org.junit.Test;

public class TestCreatePerson {

  EntityManager em;

  @Ignore
  @Test
  public void testCreatePerson() {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("sms-gateway");
    em = emf.createEntityManager();
    EntityTransaction tx = em.getTransaction();
    tx.begin();
    Person p = new Person("Smith", "Jada", "Pinkett");
    em.persist(p);
    tx.commit();
  }

}
