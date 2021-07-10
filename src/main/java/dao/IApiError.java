package dao;

import mappers.ApiErrorMapper;
import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

import java.util.List;

public interface IApiError {

    @RegisterRowMapper(ApiErrorMapper.class)
    @SqlQuery("SELECT * FROM customer_api.api_error WHERE code=:code")
    List<ApiErrorMapper> getApiError(@Bind("code") String code);
}
