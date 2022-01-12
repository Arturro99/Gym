package pl.lodz.p.it.core.shared.constant.messages;

public class MailMessagesPl extends MailMessages {

    @Override
    public String createSubjectForAccountConfirmation() {
        return "Potwierdź konto";
    }

    @Override
    public String createSubjectForPasswordResetting() {
        return "Zresetuj hasło";
    }

    @Override
    public String createSubjectForAccountUnblocking() {
        return "Zablokowano kontot";
    }

    @Override
    public String createTextForAccountConfirmation(String link) {
        return String.join(": ", "Link potwierdzający\n", link);
    }

    @Override
    public String createTextForPasswordResetting(String link) {
        return String.join(": ", "Link do zresetowania hasła", link);
    }

    @Override
    public String createTextForAccountUnblocking(String link) {
        return String.join(": ", "Link do odblokowania konta", link);
    }
}
