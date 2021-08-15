package com.buildpc.controllers;

import com.buildpc.models.CPU;
import com.buildpc.models.Part;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.effect.Bloom;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import static com.buildpc.Main.build;

public class RootController implements Initializable, ControlledScreen {
    ObservableList<CPU> intelCPUs = FXCollections.observableArrayList(
            new CPU("G1610 2.60Ghz (DEFAULT)", 0, "Intel"),
            new CPU("G1620 2.70Ghz", 50, "Intel"),
            new CPU("G1630 2.80Ghz", 90, "Intel"),
            new CPU("G1820 2.70Ghz", 105, "Intel"),
            new CPU("G1830 2.80Ghz", 130, "Intel")
            );

    ObservableList<CPU> amdCPUs = FXCollections.observableArrayList(
            new CPU("FX-2100 (DEFAULT)", 0, "AMD"),
            new CPU("FX-8350", 25, "AMD"),
            new CPU("FX-9590", 90, "AMD"),
            new CPU("FX-4100", 187, "AMD"),
            new CPU("FX-4300", 280, "AMD")
    );

    ObservableList<Part> rams = FXCollections.observableArrayList(
            new Part("4GB (DEFAULT)", 0),
            new Part("6GB", 28),
            new Part("8GB", 58),
            new Part("12GB", 108),
            new Part("16GB", 176)
    );

    ObservableList<Part> hardDrives = FXCollections.observableArrayList(
            new Part("125GB (DEFAULT)", 0),
            new Part("250GB", 27),
            new Part("500GB", 50),
            new Part("1TB", 89)
            );

    ObservableList<Part> opticalDrives = FXCollections.observableArrayList(
            new Part("CD-ROM Drive (DEFAULT)", 0),
            new Part("DVD Drive", 17),
            new Part("Combo DVD/CDRW", 40),
            new Part("DVD and CDRW", 79)
            );

    ObservableList<Part> GPUs = FXCollections.observableArrayList(
            new Part("Integrated 3D Graphics (DEFAULT)", 0),
            new Part("NVIDA GeForce G310 512MB DDR3", 80),
            new Part("NVIDA GeForce GT620 1GB DDR3", 169),
            new Part("NVIDA GeForce GT640 1GB GDDR5", 490)
            );

    SceneController parentScene;
    Part msstudent = new Part("MS Office Home & Student 2013", 139);
    Part msbusiness = new Part("MS Office Home & Business 2013", 219);
    Part accountingPkg = new Part("Accounting Package", 399);
    Part graphicsPkg = new Part("Graphics Package", 499);

    @FXML
    VBox mainVbox;

    @FXML
    Pane welcomePane;

    @FXML
    private ImageView intelImageBtn;

    @FXML
    private ImageView amdImageBtn;

    @FXML
    private ToggleGroup OS;

    @FXML
    private ComboBox<CPU> processorCombo;
    @FXML
    private ComboBox<Part> ramCombo;
    @FXML
    private ComboBox<Part> hdCombo;
    @FXML
    private ComboBox<Part> odCombo;
    @FXML
    private ComboBox<Part> gpuCombo;
    @FXML
    private RadioButton win8DefaultRadioBtn;
    @FXML
    private RadioButton win8ProRadioBtn;
    @FXML
    private RadioButton linuxRadioBtn;
    @FXML
    private CheckBox msStudent13Cbox;
    @FXML
    private CheckBox msBusiness13Cbox;
    @FXML
    private CheckBox accountingPkgCbox;
    @FXML
    private CheckBox graphicsPkgCbox;

    @FXML
    private Label subTotalLabel;

    String baseBuild;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @FXML
    void onDefaultPick(MouseEvent event) {
        if (welcomePane.isVisible()) {
            mainVbox.getChildren().remove(welcomePane);
            mainVbox.getChildren().get(1).setDisable(false);
            mainVbox.getChildren().get(1).setVisible(true);
            mainVbox.getChildren().get(2).setDisable(false);
            mainVbox.getChildren().get(2).setVisible(true);
        }

        baseBuild = ((ImageView) event.getSource()).getId();
        resetBuild(baseBuild);
    }


    @FXML
    void onClickSummaryBtn (MouseEvent event) {
        build.notifyWatchers();
        parentScene.setScreen("summary");
    }

