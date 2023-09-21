package Client;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.*;
import java.util.Date;
import java.util.Properties;
import java.util.Scanner;

public class Email implements Serializable{
    public static boolean sendMail(String recipient, String subject, String content) throws Exception, AddressException{
    	Properties properties = new Properties();
    	properties.put("mail.smtp.auth", "true");
    	properties.put("mail.smtp.starttls.enable", "true");
    	properties.put("mail.smtp.host", "smtp.gmail.com");
    	properties.put("mail.smtp.port", "587");
    	
    	String myAccount = "tharushacse@gmail.com";
    	String password = "ugwkaeoflafqnegb";
    	
    	Session session = Session.getInstance(properties, new Authenticator() {
    		@Override
    		protected PasswordAuthentication getPasswordAuthentication() {
    			return new PasswordAuthentication(myAccount,password);
    		}
    	});
    	Message message = prepareMessage(session, myAccount, recipient, subject, content);
    	
    	try {
			Transport.send(message);

			File myFile = new File("SerializedObjects.txt");
			File Details = new File("EmailDetails.ser");
			Scanner myReader = new Scanner(myFile);
			boolean has = myReader.hasNext();
			myReader.close();
			EmailObject newEmail = new EmailObject(recipient, subject, content, message.getSentDate());
			if(has){
				FileOutputStream fout = new FileOutputStream(Details,true);
				MyObjectOutputStream out = new MyObjectOutputStream(fout);
				out.writeObject(newEmail);
				BufferedWriter out1 = new BufferedWriter(new FileWriter(myFile, true));
				fout.close();
				out.close();
				out1.newLine();
				out1.close();
			}
			else{
				FileOutputStream fout = new FileOutputStream(Details);
				ObjectOutputStream out = new ObjectOutputStream(fout);
				out.writeObject(newEmail);
				BufferedWriter out1 = new BufferedWriter(new FileWriter(myFile, true));
				fout.close();
				out.close();
				out1.newLine();
				out1.close();
			}
		} 
		catch (MessagingException e) {
			System.out.println("An error occurd");
			return false;
		}
		return true;
    }
    public static Message prepareMessage(Session session, String myAccount, String recipient, String subject, String content) {
    	Message message = new MimeMessage(session);
    	try {
			message.setFrom(new InternetAddress(myAccount));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
			message.setSubject(subject);
			message.setText(content);
			message.setSentDate(new Date());
			return message;
		} catch (AddressException e) {
			
		} catch (MessagingException e) {
			
		}
    	return null;
    }
    
}
