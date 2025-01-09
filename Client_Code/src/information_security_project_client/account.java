package information_security_project_client;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class account extends Application {
    
    CheckBox ch3 = new CheckBox("Symatric Encrytion");
    CheckBox ch4 = new CheckBox("PGP");
    Button Deposits = new Button();
    Button Transformation = new Button();
    Button Recharge = new Button();
    Button Show_balance = new Button();
    Button Enter = new Button();
    TextField insert = new TextField();
    private StackPane V_R_S = new StackPane();
    private StackPane V_D_T = new StackPane();
    VBox v = new VBox();
    VBox v_c = new VBox();
    Label l = new Label();
    Label l_mony = new Label();
    Stage S;
    int type_encryption=1;
    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(v, 900, 600);
        
        l.setText("Abdalrahman Al Hlwany");
        l_mony.setText("");
        l.setPrefSize(250, 0);
        l_mony.setPrefSize(250, 0);
        l.setAlignment(Pos.TOP_CENTER);
        l.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
        l_mony.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
        v_c.setMargin(l, new Insets(20, 60, 10, 60));
        v.setMargin(V_R_S, new Insets(40, 60, 10, 60));        
        v.setMargin(V_D_T, new Insets(200, 60, 10, 60));
        ch3.setFont(Font.font("Tahoma", FontWeight.BOLD, 13));
        ch4.setFont(Font.font("Tahoma", FontWeight.BOLD, 13));
        
                
        D_Button(Deposits , "Deposits");
        D_Button(Transformation , "Transformation");
        D_Button(Recharge , "Recharge");
        D_Button(Show_balance , "Show balance"); 
        
        
        Enter.setFont(Font.font("Tahoma", FontWeight.BOLD, 15));
        Enter.setPrefWidth(400);
        Enter.setPadding(new Insets(10, 0, 10, 0));
        Enter.setText("send");
        ch3.setSelected(true);
        ch3.setOnAction((event) -> {
            ch4.setSelected(false);
            type_encryption = 1;
        });
        ch4.setOnAction((event) -> {
            ch3.setSelected(false);
            type_encryption = 2;
        });
        Deposits.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {                          
                Contact_with_server c_w_s = null ;
                try {
                    c_w_s = new Contact_with_server(type_encryption);
                } catch (IOException ex) {
                    Logger.getLogger(account.class.getName()).log(Level.SEVERE, null, ex);
                }
                String list = null;
                try {
                    list = c_w_s.Deposits();
                } catch (IOException ex) {
                    Logger.getLogger(account.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(account.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NoSuchAlgorithmException ex) {
                    Logger.getLogger(account.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NoSuchPaddingException ex) {
                    Logger.getLogger(account.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InvalidKeyException ex) {
                    Logger.getLogger(account.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InvalidAlgorithmParameterException ex) {
                    Logger.getLogger(account.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalBlockSizeException ex) {
                    Logger.getLogger(account.class.getName()).log(Level.SEVERE, null, ex);
                } catch (BadPaddingException ex) {
                    Logger.getLogger(account.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(account.class.getName()).log(Level.SEVERE, null, ex);
                }
                Deposits d = new Deposits(list);
                d.start(new Stage());                
            }
        });
        Transformation.setOnAction(new EventHandler<ActionEvent>() {            
            @Override
            public void handle(ActionEvent t) {                   
                Recharge R = new Recharge("Transformation" ,type_encryption);
                R.start(new Stage());                
            }
        });
        Recharge.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {                  
                Recharge R = new Recharge("Recharge" ,type_encryption);
                R.start(new Stage());
            }
        });
        Show_balance.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {                
                int mony=0;
                Contact_with_server c_w_s ;
                try {
                    c_w_s = new Contact_with_server(type_encryption);
                    mony = c_w_s.show_balance();
                } catch (IOException ex) {
                    Logger.getLogger(Information_Security_Project_Client.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(account.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NoSuchAlgorithmException ex) {
                    Logger.getLogger(account.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NoSuchPaddingException ex) {
                    Logger.getLogger(account.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InvalidKeyException ex) {
                    Logger.getLogger(account.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InvalidAlgorithmParameterException ex) {
                    Logger.getLogger(account.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalBlockSizeException ex) {
                    Logger.getLogger(account.class.getName()).log(Level.SEVERE, null, ex);
                } catch (BadPaddingException ex) {
                    Logger.getLogger(account.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(account.class.getName()).log(Level.SEVERE, null, ex);
                }
                l_mony.setText(mony+"");
                
                V_R_S.setAlignment(l_mony, Pos.BOTTOM_RIGHT); 
                try{
                V_R_S.getChildren().add(l_mony);
                }catch(Exception e){}
            }
        });
        
        
       
        
        v_c.setPadding(new Insets(10, 0, 0, 0));
        v_c.getChildren().add(l);
        v_c.setAlignment(Pos.BASELINE_CENTER);                
        
        V_R_S.setAlignment(Recharge, Pos.BASELINE_LEFT);
        V_R_S.setAlignment(Show_balance, Pos.BASELINE_RIGHT);
        
        V_D_T.setAlignment(Deposits, Pos.BASELINE_LEFT);
        V_D_T.setAlignment(Transformation, Pos.BASELINE_RIGHT);


        V_R_S.getChildren().addAll(Recharge,
                Show_balance);
        
        V_D_T.getChildren().addAll(Deposits,
                Transformation);
        ch3.setPadding(new Insets(10, 0, 0, 0));
        ch4.setPadding(new Insets(10, 0, 0, 0));
        v.setPadding(new Insets(5, 5, 5, 5));
        v.getChildren().addAll(ch3,ch4,v_c,V_R_S,V_D_T);
        
        v.setAlignment(Pos.BASELINE_LEFT);
        v.setPrefSize(500, 500);

        String image = Information_Security_Project_Client.class.getResource("blue.png").toExternalForm();
        v.setStyle("-fx-background-image: url('" + image + "'); "
                + "-fx-background-position: center center; "
                + "-fx-background-repeat: stretch;");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("My_Account");
        stage.show();
        S = stage;
    }
    public void D_Button(Button b ,String name_button){
        b.setStyle("-fx-font: 22 arial; -fx-base: #b6e7c9;");
        b.setFont(Font.font("Tahoma", FontWeight.BOLD, 15));
        b.setPrefWidth(300);
        b.setPadding(new Insets(10, 0, 10, 0));
        b.setText(name_button); 
    }
    
    
}
