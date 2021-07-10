package dao;

import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.Date;

public interface ICustomerDAO {

    @GetGeneratedKeys
    @SqlUpdate("INSERT INTO customer_api.customer (name," +
            "cpf," +
            "email," +
            "birth_date," +
            "gender," +
            "created_at," +
            "updated_at) " +
            "VALUES (:name, :cpf, :email, :date, 'M',  NOW(), NOW())")
    int insertCustomer(@Bind("name") String name,
                       @Bind("cpf") String cpf,
                       @Bind("email") String email,
                       @Bind("date") Date date,
                       @Bind("gender") String gender);


    @SqlUpdate("UPDATE customer_api.customer SET name = :name, " +
            "cpf = :cpf," +
            "email = :email," +
            "birth_date = :date," +
            "gender= :gender," +
            "updated_at = NOW() " +
            "WHERE id = :id")
    void updateCustomer(@Bind("name") String name,
                        @Bind("cpf") String cpf,
                        @Bind("email") String email,
                        @Bind("date") Date date,
                        @Bind("gender") String gender,
                        @Bind("id") int id);

    @SqlUpdate("DELETE FROM customer_api.customer WHERE id=:id")
    void deleteCustomer(@Bind("id") int id);

}