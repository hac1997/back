package ifsc.edu.tpj.service;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;
    @Value(value = "${spring.mail.username}")
    private String senderAddress;

    @Async
    public void sendMailMessage(
            @NotBlank @NotNull String subject,
            @NotBlank @NotNull String body,
            @Email String receiverAddress
    ) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            message.setSubject(subject);
            message.setText(body);
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(receiverAddress));
            message.setSender(new InternetAddress(senderAddress));
        } catch (MessagingException e) {
            System.out.println("Erro ao enviar email: " + e.getMessage());
        }

        mailSender.send(message);
    }
}
