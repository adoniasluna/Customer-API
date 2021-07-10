package controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import dao.AddressDAO;
import dao.ApiErrorDAO;
import dao.CustomerDAO;
import mappers.AddressMapper;
import mappers.CustomerMapper;
import model.Address;
import model.ApiError;
import model.Customer;
import response.MessageResponse;
import response.StandardResponse;
import response.StatusResponse;
import spark.QueryParamsMap;
import spark.Request;
import validator.Validator;

import java.util.Collection;
import java.util.List;

public class CustomerAPIController {
    public static StandardResponse postCustomer(Request req) {
        StandardResponse response;

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Customer customer = objectMapper.readValue(req.body(), Customer.class);

            // It checks the integrity of customer data
            if (Validator.isCustomerValid(customer) && isCpfUsed(customer.getCpf())) {

                int customerId = CustomerDAO.addCustomer(customer);
                AddressDAO.addAddress(customer.getMainAddress(), customerId);

                response = new StandardResponse(StatusResponse.POST_SUCCESS, MessageResponse.POST_SUCESS);

            } else {
                ApiError apiError = ApiErrorDAO.getApiError(Integer.toString(StatusResponse.POST_INVALID_PARAMS.getStatus()));
                ObjectMapper mapper = new ObjectMapper();

                JsonNode node = mapper.readTree(mapper.writeValueAsString(apiError));
                response = new StandardResponse(StatusResponse.POST_INVALID_PARAMS, node, apiError.getDescription());

            }


        } catch (UnrecognizedPropertyException unrecognizedPropertyException) {
            response = new StandardResponse(StatusResponse.POST_INVALID_PARAMS, MessageResponse.INTERNAL_ERROR);
        } catch (Exception e) {
            response = new StandardResponse(StatusResponse.INTERNAL_ERROR, MessageResponse.INTERNAL_ERROR);
        }

