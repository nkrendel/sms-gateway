package com.jda.gateway.persistence;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.vaadin.appfoundation.authentication.data.User;

@Entity
@Table(name="handset_log")
public class HandsetLog implements Serializable {

  private static final long serialVersionUID = 5933933414626827929L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name="date_time")
  private Timestamp dateTime;

  @Column(name="phone_number", length=18)
  private String phoneNumber;

  @Column(name="message_text")
  private String messageText;

  @ManyToOne(optional=true)
  private MessageType messageType;
  
  @ManyToOne(optional=true)
  private Handset handset;
  
  @ManyToOne(optional=true)
  private User sender;  
  
  
  public HandsetLog() {
  }

  // create a log entry for an incoming handset message
  public HandsetLog(final Handset handset, final MessageType messageType, final String messageText) {
    this.handset = handset;
    this.messageType = messageType;
    this.messageText = messageText;
    this.dateTime = new Timestamp(System.currentTimeMillis());
  }

  // create a log entry for a simple sms message
  public HandsetLog(final String phoneNumber, final String messageText) {
    this.phoneNumber = phoneNumber;
    this.messageText = messageText;
    this.dateTime = new Timestamp(System.currentTimeMillis());
  }
  
  // create a log entry for a user-driven action
  public HandsetLog(final Handset handset, final User sender, final String messageText) {
    this.handset = handset;
    this.sender = sender;
    this.messageText = messageText;
    this.dateTime = new Timestamp(System.currentTimeMillis());
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public Timestamp getDateTime() {
    return dateTime;
  }

  public void setDateTime(Timestamp dateTime) {
    this.dateTime = dateTime;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getMessageText() {
    return messageText;
  }

  public void setMessageText(String messageText) {
    this.messageText = messageText;
  }

  public MessageType getMessageType() {
    return messageType;
  }

  public void setMessageType(MessageType messageType) {
    this.messageType = messageType;
  }

  public Handset getHandset() {
    return handset;
  }

  public void setHandset(Handset handset) {
    this.handset = handset;
  }

  public User getSender() {
    return sender;
  }

  public void setSender(User sender) {
    this.sender = sender;
  }

  public String getHtml() {
    SimpleDateFormat sdf = new SimpleDateFormat();
    return String.format("<br>%s<br><large>%s</large>", sdf.format(dateTime), messageText);
  }
}
