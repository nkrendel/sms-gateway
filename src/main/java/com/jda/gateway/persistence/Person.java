package com.jda.gateway.persistence;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

@Entity
@Table(name="person")
public class Person implements Serializable {

  @Transient
  private static final long serialVersionUID = -2599618688667910739L;

//  @Id
//  @SequenceGenerator(name = "person_id_seq", sequenceName = "person_id_seq", allocationSize = 2)
//  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "person_id_seq")
//  @Column(name = "id", columnDefinition="int4 default nextval('person_id_seq')", updatable=false)
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name="family_name", length=64)
  @Size(min = 2, max = 64) 
  private String familyName;
  
  @Column(name="given_name", length=64)
  @Size(min = 2, max = 64) 
  private String givenName;
  
  @Column(name="middle_name", length=64)
  @Size(min = 2, max = 64) 
  private String middleName;
  
  @Column(name="user_id", length=32)
  @Size(min = 0, max=32)
  private String userId;

  @OneToMany(mappedBy="owner")
  private Collection<Handset> handsets;


  public Person() {
  }
  
  public Person(final String familyName, final String givenName, final String middleName) {
    this.familyName = familyName;
    this.givenName = givenName;
    this.middleName = middleName;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getFullName() {
    return this.getGivenName() + " " + this.getMiddleName() + " " + this.getFamilyName();
  }
  
  public String getFamilyName() {
    return familyName;
  }

  public void setFamilyName(String familyName) {
    this.familyName = familyName;
  }

  public String getGivenName() {
    return givenName;
  }

  public void setGivenName(String givenName) {
    this.givenName = givenName;
  }

  public String getMiddleName() {
    return middleName;
  }

  public void setMiddleName(String middleName) {
    this.middleName = middleName;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public Collection<Handset> getHandsets() {
    return handsets;
  }

  public void setHandsets(Collection<Handset> handsets) {
    this.handsets = handsets;
  }
  
  @Override
  public String toString() {
    return getFullName();
  }
}
