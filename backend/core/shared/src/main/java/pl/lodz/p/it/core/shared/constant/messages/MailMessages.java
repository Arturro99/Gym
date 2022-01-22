package pl.lodz.p.it.core.shared.constant.messages;

public abstract class MailMessages {

    public abstract String createSubjectForAccountConfirmation();

    public abstract String createSubjectForBookingStateChange();

    public abstract String createSubjectForPasswordResetting();

    public abstract String createSubjectForAccountUnblocking();

    public abstract String createTextForAccountConfirmation(String link);

    public abstract String createTextForBookingStateChange(String activity, boolean preferred);

    public abstract String createTextForPasswordResetting(String link);

    public abstract String createTextForAccountUnblocking(String link);
}
