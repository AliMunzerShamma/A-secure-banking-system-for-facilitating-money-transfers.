package information_security_project_client;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
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
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 *
 * @author Abdalrahman
 */
public class Recharge extends Application {            
    Button Enter = new Button();
    TextField insert_id = new TextField();
    TextField insert_mony = new TextField();
    TextField insert_reason_transfer = new TextField();
    Label l_id = new Label();
    Label l_mony = new Label();
    Label l_reason = new Label();
    VBox v = new VBox();    
    Stage S;
    String type;
    int type_encryption;
    
    Recharge(String type , int type_encryption){
        this.type = type;
        this.type_encryption = type_encryption;
    }
    
    @Override
    public void start(Stage stage ) {
        Scene scene = new Scene(v, 500, 500);            
        
        l_id.setText("id");
        l_mony.setText("money");
        l_reason.setText("reason transfer");
        l_id.setPrefSize(250, 0);
        l_mony.setPrefSize(250, 0);
        l_reason.setPrefSize(250, 0);
        l_id.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
        l_mony.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
        l_reason.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
        v.setMargin(l_mony,new Insets(100, 60, 10, 60));
        v.setMargin(insert_mony, new Insets(20, 60, 10, 60));
        if(type.equals("Transformation")){
            v.setMargin(l_id,new Insets(20, 60, 10, 60));
            v.setMargin(insert_id, new Insets(20, 60, 10, 60));
            v.setMargin(l_reason,new Insets(20, 60, 10, 60));
            v.setMargin(insert_reason_transfer, new Insets(20, 60, 10, 60));
        }        
        v.setMargin(Enter, new Insets(20, 60, 10, 60));
        
        Enter.setStyle("-fx-font: 22 arial; -fx-base: #b6e7c9;");
        Enter.setFont(Font.font("Tahoma", FontWeight.BOLD, 15));
        Enter.setPrefWidth(400);
        Enter.setPadding(new Insets(10, 0, 10, 0));
        Enter.setText("Enter");
         
        Enter.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                try {
                    Contact_with_server c_w_s = new Contact_with_server(type_encryption);
                    if(type.equals("Transformation"))
                        c_w_s.Transformation(Contact_with_server.id , Integer.parseInt(insert_mony.getText()),Integer.parseInt(insert_id.getText()),insert_reason_transfer.getText());
                    else
                        c_w_s.Recharge(Integer.parseInt(insert_mony.getText()));
                } catch (IOException ex) {
                    Logger.getLogger(Recharge.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {                
                    Logger.getLogger(Recharge.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NoSuchAlgorithmException ex) {
                    Logger.getLogger(Recharge.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NoSuchPaddingException ex) {
                    Logger.getLogger(Recharge.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InvalidKeyException ex) {
                    Logger.getLogger(Recharge.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InvalidAlgorithmParameterException ex) {
                    Logger.getLogger(Recharge.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalBlockSizeException ex) {
                    Logger.getLogger(Recharge.class.getName()).log(Level.SEVERE, null, ex);
                } catch (BadPaddingException ex) {
                    Logger.getLogger(Recharge.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(Recharge.class.getName()).log(Level.SEVERE, null, ex);
                }
                stage.close();
            }
        });
        
        v.setPadding(new Insets(5, 5, 5, 5));
        if(type.equals("Transformation"))
            v.getChildren().addAll(l_mony,insert_mony,l_id,insert_id ,l_reason, insert_reason_transfer, Enter);
        else
            v.getChildren().addAll(l_mony,insert_mony,Enter);
        v.setAlignment(Pos.TOP_CENTER);
        v.setPrefSize(500, 500);

        String image = Information_Security_Project_Client.class.getResource("blue.png").toExternalForm();
        v.setStyle("-fx-background-image: url('" + image + "'); "
                + "-fx-background-position: center center; "
                + "-fx-background-repeat: stretch;");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Recharge");
        stage.show();
        S = stage;
    }    
    
}
