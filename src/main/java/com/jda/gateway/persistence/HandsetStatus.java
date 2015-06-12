package com.jda.gateway.persistence;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.jda.gateway.ui.SMSGateway;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;

@Entity
@Table(name = "handset_status")
public class HandsetStatus implements Serializable {

  private static final long serialVersionUID = 3279376320836535930L;

  public static final int STATUS_UNLOCKED = 101;   // this is unreliable.  better to use description?

  @Id
  private int status;

  @NotNull
  @Column(name="description", length=128)
  @Size(min = 2, max = 128) 
  private String description;

  
  public HandsetStatus() {
  }
  
  public HandsetStatus(final int status, final String description) {
    this.status = status;
    this.description = description;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
  
  public static HandsetStatus getByStatus(final int status) {
    final EntityManager em = 
        JPAContainerFactory.createEntityManagerForPersistenceUnit(SMSGateway.PERSISTENCE_UNIT);
    return em.find(HandsetStatus.class, status);
  }
}
