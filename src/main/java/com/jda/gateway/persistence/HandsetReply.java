package com.jda.gateway.persistence;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "handset_reply")
public class HandsetReply implements Serializable {

  private static final long serialVersionUID = 5426988424294902319L;

  @Id
  @OneToOne
  private MessageType messageType;

  @NotNull
  @Column(name="pattern")
  private String pattern;

  
  public HandsetReply() {
  }


  public MessageType getMessageType() {
    return messageType;
  }


  public void setMessageType(MessageType messageType) {
    this.messageType = messageType;
  }


  public String getPattern() {
    return pattern;
  }


  public void setPattern(String pattern) {
    this.pattern = pattern;
  }
}
