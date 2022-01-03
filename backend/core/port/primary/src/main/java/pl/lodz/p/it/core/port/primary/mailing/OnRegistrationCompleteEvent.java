package pl.lodz.p.it.core.port.primary.mailing;

import static lombok.AccessLevel.PRIVATE;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.context.ApplicationEvent;
import pl.lodz.p.it.core.domain.Account;

@FieldDefaults(level = PRIVATE)
@Getter
@Setter
public class OnRegistrationCompleteEvent extends ApplicationEvent {

    Account account;

    public OnRegistrationCompleteEvent(Account account) {
        super(account);

        this.account = account;
    }
}
