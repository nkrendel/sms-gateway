package com.jda.gateway.persistence;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.Size;

import com.jda.gateway.ui.GatewayUI;

@Entity
@Table(name="handset")
public class Handset implements Serializable {

  private static final long serialVersionUID = -7939021664076766213L;

//  @Id
//  @SequenceGenerator(name = "handset_id_seq", sequenceName = "handset_id_seq", allocationSize = 2)
//  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "handset_id_seq")
//  @Column(name = "id", columnDefinition="int4 default nextval('handset_id_seq')", updatable=false)
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  
  @Column(name="device_id", length=15)
  private String deviceId;
  
  @Column(name="last_update")
  @Version
  private Timestamp lastUpdate;

  @Basic(fetch=FetchType.LAZY)
  @Column(name="last_update_message", length=255)
  @Size(min = 0, max = 255) 
  private String lastUpdateMessage;  // TODO perhaps this needs to be a FK to handset_message_log or some such thing
  
  @Lob @Basic(fetch=FetchType.LAZY)
  @Column(name="public_key")
  private byte[] publicKey;
  
  @ManyToOne
  private HandsetStatus status;

  @ManyToOne(optional=true)
  private Person owner;
  
  @OneToOne(optional=true)
  private HandsetNumber number;

  
  public Handset() {
  }
  
  public Handset(final String lastGps, final HandsetStatus status, final String phoneNumber, final String simId) {
    setStatus(status);
    setPhoneNumber(phoneNumber, simId);
  }
  
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getDeviceId() {
    return deviceId;
  }

  public void setDeviceId(String deviceId) {
    this.deviceId = deviceId;
  }

  public byte[] getPublicKey() {
    return publicKey;
  }

  public void setPublicKey(byte[] publicKey) {
    this.publicKey = publicKey;
  }

  public String getStatus() {
    if (status != null) {
      return status.getDescription();
    }
    return null;
  }

  public void setStatus(HandsetStatus status) {
    this.status = status;
  }

  public HandsetNumber getNumber() {
    return number;
  }
  
  public void setNumber(HandsetNumber number) {
    this.number = number;
  }
  
  public String getPhoneNumber() {
    if (number != null) {
      return number.getPhoneNumber();
    }
    return null;
  }

  public void setPhoneNumber(final String phoneNumber, final String simId) {
    final HandsetNumber hn = new HandsetNumber(phoneNumber, simId, this);
    this.number = hn;
  }

  public Timestamp getLastUpdate() {
    return lastUpdate;
  }

  public void setLastUpdate(Timestamp lastUpdate) {
    this.lastUpdate = lastUpdate;
  }

  public String getLastUpdateMessage() {
    return lastUpdateMessage;
  }

  public void setLastUpdateMessage(String lastUpdateMessage) {
    this.lastUpdateMessage = lastUpdateMessage;
  }

  public Person getOwner() {
    return owner;
  }

  public void setOwner(Person owner) {
    this.owner = owner;
  }
  
  public String getHtml() {
    StringBuilder htmlLabel = new StringBuilder();
    
    // first line
    htmlLabel.append("<large>");
    if (owner != null) {
      htmlLabel.append(owner.getFullName());
    }
    else {
      htmlLabel.append(String.format("%s%03d", GatewayUI.HANDSET_PREFIX, id));
    }
    htmlLabel.append("</large><br>");

    // second line
    if (number != null) {
      htmlLabel.append(number.getPhoneNumber());
      htmlLabel.append("<br>");
    }
    else {
      htmlLabel.append(deviceId);
      htmlLabel.append("<br>");
    }
    
    // third line
//    if (owner != null) {
//      htmlLabel.append(String.format("%s%03d&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;",
//          GatewayUI.HANDSET_PREFIX, id));
//    }
    final SimpleDateFormat sdf = new SimpleDateFormat();
    htmlLabel.append("Updated: ");
    htmlLabel.append(sdf.format(lastUpdate));
    
    return htmlLabel.toString();
  }
}
