package pl.lodz.p.it.core.domain;

import java.sql.Timestamp;
import java.util.HashSet;

/**
 * Class responsible for keeping a domain model of the account object.
 */
public class Account {

    private String login;

    private String email;

    private String password;

    private Boolean active;

    private Boolean confirmed;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String language;

    private Account modifiedBy;

    private Timestamp creationDate;

    private Timestamp modificationDate;

    private Timestamp lastKnownGoodLogin;

    private String lastKnownGoodLoginIp;

    private Timestamp lastKnownBadLogin;

    private String lastKnownBadLoginIp;

    private Integer badLoginsCounter;

    private Float loyaltyFactor;

    private Boolean gymMember;

    private HashSet<TrainingPlan> trainingPlans;

    private HashSet<Diet> diets;
}
