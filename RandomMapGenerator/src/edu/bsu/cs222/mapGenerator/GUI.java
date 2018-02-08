package edu.bsu.cs222.mapGenerator;

import edu.bsu.cs222.mapGenerator.map.Map;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

class GUI {
    private Map map;

    private final int mapGuiWidth = 1046;
    private ImageView mapImageView;
    private Button generateButton;
    private Button settingsButton;

    private Stage toolkitStage;
    private Button landChanceButton;
    private Button landBirthButton;
    private Button landDeathButton;
    private Slider initialLandChanceSlider;
    private Slider landBirthSlider;
    private Slider landDeathSlider;
    private ChoiceBox mapSizeBox;
    private Label landChanceLabel;
    private Label landBirthLabel;
    private Label landDeathLabel;
    private Label mapSizeLabel;
    private VBox landChanceVbox;
    private VBox landBirthVbox;
    private VBox landDeathVbox;

    public GUI(Map map) {
         this.map = map;
     }

    public void start(Stage primaryStage) {
        createGUIFeatures();
        buildGUI(primaryStage);
    }

    private void createGUIFeatures() {
        generateButton = new GuiObjectBuilder.ButtonBuilder().setButtonStyle(" -fx-background-color: " +
                "#3c7fb1,linear-gradient(#fafdfe, #e8f5fc),linear-gradient(#eaf6fd 0%, #d9f0fc 49%, #bee6fd 50%, #a7d9f5 100%);")
                .setButtonText("Generate").build();
        settingsButton = new GuiObjectBuilder.ButtonBuilder().setButtonStyle(" -fx-background-color: " +
                "#3c7fb1,linear-gradient(#fafdfe, #e8f5fc),linear-gradient(#eaf6fd 0%, #d9f0fc 49%, #bee6fd 50%, #a7d9f5 100%);")
                .setButtonText("Settings Toolkit").build();
        landChanceButton = new GuiObjectBuilder.ButtonBuilder().setButtonStyle("-fx-background-color: #ffff80;")
                .setButtonText("?").build();
        landBirthButton = new GuiObjectBuilder.ButtonBuilder().setButtonStyle("-fx-background-color: #ffff80;")
                .setButtonText("?").build();
        landDeathButton = new GuiObjectBuilder.ButtonBuilder().setButtonStyle("-fx-background-color: #ffff80;")
                .setButtonText("?").build();
        initialLandChanceSlider = new GuiObjectBuilder.SliderBuilder().setSliderMin(0).setSliderMax(100)
                .setSliderDefaultValue(45).setSliderMajorTickUnit(50).setSliderMinorTickCount(4).setSliderBlockIncrement(10)
                .build();
        landBirthSlider = new GuiObjectBuilder.SliderBuilder().setSliderMin(1).setSliderMax(8)
                .setSliderDefaultValue(4).setSliderMajorTickUnit(1).setSliderMinorTickCount(1).setSliderBlockIncrement(1)
                .build();
        landDeathSlider = new GuiObjectBuilder.SliderBuilder().setSliderMin(1).setSliderMax(10)
                .setSliderDefaultValue(5).setSliderMajorTickUnit(1).setSliderMinorTickCount(1).setSliderBlockIncrement(1)
                .build();
        mapSizeBox = new ChoiceBox(FXCollections.observableArrayList("Small", "Medium", "Large"));
        landChanceLabel = new GuiObjectBuilder.LabelBuilder().setLabelText("Land spawn percentage").build();
        landBirthLabel = new GuiObjectBuilder.LabelBuilder().setLabelText("Land birth threshold").build();
        landDeathLabel = new GuiObjectBuilder.LabelBuilder().setLabelText("Land death threshold").build();
        mapSizeLabel = new GuiObjectBuilder.LabelBuilder().setLabelText("Map image size").build();
        landChanceVbox = new GuiObjectBuilder.VBoxBuilder().setVBoxSpacing(20)
                .setVBoxLabel(new Label("The initial chance a sea tile " +
                        "has to become a land tile!")).build();
        landBirthVbox = new GuiObjectBuilder.VBoxBuilder().setVBoxSpacing(20)
                .setVBoxLabel(new Label("The adjacent land tiles required for sea to become land.")).build();
        landDeathVbox = new GuiObjectBuilder.VBoxBuilder().setVBoxSpacing(20)
                .setVBoxLabel(new Label("The adjacent sea tiles required for a land tile to become sea.")).build();
        landChanceLabel = new GuiObjectBuilder.LabelBuilder().setLabelText("Land spawn percentage").build();
        landBirthLabel = new GuiObjectBuilder.LabelBuilder().setLabelText("Land birth threshold").build();
        landDeathLabel = new GuiObjectBuilder.LabelBuilder().setLabelText("Land death threshold").build();
        mapSizeLabel = new GuiObjectBuilder.LabelBuilder().setLabelText("Map image size").build();
        landChanceVbox = new GuiObjectBuilder.VBoxBuilder().setVBoxSpacing(20)
                .setVBoxLabel(new Label("The initial chance a sea tile " +
                        "has to become a land tile!")).build();
        landBirthVbox = new GuiObjectBuilder.VBoxBuilder().setVBoxSpacing(20)
                .setVBoxLabel(new Label("The adjacent land tiles required for sea to become land.")).build();
        landDeathVbox = new GuiObjectBuilder.VBoxBuilder().setVBoxSpacing(20)
                .setVBoxLabel(new Label("The adjacent sea tiles required for a land tile to become sea.")).build();
    }

