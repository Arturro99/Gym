package pl.lodz.p.it.core.application.service.registration;

import static lombok.AccessLevel.PRIVATE;
import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;
import static org.springframework.transaction.annotation.Propagation.MANDATORY;

import java.util.UUID;
import javax.mail.MessagingException;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.core.domain.Account;
import pl.lodz.p.it.core.port.primary.DisposableUrlServicePort;
import pl.lodz.p.it.core.port.primary.mailing.MailServicePort;
import pl.lodz.p.it.core.port.primary.mailing.OnRegistrationCompleteEvent;
import pl.lodz.p.it.core.shared.constant.UrlAction;
import pl.lodz.p.it.core.shared.constant.messages.MailMessages;
import pl.lodz.p.it.core.shared.constant.messages.MailMessagesEn;
import pl.lodz.p.it.core.shared.constant.messages.MailMessagesPl;

@Service
@AllArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
@Transactional(propagation = MANDATORY, isolation = READ_COMMITTED)
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

    DisposableUrlServicePort disposableUrlServicePort;

    MailServicePort mailServicePort;

    @SneakyThrows
    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(OnRegistrationCompleteEvent event) throws MessagingException {
        Account account = event.getAccount();
        String token = UUID.randomUUID().toString();
        String login = account.getLogin();
        String email = account.getEmail();

        MailMessages mailMessages =
            account.getLanguage().equals("en") ? new MailMessagesEn() : new MailMessagesPl();

        disposableUrlServicePort
            .createDisposableUrl(token, login, UrlAction.CONFIRM_ACCOUNT);

        mailServicePort.sendEmail(email,
            mailMessages.createSubjectForAccountConfirmation(),
        mailMessages.createTextForAccountConfirmation(
            String.format("<a href=\"http://localhost:3000/confirmRegistration/%s\">Link</a>", token)));
    }
}
