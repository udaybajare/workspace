package com.invmgmt.util;

import java.io.File;
import java.util.Properties;

import javax.activation.CommandMap;
import javax.activation.MailcapCommandMap;
import javax.annotation.ManagedBean;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

@ManagedBean
public class EmailUtils {
 
    public JavaMailSender getJavaMailSender() {
	    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
	    mailSender.setHost("smtp.gmail.com");
	    mailSender.setPort(25);
	    mailSender.setUsername("udaybajare2@gmail.com");
	    mailSender.setPassword("sadguru12");
	     
	    Properties props = mailSender.getJavaMailProperties();
	    props.put("mail.transport.protocol", "smtp");
	    props.put("mail.smtp.auth", "false");
	    props.put("mail.smtp.starttls.enable", "true");
	    props.put("mail.debug", "true");
	    props.put("multipart/*","x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
	    
	    MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap(); 
	    mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html"); 
	    mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml"); 
	    mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain"); 
	    mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed"); 
	    mc.addMailcap("message/rfc822;; x-java-content- handler=com.sun.mail.handlers.message_rfc822");
	    
	    return mailSender;
	}
    
    
    public void sendMessageWithAttachment(
	    String to, String taxInvoiceName) {
	// ...

	String subject = "Tax Invoice : Hamdule Industries";
	String text = "Please find attached the Tax Invoice.";
	String pathToAttachment = System.getProperty("java.io.tmpdir") + "/TaxInvoice.pdf";
	
	MimeMessage message = getJavaMailSender().createMimeMessage();

	MimeMessageHelper helper;
	try {
	    helper = new MimeMessageHelper(message, true);
	    helper.setTo(to);
	    helper.setSubject(subject);
	    helper.setText(text);

	    FileSystemResource file = new FileSystemResource(new File(pathToAttachment));
	    helper.addAttachment(taxInvoiceName+".pdf", file);

	} catch (MessagingException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

	getJavaMailSender().send(message);
	// ...
    }
}
