package response;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;

public class StandardResponse {

    private StatusResponse status;
    private JsonNode data;
    private String message;

    public StandardResponse(StatusResponse status, MessageResponse msg) {
        this.status = status;
        this.message = msg.getMessageStatus();

        ObjectMapper mapper = new ObjectMapper();
        try {
            this.data = mapper.readTree(msg.getMessageStatus());
        } catch (IOException e){
            ObjectNode node = mapper.createObjectNode();
            data = node.put(String.valueOf(status.getStatus()), msg.getMessageStatus());
        }

    }

    public StandardResponse(StatusResponse status, JsonNode data) {
        this.status = status;
        this.data = data;
    }

    public StandardResponse(StatusResponse status, JsonNode data, String msg) {
        this.status = status;
        this.message = msg;
        this.data = data;
    }

    public StatusResponse getStatus() {
        return status;
    }

    public void setStatus(StatusResponse status) {
        this.status = status;
    }

    public JsonNode getData() {
        return data;
    }

    public void setData(JsonNode data) {
        this.data = data;
    }

    public boolean hasData(){
        return this.data != null;
    }
}
