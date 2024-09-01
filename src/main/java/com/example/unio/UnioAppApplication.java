//package com.example.unio;
//
//import javafx.application.Platform;
//import javafx.concurrent.Worker;
//import javafx.embed.swing.JFXPanel;
//import javafx.geometry.Rectangle2D;
//import javafx.scene.Scene;
//import javafx.scene.layout.BorderPane;
//import javafx.scene.web.WebEngine;
//import javafx.scene.web.WebView;
//import javafx.stage.Screen;
//import javafx.stage.Stage;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//
//@SpringBootApplication
//public class UnioAppApplication implements CommandLineRunner {
//
//    public static void main(String[] args) {
//        new JFXPanel(); // 初始化 JavaFX 环境
//        SpringApplication.run(UnioAppApplication.class, args);
//    }
//
//    @Override
//    public void run(String... args) throws Exception {
//        Platform.runLater(() -> {
//            Stage primaryStage = new Stage();
//            primaryStage.setTitle("Unio Application");
//
//            // 获取屏幕的可视区域
//            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
//
//            // 创建 WebView
//            WebView webView = new WebView();
//            WebEngine webEngine = webView.getEngine();
//
//            // 启用 JavaScript
//            webEngine.setJavaScriptEnabled(true);
//
//            // 监听页面加载状态
//            webView.getEngine().getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
//                if (newState == Worker.State.SUCCEEDED) {
//                    System.out.println("Loaded URL: " + webView.getEngine().getLocation());
//                }
//            });
//
//            // 加载页面
//            webView.getEngine().load("http://localhost:8080");
//
//            // 创建布局
//            BorderPane root = new BorderPane();
//            root.setCenter(webView);
//
//            // 设置窗口大小为屏幕的可视区域
//            Scene scene = new Scene(root, screenBounds.getWidth()/2, screenBounds.getHeight()/2);
//
//            primaryStage.setScene(scene);
//            primaryStage.setResizable(true); // 确保窗口可以调整大小
//            primaryStage.show();
//        });
//    }
//}
//
package com.example.unio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UnioAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(UnioAppApplication.class, args);

    }

}