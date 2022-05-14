package com.example.footballbootswebapiis.mail;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EmailSender {

    public static void sendEmail(Email email) throws MessagingException, IOException {
        Properties props = System.getProperties();
        props.setProperty("mail.smtp.host", "smtp.gmail.com");
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.auth", "true");
        props.put("mail.debug", "true");
        props.put("mail.store.protocol", "pop3");
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.starttls.required", "true");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");

        final String username = "letuscodeforyou@gmail.com";
        final String password = "pocpitacuunsore";
        Session session = Session.getDefaultInstance(props,
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(email.getFrom()));
        message.setRecipients(
                Message.RecipientType.TO, InternetAddress.parse(email.getTo()));
        message.setSubject(email.getSubject());

        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(email.getBody(), "text/html; charset=utf-8");

        MimeBodyPart attachmentPart = new MimeBodyPart();
        attachmentPart.attachFile(new File("./xmlBill.xml"));

        MimeBodyPart attachmentPart2 = new MimeBodyPart();
        attachmentPart2.attachFile(new File("./jsonBill.json"));

        MimeBodyPart attachmentPart3 = new MimeBodyPart();
        attachmentPart3.attachFile(new File("./pdfBill.pdf"));

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);
        multipart.addBodyPart(attachmentPart);
        multipart.addBodyPart(attachmentPart2);
        multipart.addBodyPart(attachmentPart3);

        message.setContent(multipart);

        Transport.send(message);
    }
}
