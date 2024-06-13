package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.JOptionPane;

public class EmailConfig {

    private static Properties properties;
    private static String content;
    private static MimeMessage email;
    private static Session session;

    public static void createEmailRecoveryPassword(String receiver, String subject, String user, String newPassword) {
        properties = new Properties();

        try {
            properties.load(new FileInputStream("email.properties"));
        } catch (IOException e) {
            System.out.println("Error cargando el fichero email.properties");
            
            // Cargo las porperties por si no existe el fichero de email, cosa que sólo con el .JAR pasaría
            properties.setProperty("mail.smtp.host", "smtp.gmail.com");
            properties.setProperty("mail.smtp.ssl.trust", "smtp.gmail.com");
            properties.setProperty("mail.smtp.starttls.enable", "true");
            properties.setProperty("mail.smtp.port", "587");
            properties.setProperty("mail.smtp.user", "contacto.mediaflix@gmail.com");
            properties.setProperty("mail.smtp.password", "tfmz btos slej nbll");
            properties.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
            properties.setProperty("mail.smtp.auth", "true");

        }

        session = Session.getDefaultInstance(properties);

        try {
            email = new MimeMessage(session);
            email.setFrom(new InternetAddress(properties.getProperty("mail.smtp.user")));
            email.setRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
            email.setSubject(subject);

            content = "<html><body><p>Tu nueva contraseña para el usuario <strong>\"" + user + "\"</strong> es: <strong>" + newPassword + "</strong></p></body></html>";
            email.setContent(content, "text/html");

        } catch (AddressException ex) {
            System.out.println(ex.getMessage());
        } catch (MessagingException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void createEmailWithAttachment(String receiver, String subject, File attachment) {
        properties = new Properties();

        try {
            properties.load(new FileInputStream("email.properties"));
        } catch (IOException e) {
            System.out.println("Error cargando el fichero email.properties");
            
            // Cargo las porperties por si no existe el fichero de email, cosa que sólo con el .JAR pasaría
            properties.setProperty("mail.smtp.host", "smtp.gmail.com");
            properties.setProperty("mail.smtp.ssl.trust", "smtp.gmail.com");
            properties.setProperty("mail.smtp.starttls.enable", "true");
            properties.setProperty("mail.smtp.port", "587");
            properties.setProperty("mail.smtp.user", "contacto.mediaflix@gmail.com");
            properties.setProperty("mail.smtp.password", "tfmz btos slej nbll");
            properties.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
            properties.setProperty("mail.smtp.auth", "true");
        }

        session = Session.getDefaultInstance(properties);

        try {
            email = new MimeMessage(session);
            email.setFrom(new InternetAddress(properties.getProperty("mail.smtp.user")));
            email.setRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
            email.setSubject(subject);

            // Adjunto el archivo al mensaje
            if (attachment != null && attachment.exists()) {
                MimeBodyPart attachmentBodyPart = new MimeBodyPart();
                DataSource source = new FileDataSource(attachment);
                attachmentBodyPart.setDataHandler(new DataHandler(source));
                attachmentBodyPart.setFileName(attachment.getName());

                // Crear el MimeMultipart y añado el ticket
                Multipart multipart = new MimeMultipart();
                multipart.addBodyPart(attachmentBodyPart);

                // Establecer el contenido del mensaje
                email.setContent(multipart);
            } else {
                System.out.println("No se encontró el archivo adjunto.");
            }

        } catch (AddressException ex) {
            System.out.println(ex.getMessage());
        } catch (MessagingException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void sendEmail(String message) {
        try {
            Transport transport = session.getTransport("smtp");
            transport.connect(properties.getProperty("mail.smtp.from"), properties.getProperty("mail.smtp.password"));
            transport.sendMessage(email, email.getRecipients(Message.RecipientType.TO));
            transport.close();

            JOptionPane.showMessageDialog(null, message);
        } catch (NoSuchProviderException ex) {
            System.out.println(ex.getMessage());
        } catch (MessagingException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
