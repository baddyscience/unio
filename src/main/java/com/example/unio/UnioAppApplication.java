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
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.awt.Desktop;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;

@SpringBootApplication
public class UnioAppApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(UnioAppApplication.class);

        // 添加监听器监听环境准备事件
        application.addListeners(new ApplicationListener<ApplicationEnvironmentPreparedEvent>() {
            @Override
            public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
                ConfigurableEnvironment environment = event.getEnvironment();
                int port = environment.getProperty("server.port", Integer.class, 8080);
                port = findAvailablePort(port);
                System.out.println("Using port: " + port);

                // 动态设置端口
                environment.getPropertySources().addFirst(new MapPropertySource("customPort",
                        Collections.singletonMap("server.port", port)));
            }
        });

        // 添加监听器监听应用准备就绪事件
        application.addListeners(new ApplicationListener<ApplicationReadyEvent>() {
            @Override
            public void onApplicationEvent(ApplicationReadyEvent event) {
                ConfigurableApplicationContext context = event.getApplicationContext();
                int port = context.getEnvironment().getProperty("server.port", Integer.class, 8080);
                openBrowser("http://localhost:" + port);
            }
        });

        application.run(args);
    }

    private static int findAvailablePort(int startPort) {
        int port = startPort;
        while (!isPortAvailable(port)) {
            System.out.println("Port " + port + " is not available, trying " + (port + 1));
            port++;
        }
        return port;
    }

    private static boolean isPortAvailable(int port) {
        try (ServerSocket socket = new ServerSocket(port)) {
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private static void openBrowser(String url) {
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                desktop.browse(new URI(url));
            } else {
                String os = System.getProperty("os.name").toLowerCase();
                Runtime runtime = Runtime.getRuntime();
                if (os.contains("win")) {
                    runtime.exec(new String[]{"cmd", "/c", "start", url});
                } else if (os.contains("mac")) {
                    runtime.exec(new String[]{"open", url});
                } else if (os.contains("nix") || os.contains("nux")) {
                    runtime.exec(new String[]{"xdg-open", url});
                } else {
                    System.err.println("Unsupported operating system.");
                }
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
