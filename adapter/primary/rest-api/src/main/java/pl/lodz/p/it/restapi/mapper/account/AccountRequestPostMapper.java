package pl.lodz.p.it.restapi.mapper.account;

import org.mapstruct.Mapper;
import pl.lodz.p.it.core.domain.Account;
import pl.lodz.p.it.restapi.dto.AccountRequestPost;
import pl.lodz.p.it.restapi.mapper.BaseRequestMapper;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = IGNORE)
public interface AccountRequestPostMapper extends BaseRequestMapper<AccountRequestPost, Account> {

}
