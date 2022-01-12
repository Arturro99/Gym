package pl.lodz.p.it.core.application.service.algorithm;

import static lombok.AccessLevel.PRIVATE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;
import pl.lodz.p.it.core.domain.Account;
import pl.lodz.p.it.core.port.secondary.AccountRepositoryPort;

@FieldDefaults(level = PRIVATE)
class ThirdPartyFactorsServiceTest {

    static final float presenceFactor = 1.5f;
    static final float loyaltyFactor = 10.0f;

    @Mock
    AccountRepositoryPort accountRepositoryPort;

    @InjectMocks
    ThirdPartyFactorsService thirdPartyFactorsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        ReflectionTestUtils.setField(
            thirdPartyFactorsService, "presenceFactor", presenceFactor);

        when(accountRepositoryPort.update(any(), any())).thenReturn(null);
    }

    @Test
    void shouldUpdateLoyaltyFactorWhenRewardAccountCalled() {
        //given
        Account account = Account.builder()
            .login("sample")
            .loyaltyFactor(loyaltyFactor)
            .build();

        //when
        thirdPartyFactorsService.rewardAccount(account);

        //then
        Account updatedAccount = Account.builder()
            .loyaltyFactor(loyaltyFactor * presenceFactor)
            .build();
        verify(accountRepositoryPort).update(account.getLogin(), updatedAccount);
    }
}