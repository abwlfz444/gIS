import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SiMain extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("layout.fxml"));
        Scene scene = new Scene(loader.load());
        Controller controller = new Controller();
        loader.setController(controller);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("gIS");
        stage.show();

    }
    public void run (){launch();}
}
