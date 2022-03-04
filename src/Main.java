import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.function.UnaryOperator;

public class Main extends Application {

    public static Connection conn;
    public void StartConnection()
    {
        String conn_url = "jdbc:mysql://localhost:3306/employees?user=root&password=&serverTimezone=UTC";
        try
        {
            conn = DriverManager.getConnection(conn_url);
            System.out.println("Connection to DB started.");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void StopConnection()
    {
        try {
            conn.close();
            System.out.println("Connection to DB closed.");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage stage) throws IOException {
        StartConnection();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("SimpleDBClient.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("DB Editor");
        stage.setScene(scene);

        stage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        StopConnection();
    }

    public static void main(String[] args) {
        launch();
    }
}