package pl.lodz.p.it.restapi.mapper.account;

import org.mapstruct.Mapper;
import pl.lodz.p.it.core.domain.Account;
import pl.lodz.p.it.restapi.dto.AccountDetailsResponse;
import pl.lodz.p.it.restapi.mapper.BaseResponseMapper;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = IGNORE, uses = AccountDetailsResponseMapper.class)
public interface AccountDetailsResponseMapper extends BaseResponseMapper<AccountDetailsResponse, Account> {

    @Override
    AccountDetailsResponse toDtoModel(Account domainModel);
}
