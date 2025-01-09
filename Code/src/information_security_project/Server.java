package information_security_project;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import static java.nio.charset.StandardCharsets.UTF_8;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

// The Basic Work For This class Is To insurance The process of Safe communication between This Server and All Clients -.-
//___________________________________________________________________________________________________________________
public class Server extends Thread {

   // static int id = 0;
    static Socket s;                                                            // Communication will be through use Socket -.-
    Connection con;                                                             // This For Connection Process With Database -.-
    // This is the Value Of Server Private Key Which we manually generated and copied To here -.-
    static String private_key = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDQj/imq+YLCB6HYwEIrEPuOmYmJRrj8nM09xEeN9ZIPLsTGavcpHW0r8qloSzcyBYLIzjnJSWfLxd2yK4kQsC2JrwcdeMvfPmjVywd7dIvmjfH4KSj0RPdc9cU4X/Sn6KRtWS+ExJg+vSbcpEjrI8wcbXY2zyRKvcwbBEqZvTCXtmzE0ShLn9322VPZp8m9r/xenOBBk7R8YjqLLP1LLSafjBf9LY+EsO7ImRG/SN6NLnT5C9azX1UcJ0dXD/nGdOJU+vhMWV91mzbo6qytHzOwK29OufSrpFhptQInZMJ5N6vLZUEOBAT0sbz/VFlEUKsc/agkshAjHoAwVLUhu6bAgMBAAECggEAe3QNQlNUW0lWENvIu+KEX1qQJpomHbHhAgQRTOrOW3ofXkH+2h87UVYRRRNvo8yIR9V0lFYHu/87C2PkuLAOtIOfK9WYQf7fIhGympCwrt7Bj/3oVtOmx5oPQVvttrYpm8hctqpBYciKzAz33r8BDzLv6kpzwpZFNZNyYcAJt5cttSyeGq8RUgmBlOHR+qnpOu9uA5AmrkFeqF7TwIPKEVNhzq6Xu/lha0fcuklCs0UXmuF25tAPC/hA4sqxFZmoSIS0/50vw7PhqVoXFouB+UE+O+SLaKRGWmJb+cqG9JDdKcZR73ugo4JycpUI9IqAvIQwIu4h9trFoMYj5jS1WQKBgQDyd4svgOXXXk5+Klt4T1gd7HV9AoLiDAyfn1ReDCKLy39NfOCT+vmzkHFVco9L1BPhhDEk+bKIwj6hNJo86zmpvGMsH1uQMrkKaJs6sEiD7UoW0T3dSTfYOPkJB4BEwCwxvNTrLCoBNv54BjA3fzEQYaNNbG7Dyr2isWyQQhnHFQKBgQDcM/yTOhQkB99a3U1BkMdbWA1HMeqMQTmXhjbXRVF9/UdqmrJfIPQIaI592/IQB0TTAXxtdqHFFxR14W+ESrlGf75vEvho/T9VrbG6ityGBJG1b3AdYgJ5GnkM5UoLAK1EeL2ysdedTBwgfZswO3GSzDso9Jbc3ZFcQklA2cJK7wKBgCxVXVa3O89QSAvblNP/tf3qsWTFI8dPKrO4/0Ue9OYYtFMnxSxplGSXwZE9HCRiR6xRbmbzXQiSc78oDhebbvSxJHT52tUX4SudCt0FRI0Swxw+8A/ZBmP7rtVO0M5BURNbEpdZgOit9INfpktPb1oucByE+g/Erz3jMRWDcJEtAoGBANYXXUwvPYHCfbA0VXaXspvmwhIkP+xZRgtCHHO0G4UpCZRmD2JaAaljLpbDj1dyGcIlQbqTiri6tdPeeLPgir42WQpZIVfCUR+WHqRFeh56tsf2LsUCk3D3AhY9n32PDObzlUv1iLKQWxBjzsxYHxYgpqwtEbhvW3Kdp+b7UOHVAoGARKYMRFB9MqI2rBhCwjjbfcFySEmfrE1yYznH84h1cLABJa3ciYbrhMIeATguPo9S6JuRkytNBQ6sz2SqoA6BwpUwE1aSizeSYTeSBuZD5csQzkfcsLRgdCz9PZeIrvUIEdX63H7MQJ9grtUEEVnC8cjwW5xzw8eqPFLK72aAzqY=";
    
    // The constructor -.-
    Server(Socket s, Connection con) throws IOException, SQLException {
        this.s = s;
        this.con = con;
    }
    
