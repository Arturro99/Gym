package pl.lodz.p.it.core.port.primary;

import pl.lodz.p.it.core.domain.DisposableUrl;
import pl.lodz.p.it.core.shared.constant.UrlAction;

/**
 * Interface responsible for integrating disposable url controller with services.
 */
public interface DisposableUrlServicePort extends BaseServicePort<DisposableUrl> {

    /**
     * Method responsible for creating a new disposable url with provided url, login and action
     * type.
     *
     * @param url        Value of the new url
     * @param login      User's login
     * @param actionType Action type for a new url
     */
    void createDisposableUrl(String url, String login, UrlAction actionType);

    /**
     * Method responsible for confirming user registration.
     *
     * @param token Disposable url's identifier
     */
    void confirmRegistration(String token);
}
