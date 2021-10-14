package pl.lodz.p.it.core.domain;

import java.util.HashSet;

/**
 * Class responsible for keeping a domain model of the diet object.
 */
public class Diet {

    private String number;

    private String name;

    private DietType dietType;

    private Integer calories;

    private Integer mealsNumber;

    private Double price;

    private HashSet<Account> accounts;
}
