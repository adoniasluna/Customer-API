package model;

public class ApiError  {
    private String code;
    private String description;

    public ApiError(String code, String description) {
        this.code = code;
        this.description = description;
    }


    public ApiError(){

    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
