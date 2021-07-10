package dao;

import contants.SqlConstants;
import mappers.AddressMapper;
import mappers.CustomerMapper;
import model.Address;
import model.Customer;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.reflect.ConstructorMapper;
import org.jdbi.v3.core.result.RowView;
import parser.QueryMapParamsParser;
import spark.QueryParamsMap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

public class CustomerDAO {

    static public List getCustomers(QueryParamsMap queryParamsMap) throws IOException {
        String filterBy = QueryMapParamsParser.getSQLFromQueryMap(queryParamsMap);
        String query = SqlConstants.SELECT_ALL_CUSTOMER_JOINED + " " + filterBy;
        return getCustomers(query);
    }

    static public List getCustomersByCPF(String cpf) {
        Handle h = DataBaseConnector.openConnection();
        h.registerRowMapper(ConstructorMapper.factory(CustomerMapper.class));
        String sql = SqlConstants.SELECT_VERIFY_CPF + "\'" + cpf + "\'";

        List customers = h.createQuery(sql).map(new CustomerMapper()).list();
        return customers;
    }

    static public CustomerMapper getCustomerById(String id) {
        CustomerMapper customer = null;
        String query = SqlConstants.SELECT_ALL_CUSTOMER_JOINED + " " + SqlConstants.WHERE_ID + "\'" + id + "\'";
        List<CustomerMapper> customerMapperList = getCustomers(query);

        if (customerMapperList.size() == 1) {
            customer = customerMapperList.get(0);
        }

        return customer;
    }

    static public int addCustomer(Customer customer) {
        Jdbi jdbi = DataBaseConnector.openSqlUpdateConnection();

        return jdbi.withExtension(ICustomerDAO.class, dao -> dao.insertCustomer(customer.getName(), customer.getCpf(), customer.getEmail(), customer.getBirthDate(), customer.getGender()));

    }

    private static List<CustomerMapper> getCustomers(String query) {
        Handle handle = DataBaseConnector.openConnection();
        handle.registerRowMapper(ConstructorMapper.factory(CustomerMapper.class));

        List<CustomerMapper> customers = handle.createQuery(query).registerRowMapper(new CustomerMapper())
                .registerRowMapper(new AddressMapper())
                .reduceRows((final Map<Long, CustomerMapper> map, final RowView rowView) -> {
                    Long idCustomer = rowView.getColumn("id", Long.class);
                    final CustomerMapper contact = map.computeIfAbsent(idCustomer, id -> rowView.getRow(CustomerMapper.class));
                    CustomerMapper newCustomer = new CustomerMapper(contact.getId(),
                            contact.getName(),
                            contact.getEmail(),
                            contact.getBirthDate(),
                            contact.getCpf(),
                            contact.getGender(),
                            new ArrayList(contact.getAddresses()),
                            contact.getUpdatedAt(),
                            contact.getUpdatedAt());

                    if (rowView.getColumn("customer_id", Long.class) != null) {
                        AddressMapper address = rowView.getRow(AddressMapper.class);
                        Address newAddress = new Address(address.getState(), address.getCity(), address.getNeighborhood(), address.getZipCode(), address.getStreet(), address.getNumber(), address.getAdditionalInformation(), address.isMain());

                        newCustomer.addAddress(newAddress);
                    }
                    map.put(idCustomer, newCustomer);
                }).collect(toList());

        return customers;
    }


    public static void updateCustomer(Customer customer, int id) {
        Jdbi jdbi = DataBaseConnector.openSqlUpdateConnection();

        jdbi.withExtension(ICustomerDAO.class, dao -> {
            dao.updateCustomer(
                    customer.getName(),
                    customer.getCpf(),
                    customer.getEmail(),
                    customer.getBirthDate(),
                    customer.getGender(),
                    id
            );
            return null;
        });
    }

    public static void deleteCustomer(int id) {
        Jdbi jdbi = DataBaseConnector.openSqlUpdateConnection();
        jdbi.withExtension(ICustomerDAO.class, dao -> {
            dao.deleteCustomer(id);
            return null;
        });

    }
}
