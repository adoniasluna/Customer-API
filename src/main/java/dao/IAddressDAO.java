package dao;

import mappers.AddressMapper;
import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;

public interface IAddressDAO {
    @SqlUpdate("INSERT INTO customer_api.address(customer_id," +
            " state," +
            " city," +
            " neighborhood," +
            " zipCode," +
            " street," +
            " number," +
            " additional_information," +
            " main) " +
            "VALUES(:customerId, :state, :city, :neighborhood, :zipCode, :street, :number, :additionalInfo, :main )")
    void insertAddress (@Bind("customerId") int CustomerId,
                        @Bind("state") String state,
                        @Bind("city") String city,
                        @Bind("neighborhood") String neighborhood,
                        @Bind("zipCode") String zipCode,
                        @Bind("street") String street,
                        @Bind("number") String number,
                        @Bind("additionalInfo") String additionalInfo,
                        @Bind("main") Boolean main);

    @SqlUpdate("UPDATE customer_api.address SET main=false where customer_id=:id")
    void updateMainAddress(@Bind("id") int customerId);

    @RegisterRowMapper(AddressMapper.class)
    @SqlQuery("SELECT * FROM customer_api.address WHERE customer_id=:id")
    List<AddressMapper> getAddress(@Bind("id") String customer);

    @RegisterRowMapper(AddressMapper.class)
    @SqlQuery("SELECT * FROM customer_api.address WHERE customer_id=:customer_id and address_id=:id")
    List<AddressMapper> getAddressById(@Bind("customer_id") String customer_id, @Bind("id") String id);

    @SqlUpdate("UPDATE customer_api.address " +
            "SET main=false, state=:state, city=:city, neighborhood=:neighborhood, zipCode=:zipCode, street=:street, number=:number, additional_information=:info, main=:main " +
            "where customer_id=:customer_id and address_id=address_id")
    void putAddress(@Bind("customer_id") int customerId,
                    @Bind("address_id") int addressId,
                    @Bind("state") String state,
                    @Bind("city") String city,
                    @Bind("neighborhood") String neighborhood,
                    @Bind("zipCode") String zipCode,
                    @Bind("street") String street,
                    @Bind("number") String number,
                    @Bind("info") String additionalInfo,
                    @Bind("main") Boolean main);

    @SqlUpdate("DELETE FROM customer_api.address WHERE address_id=:id")
    void deleteAddress(@Bind("id") int id);

    @SqlUpdate("DELETE FROM customer_api.address WHERE customer_id=:id")
    void deleteAddressByCustomerId(@Bind("id") String id);
}
