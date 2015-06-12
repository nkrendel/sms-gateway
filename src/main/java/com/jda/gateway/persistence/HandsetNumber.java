package com.jda.gateway.persistence;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name="handset_number")
public class HandsetNumber implements Serializable {

  private static final long serialVersionUID = 5457619101557048001L;

//  @Id
//  @SequenceGenerator(name = "handset_number_id_seq", sequenceName = "handset_number_id_seq", allocationSize = 2)
//  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "handset_number_id_seq")
//  @Column(name = "id", columnDefinition="int4 default nextval('handset_number_id_seq')", updatable=false)
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name="phone_number", length=18)
  @Size(min = 2, max = 18) 
  private String phoneNumber;

  @Column(name="sim_id", length=20)
  @Size(min = 0, max = 20) 
  private String simId;

  @OneToOne(mappedBy="number")
  private Handset handset;
  
  
  public HandsetNumber() {
  }
  
  public HandsetNumber(final String phoneNumber, final String simId) {
    this.phoneNumber = phoneNumber;
    this.simId = simId;
  }

  public HandsetNumber(final String phoneNumber, final String simId, final Handset handset) {
    this.phoneNumber = phoneNumber;
    this.handset = handset;
    this.simId = simId;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getSimId() {
    return simId;
  }

  public void setSimId(String simId) {
    this.simId = simId;
  }

  public Handset getHandset() {
    return handset;
  }

  public void setHandset(Handset handset) {
    this.handset = handset;
  }
}
