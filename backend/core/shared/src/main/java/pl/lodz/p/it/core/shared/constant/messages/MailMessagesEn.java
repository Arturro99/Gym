package pl.lodz.p.it.core.shared.constant.messages;

public class MailMessagesEn extends MailMessages {

    @Override
    public String createSubjectForAccountConfirmation() {
        return "Confirm Account";
    }

    @Override
    public String createSubjectForPasswordResetting() {
        return "Reset password";
    }

    @Override
    public String createSubjectForAccountUnblocking() {
        return "Blocked account";
    }

    @Override
    public String createTextForAccountConfirmation(String link) {
        return String.join(": ", "Confirmation link", link);
    }

    @Override
    public String createTextForPasswordResetting(String link) {
        return String.join(": ", "Link to reset password", link);
    }

    @Override
    public String createTextForAccountUnblocking(String link) {
        return String.join(": ", "Link to unblock account", link);
    }
}
