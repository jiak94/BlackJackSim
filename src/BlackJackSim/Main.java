package BlackJackSim;

import BlackJackSim.View.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.stage.Stage;

public class Main extends Application {
    private Stage primaryStage;
    private SplitPane layout;

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Black Jack Simulator");

        initLayout();
    }

    public Main(){}

    private void initLayout() {
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("View/sample.fxml"));

            layout = (SplitPane) loader.load();

            Scene scene = new Scene(layout);

            primaryStage.setScene(scene);

            Controller controller = loader.getController();

            controller.setMain(this);

            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
