package edu.bsu.cs222.mapGenerator;

import javafx.scene.control.*;
import javafx.scene.text.*;
import javafx.scene.layout.*;


public class GuiObjectBuilder {

    public static class ButtonBuilder extends javafx.scene.control.Button {
        private String buttonText;
        private String buttonStyle;

        public ButtonBuilder setButtonText(String textForButton) {
            buttonText = textForButton;
            return this;
        }

        public ButtonBuilder setButtonStyle(String styleForButton) {
            buttonStyle = styleForButton;
            return this;
        }

        public Button build() {
            Button button = new Button();
            button.setText(buttonText);
            button.setStyle(buttonStyle);
            return button;
        }

    }

    public static class SliderBuilder extends javafx.scene.control.Slider {
        private int sliderMin;
        private int sliderMax;
        private int sliderDefaultValue;
        private int sliderMajorTickUnit;
        private int sliderMinorTickCount;
        private int sliderBlockIncrement;

        public SliderBuilder setSliderMin(int minNumber) {
            sliderMin = minNumber;
            return this;
        }

        public SliderBuilder setSliderMax(int maxNumber) {
            sliderMax = maxNumber;
            return this;
        }

        public SliderBuilder setSliderDefaultValue(int defaultValue) {
            sliderDefaultValue = defaultValue;
            return this;
        }

        public SliderBuilder setSliderMajorTickUnit(int majorTickUnit) {
            sliderMajorTickUnit = majorTickUnit;
            return this;
        }

        public SliderBuilder setSliderMinorTickCount(int minorTickCount) {
            sliderMinorTickCount = minorTickCount;
            return this;
        }

        public SliderBuilder setSliderBlockIncrement(int blockIncrement) {
            sliderBlockIncrement = blockIncrement;
            return this;
        }

        public Slider build() {
            Slider slider = new Slider();
            slider.setMin(sliderMin);
            slider.setMax(sliderMax);
            slider.setValue(sliderDefaultValue);
            slider.setShowTickLabels(true);
            slider.setShowTickMarks(true);
            slider.setMajorTickUnit(sliderMajorTickUnit);
            slider.setMinorTickCount(sliderMinorTickCount);
            slider.setBlockIncrement(sliderBlockIncrement);
            return slider;
        }
    }

    public static class LabelBuilder {
        private String labelText;

        public LabelBuilder setLabelText(String txtLabel) {
            labelText = txtLabel;
            return this;
        }

        public Label build() {
            Label label = new Label();
            label.setText(labelText);
            label.setFont(new Font("Sans", 16));
            return label;
        }
    }

    public static class VBoxBuilder {
        private int vBoxSpacing;
        private Label vBoxLabel;

        public VBoxBuilder setVBoxSpacing(int spaceForVbox) {
            vBoxSpacing = spaceForVbox;
            return this;
        }

        public VBoxBuilder setVBoxLabel(Label label) {
            vBoxLabel = label;
            return this;
        }

        public VBox build() {
            VBox vbox = new VBox();
            vbox.setSpacing(vBoxSpacing);
            vbox.getChildren().add(vBoxLabel);
            return vbox;
        }
    }
}
