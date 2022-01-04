package pl.lodz.p.it.restapi.mapper.account;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import pl.lodz.p.it.core.domain.Account;
import pl.lodz.p.it.restapi.dto.AccountOwnRequestPut;
import pl.lodz.p.it.restapi.dto.AccountRequestPut;
import pl.lodz.p.it.restapi.mapper.BaseRequestMapper;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = IGNORE,
    builder = @Builder(disableBuilder = true))
public interface AccountOwnRequestPutMapper extends BaseRequestMapper<AccountOwnRequestPut, Account> {

}
