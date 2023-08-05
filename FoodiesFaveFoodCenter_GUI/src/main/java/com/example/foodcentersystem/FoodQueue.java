package com.example.foodcentersystem;

import java.util.ArrayList;
import java.util.Arrays;

public class FoodQueue {
    private final int queueNumber; // Considering as an ID of a queue object
    private final int maxQueueLength;
    private double queueIncome;
    private double burgerPrice; // Price of a burger is LKR 650

    private final ArrayList<Customer> removedCustomers;
    private final ArrayList<Customer> queueWithCustomers;


    public FoodQueue(int queueNumber, int maxQueueLength, int burgerPrice) {
        this.queueNumber = queueNumber;
        this.maxQueueLength = maxQueueLength;
        this.queueWithCustomers = new ArrayList<>(maxQueueLength);
        this.removedCustomers = new ArrayList<>();
        this.burgerPrice = burgerPrice;
    }

    public int getQueueNumber() {
        return queueNumber;
    }

    public int getMaxQueueLength() {
        return maxQueueLength;
    }

    public double getBurgerPrice() {
        return burgerPrice;
    }

    private void setBurgerPrice(double burgerPrice) {
        this.burgerPrice = burgerPrice;
    }

    public ArrayList<Customer> getQueueWithCustomers() {
        return queueWithCustomers;
    }

    public void addCustomerToQueue(Customer customer) {
        this.queueWithCustomers.add(customer);
    }

    public String removeCustomerFromQueue(int index) {
        if (this.queueWithCustomers.get(index) != null) {
            Customer removedCustomer = this.queueWithCustomers.remove(index);
            return removedCustomer.getCustomerFullName();
        } else {
            return "There are no customers in the queue.";
        }
    }

    public void sortCustomers() {
        ArrayList<Customer> clonedArrayList = new ArrayList<>(this.getQueueWithCustomers());
        clonedArrayList.sort(Customer.StuNameComparator);

        System.out.println("Cashier " + this.getQueueNumber() + " :" + toString(clonedArrayList));
    }

    public String toString(ArrayList<Customer> CustomersList) {
        ArrayList<String> customerNames = new ArrayList<>();
        for (Customer customer : CustomersList) {
            customerNames.add(customer.getCustomerFullName());
        }
        Object[] customerNamesArray = customerNames.toArray();
        return Arrays.toString(customerNamesArray);
    }

    public double getQueueIncome() {
        return this.queueIncome;
    }

    public void setQueueIncome() {
        int totalBurgersServed = 0;
        for (Customer removedCustomer : removedCustomers) {
            totalBurgersServed += removedCustomer.getBurgerCountRequired();
        }

        queueIncome = (totalBurgersServed) * (burgerPrice);
    }

    public ArrayList<Customer> getRemovedCustomers() {
        return removedCustomers;
    }

    public void addRemovedCustomers(Customer removedCustomer) {
        this.removedCustomers.add(removedCustomer);
    }

    public int getTotalBurgersServed() {
        int totalBurgersServed = 0;
        for (Customer removedCustomer: this.removedCustomers) {
            totalBurgersServed += removedCustomer.getBurgerCountRequired();
        }
        return totalBurgersServed;
    }
}
