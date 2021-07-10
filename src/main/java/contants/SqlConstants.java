package contants;

public class SqlConstants {
    public static final String ASC = "ASC";
    public static final String DESC = "DESC";
    public static final String SORT_BY = "ORDER BY";
    public static final String WHERE = "WHERE";

    public static final String SELECT_VERIFY_CPF = "SELECT * FROM customer_api.customer WHERE cpf=";
    public static final String SELECT_ALL_CUSTOMER_JOINED = "SELECT * FROM customer_api.customer JOIN customer_api.address ON customer.id=address.customer_id";
    public static final String WHERE_ID = "WHERE ID=";
}
