package com.example.java_fx_practice;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;

public class AccountBookSystem extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("가계부 애플리케이션");

        // ExpenseManager 객체 생성
        ExpenseManagementController expenseManager = new ExpenseManagementController();


        // 레이아웃을 가져와 Scene에 추가
        VBox expenseManagerLayout = expenseManager.getLayout();
        Scene scene = new Scene(expenseManagerLayout, 400, 400);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}




