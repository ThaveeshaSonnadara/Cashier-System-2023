package com.example.foodcentersystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class SearchController implements Initializable {

    @FXML
    private Button backToHomeBtn;

    @FXML
    private ListView<String> namesListView;

    @FXML
    private Button searchBtn;

    @FXML
    private TextField searchTextField;

    @FXML
    private AnchorPane searchViewScene;


    ArrayList<String> allCustomersWithDetails = new ArrayList<>();

    public void setAllCustomersWithDetails(ArrayList<FoodQueue> foodQueue) {
        for (FoodQueue eachQueue: foodQueue) {
            String queueNumber = Integer.toString(eachQueue.getQueueNumber());
            for (Customer customer: eachQueue.getQueueWithCustomers()) {
                String customerName = customer.getCustomerFullName();
                String burgerCount = Integer.toString(customer.getBurgerCountRequired());

                allCustomersWithDetails.add("QN: "+queueNumber+ " | "+customerName+" | "+"Order: "+burgerCount+" Burgers");
            }
        }

    }

    @FXML
    public void switchToHomeScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("home-view.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setMinWidth(600);
        stage.setMinHeight(400);
        stage.show();
    }

    public void search(ActionEvent event) {
        namesListView.getItems().clear();
        if (searchTextField.hasProperties()) {
            namesListView.getItems().addAll(searchList(searchTextField.getText(), allCustomersWithDetails));
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setAllCustomersWithDetails(Main.foodQueues);
        namesListView.getItems().addAll(allCustomersWithDetails);
    }

    private ArrayList<String> searchList(String searchWords, ArrayList<String> listOfStrings) {
        ArrayList<String> searchWordsArray = new ArrayList<>(List.of(searchWords.trim().split(" ")));

        return (ArrayList<String>) listOfStrings.stream().filter(input -> searchWordsArray.stream().allMatch(word ->
                input.toLowerCase().contains(word.toLowerCase()))
        ).collect(Collectors.toList());
    }
}

