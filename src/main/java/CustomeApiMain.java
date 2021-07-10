import controller.CustomerAPIController;
import response.StandardResponse;

import static spark.Spark.*;

public class CustomeApiMain {
    public static void main(String[] args) throws Exception {
        get("/customers", (req, res) -> {
            res.type("application/json");
            StandardResponse r = CustomerAPIController.getCustomer(req);

            res.status(r.getStatus().getStatus());
            return r.getData();
        });


        get("/customers/:id", (req, res) -> {
            res.type("application/json");
            StandardResponse r = CustomerAPIController.getCustomerById(req);

            res.status(r.getStatus().getStatus());
            return r.getData();
        });

        post("/customers", (req, res) -> {
            res.type("application/json");
            StandardResponse r = CustomerAPIController.postCustomer(req);

            res.status(r.getStatus().getStatus());

            if (r.hasData()) {
                return r.getData();
            } else {
                return r.getStatus().getStatus();
            }
        });

        post("/customers/:id/addresses", (req, res) -> {
            res.type("application/json");
            StandardResponse r = CustomerAPIController.postAddress(req);


            return r.getData();
        });

        put("/customers/:id", (req, res) -> {
            StandardResponse r = CustomerAPIController.putCustomer(req);
            return r;
        });

        delete("/customers/:id", (req, res) -> {
            // Update something
            StandardResponse r = CustomerAPIController.deleteCustomer(req);
            return r;
        });

        get("/customers/:id/addresses", (req, res) -> {
            res.type("application/json");
            StandardResponse r = CustomerAPIController.getAddress(req);

            res.status(r.getStatus().getStatus());
            return r.getData();
        });

        get("/customers/:id/addresses/:addressId", (req, res) -> {
            res.type("application/json");
            StandardResponse r = CustomerAPIController.getAddressById(req);

            res.status(r.getStatus().getStatus());
            return r.getData();
        });

        put("/customers/:customerId/adresses/:id", (req, res) -> {
            StandardResponse r = CustomerAPIController.putAddress(req);
            return r;
        });

        delete("/customers/:customerId/adresses/:id", (req, res) -> {
            StandardResponse r = CustomerAPIController.deleteAddress(req);
            return r;
        });
    }


}