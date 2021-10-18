package pl.lodz.p.it.core.domain;

/**
 * Class responsible for keeping a domain model of the booking object.
 */
public class Booking extends BaseModel {

    private String number;

    private Account account;

    private Activity activityEntity;

    private Boolean active;

    private Boolean completed;
}