    // Run On The Threads -.-
    public void run() {
        DataInputStream dis = null;                                             
        DataOutputStream dos;                                                   
        int type = 0;                                                           // Init The Number Of Process Type That it will perform By the server -.-
        try {
            dis = new DataInputStream(s.getInputStream());                      // To Read From The Socket s -.-
            dos = new DataOutputStream(s.getOutputStream());                    // To Write In The Socket s -.-
            type = dis.readInt();                                               // Read The Number Of Process Type TO DO -.-
            
            if (type == 1) {                                                    // type = 1 : This means the operation is registeration -.-
                SecretKey key =  Encryption.keygenerator();                     // generate a Key For The Symmetric Encryption -.-
                String encodedKey = Base64.getEncoder().encodeToString(key.getEncoded()); // Convert The Key To String -.-
                System.out.println("key.getEncoded() : " + key);                
                dos.writeInt(Information_Security_Project.id);                  // Write The id Of New Registered user To The Socket -.-
                ObjectInputStream diss = new ObjectInputStream(s.getInputStream()); // To Read From The Socket s -.-
                String public_key = (String) diss.readObject();                 // Read The Client Public Key From The Socket -.-
                Rigister(con, Information_Security_Project.id + "",encodedKey,public_key); // and Call The Rigister To Do The registeration Process -.-
                Information_Security_Project.id++;                              // add One To Sum Of Ids In The Server For The New Client -.-
                
            } else if (type == 2) {                                             // type = 2 : This means the operation is log in -.-
                int id_user = dis.readInt();                                    // Read From The Socket The Value Of id For The user who wants to log in -.-
                int id = Log_in(con, id_user + "");                             // and Call The Log_in To Do The Log In Process -.-                            
                dos.writeInt(id);                                               // Write To The Socket The Value Of User id To Verifi That The Log In Process Is succeeded -.-        
                System.out.println(Information_Security_Project.id +"************");
                
            } else if (type == 3) {                                             // type = 3 : This means the operation is Put more money into the Client account -.-
                int id_user = dis.readInt();                                    // Read From The Socket The Value Of id For The user who wants to recharge -.-
                //int mony = dis.readInt();
                int type_encryption = dis.readInt();                            // Read The Type Of Encryption Which the Client wants to be Encrypt the value of money Through it -.-
                System.out.println("type_encryption : " + type_encryption);
                if(type_encryption == 1){                                       // type_encryption = 1 : Means That The Type Of Encryption Is Symmetric Encryption -.-
                    ObjectInputStream diss = new ObjectInputStream(s.getInputStream()); // To Read From The Socket s -.-
                    byte[] iv = (byte[]) diss.readObject();                     // Read The initializing Vectore For The Symmetric Encryption , CBC Mode -.- 
                    String iv_key = Base64.getEncoder().encodeToString(iv);     // and Convert The IV to String -.-
                    iv_key = iv_key.substring(0, 16);                           // Cut First 16 bit Of the IV -.-
                    byte[] mony_en = (byte[])diss.readObject();                 // and Read The Encrypted Value Of Money -.-
                    String key = (String)know_anythink(con , id_user + "" , "key_generator"); // Get The Symmetric Key For This Client By his Id From The Database -.-
                    byte[] mony_de = Encryption.Symmetric_decoding(mony_en, iv_key, key); // Decrypt The Value Of Money By The Symmetric Key -.-                            
                    int mony = Integer.parseInt(new String(mony_de));           // Convert The Decrypted Value Of Money To Integer Value -.-
                    mony +=(Integer.parseInt((String) know_anythink(con, id_user+"", "mony"))); // Add The Value Of Money To The Money Of this Client account -.-              
                    Update_anythink(con, id_user + "", mony + "", "mony");      // Update The Client Info In Database -.-                   
                    ObjectOutputStream doss = new ObjectOutputStream(s.getOutputStream()); // To Write In The Socket s -.-
                    doss.writeObject(Encryption.Symmetric_encryption("Tm ........", iv_key, key)); // Write The acknowledgement Which confirms the successful completion of the process -.-
                    System.out.println(mony  + "     ***************************");
                }else if(type_encryption == 2){                                 // type_encryption = 2 : Means That The Type Of Encryption Is PGP Encryption -.-
                    String key_session_de = recive_session_key_en(s);           // Read The Encrypted Session Symmetric key , and Decrypt it Using Symmitric Decryption -.-
                    ObjectInputStream diss = new ObjectInputStream(s.getInputStream()); // To Read From The Socket s -.-
                    byte[] mony_en = (byte[]) diss.readObject();                // and Read The Encrypted Value Of Money -.-
                    byte[] mony_de = Encryption.Symmetric_decoding(mony_en, "                ", key_session_de); // Decrypt The Value Of Money By The Symmetric Key -.-                  
                    int mony = Integer.parseInt(new String(mony_de));           // Convert The Decrypted Value Of Money To Integer Value -.-
                    mony +=(Integer.parseInt((String) know_anythink(con, id_user+"", "mony"))); // Add The Value Of Money To The Money Of this Client account -.-
                    System.out.println(mony  + "     ***************************");
                    Update_anythink(con, id_user + "", mony + "", "mony");      // Update The Client Info In Database -.-              
                    ObjectOutputStream doss = new ObjectOutputStream(s.getOutputStream()); // To Write In The Socket s -.-
                    doss.writeObject(Encryption.Symmetric_encryption("Tm ........", "                ", key_session_de)); // Write The acknowledgement Which confirms the successful completion of the process -.-      
                }
                
            } else if (type == 4) {                                             // type = 4 : This means the operation is Sending a money order from the Client account to another account -.-
                int id_user1 = dis.readInt();                                   // Read From The Socket The Value Of id For The The sender Client -.-
                int type_encryption = dis.readInt();                            // Read The Type Of Encryption Which the Client wants to be Encrypt Info Through it -.-
                if(type_encryption == 1){                                       // type_encryption = 1 : Means That The Type Of Encryption Is Symmetric Encryption -.-
                    ObjectInputStream diss = new ObjectInputStream(s.getInputStream()); // To Read From The Socket s -.-
                    byte[] iv = (byte[]) diss.readObject();                     // Read The initializing Vectore For The Symmetric Encryption , CBC Mode -.- 
                    String iv_key = Base64.getEncoder().encodeToString(iv);     // and Convert The IV to String -.-
                    iv_key = iv_key.substring(0, 16);                           // Cut First 16 bit Of the IV -.-
                    byte[] mony_en = (byte[])diss.readObject();                 // and Read The Encrypted Value Of Money -.-
                    String key = (String)know_anythink(con , id_user1+"" , "key_generator"); // Get The Symmetric Key For This Client By his Id From The Database -.-
                    byte[] mony_de = Encryption.Symmetric_decoding(mony_en, iv_key, key); // Decrypt The Value Of Money By The Symmetric Key -.-                
                    int mony = Integer.parseInt(new String(mony_de));           // Convert The Decrypted Value Of Money To Integer Value -.-         
                    byte[] id_en = (byte[])diss.readObject();                   // and Read The Encrypted Value Of The destination id -.-
                    //String key = (String)know_anythink(con , id_user1+"" , "key_generator");
                    byte[] id_de = Encryption.Symmetric_decoding(id_en, iv_key, key); // Decrypt The Value Of destination id By The Symmetric Key -.-                                                 
                    int id_user2 = Integer.parseInt(new String(id_de));         // Convert The Decrypted Value Of destination id To Integer Value -.-
                    byte[] reason_transfer_en = (byte[]) diss.readObject();     // and Read The Encrypted Value Of The reason for the transfer Money -.-
                    byte[] reason_transfer_de = Encryption.Symmetric_decoding(reason_transfer_en,iv_key, key); // Decrypt The Value Of The reason for the transfer Money By The Symmetric Key -.-                                               
                    String reason_transfer = new String(reason_transfer_de);    // Convert The Decrypted Value Of The reason for the transfer Money To String -.-
                    System.out.println("reason_transfer : " + reason_transfer);
                    boolean boo = check_transformation(id_user1 , mony , id_user2 ,reason_transfer); // Check If The Sender Client Have enough Money To Make the transfer Successful -.-                   System.out.println("id : " + id_user2 + "         mony : " + mony);
                    int mony_u1, mony_u2;
                    if (boo == true) {                                          // If The check_transformation Is Done -.-
                        ObjectOutputStream doss = new ObjectOutputStream(s.getOutputStream()); // To Write In The Socket s -.-
                        doss.writeObject(Encryption.Symmetric_encryption("Balance transferred ........",iv_key,key)); // Encrypt & Send The acknowledgment That Tell The Sender Client The Transformation Process Succeeded -.-                                           
                    } else {
                        ObjectOutputStream doss = new ObjectOutputStream(s.getOutputStream()); // To Write In The Socket s -.-
                        doss.writeObject(Encryption.Symmetric_encryption("The amount cannot be transferred " + mony + " because your current balance is : " + know_anythink(con, id_user1 + "", "mony"),iv_key ,key)); // Encrypt & Send The acknowledgment That Tell The Sender Client The Transformation Process Failed -.-                 
                    }
                }
                else if(type_encryption == 2){                                  // type_encryption = 2 : Means That The Type Of Encryption Is PGP Encryption -.-
                    String key_session_de = recive_session_key_en(s);           // Read The Encrypted Session Symmetric key , and Decrypt it Using Symmitric Decryption -.-
                    ObjectInputStream diss = new ObjectInputStream(s.getInputStream()); // To Read From The Socket s -.-
                    byte[] mony_en = (byte[])diss.readObject();                 // and Read The Encrypted Value Of Money -.-
                    byte[] mony_de = Encryption.Symmetric_decoding(mony_en, "                ", key_session_de); // Decrypt The Value Of Money By The Symmetric Key -.- 
                    int mony = Integer.parseInt(new String(mony_de));           // Convert The Decrypted Value Of Money To Integer Value -.-
                    byte[] id_en = (byte[])diss.readObject();                   // and Read The Encrypted Value Of The destination id -.- 
                    byte[] id_de = Encryption.Symmetric_decoding(id_en, "                ", key_session_de); // Decrypt The Value Of destination id By The Symmetric Key -.-                                                
                    int id_user2 = Integer.parseInt(new String(id_de));         // Convert The Decrypted Value Of destination id To Integer Value -.-
                    byte[] reason_transfer_en = (byte[]) diss.readObject();     // and Read The Encrypted Value Of The reason for the transfer Money -.-
                    byte[] reason_transfer_de = Encryption.Symmetric_decoding(reason_transfer_en, "                ", key_session_de); // Decrypt The Value Of The reason for the transfer Money By The Symmetric Key -.-                                                 
                    String reason_transfer = new String(reason_transfer_de);    // Convert The Decrypted Value Of The reason for the transfer Money To String -.-
                    System.out.println("reason_transfer : " + reason_transfer);
                    boolean boo = check_transformation(id_user1 , mony , id_user2 , reason_transfer); // Check If The Sender Client Have enough Money To Make the transfer Successful -.-
                    int mony_u1 , mony_u2 ;
                    if (boo == true) {                                          // If The check_transformation Is Done -.-
                        ObjectOutputStream doss = new ObjectOutputStream(s.getOutputStream()); // To Write In The Socket s -.-
                        doss.writeObject(Encryption.Symmetric_encryption("Balance transferred ........", "                ", key_session_de)); // Encrypt & Send The acknowledgment That Tell The Sender Client The Transformation Process Succeeded -.-                                           
                    } else {
                        ObjectOutputStream doss = new ObjectOutputStream(s.getOutputStream()); // To Write In The Socket s -.-
                        doss.writeObject(Encryption.Symmetric_encryption("The amount cannot be transferred " + mony + " because your current balance is : " + know_anythink(con, id_user1 + "", "mony"), "                ", key_session_de));  // Encrypt & Send The acknowledgment That Tell The Sender Client The Transformation Process Failed -.-                                          
                    }
                }
            }
            
            else if(type == 5){                                                 // type = 5 : This means the operation is Show money values deposited under this user's account -.-
                int id = dis.readInt();                                         // Read From The Socket The Value Of id For The The Client Who wants to know the value of money in his account -.-
                int type_encryption = dis.readInt();                            // Read The Type Of Encryption Which the Client wants to be Encrypt Info Through it -.-
                //String iv = dis.readUTF();                
                int mony = (Integer.parseInt((String) know_anythink(con , id+"","mony"))); // Get The Value Of Money For This Client From The Database -.-
                if(type_encryption == 1){                                       // type_encryption = 1 : Means That The Type Of Encryption Is Symmetric Encryption -.-
                    String key = (String)know_anythink(con , id+"" , "key_generator"); // Get The Symmetric Key For This Client By his Id From The Database -.-
                    SecretKey key1 =  Encryption.keygenerator();                //generate a Key For Calculate The IV -.-
                    String iv = Base64.getEncoder().encodeToString(key1.getEncoded()); // generate initializing Vectore For The Symmetric Encryption , CBC Mode -.-
                    String iv_16 = iv.substring(0, 16);                         // Cut First 16 bit Of the IV -.-
                    System.out.println("key : " + key +"\niv : " + iv_16);
                    byte[] mony_en = Encryption.Symmetric_encryption(mony+"",iv_16, key); // Encrypt The Value money -.-
                    ObjectOutputStream doss = new ObjectOutputStream(s.getOutputStream()); // To Write In The Socket s -.-
                    doss.writeObject(key1.getEncoded());                        // Write The Symmetric Key To The Socket -.-
                    doss.writeObject(mony_en);                                  // Write The Encrypted Value Of Money -.-
                }
                else if(type_encryption == 2){                                  // type_encryption = 2 : Means That The Type Of Encryption Is PGP Encryption -.-
                    String key_session_de = recive_session_key_en(s);           // Read The Encrypted Session Symmetric key , and Decrypt it Using Symmitric Decryption -.-
                    System.out.println(key_session_de);
                    ObjectOutputStream doss = new ObjectOutputStream(s.getOutputStream()); // To Write In The Socket s -.-                   
                    doss.writeObject(Encryption.Symmetric_encryption(mony+"", "                ", key_session_de)); // Encrypt & Write The Encrypted Value Of Money -.-
                }
                
            }else if(type ==6){                                                 // type = 6 : This means the operation is Show deposits or transfers made by the Client from his account -.-
                int id = dis.readInt();                                         // Read From The Socket The Value Of id For The The Client Who wants to know All the transfers Operations -.-
                String des = (String)know_anythink(con , id+"" , "Remittances");//Get All The Remittances Which was done through this Client account From Database-.-
                System.out.println(des);
                int type_encryption = dis.readInt();                            // Read The Type Of Encryption Which the Client wants to be Encrypt Info Through it -.-
                if(type_encryption == 1){                                       // type_encryption = 1 : Means That The Type Of Encryption Is Symmetric Encryption -.-
                    String key = (String)know_anythink(con , id+"" , "key_generator"); // Get The Symmetric Key For This Client By his Id From The Database -.-
                    SecretKey key1 =  Encryption.keygenerator();                //generate a Key For Calculate The IV -.-
                    String iv = Base64.getEncoder().encodeToString(key1.getEncoded()); // generate initializing Vectore For The Symmetric Encryption , CBC Mode -.-
                    String iv_16 = iv.substring(0, 16);                         // Cut First 16 bit Of the IV -.-
                    byte[] des_en = Encryption.Symmetric_encryption(des+"",iv_16, key); // Encrypt All The Remittances -.-
                    ObjectOutputStream doss = new ObjectOutputStream(s.getOutputStream()); // To Write In The Socket s -.-
                    doss.writeObject(key1.getEncoded());                        // Write The Symmetric Key To The Socket -.-
                    doss.writeObject(des_en);                                   // Write The Encrypted Remittances -.-
                }
                else if(type_encryption ==2){                                   // type_encryption = 2 : Means That The Type Of Encryption Is PGP Encryption -.-
                    String key_session_de = recive_session_key_en(s);           // Read The Encrypted Session Symmetric key , and Decrypt it Using Symmitric Decryption -.- 
                    byte[] des_en = Encryption.Symmetric_encryption(des+"","                ", key_session_de); // Encrypt All The Remittances -.-
                    ObjectOutputStream doss = new ObjectOutputStream(s.getOutputStream()); // To Write In The Socket s -.-
                    doss.writeObject(des_en);                                   // Write The Encrypted Remittances -.-
                }
            }
            
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidAlgorithmParameterException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeySpecException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // The Basic Work For This Function is Complete The registeration operation , By Insert The New User To The User Table In Database -.-
    static public void Rigister(Connection con, String id , String keygenerator , String public_key) throws SQLException {
        String query = " insert into user (id,mony,Remittances,num_Remittances,key_generator,public_key)"
                + " values (?,?,?,?,?,?)";
        PreparedStatement preparedStmt = con.prepareStatement(query);
        preparedStmt.setString(1, id);
        preparedStmt.setString(2, 0 + "");
        preparedStmt.setString(3, "");
        preparedStmt.setString(4, 0 + "");
        preparedStmt.setString(5, keygenerator);
        preparedStmt.setString(6, public_key);
        preparedStmt.execute();
    }

    // The Basic Work For This Function is Complete The Log In operation, By Get The Id From Users Table In The Database ,
    // and Return it , (if Not Found (Wich Means That this Not User For This System)) Return -1 -.-
    static public int Log_in(Connection con, String id) throws SQLException {
        Statement stmt = con.createStatement();
        String query = " select id from user where id=" + id;
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            System.out.println(rs.getString("id"));
            return Integer.parseInt(id);
        }
        return -1;
    }    

    // After do any Process Which may affect the information inside The Database , All information must be updated within The Database -.-
    // This Can Be Done By The following Function -.-
    static public void Update_anythink(Connection con, String id, String mony, String update) throws SQLException {
        String query = "UPDATE `user` SET " + update + "=? WHERE id=?";
        PreparedStatement preparedStmt = con.prepareStatement(query);
        preparedStmt.setString(2, id);
        preparedStmt.setString(1, mony);
        preparedStmt.execute();
    }

    // Before making any money transfer , The balance must be verified, 
    // meaning that there is sufficient money to successfully complete the transfer -.-
    // This Can Be Done By The following Function -.-
    static public boolean Check_the_balance(Connection con, String id, String mony) throws SQLException {
        Statement stmt = con.createStatement();
        String query = " select * from user where id=" + id;
        ResultSet rs = stmt.executeQuery(query);
        int m = 0;
        if (rs.next()) {
            m = Integer.parseInt(rs.getString("mony"));
        }
        if (m >= Integer.parseInt(mony)) {
            return true;
        } else {
            return false;
        }
    }

    // Get any Information about The Client By his Id , From The Database
    // We can specify any information we want through the variable : (know) -.-
    static public Object know_anythink(Connection con, String id, String know) throws SQLException {
        Statement stmt = con.createStatement();
        String query = " select * from user where id=" + id;
        ResultSet rs = stmt.executeQuery(query);
        if (rs.next()) {
            return rs.getString(know);
        } else {
            return 0;
        }
    }

    // Get All The Remittances For Client , From The Database , By his Id -.-
    static public String read_file(Connection con, String id) throws SQLException {
        Statement stmt = con.createStatement();
        String query = " select Remittances from user where id=" + id;
        ResultSet rs = stmt.executeQuery(query);
        if (rs.next()) {
            System.out.println("***************" + id);
            return rs.getString("Remittances");
        } else {
            return null;
        }
    }
    
    // The Basic Work For This Function is To Convert The Manual Key To Private Key -.-
    PrivateKey get_private_key_server() throws NoSuchAlgorithmException, InvalidKeySpecException{
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(private_key));
        KeyFactory pri_key_s = KeyFactory.getInstance("RSA");
        PrivateKey privateKeyServer = pri_key_s.generatePrivate(spec);
        return privateKeyServer;
    }
    
