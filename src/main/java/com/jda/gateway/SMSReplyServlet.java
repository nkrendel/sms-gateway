package com.jda.gateway;

import java.io.IOException;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;

import com.twilio.sdk.verbs.Body;
import com.twilio.sdk.verbs.Message;
import com.twilio.sdk.verbs.TwiMLException;
import com.twilio.sdk.verbs.TwiMLResponse;


@WebServlet(value = "/reply", name = "sms-reply-servlet")
public class SMSReplyServlet extends GenericServlet {

  private static final long serialVersionUID = 9146359267590131601L;

  public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
      String fromNumber = request.getParameter("From");
      System.err.println("Received from: " + fromNumber);
//      Map<String, String[]> params = request.getParameterMap();
//      if (params != null && params.size() > 0) {
//        for (Map.Entry<String, String[]> entry : params.entrySet()) {
//          System.err.println(" param entry key: [" + entry.getKey() + "], value: [" + entry.getValue() + "]");
//        }
//      }
      String message = "SMS Gateway User, thanks for the message!";

      // Create a TwiML response and add our friendly message.
      TwiMLResponse twiml = new TwiMLResponse();
      final Body body = new Body(message);
      final Message msg = new Message();
      try {
          msg.append(body);
          twiml.append(msg);
      } catch (TwiMLException e) {
          e.printStackTrace();
      }

      response.setContentType("application/xml");
      response.getWriter().print(twiml.toXML());
  }
}
