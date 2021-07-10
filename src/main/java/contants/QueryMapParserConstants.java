package contants;

import java.util.HashMap;
import java.util.Map;


public class QueryMapParserConstants {
    public static final String NAME = "name";
    public static final String BIRTH_DATE = "birthDate";
    public static final String STATE = "state";
    public static final String CITY = "city";
    public static final String[] FILTERED_COLUMNS = {NAME, BIRTH_DATE, STATE, CITY};

    public static final String SORT_BY = "sortBy";
    public static final String SORT_ORDER = "sortOrder";

    public static final String SORT_BY_CUSTOMER_NAME = "CUSTOMER_NAME";
    public static final String SORT_BY_CUSTOMER_BIRTH_DATE = "CUSTOMER_BIRTH_DATE";
    public static final String SORT_BY_CUSTOMER_CREATED_AT = "CREATED_AT";
    public static final String SORT_BY_ADDRESS_STATE = "ADDRESS_STATE";
    public static final String SORT_BY_ADDRESS_CITY = "ADDRESS_CITY";
    public static final String[] SORT_BY_COLUMNS = {SORT_BY_CUSTOMER_NAME,
            SORT_BY_CUSTOMER_BIRTH_DATE,
            SORT_BY_CUSTOMER_CREATED_AT,
            SORT_BY_ADDRESS_STATE,
            SORT_BY_ADDRESS_CITY
    };

    public static final Map<String, String> ordedFielToColumn  = new HashMap<String, String>() {{
        put(SORT_BY_CUSTOMER_NAME, CustomerColumnsConstants.NAME);
        put(SORT_BY_CUSTOMER_BIRTH_DATE, CustomerColumnsConstants.BIRTH_DATE);
        put(SORT_BY_ADDRESS_STATE, AddressColumnsConstants.STATE);
        put(SORT_BY_ADDRESS_CITY, AddressColumnsConstants.CITY);
    }};

    public static final Map<String, String> filterFieldToColumn = new HashMap<String, String>() {{
        put(NAME, CustomerColumnsConstants.NAME);
        put(BIRTH_DATE, CustomerColumnsConstants.BIRTH_DATE);
        put(STATE, AddressColumnsConstants.STATE);
        put(CITY, AddressColumnsConstants.CITY);
    }};

    public static final String EQUAL_STATEMENT = "COLUMN = " + "\"" + "VALUE" + "\"";
}

