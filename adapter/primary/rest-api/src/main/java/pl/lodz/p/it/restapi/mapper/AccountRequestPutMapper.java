package pl.lodz.p.it.restapi.mapper;

import org.mapstruct.Mapper;
import pl.lodz.p.it.core.domain.Account;
import pl.lodz.p.it.restapi.dto.AccountRequestPut;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = IGNORE)
public interface AccountRequestPutMapper extends BaseMapper<AccountRequestPut, Account> {

}
