package com.jda.gateway;

// Install the Java helper library from twilio.com/docs/java/install
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Message;

public class TwilioUtil {

  public static final String TWILIO_PROPS = "META-INF/twilio.properties";
  public static final String ACCOUNT_SID = "account_sid";
  public static final String AUTH_TOKEN = "auth_token";

  public static String sendMessage(final String phoneNumber, final String message) throws TwilioRestException, IOException {
    final TwilioRestClient client = getTwilioClient();
    final String fromNumber = client.getAccount().getIncomingPhoneNumbers().iterator().next().getPhoneNumber();

    // Build a filter for the MessageList
    List<NameValuePair> params = new ArrayList<NameValuePair>();
    params.add(new BasicNameValuePair("Body", message));
    params.add(new BasicNameValuePair("To", phoneNumber));
    params.add(new BasicNameValuePair("From", fromNumber));
    // params.add(new BasicNameValuePair("MediaUrl",
    // "http://www.example.com/hearts.png"));

    MessageFactory messageFactory = client.getAccount().getMessageFactory();
    Message twilioMessage = messageFactory.create(params);
    return twilioMessage.getSid();
  }

  private static TwilioRestClient getTwilioClient() throws IOException {
    TwilioRestClient client = null;

    Properties twilioProps = new Properties();
    twilioProps.load(TwilioUtil.class.getClassLoader().getResourceAsStream(TWILIO_PROPS));
    //twilioProps.load(ClassLoader.getSystemResourceAsStream(TWILIO_PROPS));

    final String sid = twilioProps.getProperty(ACCOUNT_SID);
    final String token = twilioProps.getProperty(AUTH_TOKEN);

    client = new TwilioRestClient(sid, token);
    return client;
  }
}
