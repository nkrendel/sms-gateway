package com.jda.gateway.persistence;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "handset_action")
public class HandsetAction implements Serializable {

  @Transient
  private static final long serialVersionUID = 1631020320136415499L;

//  @Id
//  @SequenceGenerator(name = "handset_action_id_seq", sequenceName = "handset_action_id_seq", allocationSize = 2)
//  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "handset_action_id_seq")
//  @Column(name = "id", columnDefinition="int4 default nextval('handset_action_id_seq')", updatable=false)
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @NotNull
  @Column(name="action", length = 128)
  @Size(min = 2, max = 128) 
  // the name of the action
  private String action;
  
  @NotNull
  @Column(name="message", length = 256)
  @Size(max = 256)
  // the message sent to the target phone
  private String message;

  @NotNull
  @Column(name="argument")
  // does this action take an argument?
  private boolean argument;

  @Column(name="default_value")
  // the default value for the argument
  private String defaultValue;

  
  public HandsetAction() {
  }
  
  public HandsetAction(final String action) {
    this.action = action;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getAction() {
    return action;
  }

  public void setAction(String action) {
    this.action = action;
  }
  
  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public boolean isArgument() {
    return argument;
  }

  public void setArgument(boolean argument) {
    this.argument = argument;
  }

  public String getDefaultValue() {
    return defaultValue;
  }

  public void setDefaultValue(String defaultValue) {
    this.defaultValue = defaultValue;
  }
}
