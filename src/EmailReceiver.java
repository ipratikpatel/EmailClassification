import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.*;
import javax.mail.Flags.Flag;
import javax.mail.internet.MimeMessage;
import javax.mail.search.FlagTerm;


//HTML parser
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;



public class EmailReceiver {
	
    public static void getEmailFromGmail() 
    {
    	PrintWriter output = null;
    	try {
			output = new PrintWriter("mails.txt", "UTF-8");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("File not found exception!");
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			System.out.println("UnsupportedEncodingException");
			e.printStackTrace();
		}
        Properties props = new Properties();
        props.setProperty("mail.store.protocol", "imaps");
        try {
            Session session = Session.getInstance(props, null);
            Store store = session.getStore();
            store.connect("imap.gmail.com", "username@gmail.com", "password");
            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);
            output.println("Number of emails " + inbox.getMessageCount());
            //System.out.println(inbox.getMessageCount());
            
            Message msg = null;
            Address[] in;
            
            for (int i = 1; i < 10; i++) {
            	output.println("MESSAGE # " + i + " -----------------");
				msg = inbox.getMessage(i);
				in = msg.getFrom();
				for(Address address : in)
					output.println("FROM:" + address.toString());
				
				Object content = (Object) msg.getContent();
	            
	            if(content instanceof String){
	            	String body = (String)content;
	            	body = body.replaceAll("\\r|\\n", " ");
	            	output.println("SENT DATE:" + msg.getSentDate());
	            	output.println("SUBJECT:" + msg.getSubject());
	            	output.println("CONTENT:" + body);
	            }
	            else if(content instanceof Multipart){
		            Multipart mp = (Multipart) content;
		            BodyPart bp = mp.getBodyPart(0);
		            String body = bp.getContent().toString();
		            body = body.replaceAll("\\r|\\n", " ");
		            output.println("SENT DATE:" + msg.getSentDate());
		            output.println("SUBJECT:" + msg.getSubject());
		            output.println("CONTENT:" + body);
		            
	            }
	            else
	            	output.println("What Shenanigan is this?");
			}
            
        } catch (Exception mex) {
            mex.printStackTrace();
        }
        //System.out.println("Done!");
        output.close();
    }
    
    /**
     * Given a folder name containing folder TRAINING with all the training email
     * It converts them in to readable forma or in text
     * @param folderName
     */
    public static void allMailsFromFolder(String folderName){
    	//PrintWriter to write to output.txt file
    	PrintWriter output = null;
    	try {
			output = new PrintWriter("output.txt", "UTF-8");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("File not found exception!");
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			System.out.println("UnsupportedEncodingException");
			e.printStackTrace();
		}
    	
    	String filename = null;
    	if(folderName.equals("TRAINING")){
    		
    		for (int i = 0; i <= 4326; i++) {
    			filename = "TRAINING//TRAIN_";
				filename += String.format("%05d", i);
				filename += ".eml";
				output.println("Email # " + i + " Filename: " + filename + " -----------------");
				
				output.println(mailFromFile(output, filename));
			}
    		
    	} else if(folderName.equals("TESTING")) {
    		
    	} else
    		System.out.println("GO HOME YOU'RE DRUNK!");
    	
    	output.close();  
    	
    }
    
    /**
     * Reads a Email file with .eml extension and
     * prints it to the given PrintWriter.
     * It removes all HTML tags and other preprocessing to use email as a string
     * @param output PrintWriter, used to output the email in a text file
     * @param filename String, Path to the email file
     * @return String containing sender, subject and body of the email
     */
    public static String mailFromFile(PrintWriter output, String filename) {
    	//System.out.println("mailFromTraining");
    	
    	//Get the email file	
		File emlFile = new File(filename);
		
		String ans = "";
		
		//Define Protocols
		Properties props = System.getProperties();
        props.put("mail.host", "smtp.dummydomain.com");
        props.put("mail.transport.protocol", "smtp");
        props.setProperty("mail.mime.multipart.ignoreexistingboundaryparameter", "true");
        //props.setProperty("mail.mime.parameters.strict", "false");
        //props.setProperty("mail.mime.address.strict", "false");

        //Get session instance to read email
        Session mailSession = Session.getDefaultInstance(props, null);
        
        //Read email file
        InputStream source = null;
		try {
			source = new FileInputStream(emlFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "File Not Found";
		}
		Message msg;
		try {
			
			//Get message using session and inputstream
			msg = new MimeMessage(mailSession, source);
			
			//Get all the froms'
			Address[] in;
			in = msg.getFrom();
			for(Address address : in) {
				output.println("FROM:" + address.toString());
				ans += address.toString();
			}
			
			//Get Message Content
			Object content = msg.getContent();
            
            if(content instanceof String){
            	//If message body is a string
            	String body = (String)content;
            	Document doc = Jsoup.parse(body);
            	doc.select("script, style, .hidden").remove();
            	
	            body = doc.body().text();
	            //body = RemoveUrl(body);
	            //body = body.replaceAll("[\S]+://[\S]+", "");
	            
            	output.println("SENT DATE:" + msg.getSentDate());
            	String subject = msg.getSubject();
            	ans += " ";
            	output.println("SUBJECT:" + subject);
            	ans += subject;
            	output.println("CONTENT:" + body);
            	ans += " ";
            	ans += body;
            }
            else if(content instanceof Multipart){
            	//System.out.println("Multipart");
            	//If Message is a Multipart
	            Multipart mp = (Multipart) content;
	            BodyPart bp = mp.getBodyPart(0);
	            String body = bp.getContent().toString();
	            //Remove HTML tags from the body if any
	            Document doc = Jsoup.parse(body);
            	doc.select("script, style, .hidden").remove();

	            body = doc.body().text();
	            //body = body.replaceAll("\\<.*?\\>", "");
	            //body = body.replaceAll("\\r|\\n", " ");
	            output.println("SENT DATE:" + msg.getSentDate());
	            String subject = msg.getSubject();
	            output.println("SUBJECT:" + subject);
	            ans += " ";
	            ans += subject;
	            output.println("CONTENT:" + body.toString());
	            ans += " ";
	            ans += body;
	            
            }
            else
            	output.println("What Shenanigan is this?");

				
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			//System.out.println("Filename: " + filename + " MessagingException");
			output.println("Catch String rep of Mail------------------------------");
			return ans;
			//e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Filename: " + filename + " IOException");
			return ans;
			//e.printStackTrace();
		}
		output.println("String rep of Mail------------------------------");
		//output.println(ans);
		return ans;
    	
    }
    
    public static String RemoveUrl(String commentstr)
    {
        String commentstr1=commentstr;
        String urlPattern = "((https?|ftp|gopher|telnet|file|Unsure|http):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
        Pattern p = Pattern.compile(urlPattern,Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(commentstr1);
        int i=0;
        while (m.find()) {
            commentstr1=commentstr1.replaceAll(m.group(i),"").trim();
            i++;
        }
        return commentstr1;
    }
}

