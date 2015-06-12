package com.jda.gateway;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;


@WebListener 
public class SMSServletContextListener implements ServletContextListener {

  /* (non-Javadoc)
   * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
   */
  @Override
  public void contextDestroyed(ServletContextEvent arg0) {
  }

  /* (non-Javadoc)
   * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
   */
  @Override
  public void contextInitialized(ServletContextEvent arg0) {
    // Set the secret salt value for passwords, this value shouldn't be
    // changed after the application has been take into use
    System.setProperty("authentication.password.salt", "Old Salty Dog Blues");

    // The minimum length for passwords is six characters
    System.setProperty("authentication.password.validation.length", "6");

    // Require that all passwords have lower and upper case letters and
    // additionally at least one numeric character. Special characters are
    // not required.
    System.setProperty("authentication.password.validation.lowerCaseRequired", "true");
    System.setProperty("authentication.password.validation.upperCaseRequired", "true");
    System.setProperty("authentication.password.validation.numericRequired", "true");
    System.setProperty("authentication.password.validation.specialCharacterRequired", "false");    
    
    // Only allow three successive failed login attempts before an
    // account becomes locked, on the fourth failed attempt the account
    // will be locked.
    System.setProperty("authentication.maxFailedLoginAttempts", "3");
}

}
