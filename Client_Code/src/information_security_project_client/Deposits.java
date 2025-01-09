package information_security_project_client;

import java.util.Collections;
import java.util.LinkedList;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.ScrollPane;

public class Deposits extends Application{

    
    TextArea tArea = new TextArea();
    Label message = new Label("Deposits: ");
    Button close = new Button("close");
    String list;
    
    Deposits(String list){
        this.list = list;
    }

    @Override
    public void start(Stage primaryStage){
        BorderPane bPane = new BorderPane();



        HBox hBox = new HBox(message);
            bPane.setTop(hBox);

        HBox buttons = new HBox(10);
            buttons.getChildren().addAll(close);
        bPane.setBottom(buttons);
            buttons.setAlignment(Pos.CENTER);
        VBox vBox = new VBox();
        display();
            vBox.getChildren().addAll(hBox, tArea, buttons);

        bPane.setCenter(new ScrollPane(tArea));

        Scene scene = new Scene(vBox, 300,250);
        primaryStage.setTitle("abdalrahman al hlwnay");
        primaryStage.setScene(scene);
        primaryStage.show();

        

        close.setOnAction(e -> {
            primaryStage.close();
            //display();
        });//end action

    }//end stage 

    private void display() {
        
            tArea.setText(null);
            tArea.appendText(list + " ");

    }//end display


}//end class 
