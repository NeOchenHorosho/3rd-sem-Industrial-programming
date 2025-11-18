import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.*;

public class ExpressionCalculatorApp extends Application {

    private TextField inputField;
    private Label rpnLabel;
    private Label resultLabel;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Калькулятор выражений с RPN");

        Label inputLabel = new Label("Введите выражение:");
        inputField = new TextField();
        inputField.setPromptText("Например: (3 + 5) * 2 - 8 / 4");
        inputField.setPrefWidth(300);

        Button calculateButton = new Button("Вычислить");
        calculateButton.setOnAction(e -> calculateExpression());
        
        Label rpnTitleLabel = new Label("Польская запись (RPN):");
        rpnTitleLabel.setStyle("-fx-font-weight: bold;");
        rpnLabel = new Label();
        rpnLabel.setStyle("-fx-border-color: lightgray; -fx-padding: 10; -fx-background-color: #f0f0f0;");
        rpnLabel.setMinHeight(30);
        rpnLabel.setMaxWidth(Double.MAX_VALUE);

        Label resultTitleLabel = new Label("Результат:");
        resultTitleLabel.setStyle("-fx-font-weight: bold;");
        resultLabel = new Label();
        resultLabel.setStyle("-fx-border-color: lightgray; -fx-padding: 10; -fx-background-color: #e8f5e9;");
        resultLabel.setMinHeight(30);
        resultLabel.setMaxWidth(Double.MAX_VALUE);

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.TOP_LEFT);
        layout.getChildren().addAll(
                inputLabel, inputField,
                calculateButton,
                rpnTitleLabel, rpnLabel,
                resultTitleLabel, resultLabel
        );

        Scene scene = new Scene(layout, 400, 350);
        primaryStage.setScene(scene);
        primaryStage.show();

        inputField.setOnAction(e -> calculateExpression());
    }

    private void calculateExpression() {
        String expression = inputField.getText().trim();
        
        if (expression.isEmpty()) {
            showError("Введите выражение!");
            return;
        }

        try {
            String rpn = infixToRPN(expression);
            rpnLabel.setText(rpn);

            double result = evaluateRPN(rpn);
            resultLabel.setText(String.format("%.4f", result));
            resultLabel.setStyle("-fx-border-color: lightgray; -fx-padding: 10; -fx-background-color: #e8f5e9;");
            
        } catch (ArithmeticException e) {
            showError("Ошибка: " + e.getMessage());
        } catch (Exception e) {
            showError("Ошибка в выражении: " + e.getMessage());
        }
    }

    private void showError(String message) {
        rpnLabel.setText("");
        resultLabel.setText(message);
        resultLabel.setStyle("-fx-border-color: red; -fx-padding: 10; -fx-background-color: #ffebee;");
    }

    private String infixToRPN(String expression) {
        StringBuilder output = new StringBuilder();
        Deque<Character> stack = new ArrayDeque<>();
        
        expression = expression.replaceAll("\\s+", ""); 
        
        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);
            
            
            if (Character.isDigit(c) || c == '.') {
                if (output.length() > 0 && output.charAt(output.length() - 1) != ' ') {
                    output.append(' ');
                }
                while (i < expression.length() && 
                       (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
                    output.append(expression.charAt(i));
                    i++;
                }
                i--;
                output.append(' ');
            }
            
            else if (c == '(') {
                stack.push(c);
            }
            
            else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    output.append(stack.pop()).append(' ');
                }
                if (stack.isEmpty()) {
                    throw new IllegalArgumentException("Несбалансированные скобки");
                }
                stack.pop(); 
            }
            
            else if (isOperator(c)) {
                while (!stack.isEmpty() && stack.peek() != '(' &&
                       precedence(stack.peek()) >= precedence(c)) {
                    output.append(stack.pop()).append(' ');
                }
                stack.push(c);
            }
        }
        
       
        while (!stack.isEmpty()) {
            char op = stack.pop();
            if (op == '(' || op == ')') {
                throw new IllegalArgumentException("Несбалансированные скобки");
            }
            output.append(op).append(' ');
        }
        
        return output.toString().trim();
    }
    private double evaluateRPN(String rpn) {
        Deque<Double> stack = new ArrayDeque<>();
        String[] tokens = rpn.split("\\s+");
        
        for (String token : tokens) {
            if (token.isEmpty()) continue;
            
            if (isOperator(token.charAt(0)) && token.length() == 1) {
                if (stack.size() < 2) {
                    throw new IllegalArgumentException("Некорректное выражение");
                }
                
                double b = stack.pop();
                double a = stack.pop();
                double result;
                
                switch (token.charAt(0)) {
                    case '+':
                        result = a + b;
                        break;
                    case '-':
                        result = a - b;
                        break;
                    case '*':
                        result = a * b;
                        break;
                    case '/':
                        if (Math.abs(b) < 1e-10) {
                            throw new ArithmeticException("Деление на ноль!");
                        }
                        result = a / b;
                        break;
                    default:
                        throw new IllegalArgumentException("Неизвестный оператор: " + token);
                }
                
                stack.push(result);
            } else {
                try {
                    stack.push(Double.parseDouble(token));
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Некорректное число: " + token);
                }
            }
        }
        
        if (stack.size() != 1) {
            throw new IllegalArgumentException("Некорректное выражение");
        }
        
        return stack.pop();
    }

    private boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    private int precedence(char operator) {
        switch (operator) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            default:
                return -1;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}