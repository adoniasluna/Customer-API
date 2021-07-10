package dao;

import mappers.ApiErrorMapper;
import model.ApiError;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import java.util.List;

public class ApiErrorDAO {
    public static ApiError getApiError(String code) {
        Jdbi jdbi = Jdbi.create("jdbc:mysql://localhost:3306", "test_user", "1234");
        jdbi.installPlugin(new SqlObjectPlugin());

        List<ApiErrorMapper> apiError = jdbi.withExtension(IApiError.class, dao -> dao.getApiError(code));

        return apiError.get(0);
    }
}
