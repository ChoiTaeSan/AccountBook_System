package com.example.java_fx_practice;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.control.Alert;
import java.io.*;
import java.time.LocalDate;

public class ExpenseManagementController {

    // UI 요소 필드
    @FXML
    private TextField expenseField = new TextField(); // 지출 금액 입력 텍스트 필드
    @FXML
    private TextField categoryField = new TextField(); // 카테고리 입력 텍스트 필드
    @FXML
    private DatePicker dateField = new DatePicker(); // 날짜 선택 DatePicker
    @FXML
    private Button addExpenseButton = new Button("지출 추가"); // 지출 추가 버튼
    @FXML
    private Button saveButton = new Button("저장"); // 저장 버튼
    @FXML
    private Button loadButton = new Button("불러오기"); // 불러오기 버튼
    @FXML
    private ListView<String> expenseList = new ListView<>(); // 지출 내역을 표시하는 리스트 뷰
    @FXML
    private Label totalExpenseLabel = new Label("총 지출: 0"); // 총 지출을 표시하는 레이블

    // 총 지출을 저장하는 클래스 필드
    private double totalExpense = 0;

    public ExpenseManagementController() {
        // UI 요소 초기화 및 이벤트 핸들러 설정

        // "지출 추가" 버튼 클릭 이벤트 핸들러
        addExpenseButton.setOnAction(e -> {
            // 사용자가 입력한 지출 금액, 카테고리 및 날짜 가져오기
            String expense = expenseField.getText();
            String category = categoryField.getText();
            LocalDate date = dateField.getValue();

            // 유효한 입력인지 확인
            if (!expense.isEmpty() && date != null) {
                double amount = Double.parseDouble(expense);

                // 지출 내역 항목 생성
                String entry = date + " - " + category + ": " + expense;

                // 지출 내역 리스트에 추가
                expenseList.getItems().add(entry);

                // 총 지출 업데이트
                totalExpense += amount;
                totalExpenseLabel.setText("총 지출: " + totalExpense);

                // 입력 필드 초기화
                expenseField.clear();
                categoryField.clear();
                dateField.setValue(null);
            } else {
                // 유효하지 않은 입력에 대한 경고 메시지
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("경고");
                alert.setHeaderText("유효하지 않은 입력");
                alert.setContentText("지출 금액 및 날짜는 필수 입력 항목입니다.");
                alert.showAndWait();
            }
        });

        // "저장" 버튼 클릭 이벤트 핸들러
        saveButton.setOnAction(e -> {
            // 지출 내역을 CSV 파일로 저장
            try (PrintWriter writer = new PrintWriter("expenses.csv")) {
                for (String entry : expenseList.getItems()) {
                    writer.println(entry);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        // "불러오기" 버튼 클릭 이벤트 핸들러
        loadButton.setOnAction(e -> {
            // 저장된 지출 내역을 CSV 파일에서 불러오기
            expenseList.getItems().clear(); // 기존 내역 지우기
            totalExpense = 0;

            try (BufferedReader reader = new BufferedReader(new FileReader("expenses.csv"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    expenseList.getItems().add(line);

                    // 지출 내역에서 금액 추출하여 총 지출 업데이트
                    double amount = Double.parseDouble(line.split(": ")[1]);
                    totalExpense += amount;
                }

                // 총 지출 레이블 업데이트
                totalExpenseLabel.setText("총 지출: " + totalExpense);


            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }
    public VBox getLayout() {
        // UI 요소를 레이아웃에 추가하고 반환
        VBox layout = new VBox(10);
        layout.getChildren().addAll(expenseField, categoryField, dateField, addExpenseButton, saveButton, loadButton, expenseList, totalExpenseLabel);
        return layout;
    }
}