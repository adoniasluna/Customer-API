package parser;

import contants.QueryMapParserConstants;
import contants.SqlConstants;
import spark.QueryParamsMap;

import java.util.*;

public class QueryMapParamsParser {

    public static String getSQLFromQueryMap(QueryParamsMap qp) {
        String sqlStatment = getFilteredColumns(qp);

        if(!sqlStatment.isEmpty()){
            sqlStatment= SqlConstants.WHERE + " " + sqlStatment;
        }

        // To avoid using order clause without order in the query
        if(isSortedBy(qp)){
            sqlStatment +=  " " + getSortBy(qp) + " " + getSortOrder(qp);
        }

        return sqlStatment;
    }

    private static String getFilteredColumns(QueryParamsMap qp) {
        List<String> listColumns = new ArrayList<>();

        qp.toMap().forEach((k, v) -> {
            if (isValidColumn(k)) {
                String column = getFilteredColumnFromKey(k);
                listColumns.add(buildEqualsStatement(column, v[0]));
            }
        });
        StringJoiner joiner = new StringJoiner(" AND ");

        for (String c : listColumns) {
            joiner.add(c);
        }
        return joiner.toString();
    }

    private static String getSortBy(QueryParamsMap qp) {
        String sortBy = "";

        if (qp.hasKey(QueryMapParserConstants.SORT_BY)) {
            String sortByValue = qp.value(QueryMapParserConstants.SORT_BY);

            // Will it ignore more than one sortBy key?
            if (isValidSortBy(sortByValue)) {
                sortBy = SqlConstants.SORT_BY + " " + getOrdedColumnFromKey(sortByValue);
            }
        }

        return sortBy;
    }

    private static String getSortOrder(QueryParamsMap qp) {
        String orderColumn = "";
        if (isSortedBy(qp)) {
            String ordedBy = qp.value(QueryMapParserConstants.SORT_ORDER);

            if (isValidSortOrder(ordedBy)) {
                orderColumn = ordedBy;
            }
        }

        return orderColumn;
    }

    private static String getFilteredColumnFromKey(String k) {
        return QueryMapParserConstants.filterFieldToColumn.get(k);
    }

    private static String getOrdedColumnFromKey(String k){
        return QueryMapParserConstants.ordedFielToColumn.get(k);
    }

    private static boolean isValidSortBy(String k) {
        return Arrays.asList(QueryMapParserConstants.SORT_BY_COLUMNS).contains(k);
    }

    private static boolean isValidSortOrder(String sortOrder) {
        return sortOrder.equals(SqlConstants.ASC) || sortOrder.equals(SqlConstants.DESC);
    }

    private static String buildEqualsStatement(String column, String value) {
        return QueryMapParserConstants.EQUAL_STATEMENT.replace("COLUMN", column).replace("VALUE", value);
    }

    private static boolean isValidColumn(String key) {
        return Arrays.asList(QueryMapParserConstants.FILTERED_COLUMNS).contains(key);

    }

    private static boolean isSortedBy(QueryParamsMap queryParamsMap){
        return queryParamsMap.hasKey(QueryMapParserConstants.SORT_ORDER);
    }
}
