package com.buildpc.controllers;

import com.buildpc.models.IWatcher;
import com.buildpc.models.Part;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static com.buildpc.Main.build;

public class SummaryController implements Initializable, ControlledScreen, IWatcher {
    SceneController parentScene;

    List<Part> apps = new ArrayList<>();
    List<Node> allNodes = new ArrayList<>();

    @FXML
    GridPane grid = new GridPane();
    @FXML
    GridPane gridTotal = new GridPane();

    @FXML
    ImageView buildImageView;

    @FXML
    Label processorLblName;
    @FXML
    Label ramLblName;
    @FXML
    Label hdLblName;
    @FXML
    Label odLblName;
    @FXML
    Label gpuLblName;
    @FXML
    Label osLblName;

    @FXML
    Label processorLblPrice;
    @FXML
    Label ramLblPrice;
    @FXML
    Label hdLblPrice;
    @FXML
    Label odLblPrice;
    @FXML
    Label gpuLblPrice;
    @FXML
    Label osLblPrice;

    @FXML
    Button returnBtn;

    @FXML
    Button orderBtn;

    Image intelImg;
    Image amdImg;

    @Override
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        build.addWatcher(this);
        intelImg = new Image("file:src/main/resources/image/intel.png");
        amdImg = new Image("file:src/main/resources/image/amd.png");
    }

    @Override
    public void setScreenParent(SceneController scene) {
        parentScene = scene;
    }


    @Override
    public void update() {
        processorLblName.setText(build.getProcessor().getName());
        processorLblPrice.setText(String.format("$%.2f", build.getProcessor().getPrice()));

        ramLblName.setText(build.getRam().getName());
        ramLblPrice.setText(String.format("$%.2f", build.getRam().getPrice()));

        hdLblName.setText(build.getHd().getName());
        hdLblPrice.setText(String.format("$%.2f", build.getHd().getPrice()));

        odLblName.setText(build.getOpticalDrive().getName());
        odLblPrice.setText(String.format("$%.2f", build.getOpticalDrive().getPrice()));

        gpuLblName.setText(build.getGpu().getName());
        gpuLblPrice.setText(String.format("$%.2f", build.getGpu().getPrice()));

        osLblName.setText(build.getOs().getName());
        osLblPrice.setText(String.format("$%.2f", build.getOs().getPrice()));

        grid.getChildren().removeAll(allNodes);
        gridTotal.getChildren().removeAll(allNodes);
        apps = build.getApps();

        int rowCount = apps.size() + 7;

        for (int rowIndex = 7; rowIndex < rowCount; rowIndex++) {
            for (int colIndex = 0; colIndex < 3; colIndex++) {
                Label appLbl = new Label();
                appLbl.setPadding(new Insets(5,5,5,5));
                allNodes.add(appLbl);
                grid.add(appLbl, colIndex, rowIndex, 1, 1);

                if (colIndex == 0) {
                    appLbl.setText("Application");
                } else if (colIndex == 1) {
                    appLbl.setText(apps.get(rowIndex-7).getName());
                } else if (colIndex == 2) {
                    appLbl.setText(String.format("$%.2f",apps.get(rowIndex-7).getPrice()));
                }
            }
        }

        Label speakerLbl = new Label("Speaker");
        Label speakerLblItem = new Label("2 Piece Powered Speaker Set (Included)");
        Label keyboardLbl = new Label("Keyboard");
        Label keyboardLblItem = new Label("USB Wired Entry Keyboard (Included");
        Label mouseLbl = new Label("Mouse");
        Label mouseLblItem = new Label("USB Optical Mouse (Included");
        Label audioLbl = new Label("Audio");
        Label audioLblItem = new Label("Integrated Audio (Included)");

        Insets padding = new Insets(5,5,5,5);

        speakerLbl.setPadding(padding);
        speakerLblItem.setPadding(padding);
        keyboardLbl.setPadding(padding);
        keyboardLblItem.setPadding(padding);
        mouseLbl.setPadding(padding);
        mouseLblItem.setPadding(padding);
        audioLbl.setPadding(padding);
        audioLblItem.setPadding(padding);

        allNodes.add(speakerLbl);
        allNodes.add(speakerLblItem);
        allNodes.add(keyboardLbl);
        allNodes.add(keyboardLblItem);
        allNodes.add(mouseLbl);
        allNodes.add(mouseLblItem);
        allNodes.add(audioLbl);
        allNodes.add(audioLblItem);

        grid.add(speakerLbl,0, rowCount);
        grid.add(speakerLblItem,1, rowCount);
        grid.add(keyboardLbl,0, rowCount+1);
        grid.add(keyboardLblItem,1, rowCount+1);
        grid.add(mouseLbl,0, rowCount+2);
        grid.add(mouseLblItem,1, rowCount+2);
        grid.add(audioLbl,0, rowCount+3);
        grid.add(audioLblItem,1, rowCount+3);

        double basePrice;
        if (build.getProcessor().getMake().equals("Intel")){
            basePrice = 499;
        } else {
            basePrice = 599;
        }

        double shipping = build.getTotalPrice() * 0.025;
        double tax = (build.getTotalPrice() + shipping) * 0.078;
        double totalCost = build.getTotalPrice() + shipping + tax;

        Label baseLbl = new Label(String.format("$%.2f",basePrice));
        Label upgradeLbl = new Label(String.format("$%,.2f",build.getTotalPrice()-basePrice));
        Label subTotalLbl = new Label(String.format("$%,.2f",build.getTotalPrice()));
        Label shippingLbl = new Label(String.format("$%.2f (2.5%%)", shipping));
        Label taxLbl = new Label(String.format("$%.2f (7.8%%)", tax));
        Label totalCostLbl = new Label(String.format("$%,.2f", totalCost));

        allNodes.add(baseLbl);
        allNodes.add(upgradeLbl);
        allNodes.add(subTotalLbl);
        allNodes.add(shippingLbl);
        allNodes.add(taxLbl);
        allNodes.add(totalCostLbl);

        gridTotal.add(baseLbl, 1,1,1,1);
        gridTotal.add(upgradeLbl, 1,2,1,1);
        gridTotal.add(subTotalLbl, 1,4,1,1);
        gridTotal.add(shippingLbl, 1,5,1,1);
        gridTotal.add(taxLbl, 1,6,1,1);
        gridTotal.add(totalCostLbl, 1,7,1,1);

        if (build.getProcessor().getMake().equals("Intel")){
            buildImageView.setImage(intelImg);
        } else {
            buildImageView.setImage(amdImg);
        }

    }

    @FXML
    void onClickReturnBtn () {
         parentScene.setScreen("root");
    }

    @FXML
    void onClickOrderBtn () {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setContentText("Order Submitted");
        a.setHeaderText("Thank you.");
        a.setTitle("Order Submitted");

        a.show();
    }
}
