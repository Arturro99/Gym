package pl.lodz.p.it.core.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.OffsetDateTime;
import java.util.HashSet;

/**
 * Class responsible for keeping a domain model of the account object.
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Account extends BaseModel {

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

    private OffsetDateTime lastKnownGoodLogin;

    private String lastKnownGoodLoginIp;

    private OffsetDateTime lastKnownBadLogin;

    private String lastKnownBadLoginIp;

    private Integer badLoginsCounter;

    private Float loyaltyFactor;

    private Boolean gymMember;

    private HashSet<TrainingPlan> trainingPlans;

    private HashSet<Diet> diets;
}
