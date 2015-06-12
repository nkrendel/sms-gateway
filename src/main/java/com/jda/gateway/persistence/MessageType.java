package com.jda.gateway.persistence;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="message_type")
public class MessageType implements Serializable {

  private static final long serialVersionUID = -4742446393719374778L;

  @Id
  @Column(name="type", length=32)
  private String type;
  
  @Column(name="description", length=128)
  private String description;
  

  public MessageType() {
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

}