package model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

public class Customer {

    private String name;
    private String email;
    private Date birthDate;
    private String cpf;
    private String gender;

    private Address mainAddress;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @JsonIgnore
    public Address getMainAddress() {
        return mainAddress;
    }

    public void setMainAddress(Address mainAddress) {
        this.mainAddress = mainAddress;
    }

    public Customer(String name, String email, Date birthDate, String cpf, String gender) {
        this.name = name;
        this.email = email;
        this.birthDate = birthDate;
        this.cpf = cpf;
        this.gender = gender;
    }

    public Customer(){};
}