        return response;
    }

    public static StandardResponse getCustomer(Request request) {
        StandardResponse response = getCustomer(request.queryMap());
        return response;
    }

    public static StandardResponse getCustomerById(Request request) {
        String id = request.params(":id");
        StandardResponse response;

        try {
            CustomerMapper customers = CustomerDAO.getCustomerById(id);

            if (customers == null) {
                response = new StandardResponse(StatusResponse.CUSTOMER_BY_ID_NOT_FOUND, MessageResponse.CUSTOMER_BY_ID_NOT_FOUND);
            } else {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode node = mapper.readTree(mapper.writeValueAsString(customers));
                response = new StandardResponse(StatusResponse.GET_SUCCESS, node);
            }

        } catch (Exception e) {
            return new StandardResponse(StatusResponse.INTERNAL_ERROR, MessageResponse.INTERNAL_ERROR);
        }

        return response;
    }

    public static StandardResponse putCustomer(Request req) {
        StandardResponse response;
        String customerId = req.params(":id");

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Customer customer = objectMapper.readValue(req.body(), Customer.class);

            if (customerExist(customerId) && Validator.isCustomerValid(customer)) {
                CustomerDAO.updateCustomer(customer, Integer.parseInt(customerId));

                if (customer.getMainAddress() != null) {
                    AddressDAO.addAddress(customer.getMainAddress(), Integer.parseInt(customerId));
                }

                response = new StandardResponse(StatusResponse.PUT_SUCCESS, MessageResponse.PUT_SUCCESS);
            } else {
                response = new StandardResponse(StatusResponse.CUSTOMER_BY_ID_NOT_FOUND, MessageResponse.CUSTOMER_BY_ID_NOT_FOUND);
            }

        } catch (Exception e) {
            response = new StandardResponse(StatusResponse.INTERNAL_ERROR, MessageResponse.INTERNAL_ERROR);
        }

        return response;
    }

    public static StandardResponse getAddress(Request request) {
        String customerId = request.params(":id");
        StandardResponse response;

        try {
            List<AddressMapper> addresses = AddressDAO.getAddress(customerId);

            if (addresses.isEmpty()) {
                response = new StandardResponse(StatusResponse.ADDRESS_NOT_FOUND, MessageResponse.ADDRESS_NOT_FOUND);
            } else {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode node = mapper.readTree(mapper.writeValueAsString(addresses));
                response = new StandardResponse(StatusResponse.GET_SUCCESS, node);
            }

        } catch (Exception e) {
            return new StandardResponse(StatusResponse.INTERNAL_ERROR, MessageResponse.INTERNAL_ERROR);
        }

        return response;
    }

    public static StandardResponse deleteCustomer(Request req) {
        String id = req.params(":id");
        StandardResponse response;

        try {

            if (customerExist(id)) {
                CustomerDAO.deleteCustomer(Integer.parseInt(id));
                AddressDAO.deletAllAddressByCustomerId(id);
                response = new StandardResponse(StatusResponse.DELETE_SUCESS, MessageResponse.DELETE_SUCESS);
            } else {
                response = new StandardResponse(StatusResponse.INTERNAL_ERROR, MessageResponse.INTERNAL_ERROR);
            }
        } catch (Exception e) {
            response = new StandardResponse(StatusResponse.INTERNAL_ERROR, MessageResponse.INTERNAL_ERROR);
        }

        return response;
    }

    public static StandardResponse getAddressById(Request req) {
        String id = req.params(":id");
        String customerId = req.params(":customerId");
        StandardResponse response;

        try {
            AddressMapper address = AddressDAO.getAddressById(id, customerId);

            if (address == null) {
                response = new StandardResponse(StatusResponse.ADDRESS_BY_ID_NOT_FOUND, MessageResponse.ADDRESS_BY_ID_NOT_FOUND);
            } else {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode node = mapper.readTree(mapper.writeValueAsString(address));
                response = new StandardResponse(StatusResponse.GET_SUCCESS, node);
            }

        } catch (Exception e) {
            return new StandardResponse(StatusResponse.INTERNAL_ERROR, MessageResponse.INTERNAL_ERROR);
        }

        return response;
    }

    public static StandardResponse postAddress(Request req) {
        StandardResponse response = null;

        int customerId = Integer.parseInt(req.params(":id"));

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Address address = objectMapper.readValue(req.body(), Address.class);

            if (Validator.isAddressValid(address)) {
                // Set the current main address to false
                if (address.isMain()) {
                    AddressDAO.updateMainAddress(customerId);
                }

                AddressDAO.addAddress(address, customerId);
            }

        } catch (Exception e) {
            return new StandardResponse(StatusResponse.INTERNAL_ERROR, MessageResponse.INTERNAL_ERROR);
        }

        return response;
    }

    public static StandardResponse putAddress(Request req) {
        String id = req.params(":id");
        String customerId = req.params(":customerId");
        StandardResponse response;

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Address addr = objectMapper.readValue(req.body(), Address.class);

            if (customerExist(customerId) && addressExist(customerId, id)) {
                if(addr.isMain()){
                    AddressDAO.updateMainAddress(Integer.parseInt(customerId));
                }

                AddressDAO.updateAddress(addr, Integer.parseInt(customerId), Integer.parseInt(id));
                response = new StandardResponse(StatusResponse.ADDRESS_PUT_SUCCESS, MessageResponse.PUT_ADDRESS_SUCCESS);
            } else {
                response = new StandardResponse(StatusResponse.ADDRESS_ID_NOT_FOUND, MessageResponse.ADDRESS_NOT_FOUND);
            }

        } catch (Exception e) {
            response = new StandardResponse(StatusResponse.INTERNAL_ERROR, MessageResponse.INTERNAL_ERROR);
        }

        return response;
    }

    private static StandardResponse getCustomer(QueryParamsMap queryParamsMap) {
        StandardResponse response;

        try {
            Collection customers = CustomerDAO.getCustomers(queryParamsMap);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(mapper.writeValueAsString(customers));

            if (node == null) {
                response = new StandardResponse(StatusResponse.CUSTOMER_NOT_FOUND, MessageResponse.CUSTOMER_NOT_FOUND);
            } else {
                response = new StandardResponse(StatusResponse.GET_SUCCESS, node);
            }

        } catch (Exception e) {
            return new StandardResponse(StatusResponse.INTERNAL_ERROR, MessageResponse.INTERNAL_ERROR);
        }

        return response;
    }

    public static StandardResponse deleteAddress(Request req) {
        String id = req.params(":id");
        String customerId = req.params(":customerId");
        StandardResponse response;

        try {
            AddressMapper addr = AddressDAO.getAddressById(id,customerId);

            if (addr.isMain() && customerExist(customerId) && addressExist(customerId, id)) {

                AddressDAO.deleteAddress(Integer.parseInt(customerId));
                response = new StandardResponse(StatusResponse.DELETE_ADDRESS_SUCCESS, MessageResponse.DELETE_SUCESS);
            } else {
                response = new StandardResponse(StatusResponse.ADDRESS_ID_NOT_FOUND, MessageResponse.ADDRESS_BY_ID_NOT_FOUND);
            }
        } catch (Exception e) {
            response = new StandardResponse(StatusResponse.INTERNAL_ERROR, MessageResponse.INTERNAL_ERROR);
        }

        return response;
    }

    private static boolean isCpfUsed(String cpf) {
        return CustomerDAO.getCustomersByCPF(cpf).size() == 0;
    }

    private static boolean customerExist(String id) {
        return CustomerDAO.getCustomerById(id) != null;
    }

    private static boolean addressExist(String customerId, String id) {
        return AddressDAO.getAddressById(id, customerId) != null;
    }


}
