package pl.lodz.p.it.core.domain;

import java.sql.Timestamp;

/**
 * Class responsible for keeping a domain model of the activity object.
 */
public class Activity {

    private String number;

    private String name;

    private Timestamp creationDate;

    private Integer duration;

    private Account trainer;

    private Boolean active;
}
