package pl.lodz.p.it.core.application.primary.service.algorithm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;
import pl.lodz.p.it.core.domain.Account;
import pl.lodz.p.it.core.port.secondary.AccountRepositoryPort;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class GoodsFactorServiceTest {

    static final float addingFactor = 1.1f;
    static final float removingFactor = 0.91f;
    static final float loyaltyFactor = 10f;

    @Mock
    AccountRepositoryPort accountRepositoryPort;
    @InjectMocks
    GoodsFactorService goodsFactorService;
    Account account;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        account = Account.builder()
            .loyaltyFactor(loyaltyFactor)
            .build();

        ReflectionTestUtils.setField(
            goodsFactorService, "dietAddingFactor", addingFactor);
        ReflectionTestUtils.setField(
            goodsFactorService, "dietRemovingFactor", removingFactor);
        ReflectionTestUtils.setField(
            goodsFactorService, "trainingPlanAddingFactor", addingFactor);
        ReflectionTestUtils.setField(
            goodsFactorService, "trainingPlanRemovingFactor", removingFactor);

        when(accountRepositoryPort.save(any())).thenReturn(null);
    }

    @Test
    void shouldReturnProperLoyaltyFactorWhenIncreaseDietOrIncreaseTrainingPlanPossessingFactorCalled() {
        //given
        Float dietFactor;
        Float trainingPlanFactor;

        //when
        dietFactor = goodsFactorService.getIncreasedDietPossessingFactor(account);
        trainingPlanFactor = goodsFactorService.getIncreasedTrainingPlanPossessingFactor(account);

        //then
        assertEquals(loyaltyFactor * addingFactor, dietFactor);
        assertEquals(loyaltyFactor * addingFactor, trainingPlanFactor);
    }

    @Test
    void shouldProperlySetLoyaltyFactorWhenDecreaseDietOrIncreaseTrainingPlanPossessingFactorCalled() {
        //given
        Float dietFactor;
        Float trainingPlanFactor;

        //when
        dietFactor = goodsFactorService.getDecreasedDietPossessingFactor(account);
        trainingPlanFactor = goodsFactorService.getDecreasedTrainingPlanPossessingFactor(account);

        //then
        assertEquals(loyaltyFactor * removingFactor, dietFactor);
        assertEquals(loyaltyFactor * removingFactor, trainingPlanFactor);
    }
}