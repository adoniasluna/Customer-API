package response;

public enum StatusResponse {
    GET_SUCCESS(200),
    CREATE_SUCCESS(201),
    POST_INVALID_PARAMS(400),
    INTERNAL_ERROR(500),
    ERROR(300),
    CUSTOMER_NOT_FOUND(204),
    CUSTOMER_BY_ID_NOT_FOUND(404),
    ADDRESS_ID_NOT_FOUND(404),
    ADDRESS_NOT_FOUND(204),
    ADDRESS_BY_ID_NOT_FOUND(204),
    POST_SUCCESS(201),
    PUT_SUCCESS(200),
    ADDRESS_PUT_SUCCESS(200),
    DELETE_SUCESS(204),
    DELETE_ADDRESS_SUCCESS(200)
    ;
    final private int status;

    StatusResponse(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
