package pl.lodz.p.it.core.domain;

/**
 * Class responsible for keeping a domain model of the activity object.
 */
public class Activity extends BaseModel {

    private String number;

    private String name;

    private Integer duration;

    private Account trainer;

    private Boolean active;
}
