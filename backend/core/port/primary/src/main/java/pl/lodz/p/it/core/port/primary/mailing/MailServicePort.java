package pl.lodz.p.it.core.port.primary.mailing;

import javax.mail.MessagingException;

/**
 * Interface responsible for providing methods associated with mailing.
 */
public interface MailServicePort {

    /**
     * Method responsible for sending a simple email message.
     *
     * @param to      The email address of the user that is going to receive the mail
     * @param subject The subject of the mail
     * @param text    The content of the mail
     */
    void sendEmail(String to, String subject, String text) throws MessagingException;
}
