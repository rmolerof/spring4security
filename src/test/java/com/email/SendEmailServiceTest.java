package com.email;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.junit4.SpringRunner;

import hello.Application;
import hello.services.GlobalProperties;
import hello.services.Utils;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class SendEmailServiceTest {
	
	@Autowired
	private Utils utils;
	@Autowired
	private ResourceLoader resourceLoader;
	@Autowired
	private GlobalProperties globalProperties;
	
	private String basePath;
	
	@Test
	public void sendEmailTest() {
		String to = "rmolerof@gmail.com";// mecamolfer@hotmail.com
		String from = globalProperties.getFrom();
		String subject = "GRIFO LA JOYA DE SANTA ISABEL E.I.R.L - FACTURA: F001-0000001";
		String body = "Buen Día. Adjuntado está su comprobante de pago de FACTURA: F001-0000001";
		List<String> attachmentPaths = Arrays.asList(new String("C:/Users/mecam/xmlsSunat/20501568776-07-B001-00000022.XML"), new String("C:/Users/mecam/xmlsSunat/invoice.pdf"));
		
		utils.sendEmail(to, from, subject, body, attachmentPaths);
		assertTrue(true);
	}
	
//	@Test
	public void sendEmailRawTest() {
		
		String to = "";
		String from = "";
		String host = "";
		
		/**
		 * Grifos la Joya Details
		 */
	    to = "rmolerof@gmail.com";
		from = globalProperties.getFrom();
		final String username = globalProperties.getUsername();
		final String password = globalProperties.getPassword();
		host = globalProperties.getHost();
		
		/*
		 * Gmail 
		 */
		/*from = "fromemail@gmail.com";
		final String username = "ruden.madero";
		final String password = "rudenmadero@0820";
		host = "smtp.gmail.com";*/
		
		/*
		 * Amazon SES
		 */
		/*from = "support@grifoslajoya.net";
		// IAM user name: ses-smtp-user.grifoslajoya
		final String username = "AKIAJGROAN7ASXFRCUFQ";
		final String password = "AiNB2ihZiZ5+OxNnH8q21B1ft0hM6BkoyB6lkhTU3dvT";
		host = "email-smtp.us-east-1.amazonaws.com"; */

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", "587");

		// Get the Session object.
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			// Create a default MimeMessage object.
			Message message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

			// Set Subject: header field
			message.setSubject("GRIFO LA JOYA DE SANTA ISABEL E.I.R.L - FACTURA: F001-0000001");

			// Create the message part
			BodyPart messageBodyPart = new MimeBodyPart();

			// Now set the actual message
			messageBodyPart.setText("This is message body.");

			// Create a multipar message
			Multipart multipart = new MimeMultipart();

			// Set text message part
			multipart.addBodyPart(messageBodyPart);

			// Part two is attachment
			addAttachment(multipart, "C:/Users/mecam/xmlsSunat/20501568776-07-B001-00000022.XML");
			addAttachment(multipart, "C:/Users/mecam/xmlsSunat/invoice.pdf");

			// Send the complete message parts
			message.setContent(multipart);

			// Send message
			Transport.send(message);

			System.out.println("Sent message successfully....");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

		
	}

	private static void addAttachment(Multipart multipart, String filename) throws MessagingException {
		DataSource source = new FileDataSource(filename);
		BodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setDataHandler(new DataHandler(source));
		messageBodyPart.setFileName(filename);
		multipart.addBodyPart(messageBodyPart);
	}
	
	public String getBasePath() {
		if (null == this.basePath) {
			try {
				this.basePath = resourceLoader.getResource("classpath:/static/").getFile().getPath();
				
				return this.basePath;
			} catch (IOException e) {
				e.printStackTrace();
			}
		} 
		
		return basePath;
	}
	
}