package mappers;

import contants.AddressColumnsConstants;
import model.Address;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AddressMapper extends Address implements RowMapper<AddressMapper> {
    int id;

    public AddressMapper(){
        super();
    }



    @Override
    public AddressMapper map(ResultSet rs, StatementContext ctx) throws SQLException {
        this.setState(rs.getString(AddressColumnsConstants.STATE));
        this.setCity(rs.getString(AddressColumnsConstants.CITY));
        this.setNeighborhood(rs.getString(AddressColumnsConstants.NEIGHBORHOOD));
        this.setZipCode(rs.getString(AddressColumnsConstants.ZIP_CODE));
        this.setState(rs.getString(AddressColumnsConstants.STREET));
        this.setNumber(rs.getString(AddressColumnsConstants.NUMBER));
        this.setAdditionalInformation(rs.getString(AddressColumnsConstants.ADDITIONAL_INFORMATION));
        this.setMain(rs.getBoolean(AddressColumnsConstants.MAIN));
        this.id = rs.getInt(AddressColumnsConstants.ADDRESS_ID);

        return this;
    }


}
