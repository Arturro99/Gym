package pl.lodz.p.it.core.shared.constant.messages;

public class MailMessagesPl extends MailMessages {

    @Override
    public String createSubjectForAccountConfirmation() {
        return "Potwierdź konto";
    }

    @Override
    public String createSubjectForBookingStateChange() {
        return "Zmiana statusu rezerwacji";
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
    public String createTextForBookingStateChange(String activity, boolean preferred) {
        return preferred ?
            String.join(" ", "Twoja rezerwacja została zakwalifikowana do udziału w aktywności", activity) :
            String.join(" ", "Niestety, Twoja rezerwacja na aktywność", activity, "została przesunięta na listę oczekujących.");
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
