package dao;

import mappers.AddressMapper;
import model.Address;
import org.jdbi.v3.core.Jdbi;

import java.util.List;


public class AddressDAO {
    public static void addAddress(Address address, int customerId) {
        Jdbi jdbi = DataBaseConnector.openSqlUpdateConnection();

        jdbi.withExtension(IAddressDAO.class, dao -> {
            dao.insertAddress(customerId,
                    address.getState(),
                    address.getCity(),
                    address.getNeighborhood(),
                    address.getZipCode(),
                    address.getStreet(),
                    address.getNumber(),
                    address.getAdditionalInformation(),
                    address.isMain());
            return null;
        });
    }

    public static void updateMainAddress(int customerId) {
        Jdbi jdbi = DataBaseConnector.openSqlUpdateConnection();

        jdbi.withExtension(IAddressDAO.class, dao -> {
            dao.updateMainAddress(customerId);
            return null;
        });
    }

    public static List<AddressMapper> getAddress(String customerId) {
        Jdbi jdbi = DataBaseConnector.openSqlUpdateConnection();

        return jdbi.withExtension(IAddressDAO.class, dao -> dao.getAddress(customerId));
    }

    public static AddressMapper getAddressById(String id, String customerId) {
        Jdbi jdbi = DataBaseConnector.openSqlUpdateConnection();
        List<AddressMapper> addressList = jdbi.withExtension(IAddressDAO.class, dao -> dao.getAddressById(customerId, id));
        AddressMapper address = null;

        if (addressList.size() == 1) {
            address = addressList.get(0);
        }


        return address;
    }

    public static void updateAddress(Address addr, int customerId, int id) {
        Jdbi jdbi = DataBaseConnector.openSqlUpdateConnection();
        jdbi.withExtension(IAddressDAO.class, dao -> {
            dao.putAddress(customerId,
                    id,
                    addr.getState(),
                    addr.getCity(),
                    addr.getNeighborhood(),
                    addr.getZipCode(),
                    addr.getStreet(),
                    addr.getNumber(),
                    addr.getAdditionalInformation(),
                    addr.isMain());
            return null;
        });
    }

    public static void deleteAddress(int id) {
        Jdbi jdbi = DataBaseConnector.openSqlUpdateConnection();
        jdbi.withExtension(IAddressDAO.class, dao -> {
            dao.deleteAddress(id);
            return null;
        });
    }

    public static void deletAllAddressByCustomerId(String id) {
        Jdbi jdbi = DataBaseConnector.openSqlUpdateConnection();
        jdbi.withExtension(IAddressDAO.class, dao -> {
            dao.deleteAddressByCustomerId(id);
            return null;
        });
    }
}
