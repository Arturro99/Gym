package pl.lodz.p.it.restapi.mapper.account;

import org.mapstruct.Mapper;
import pl.lodz.p.it.core.domain.Account;
import pl.lodz.p.it.restapi.dto.AccountGeneralResponse;
import pl.lodz.p.it.restapi.mapper.BaseResponseMapper;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = IGNORE)
public interface AccountGeneralResponseMapper extends BaseResponseMapper<AccountGeneralResponse, Account> {

}
