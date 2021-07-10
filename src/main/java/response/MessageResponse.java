package response;

public enum MessageResponse {
    GET_SUCCESS("200"),
    CREATE_SUCCESS("201"),
    INVALID_PARAMS("400"),
    INTERNAL_ERROR("{\"500\":\"Erro interno do servidor\"}"),
    ERROR("300"),
    CUSTOMER_NOT_FOUND("Não cliente encontrado"),
    CUSTOMER_BY_ID_NOT_FOUND("404"),
    ADDRESS_NOT_FOUND("500: Não foi encontrado nenhum endereço"),
    ADDRESS_BY_ID_NOT_FOUND("Enderço não encontrado"),
    POST_INVALID_PARAMS("Parâmetros invalidos"),
    POST_SUCESS("Cliente inserido com sucesso"),
    PUT_SUCCESS("Clientes listados com sucesso "),
    PUT_ADDRESS_SUCCESS("Endereço listado com sucesso"),
    DELETE_SUCESS("Customer removido com sucesso"),
    DELETE_ADDRESS_SUCCESS("Address removido com sucesso"),
    DELET_ADDRES_CUSTOMER_NOT_FIND("Enderço não encontrado")
    ;

    final private String messageStatus;

    MessageResponse(String status) {
        this.messageStatus = status;
    }

    public String getMessageStatus() {
        return messageStatus;
    }
}
