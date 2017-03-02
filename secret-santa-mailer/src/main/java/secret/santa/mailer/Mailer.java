package secret.santa.mailer;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import lombok.Data;
import lombok.experimental.Accessors;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

public class Mailer
{
    private final Properties properties;
    
    public Mailer(final Properties properties) {
        this.properties = properties;
    }

    public Mail compose()
    {
        return new Mail(Session.getInstance(properties));
    }
    
    @Data
    @Accessors(fluent = true)
    public class Mail
    {
        private final MimeMessage message;
        private final Session session;
        
        private List<MailerAddress> fromAddresses = new ArrayList<MailerAddress>();
        private List<MailerAddress> toAddresses = new ArrayList<MailerAddress>();
        private List<MailerAddress> ccAddresses = new ArrayList<MailerAddress>();
        private String subject = "";
        private String body = "";
        private String charset = "UTF-8";
        private String mimeSubType = "plain";
        private List<Attachment> attachments = new ArrayList<Attachment>();
        
        public Mail from(MailerAddress ... from)
        {
            this.fromAddresses.addAll(Arrays.asList(from));
            return this;
        }
        
        public Mail to(MailerAddress ... to)
        {
            this.toAddresses.addAll(Arrays.asList(to));
            return this;
        }
        
        public Mail cc(MailerAddress ... cc)
        {
            this.ccAddresses.addAll(Arrays.asList(cc));
            return this;
        }
        
        public Mail cc(List<MailerAddress> cc)
        {
            if (cc != null && !cc.isEmpty()) this.ccAddresses.addAll(cc);
            return this;
        }
        
        public Mail body(final String body)
        {
            this.body = body;
            return this;
        }
        
        public void send()
        {
            System.out.println("SENDING=[FROM=[" + StringUtils.join(fromAddresses, ", ") + "], TO=[" + StringUtils.join(toAddresses, ", ") + "], SUBJECT=["+ subject +"]]");
            
            try
            {
                addFromAddress(message);
                addToAddresses(message);
                addCcAddress(message);
                
                message.setSubject(subject);
                message.setText((body != null ? body : ""), charset, mimeSubType);
                
                int smtpPort = NumberUtils.toInt("" + session.getProperties().get("mail.smtp.port"), 25);
                
                if (smtpPort != 25)
                {
                    message.saveChanges();
                    
                    Transport transport = this.session.getTransport();
                    transport.connect(this.session.getProperties().getProperty("mail.smtp.host", "localhost"), 
                                      smtpPort, 
                                      null, 
                                      null);
                    
                    transport.sendMessage(message, message.getAllRecipients());
                }
                else
                {
                    Transport.send(message);
                }
            }
            catch (MessagingException e)
            {
                e.printStackTrace();
            }
        }
        
        private void addFromAddress(Message message) throws MessagingException
        {
            List<Address> inetAddresses = new ArrayList<Address>();
            fromAddresses.forEach(a -> {inetAddresses.add(a.getInternetAddress());});
            
            if (!inetAddresses.isEmpty()) message.addFrom(inetAddresses.toArray(new Address[0]));
        }
        
        private void addToAddresses(Message message) throws MessagingException
        {
            addRecipientAddresses(RecipientType.TO, message, toAddresses);
        }

        private void addCcAddress(Message messge) throws MessagingException
        {
           addRecipientAddresses(RecipientType.CC, message, ccAddresses);
        }
        
        private void addRecipientAddresses(RecipientType type, Message message, List<MailerAddress> addresses) throws MessagingException
        {
            List<Address> inetAddresses = new ArrayList<Address>();
            addresses.forEach(a -> {inetAddresses.add(a.getInternetAddress());});
            if (!inetAddresses.isEmpty()) message.addRecipients(type, inetAddresses.toArray(new Address[0]));
        }

        private Mail(Session session)
        {
            this.session = session;
            this.message = new MimeMessage(session);
        }
    }

    @Data
    @Accessors(fluent = true)
    public class Attachment
    {
        String name;
        byte [] data;
        String mimeType = "text/Plain";
        
        public String toString()
        {
            return "ATTACHMENT=[NAME=[" + name + "]" +
                   ("text/plain".equalsIgnoreCase(mimeType) ? ", DATA=[" + new String(data) + "]]" : "") +  
                   "]"; 
        }
    }
    
    @Data(staticConstructor = "email")
    @Accessors(fluent = true)
    public static class MailerAddress {
        private final String email;
        private String alias;
        
        public InternetAddress getInternetAddress() {
            try
            {
                return alias != null ? new InternetAddress(email, alias) : new InternetAddress(email);
            }
            catch (AddressException | UnsupportedEncodingException e)
            {
                e.printStackTrace();
            }
            return null;
        }
    }
    
    @Data(staticConstructor = "with")
    @Accessors(fluent = true)
    public static class Builder {
        private String host = "localhost";
        private String port = "25";
        
        public Mailer build() {
            Properties properties = new Properties();
            properties.setProperty("mail.smtp.host", host);
            properties.setProperty("mail.smtp.port", port);
            
            return new Mailer(properties);
        }
    }
    

}
