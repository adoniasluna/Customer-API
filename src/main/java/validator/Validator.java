package validator;

import model.Address;
import model.Customer;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static java.time.temporal.ChronoUnit.YEARS;

public class Validator {
    public static boolean isCustomerValid(Customer c) {
        return !customerHasFieldNull(c) && isValidBirthDate(c.getBirthDate()) && isAddressValid(c.getMainAddress()) && c.getMainAddress().isMain();
    }

    public static boolean isAddressValid(Address address) {
        boolean isValid = true;


        return isValid;
    }

    private static boolean isValidBirthDate(Date birthDate) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime birth = birthDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

        long years = YEARS.between(birth, now);
        return years <= 100 && years > 0;
    }

    private static boolean customerHasFieldNull(Customer c) {
        return c.getName() == null || c.getBirthDate() == null || c.getCpf() == null || c.getEmail() == null || c.getGender() == null || c.getMainAddress() == null;
    }
}
