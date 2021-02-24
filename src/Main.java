import app.Manager;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        Manager manager = new Manager(primaryStage);
        manager.showMenusPage();
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
