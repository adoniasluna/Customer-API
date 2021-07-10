package mappers;

import contants.ApiColumnsConstant;
import model.ApiError;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ApiErrorMapper extends ApiError implements RowMapper<ApiErrorMapper> {

    public ApiErrorMapper(String code, String description) {
        super(code, description);
    }

    public ApiErrorMapper() {
        super();
    }


    @Override
    public ApiErrorMapper map(ResultSet rs, StatementContext ctx) throws SQLException {
        this.setCode(rs.getString(ApiColumnsConstant.CODE));
        this.setDescription(rs.getString(ApiColumnsConstant.DESCRIPTION));
        return this;
    }
}