    private void buildGUI(Stage primaryStage) {
        AnchorPane layout = new AnchorPane();
        layout.setStyle("-fx-background-color: #ffe4e1;");
        setActionEventsForPrimaryStageButtons();
        setAnchorPanesForPrimaryStage();
        layout.getChildren().addAll(generateButton, settingsButton, buildMapScrollPane());
        primaryStage.setTitle("2D Map Generator");
        primaryStage.setScene(buildMapScene(layout));
        primaryStage.show();
    }

    private void setActionEventsForPrimaryStageButtons() {
        generateButton.setOnAction(e -> {
            applySettings();
            generateMap();
            updateMapImage();
        });
        settingsButton.setOnAction(e ->
                showToolkitUI());
        mapSizeBox.setValue("Medium");
    }

    private void setAnchorPanesForPrimaryStage() {
        AnchorPane.setBottomAnchor(generateButton, 10d);
        AnchorPane.setLeftAnchor(generateButton, mapGuiWidth / 2d - 40);
        AnchorPane.setBottomAnchor(settingsButton, 10d);
        AnchorPane.setLeftAnchor(settingsButton, mapGuiWidth / 2d + 150);
    }

    private void buildGenerationToolkitUi() {
        toolkitStage = new Stage();
        toolkitStage.setTitle("Generation Toolkit");
        toolkitStage.setOnCloseRequest(e -> toolkitStage = null);
        AnchorPane toolkitLayout = new AnchorPane();
        toolkitLayout.setStyle("-fx-background-color: #ffe4e1;");
        int toolkitGuiHeight = 300;
        int toolkitGuiWidth = 350;
        setActionEventsForToolkitUiButtons();
        setAnchorPanesForToolkitGui();
        Scene toolkitScene = new Scene(toolkitLayout, toolkitGuiWidth, toolkitGuiHeight);
        toolkitLayout.getChildren().addAll(landChanceButton, landChanceLabel, initialLandChanceSlider,
                landBirthButton, landBirthLabel, landBirthSlider, landDeathButton, landDeathLabel, landDeathSlider,
                mapSizeBox, mapSizeLabel);
        toolkitStage.setScene(toolkitScene);
        toolkitStage.show();
    }

    private void setActionEventsForToolkitUiButtons() {
        landChanceButton.setOnAction(event ->
                buildHelpButtonGui(landChanceVbox));
        landBirthButton.setOnAction(event ->
                buildHelpButtonGui(landBirthVbox));
        landDeathButton.setOnAction(event ->
                buildHelpButtonGui(landDeathVbox));
    }


