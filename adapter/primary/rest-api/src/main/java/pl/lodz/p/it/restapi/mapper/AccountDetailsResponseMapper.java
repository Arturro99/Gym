package pl.lodz.p.it.restapi.mapper;

import org.mapstruct.Mapper;
import pl.lodz.p.it.core.domain.Account;
import pl.lodz.p.it.restapi.dto.AccountDetailsResponse;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = IGNORE)
public interface AccountDetailsResponseMapper extends BaseMapper<AccountDetailsResponse, Account> {

}
