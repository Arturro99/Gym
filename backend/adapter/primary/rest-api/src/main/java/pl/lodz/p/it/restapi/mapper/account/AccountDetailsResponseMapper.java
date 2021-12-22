package pl.lodz.p.it.restapi.mapper.account;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

import org.mapstruct.Mapper;
import pl.lodz.p.it.core.domain.Account;
import pl.lodz.p.it.restapi.dto.AccountDetailsResponse;
import pl.lodz.p.it.restapi.mapper.BaseResponseMapper;
import pl.lodz.p.it.restapi.mapper.diet.DietResponseMapper;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = IGNORE, uses = DietResponseMapper.class)
public interface AccountDetailsResponseMapper extends
    BaseResponseMapper<AccountDetailsResponse, Account> {

}
