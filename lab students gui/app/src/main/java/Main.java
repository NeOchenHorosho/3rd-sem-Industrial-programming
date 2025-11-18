import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import studentset.Student;
import studentset.StudentSet;

import java.io.File;
import java.io.IOException;

public class Main extends Application {

    private TextField tfFile1;
    private TextField tfFile2;
    private TextField tfOutputFile;

    private TableView<Student> tableSet1;
    private TableView<Student> tableSet2;
    private TableView<Student> tableResult;

    private StudentSet set1 = new StudentSet();
    private StudentSet set2 = new StudentSet();
    private StudentSet resultSet = null;

    private ComboBox<String> cbOperation;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Операции над множествами студентов");

    
        tfFile1 = new TextField();
        tfFile1.setPromptText("Файл 1...");
        Button btnBrowse1 = new Button("...");
        btnBrowse1.setOnAction(e -> chooseFile(primaryStage, tfFile1));

        tfFile2 = new TextField();
        tfFile2.setPromptText("Файл 2...");
        Button btnBrowse2 = new Button("...");
        btnBrowse2.setOnAction(e -> chooseFile(primaryStage, tfFile2));

        Button btnLoad = new Button("Загрузить файлы");
        btnLoad.setOnAction(e -> loadFiles());

        HBox topBox = new HBox(10,
                new Label("Файл 1:"), tfFile1, btnBrowse1,
                new Label("Файл 2:"), tfFile2, btnBrowse2,
                btnLoad
        );
        topBox.setPadding(new Insets(10));
        topBox.setAlignment(Pos.CENTER_LEFT);

        
        tableSet1 = createStudentTable("Множество 1");
        tableSet2 = createStudentTable("Множество 2");
        tableResult = createStudentTable("Результат");

        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        Tab tab1 = new Tab("Множество 1", tableSet1);
        Tab tab2 = new Tab("Множество 2", tableSet2);
        Tab tab3 = new Tab("Результат", tableResult);

        tabPane.getTabs().addAll(tab1, tab2, tab3);

        
        cbOperation = new ComboBox<>();
        cbOperation.getItems().addAll("union", "intersection", "difference");
        cbOperation.setValue("union");

        Button btnDoOperation = new Button("Выполнить");
        btnDoOperation.setOnAction(e -> performOperation());

        HBox operationBox = new HBox(10,
                new Label("Операция:"), cbOperation, btnDoOperation
        );
        operationBox.setPadding(new Insets(10));
        operationBox.setAlignment(Pos.CENTER_LEFT);

        
        tfOutputFile = new TextField();
        tfOutputFile.setPromptText("Файл для сохранения результата...");

        Button btnBrowseSave = new Button("...");
        btnBrowseSave.setOnAction(e -> chooseSaveFile(primaryStage, tfOutputFile));

        Button btnSave = new Button("Сохранить результат");
        btnSave.setOnAction(e -> saveResult());

        HBox bottomBox = new HBox(10,
                new Label("Сохранить в:"), tfOutputFile, btnBrowseSave, btnSave
        );
        bottomBox.setPadding(new Insets(10));
        bottomBox.setAlignment(Pos.CENTER_LEFT);

        
        VBox root = new VBox(10, topBox, tabPane, operationBox, bottomBox);
        root.setPadding(new Insets(10));

        Scene scene = new Scene(root, 900, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    
    private TableView<Student> createStudentTable(String title) {
        TableView<Student> table = new TableView<>();

        

        TableColumn<Student, Number> colRecordBook = new TableColumn<>("№ зачетки");
        colRecordBook.setCellValueFactory(new PropertyValueFactory<>("recordBookNumber"));

        TableColumn<Student, String> colName = new TableColumn<>("Имя");
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Student, Number> colGroup = new TableColumn<>("Группа");
        colGroup.setCellValueFactory(new PropertyValueFactory<>("groupNumber"));

        TableColumn<Student, Number> colAvgGrade = new TableColumn<>("Средний балл");
        colAvgGrade.setCellValueFactory(new PropertyValueFactory<>("averageGrade"));

        table.getColumns().addAll(colRecordBook, colName, colGroup, colAvgGrade);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);

        return table;
    }

    
    private void chooseFile(Stage stage, TextField targetField) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Выбор файла");
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            targetField.setText(file.getAbsolutePath());
        }
    }

    
    private void chooseSaveFile(Stage stage, TextField targetField) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Сохранить файл");
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            targetField.setText(file.getAbsolutePath());
        }
    }

    
    private void loadFiles() {
        String file1 = tfFile1.getText().trim();
        String file2 = tfFile2.getText().trim();

        if (file1.isEmpty() || file2.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Ошибка", "Укажите оба входных файла.");
            return;
        }

        try {
            set1.readFromFile(file1);
            set2.readFromFile(file2);

            ObservableList<Student> list1 = FXCollections.observableArrayList(set1.getStudents());
            ObservableList<Student> list2 = FXCollections.observableArrayList(set2.getStudents());

            tableSet1.setItems(list1);
            tableSet2.setItems(list2);

            
            tableResult.setItems(FXCollections.observableArrayList());
            resultSet = null;

        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Ошибка чтения файла", e.getMessage());
        }
    }

    
    private void performOperation() {
        if (set1.getStudents().isEmpty() || set2.getStudents().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Нет данных",
                    "Сначала загрузите оба файла.");
            return;
        }

        String op = cbOperation.getValue();
        if (op == null) {
            showAlert(Alert.AlertType.WARNING, "Ошибка", "Выберите операцию.");
            return;
        }

        switch (op) {
            case "union":
                resultSet = set1.union(set2);
                break;
            case "intersection":
                resultSet = set1.intersection(set2);
                break;
            case "difference":
                resultSet = set1.difference(set2);
                break;
            default:
                showAlert(Alert.AlertType.ERROR, "Ошибка", "Неизвестная операция: " + op);
                return;
        }

        ObservableList<Student> resultList =
                FXCollections.observableArrayList(resultSet.getStudents());
        tableResult.setItems(resultList);
    }

    
    private void saveResult() {
        if (resultSet == null || resultSet.getStudents().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Нет результата",
                    "Сначала выполните операцию и получите результат.");
            return;
        }

        String outputFile = tfOutputFile.getText().trim();
        if (outputFile.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Ошибка",
                    "Укажите имя файла для сохранения результата.");
            return;
        }

        try {
            resultSet.writeToFile(outputFile);
            showAlert(Alert.AlertType.INFORMATION, "Готово",
                    "Результат успешно сохранён в файл:\n" + outputFile);
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Ошибка записи файла", e.getMessage());
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}