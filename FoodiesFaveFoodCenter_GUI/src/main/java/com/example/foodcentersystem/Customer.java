package com.example.foodcentersystem;

import javafx.scene.image.Image;
import java.util.*;

public class Customer {
    private final String customerFirstName;
    private final String customerLastName;
    private int burgerCountRequired;
    private Image photo; // representing a customer object with an image, main usage in QueuesController

    public Customer(String customerFirstName, String customerLastName, int burgerCountRequired) {
        this.customerFirstName = customerFirstName;
        this.customerLastName = customerLastName;
        this.burgerCountRequired = burgerCountRequired;
        this.photo = new Image(Objects.requireNonNull(getClass().getResourceAsStream("png-egg.png")));
    }

    public Image getPhoto() {
        return photo;
    }

    private void setPhoto(Image photo) {
        this.photo = photo;
    }

    public String getCustomerFullName() {
        return this.customerFirstName + " " + this.customerLastName;
    }

    public int getBurgerCountRequired() {
        return burgerCountRequired;
    }

    private void setBurgerCountRequired(int burgerCountRequired) {
        this.burgerCountRequired = burgerCountRequired;
    }

    @Override
    public String toString() {
        return "[" +
                "Customer Name='" + getCustomerFullName() + '\'' +
                ", Order Amount=" + burgerCountRequired +
                ']';
    }

    // Comparing attributes of students
    // overriding the compareTo method of Comparable class
    // Usage of comparator
    public static Comparator<Customer> StuNameComparator = (customer1, customer2) -> {
        String StudentName1 = customer1.getCustomerFullName().toUpperCase();
        String StudentName2 = customer2.getCustomerFullName().toUpperCase();

        // Returning in ascending order
        return StudentName1.compareTo(StudentName2);
    };
}