    // The Basic Work For This Function is To Read The Encrypted Session Symmetric key ,
    // and Decrypt it Using Symmitric Decryption -.-
    String recive_session_key_en(Socket s) throws IOException, ClassNotFoundException, NoSuchAlgorithmException, InvalidKeySpecException, Exception{
        ObjectInputStream diss = new ObjectInputStream(s.getInputStream());
        byte [] key_session_enc = (byte[]) diss.readObject();                   
        byte[] key_session_dec = Encryption.PGP_decoding(key_session_enc, get_private_key_server());
        String key_session_de = new String(key_session_dec,UTF_8);
        return key_session_de;
    }
    
    // This Function Checking The transformation , and it depends On The Check_the_balance Function -.-
    boolean check_transformation(int id_user1 , int mony , int id_user2 ,String reason_transfer) throws SQLException, IOException{
        boolean boo = Check_the_balance(con, id_user1 + "", mony + "");
        if(boo == true){
            int mony_u1 = (Integer.parseInt((String) know_anythink(con, id_user1 + "", "mony"))) - mony;
            int mony_u2 = (Integer.parseInt((String) know_anythink(con, id_user2 + "", "mony"))) + mony;
            Update_anythink(con, id_user1 + "", mony_u1 + "", "mony");
            Update_anythink(con, id_user2 + "", mony_u2 + "", "mony");                            
            int Remittances = (Integer.parseInt((String) know_anythink(con, id_user2 + "", "num_Remittances"))) + 1;
            Update_anythink(con, id_user2 + "", Remittances + "", "num_Remittances");
            String t = new String(read_file(con, id_user2 + ""));
            t = t + "from " + id_user1 + " : " + mony+ "  / " + reason_transfer + " \n";
            Update_anythink(con, id_user2 + "", t, "Remittances");
            String tt = new String(read_file(con, id_user1 + ""));
            tt = tt + "me to " + id_user2 + " : " + mony + "  / " + reason_transfer+"\n";
            Update_anythink(con, id_user1 + "", tt, "Remittances");
        }
        return boo;
    }
}
//___________________________________________________________________________________________________________________