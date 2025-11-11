import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class OlympiadAwardCalculator extends Application {
    
    private ToggleGroup congratulatorGroup;
    private Map<String, RadioButton> congratulatorButtons;
    private ListView<String> giftListView;
    private RadioButton concertYes, concertNo;
    private CheckBox regularClientCB;
    private Label totalCostLabel;
    private TextField orderDetailsField;
    
    private Map<String, Map<String, Double>> gifts;
    private final double CONCERT_COST = 5000.0;
    private final double DISCOUNT = 0.10;
    
    @Override
    public void start(Stage primaryStage) {
        try {
            loadDataFromFile("data.txt");
        } catch (IOException e) {
            showError("Ошибка чтения файла: " + e.getMessage());
            return;
        }
        
        VBox root = new VBox(15);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.TOP_LEFT);
        
        
        Label congratulatorLabel = new Label("Выберите поздравителя:");
        congratulatorGroup = new ToggleGroup();
        congratulatorButtons = new HashMap<>();
        
        VBox congratulatorsBox = new VBox(5);
        for (String congratulator : gifts.keySet()) {
            RadioButton rb = new RadioButton(congratulator);
            rb.setToggleGroup(congratulatorGroup);
            rb.setOnAction(e -> updateGiftList());
            congratulatorButtons.put(congratulator, rb);
            congratulatorsBox.getChildren().add(rb);
        }
        
        
        Label giftLabel = new Label("Выберите подарок:");
        giftListView = new ListView<>();
        giftListView.setPrefHeight(80);
        giftListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        giftListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> calculate());
        
        
        Label concertLabel = new Label("Нужен ли концерт:");
        ToggleGroup concertGroup = new ToggleGroup();
        
        concertYes = new RadioButton("Да");
        concertYes.setToggleGroup(concertGroup);
        concertYes.setOnAction(e -> calculate());
        
        concertNo = new RadioButton("Нет");
        concertNo.setToggleGroup(concertGroup);
        concertNo.setSelected(true);
        concertNo.setOnAction(e -> calculate());
        
        
        regularClientCB = new CheckBox("Постоянный клиент (скидка 10%)");
        regularClientCB.setOnAction(e -> calculate());
        
        
        Label costLabel = new Label("Итоговая стоимость:");
        totalCostLabel = new Label("0.0 руб.");
        
        Label orderLabel = new Label("Состав заказа:");
        orderDetailsField = new TextField();
        orderDetailsField.setEditable(false);
        
        root.getChildren().addAll(
            congratulatorLabel, congratulatorsBox,
            new Separator(),
            giftLabel, giftListView,
            new Separator(),
            concertLabel, concertYes, concertNo,
            new Separator(),
            regularClientCB,
            new Separator(),
            costLabel, totalCostLabel,
            orderLabel, orderDetailsField
        );
        
        Scene scene = new Scene(root, 400, 600);
        primaryStage.setTitle("Расчет затрат на награждение");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private void loadDataFromFile(String filename) throws IOException {
        gifts = new HashMap<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            String currentCongratulator = null;
            
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }
                
                if (line.startsWith("[") && line.endsWith("]")) {
                    currentCongratulator = line.substring(1, line.length() - 1);
                    gifts.put(currentCongratulator, new HashMap<>());
                } else if (currentCongratulator != null && line.contains(":")) {
                    String[] parts = line.split(":");
                    if (parts.length == 2) {
                        String giftName = parts[0].trim();
                        double price = Double.parseDouble(parts[1].trim());
                        gifts.get(currentCongratulator).put(giftName, price);
                    }
                }
            }
        }
    }
    
    private void updateGiftList() {
        giftListView.getItems().clear();
        
        RadioButton selected = (RadioButton) congratulatorGroup.getSelectedToggle();
        if (selected != null) {
            String congratulator = selected.getText();
            Map<String, Double> giftMap = gifts.get(congratulator);
            if (giftMap != null) {
                giftListView.getItems().addAll(giftMap.keySet());
            }
        }
        
        calculate();
    }
    
    private void calculate() {
        RadioButton selectedCongratulator = (RadioButton) congratulatorGroup.getSelectedToggle();
        String selectedGift = giftListView.getSelectionModel().getSelectedItem();
        
        if (selectedCongratulator == null || selectedGift == null) {
            totalCostLabel.setText("0.0 руб.");
            orderDetailsField.setText("");
            return;
        }
        
        String congratulator = selectedCongratulator.getText();
        double giftCost = gifts.get(congratulator).get(selectedGift);
        double totalCost = giftCost;
        
        StringBuilder order = new StringBuilder();
        order.append("Поздравитель: ").append(congratulator).append("; ");
        order.append("Подарок: ").append(selectedGift).append("; ");
        
        if (concertYes.isSelected()) {
            totalCost += CONCERT_COST;
            order.append("Концерт: Да; ");
        } else {
            order.append("Концерт: Нет; ");
        }
        
        if (regularClientCB.isSelected()) {
            totalCost *= (1 - DISCOUNT);
            order.append("Скидка: 10%");
        } else {
            order.append("Скидка: Нет");
        }
        
        totalCostLabel.setText(String.format("%.2f руб.", totalCost));
        orderDetailsField.setText(order.toString());
    }
    
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}