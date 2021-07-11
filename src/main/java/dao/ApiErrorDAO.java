package dao;

import mappers.ApiErrorMapper;
import model.ApiError;
import org.jdbi.v3.core.Jdbi;

import java.util.List;

public class ApiErrorDAO {
    public static ApiError getApiError(String code) {
        Jdbi jdbi = DataBaseConnector.openSqlUpdateConnection();
        List<ApiErrorMapper> apiError = jdbi.withExtension(IApiError.class, dao -> dao.getApiError(code));

        return apiError.get(0);
    }
}
