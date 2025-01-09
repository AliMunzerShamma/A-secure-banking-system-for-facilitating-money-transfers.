package information_security_project_client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Information_Security_Project_Client extends Application {

    ImageView I = new ImageView();
    Button Rigister = new Button();
    Button Log_in = new Button();
    Button Enter = new Button();
    TextField insert = new TextField();
    VBox v = new VBox();
    Label l = new Label();
    Stage S;

    public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {
        
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = new Scene(v, 900, 600);

        l.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
        v.setMargin(l, new Insets(20, 60, 10, 60));
        v.setMargin(Rigister, new Insets(200, 60, 10, 60));
        v.setMargin(Log_in, new Insets(20, 60, 10, 60));        
        

        Rigister.setFont(Font.font("Tahoma", FontWeight.BOLD, 15));
        Rigister.setPrefWidth(400);
        Rigister.setPadding(new Insets(10, 0, 10, 0));
        Rigister.setText("Rigister");

        Log_in.setFont(Font.font("Tahoma", FontWeight.BOLD, 15));
        Log_in.setPrefWidth(400);
        Log_in.setPadding(new Insets(10, 0, 10, 0));
        Log_in.setText("Log in");
        
        Enter.setFont(Font.font("Tahoma", FontWeight.BOLD, 15));
        Enter.setPrefWidth(400);
        Enter.setPadding(new Insets(10, 0, 10, 0));
        Enter.setText("Enter");

        I.setFitHeight(250);
        I.setFitWidth(500);

        Rigister.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                stage.close();
                Contact_with_server c_w_s;
                try {
                    c_w_s = new Contact_with_server(0);
                    c_w_s.Register();
                } catch (IOException ex) {
                    Logger.getLogger(Information_Security_Project_Client.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(Information_Security_Project_Client.class.getName()).log(Level.SEVERE, null, ex);
                }
                account acc = new account();
                acc.start(new Stage());
            }
        });
        Log_in.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {                             
                v.setMargin(insert, new Insets(20, 60, 10, 60));
                v.setMargin(Enter, new Insets(20, 60, 10, 60));
                v.getChildren().addAll( insert , Enter);                
            }
        });
        
        Enter.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {                
                int id = Integer.parseInt(insert.getText());
                insert.clear();
                stage.close();
                Contact_with_server c_w_s ;
                try {
                    c_w_s = new Contact_with_server(0);
                    c_w_s.Log_in(id);                    
                } catch (IOException ex) {
                    Logger.getLogger(Information_Security_Project_Client.class.getName()).log(Level.SEVERE, null, ex);
                }
                account acc = new account();
                acc.start(new Stage());
            }
        });
        
        v.setPadding(new Insets(5, 5, 5, 5));
        v.getChildren().addAll(Rigister, Log_in);
        v.setAlignment(Pos.TOP_CENTER);
        v.setPrefSize(500, 500);

        String image = Information_Security_Project_Client.class.getResource("blue.png").toExternalForm();
        v.setStyle("-fx-background-image: url('" + image + "'); "
                + "-fx-background-position: center center; "
                + "-fx-background-repeat: stretch;");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("System_Bank");
        stage.show();
        S = stage;
    }

}