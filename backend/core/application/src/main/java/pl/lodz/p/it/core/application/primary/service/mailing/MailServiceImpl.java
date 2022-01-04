package pl.lodz.p.it.core.application.primary.service.mailing;

import static lombok.AccessLevel.PRIVATE;
import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;
import static org.springframework.transaction.annotation.Propagation.MANDATORY;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.core.port.primary.mailing.MailServicePort;

@Service
@Transactional(propagation = MANDATORY, isolation = READ_COMMITTED)
@FieldDefaults(level = PRIVATE)
@AllArgsConstructor
public class MailServiceImpl implements MailServicePort {

    final JavaMailSender mailSender;

    @Value("${email.address}")
    String emailAddress;

    @Autowired
    public MailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendEmail(String to, String subject, String text) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            message.setSubject(subject);

            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(emailAddress);
            helper.setTo(to);
            helper.setText(text, true);
            mailSender.send(message);
        } catch (MessagingException ex) {
            throw new RuntimeException(ex);
        }
    }
}