    private void setAnchorPanesForToolkitGui() {
        AnchorPane.setTopAnchor(landChanceButton, 15d);
        AnchorPane.setRightAnchor(landChanceButton, 65d);
        AnchorPane.setTopAnchor(landChanceLabel, 15d);
        AnchorPane.setRightAnchor(landChanceLabel, 90d);
        AnchorPane.setTopAnchor(initialLandChanceSlider, 35d);
        AnchorPane.setRightAnchor(initialLandChanceSlider, 95d);
        AnchorPane.setTopAnchor(landBirthLabel, 75d);
        AnchorPane.setRightAnchor(landBirthLabel, 90d);
        AnchorPane.setTopAnchor(landBirthButton, 75d);
        AnchorPane.setRightAnchor(landBirthButton, 65d);
        AnchorPane.setTopAnchor(landBirthSlider, 95d);
        AnchorPane.setRightAnchor(landBirthSlider, 95d);
        AnchorPane.setTopAnchor(landDeathLabel, 135d);
        AnchorPane.setRightAnchor(landDeathLabel, 90d);
        AnchorPane.setTopAnchor(landDeathButton, 135d);
        AnchorPane.setRightAnchor(landDeathButton, 65d);
        AnchorPane.setTopAnchor(landDeathSlider, 155d);
        AnchorPane.setRightAnchor(landDeathSlider, 95d);
        AnchorPane.setTopAnchor(mapSizeLabel, 210d);
        AnchorPane.setRightAnchor(mapSizeLabel, 155d);
        AnchorPane.setTopAnchor(mapSizeBox, 210d);
        AnchorPane.setRightAnchor(mapSizeBox, 60d);
    }

    private void buildHelpButtonGui(VBox vbox) {
        final Stage dialog = new Stage();
        Scene dialogScene = new Scene(vbox, 350, 50);
        dialog.setScene(dialogScene);
        dialog.show();
    }

    private ScrollPane buildMapScrollPane() {
        ScrollPane mapScrollPane = new ScrollPane();
        mapImageView = new ImageView();
        WritableImage mapImage = new WritableImage(2048, 1024);
        initializeMapImage(mapImage);
        mapImageView.setImage(mapImage);
        mapScrollPane.setContent(mapImageView);
        AnchorPane.setTopAnchor(mapScrollPane, 10d);
        AnchorPane.setLeftAnchor(mapScrollPane, 10d);
        AnchorPane.setRightAnchor(mapScrollPane, 10d);
        AnchorPane.setBottomAnchor(mapScrollPane, 40d);
        return mapScrollPane;
    }

    private Scene buildMapScene(AnchorPane layout) {
        int mapGuiHeight = 574;
        return new Scene(layout, mapGuiWidth, mapGuiHeight);
    }

    private void showToolkitUI() {
        if(toolkitStage == null) {
            buildGenerationToolkitUi();
        } else {
            toolkitStage.setAlwaysOnTop(true);
        }
    }

    private void initializeMapImage(WritableImage mapImage) {
        for (int j = 0; j < 1024; j++) {
            for (int i = 0; i < 2048; i++) {
                mapImage.getPixelWriter().setColor(i, j, new Color(0d, 0d, 0d, 1d));
            }
        }
    }

    private void applySettings() {
        setMapDimensions();
        map.setInitialLandSpawnPercentage((float)initialLandChanceSlider.getValue()/100f);
        map.setLandBirthThreshold((int)landBirthSlider.getValue());
        map.setLandDeathThreshold((int)landDeathSlider.getValue());
    }

    private void setMapDimensions() {
        String mapSize = (String)mapSizeBox.getValue();
        if(mapSize.equalsIgnoreCase("small")) {
            map.setMapWidthInTiles(64);
            map.setMapHeightInTiles(32);
        } else if(mapSize.equalsIgnoreCase("medium")) {
            map.setMapWidthInTiles(128);
            map.setMapHeightInTiles(64);
        } else if(mapSize.equalsIgnoreCase("large")) {
            map.setMapWidthInTiles(256);
            map.setMapHeightInTiles(128);
        }
    }

    private void generateMap() {
        map.generateNewMap();
    }

    private void updateMapImage() {
        mapImageView.setImage(map.getMapWritableImage());
    }
}