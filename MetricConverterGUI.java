import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class MetricConverterGUI extends Application {

    private TextField inputField;
    private ComboBox<String> conversionTypeComboBox;
    private ComboBox<String> unitFromComboBox;
    private ComboBox<String> unitToComboBox;
    private TextArea resultArea;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Metric Converter");

        // Create UI components
        Label titleLabel = new Label("Metric Converter");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        inputField = new TextField();
        inputField.setPromptText("Enter value");

        conversionTypeComboBox = new ComboBox<>();
        conversionTypeComboBox.getItems().addAll("Temperature", "Area", "Time");
        conversionTypeComboBox.setPromptText("Select Conversion Type");

        unitFromComboBox = new ComboBox<>();
        unitFromComboBox.setPromptText("From");

        unitToComboBox = new ComboBox<>();
        unitToComboBox.setPromptText("To");

        Button convertButton = new Button("Convert");
        convertButton.setOnAction(e -> convert());

        resultArea = new TextArea();
        resultArea.setEditable(false);

        // Set up the layout
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        // Add components to the grid
        grid.add(titleLabel, 0, 0, 2, 1);
        grid.add(conversionTypeComboBox, 0, 1, 2, 1);
        grid.add(inputField, 0, 2, 2, 1);
        grid.add(unitFromComboBox, 0, 3);
        grid.add(unitToComboBox, 1, 3);
        grid.add(convertButton, 0, 4, 2, 1);
        grid.add(resultArea, 0, 5, 2, 1);

        // Set up the scene
        Scene scene = new Scene(grid, 400, 300);
        primaryStage.setScene(scene);

        // Populate unit comboboxes based on the selected conversion type
        conversionTypeComboBox.setOnAction(e -> populateUnitComboBoxes());

        // Show the stage
        primaryStage.show();
    }

    private void convert() {
        // Retrieve user input
        String inputText = inputField.getText();
        if (inputText.isEmpty()) {
            resultArea.setText("Please enter a value to convert.");
            return;
        }

        double inputValue;
        try {
            inputValue = Double.parseDouble(inputText);
        } catch (NumberFormatException e) {
            resultArea.setText("Invalid input. Please enter a valid number.");
            return;
        }

        // Implement the conversion logic based on user input
        String conversionType = conversionTypeComboBox.getValue();
        String unitFrom = unitFromComboBox.getValue();
        String unitTo = unitToComboBox.getValue();

        if (conversionType == null || unitFrom == null || unitTo == null) {
            resultArea.setText("Please select conversion type and units.");
            return;
        }

        String result;
        switch (conversionType) {
            case "Temperature":
                result = convertTemperature(inputValue, unitFrom, unitTo);
                break;
            case "Area":
                result = convertArea(inputValue, unitFrom, unitTo);
                break;
            case "Time":
                result = convertTime(inputValue, unitFrom, unitTo);
                break;
            default:
                result = "Invalid conversion type.";
        }

        // Update the resultArea with the conversion result
        resultArea.setText(result);
    }

    private String convertTemperature(double value, String unitFrom, String unitTo) {
        if ("Fahrenheit".equals(unitFrom) && "Celsius".equals(unitTo)) {
            double celsius = (value - 32.0) * 5.0 / 9.0;
            return value + " degrees Fahrenheit is " + celsius + " degrees Celsius.";
        } else if ("Celsius".equals(unitFrom) && "Fahrenheit".equals(unitTo)) {
            double fahrenheit = (value * 9.0 / 5.0) + 32.0;
            return value + " degrees Celsius is " + fahrenheit + " degrees Fahrenheit.";
        } else {
            return "Invalid temperature conversion.";
        }
    }

    private String convertArea(double value, String unitFrom, String unitTo) {
        if ("Square Feet".equals(unitFrom) && "Square Meters".equals(unitTo)) {
            double squareMeters = value * 0.092903;
            return value + " square feet is equal to " + squareMeters + " square meters.";
        } else if ("Square Feet".equals(unitFrom) && "Square Yards".equals(unitTo)) {
            double squareYards = value / 9.0;
            return value + " square feet is equal to " + squareYards + " square yards.";
        } else if ("Square Feet".equals(unitFrom) && "Square Acres".equals(unitTo)) {
            double squareAcres = value / 43560.0;
            return value + " square feet is equal to " + squareAcres + " square acres.";
        } else {
            return "Invalid area conversion.";
        }
    }

    private String convertTime(double value, String unitFrom, String unitTo) {
        if ("Seconds".equals(unitFrom) && "Minutes".equals(unitTo)) {
            double minutes = value / 60.0;
            double remainingSeconds = value % 60.0;
            return value + " seconds is equal to " + minutes + " minutes and " + remainingSeconds + " seconds.";
        } else if ("Seconds".equals(unitFrom) && "Hours".equals(unitTo)) {
            double hours = value / 3600.0;
            double remainingMinutes = (value % 3600.0) / 60.0;
            return value + " seconds is equal to " + hours + " hours and " + remainingMinutes + " minutes.";
        } else if ("Seconds".equals(unitFrom) && "Days".equals(unitTo)) {
            double days = value / 86400.0;
            double remainingHours = (value % 86400.0) / 3600.0;
            return value + " seconds is equal to " + days + " days and " + remainingHours + " hours.";
        } else {
            return "Invalid time conversion.";
        }
    }

    private void populateUnitComboBoxes() {
        String conversionType = conversionTypeComboBox.getValue();
    
        if (conversionType == null) {
            return;
        }
    
        // Clear existing items in unitFromComboBox and unitToComboBox
        unitFromComboBox.getItems().clear();
        unitToComboBox.getItems().clear();
    
        // Populate unit comboboxes based on the selected conversion type
        switch (conversionType) {
            case "Temperature":
                unitFromComboBox.getItems().addAll("Fahrenheit", "Celsius");
                unitToComboBox.getItems().addAll("Fahrenheit", "Celsius");
                break;
            case "Area":
                unitFromComboBox.getItems().addAll("Square Feet");
                unitToComboBox.getItems().addAll("Square Meters", "Square Yards", "Square Acres");
                break;
            case "Time":
                unitFromComboBox.getItems().addAll("Seconds");
                unitToComboBox.getItems().addAll("Minutes", "Hours", "Days");
                break;
            default:
                // Handle other conversion types if needed
                break;
        }
    
        // Set the prompt text for the unit comboboxes
        unitFromComboBox.setPromptText("From");
        unitToComboBox.setPromptText("To");
    }
}