    @FXML
    void onChangeDropDown(ActionEvent event) {
        ComboBox cb = (ComboBox) event.getSource();
        if (cb.getValue() == null )
            return;

        switch (cb.getId()) {
            case "processorCombo":
                build.setProcessor((CPU) cb.getValue());
                break;
            case "ramCombo":
                build.setRam((Part) cb.getValue());
                break;
            case "hdCombo":
                build.setHd((Part) cb.getValue());
                break;
            case "odCombo":
                build.setOpticalDrive((Part) cb.getValue());
                break;
            case "gpuCombo":
                build.setGpu((Part) cb.getValue());
                break;
        }
        updateSubTotal();
    }

    @FXML
    void onSelectBox () {
        if (win8DefaultRadioBtn.isSelected())
            build.setOs(new Part("Windows 8.1", 0));

        if (win8ProRadioBtn.isSelected())
            build.setOs(new Part("Windows 8.1 Pro", 59));

        if (linuxRadioBtn.isSelected())
            build.setOs(new Part("Linux", -89));

        if (msStudent13Cbox.isSelected())
            build.addApp("msstudent", msstudent);
        else
            build.remove("msstudent");

        if (msBusiness13Cbox.isSelected())
            build.addApp("msbusiness", msbusiness);
        else
            build.remove("msbusiness");

        if (accountingPkgCbox.isSelected())
            build.addApp("accountingpkg", accountingPkg);
        else
            build.remove("accountingpkg");

        if (graphicsPkgCbox.isSelected())
            build.addApp("graphicspkg", graphicsPkg);
        else
            build.remove("graphicspkg");
        updateSubTotal();
    }

    @FXML
    void onClickResetBtn () {
        resetBuild(baseBuild);
    }

    @FXML
    void onClickCalculateBtn (){
        Alert a = new Alert(Alert.AlertType.INFORMATION);

        double basePrice;
        if (build.getProcessor().getMake().equals("Intel")){
            basePrice = 499;
        } else {
            basePrice = 599;
        }
        double upgrade = build.getTotalPrice() - basePrice;
        double shipping = build.getTotalPrice() * 0.025;
        double tax = (build.getTotalPrice() + shipping) * 0.078;
        double totalCost = build.getTotalPrice() + shipping + tax;
        a.setHeaderText("Calculated cost");
        a.setContentText(String.format(
                "Base Package: $%.2f \n" +
                "Upgrade: $%,.2f \n" +
                "Subtotal: $%,.2f \n" +
                "Shipping: $%.2f \n" +
                "Tax: $%.2f \n " +
                "Total Cost: $%,.2f", basePrice, upgrade,build.getTotalPrice(),shipping,tax,totalCost));
        a.show();
    }

    @Override
    public void setScreenParent(SceneController scene) {
        parentScene = scene;
    }

    void updateSubTotal(){
        subTotalLabel.setText(String.valueOf(build.getTotalPrice()));
    }

    void resetBuild(String baseBuild) {
        if(baseBuild.equals("intelImageBtn")) {
            processorCombo.setItems(intelCPUs);
            intelImageBtn.setOpacity(1.0);
            intelImageBtn.setEffect(new Bloom());
            amdImageBtn.setEffect(null);
            amdImageBtn.setOpacity(0.65);
        } else if (baseBuild.equals("amdImageBtn")) {
            processorCombo.setItems(amdCPUs);
            intelImageBtn.setOpacity(0.65);
            amdImageBtn.setOpacity(1.0);
            amdImageBtn.setEffect(new Bloom());
            intelImageBtn.setEffect(null);
        } else {
            return;
        }

        ramCombo.setItems(rams);
        hdCombo.setItems(hardDrives);
        odCombo.setItems(opticalDrives);
        gpuCombo.setItems(GPUs);
        win8DefaultRadioBtn.setSelected(true);

        List<ComboBox> cbs = new ArrayList<ComboBox>(Arrays.asList(
                processorCombo,
                ramCombo,
                hdCombo,
                odCombo,
                gpuCombo));

        for (ComboBox cb: cbs) {
            cb.getSelectionModel().selectFirst();
        }

        msStudent13Cbox.setSelected(false);
        msBusiness13Cbox.setSelected(false);
        graphicsPkgCbox.setSelected(false);
        accountingPkgCbox.setSelected(false);
        onSelectBox();

        build.setProcessor(processorCombo.getSelectionModel().getSelectedItem());
        build.setRam(ramCombo.getSelectionModel().getSelectedItem());
        build.setHd(hdCombo.getSelectionModel().getSelectedItem());
        build.setOpticalDrive(odCombo.getSelectionModel().getSelectedItem());
        build.setGpu(gpuCombo.getSelectionModel().getSelectedItem());
        build.setOs(new Part("Windows 8.1", 0));

    }
}
