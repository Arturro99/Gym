package pl.lodz.p.it.restapi.mapper.account;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import pl.lodz.p.it.core.domain.Account;
import pl.lodz.p.it.restapi.dto.AccountRequestPost;
import pl.lodz.p.it.restapi.mapper.BaseRequestMapper;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = IGNORE,
    builder = @Builder(disableBuilder = true))
public interface AccountRequestPostMapper extends BaseRequestMapper<AccountRequestPost, Account> {

}
