package com.example.foodcentersystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class QueuesController implements Initializable {
    @FXML
    private ImageView cashierImgViewer1;

    @FXML
    private ImageView cashierImgViewer2;

    @FXML
    private ImageView cashierImgViewer3;

    @FXML
    private VBox queue1VBox;

    @FXML
    private VBox queue2VBox;

    @FXML
    private VBox queue3VBox;

    @FXML
    private Label waitingQueueNames;
    ArrayList<Image> queue1Images = new ArrayList<>();
    ArrayList<Image> queue2Images = new ArrayList<>();
    ArrayList<Image> queue3Images = new ArrayList<>();
    ArrayList<String> allCustomerNamesInWaitingQueue = new ArrayList<>();

    public void setAllCustomerNamesInWaitingQueue(FoodQueue foodQueue) {
        for (Customer customer: foodQueue.getQueueWithCustomers()) {
            String customerName = customer.getCustomerFullName();

            allCustomerNamesInWaitingQueue.add(customerName);
        }
    }

    public void switchToHomeScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("home-view.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setMinWidth(600);
        stage.setMinHeight(400);
        stage.show();
    }

    private void getImages(ArrayList<FoodQueue> foodQueues) {
        for (FoodQueue eachQueue: foodQueues) {
            int queueNumber = eachQueue.getQueueNumber();

            if (!eachQueue.getQueueWithCustomers().isEmpty()) {
                switch (queueNumber) {
                    case 1 -> {
                        for (Customer customer: eachQueue.getQueueWithCustomers()) {
                            queue1Images.add(customer.getPhoto());
                        }
                    }
                    case 2 -> {
                        for (Customer customer: eachQueue.getQueueWithCustomers()) {
                            queue2Images.add(customer.getPhoto());
                        }
                    }
                    case 3 -> {
                        for (Customer customer: eachQueue.getQueueWithCustomers()) {
                            queue3Images.add(customer.getPhoto());
                        }
                    }
                }
            }
        }
    }

    private void addImageViewerToVBox(ArrayList<Image> imagesList, VBox queueVBox) {
        if (imagesList.listIterator().hasNext()) {
            for (Image image: imagesList) {
                ImageView customerImageViewer = new ImageView();
                customerImageViewer.setFitHeight(60.0);
                customerImageViewer.setFitWidth(60.0);
                customerImageViewer.minHeight(60.0);
                customerImageViewer.minWidth(60.0);
                customerImageViewer.setImage(image);

                queueVBox.getChildren().add(customerImageViewer);
            }
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setAllCustomerNamesInWaitingQueue(Main.waitingQueue);
        if (!allCustomerNamesInWaitingQueue.isEmpty()) {
            waitingQueueNames.setText(String.valueOf(allCustomerNamesInWaitingQueue));
        }

        Image cashierImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("cashier.png")));


        cashierImgViewer1.setImage(cashierImage);
        cashierImgViewer2.setImage(cashierImage);
        cashierImgViewer3.setImage(cashierImage);


        getImages(Main.foodQueues);


        if (queue1Images != null && queue2Images != null && queue3Images != null) {
            addImageViewerToVBox(queue1Images, queue1VBox);
            addImageViewerToVBox(queue2Images, queue2VBox);
            addImageViewerToVBox(queue3Images, queue3VBox);
        }
    }
}
