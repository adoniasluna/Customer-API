package mappers;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import contants.CustomerColumnsConstants;
import model.Address;
import model.Customer;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.mapper.reflect.JdbiConstructor;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@JsonPropertyOrder({ "id", "name" })
public class CustomerMapper extends Customer implements RowMapper<CustomerMapper> {
    private int id;
    private Collection<Address> addresses = new ArrayList<>();
    private String createdAt;
    private String updatedAt;

    @JdbiConstructor
    public CustomerMapper(){

    }

    public CustomerMapper(int id, String name, String email, Date birthDate, String cpf, String gender, ArrayList addresses, String createdAt, String updatedAt){
        super(name, email, birthDate, cpf, gender);
        this.addresses = addresses;
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;

    };

    @Override
    public CustomerMapper map(ResultSet rs, StatementContext ctx) throws SQLException {
        // Customer
        this.setName(rs.getString(CustomerColumnsConstants.NAME));
        this.setEmail(rs.getString(CustomerColumnsConstants.EMAIL));
        this.setBirthDate(rs.getDate(CustomerColumnsConstants.BIRTH_DATE));
        this.setCpf(rs.getString(CustomerColumnsConstants.CPF));
        this.setGender(rs.getString(CustomerColumnsConstants.GENDER));

        this.setCreatedAt(new SimpleDateFormat("dd-M-yyyy'T'hh:mm:ss").format(rs.getTimestamp(CustomerColumnsConstants.CREATED_AT)));
        this.setUpdatedAt(new SimpleDateFormat("dd-M-yyyy'T'hh:mm:ss").format(rs.getTimestamp(CustomerColumnsConstants.CREATED_AT)));
        this.setId(rs.getInt(CustomerColumnsConstants.CUSTOMER_ID));

        return this;
    }



    public Collection<Address> getAddresses() {
        return addresses;
    }

    public void addAddress(Address row) {
        this.addresses.add(row);
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
