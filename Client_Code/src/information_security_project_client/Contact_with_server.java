package information_security_project_client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.charset.Charset;
import static java.nio.charset.StandardCharsets.UTF_8;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import static java.security.KeyRep.Type.PUBLIC;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.swing.JOptionPane;

// The Basic Work For This class Is To insurance The process of Safe communication between Server and This Client -.-
//___________________________________________________________________________________________________________________
public class Contact_with_server {
    Socket s;                                                                   // Communication will be through use Socket -.-
    
    byte[] emojiBytes = new byte[]{(byte)0xF0, (byte)0x9F, (byte)0x98, (byte)0x93};// We Used emoji To represent The Man In The Middle -.-
    String emojiAsString = new String(emojiBytes, Charset.forName("UTF-8"));    //Convert emoji From Bytes To String -.-
    
    //This is the Value Of Server Public Key Which we manually generated and copied To here -.-
    static String public_key_server = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA0I/4pqvmCwgeh2MBCKxD7jpmJiUa4/JzNPcRHjfWSDy7Exmr3KR1tK/KpaEs3MgWCyM45yUlny8XdsiuJELAtia8HHXjL3z5o1csHe3SL5o3x+Cko9ET3XPXFOF/0p+ikbVkvhMSYPr0m3KRI6yPMHG12Ns8kSr3MGwRKmb0wl7ZsxNEoS5/d9tlT2afJva/8XpzgQZO0fGI6iyz9Sy0mn4wX/S2PhLDuyJkRv0jejS50+QvWs19VHCdHVw/5xnTiVPr4TFlfdZs26OqsrR8zsCtvTrn0q6RYabUCJ2TCeTery2VBDgQE9LG8/1RZRFCrHP2oJLIQIx6AMFS1IbumwIDAQAB";
   
    // here We defind Our choices Which indicates the operations that the user can perform -.-
    int register = 1 ,                                                          // 1 means the operation is registeration -.-
        log_in = 2 ,                                                            // 2 means the operation is log in -.-
        show_bal = 5 ,                                                          // 5 means the operation is Show money values deposited under this user's account -.-
        mony ,                                                                  // The Value Of money -.-
        recharge = 3 ,                                                          // 3 means the operation is Put more money into this account -.-
        transformation = 4 ,                                                    // 4 means the operation is Sending a money order from this account to another account -.-
        deposits = 6;                                                           // 6 means the operation is Show deposits or transfers made by this user from this account -.-
    
    static int id;                                                              // id Of this user -.-
    
    int type_encryption;                                                        // The type of encryption process -.-
    
    // The constructor -.-
    Contact_with_server(int type_encryption) throws IOException{
        this.s = new Socket("192.168.43.88",3333);                              // listen The Socket To IP Of Server -.-
        this.type_encryption = type_encryption;
    }
    
    // Register Function -.-
    public void Register() throws IOException, Exception{
        {
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());   // To Write In The Socket s -.- 
            DataInputStream dis = new DataInputStream(s.getInputStream());      // To Read From The Socket s -.-
            dos.writeInt(register);                                             // Write register choice To Socket, This Mean The Number (1) -.-  
            this.id = dis.readInt();                                            // Get The Value Of id From Socket -.-
        }
        PublicKey public_key = Encryption.generateKeyPair(id).getPublic();      // generate a Public Key For this new user who made the Register operation -.-
        String pk = Base64.getEncoder().encodeToString(public_key.getEncoded());// Convert The generated Public Key To String -.-
        System.out.println(pk);
        ObjectOutputStream dos = new ObjectOutputStream(s.getOutputStream());
        dos.writeObject(pk);                                                    //Write Public Key Value To Socket -.-            
    }
    
    // Log In Function -.-
    public void Log_in(int id) throws IOException{
        DataOutputStream dos = new DataOutputStream(s.getOutputStream());       // To Write In The Socket s -.-
        DataInputStream dis = new DataInputStream(s.getInputStream());          // To Read From The Socket s -.-
        dos.writeInt(log_in);                                                   // Write LogIn choice To Socket , This Mean The Number (2) -.-
        dos.writeInt(id);                                                       // and Write The Id Of The User -.-      
        System.out.println(id + "***************");
        this.id = dis.readInt();                                                // Read The Entered id From The Socket -.-
        System.out.println(this.id + "   asd***************");
    }
    
    // Show money values deposited under this user's account -.-
    public int show_balance() throws IOException, ClassNotFoundException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException, Exception{
        
        {
        DataOutputStream dos = new DataOutputStream(s.getOutputStream());       // To Write In The Socket s -.-
        DataInputStream dis = new DataInputStream(s.getInputStream());          // To Read From The Socket s -.-
        dos.writeInt(show_bal);                                                 // Write Show balance Choice , This Mean The Number (5) -.-
        dos.writeInt(id);                                                       // and Write The Id Of The User -.- 
        dos.writeInt(type_encryption);                                          // and here we Write The Type Of Encryption For This Process -.-
        }        
        System.out.println(id + "***************");
        if(type_encryption == 1){                                               // Number 1 Means That The Type Of Encryption Is Symmetric Encryption -.-
            ObjectInputStream dis = new ObjectInputStream(s.getInputStream());
            byte[] iv = (byte[]) dis.readObject();                              // Read The initializing Vectore For The Symmetric Encryption , CBC Mode -.-
            String iv_key = Base64.getEncoder().encodeToString(iv);             // and Convert The IV to String -.-
            iv_key = iv_key.substring(0, 16);                                   // Cut First 16 bit Of the IV -.-
            byte[] mony_en = (byte[]) dis.readObject();                         // and Read The Encrypted Value Of Money -.-
            JOptionPane.showMessageDialog(null, "man in the middle " + emojiAsString + emojiAsString + "\n" + new String (mony_en,UTF_8));
            this.mony = Integer.parseInt(new String (Encryption.Symmetric_decoding(mony_en, iv_key,Encryption.key)));//***************************
        }
        else if(type_encryption == 2){                                          // Number 2 Means That The Type Of Encryption Is PGP Encryption -.-
            String session_key = send_session_key_en(s);                        // The key to this session -.-
            ObjectInputStream dis = new ObjectInputStream(s.getInputStream());
            byte[] mony_en = (byte[]) dis.readObject();                         // Read The Encrypted Value Of Money -.-
            System.out.println(session_key);
            JOptionPane.showMessageDialog(null, "man in the middle " + emojiAsString + emojiAsString + "\n" + new String (mony_en,UTF_8));
            this.mony = Integer.parseInt(new String(Encryption.Symmetric_decoding(mony_en, "                " , session_key)));
        }
        
        return this.mony;
    }
    
    // Recharge Function -.-
    public void Recharge(int mony) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, Exception{
        {
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());   // To Write In The Socket s -.-
            DataInputStream dis = new DataInputStream(s.getInputStream());      // To Read From The Socket s -.-
            dos.writeInt(recharge);                                             // Write recharge Choice To The Socket, This Mean The Number (3) -.-
            dos.writeInt(id);                                                   // and Write The Id Of The User -.-
            dos.writeInt(type_encryption);                                      // and Write The Type of The Encryption To The Socket -.-
       //     dos.writeUTF(Encryption.initVector);
        }
        if(type_encryption == 1){                                               // Number 1 Means That The Type Of Encryption Is Symmetric Encryption -.-
            
            SecretKey key1 =  Encryption.keygenerator();                        // generate a Key For The Symmetric Encryption -.-
            String iv = Base64.getEncoder().encodeToString(key1.getEncoded());  // generate initializing Vectore For The Symmetric Encryption , CBC Mode -.-
            String iv_16 = iv.substring(0, 16);                                 // Cut First 16 bit Of the IV -.-
            byte[] mony_en;
            {
                mony_en = Encryption.Symmetric_encryption(mony+"" , iv_16 , Encryption.key); // Encrypt The Value Of Deposited money To This Account -.-
                ObjectOutputStream dos = new ObjectOutputStream(s.getOutputStream()); // To Write In The Socket s -.-
                dos.writeObject(key1.getEncoded());                             // Write The Symmetric Key To The Socket -.-
                dos.writeObject(mony_en);                                       // Write The Encrypted Value Of Money -.-
            }
            ObjectInputStream dis = new ObjectInputStream(s.getInputStream());  // To Read From The Socket s -.-
            byte[] result = (byte[]) dis.readObject();                          // Read The Acknowledgment From The Socket , Wich Is Sends From The Server -.-
            byte[] result_de = Encryption.Symmetric_decoding(result, iv_16, Encryption.key); // and Decrypt The result By The Symmetric Key and IV -.-
            JOptionPane.showMessageDialog(null, "man in the middle " + emojiAsString + emojiAsString + "\n" + new String (key1.getEncoded(),UTF_8) + new String (mony_en,UTF_8) + new String (result,UTF_8));
            JOptionPane.showMessageDialog(null, new String (result_de));
        }
        else if(type_encryption == 2){                                          // Number 2 Means That The Type Of Encryption Is PGP Encryption -.-
            String session_key = send_session_key_en(s);                        // The key to this session -.-
            byte[] mony_en = Encryption.Symmetric_encryption(mony+"" ,"                ", session_key); // Encrypt The Value Of Money By The Session Key (Symmetric Key) -.-
            ObjectOutputStream dos = new ObjectOutputStream(s.getOutputStream()); // To Write In The Socket s -.-
            dos.writeObject(mony_en);                                           // Write The Encrypted Value Of Money -.-
            ObjectInputStream dis = new ObjectInputStream(s.getInputStream());  // To Read From The Socket s -.-
            byte[] result = (byte[]) dis.readObject();                          // Read The Acknowledgment From The Socket , Wich Is Sends From The Server -.-
            byte[] result_de = Encryption.Symmetric_decoding(result, "                ", session_key); // and Decrypt The result By The Symmetric Key and IV -.-
            JOptionPane.showMessageDialog(null, "man in the middle " + emojiAsString + emojiAsString + "\n"  + new String (mony_en,UTF_8) + new String (result,UTF_8));
            JOptionPane.showMessageDialog(null, new String (result_de));
        }
    }
    
    // Transformation Function -.-
    public void Transformation(int id_from , int mony , int id_to ,String reason_transfer) throws IOException, ClassNotFoundException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, Exception{
        {
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());   // To Write In The Socket s -.-       
            dos.writeInt(transformation);                                       // Write Transformation Choice To The Socket, This Mean The Number (4) -.-
            dos.writeInt(id_from);                                              // Write The sender id (Me) -.-
            dos.writeInt(type_encryption);                                      // and here we Write The Type Of Encryption For This Process -.- 
        }
        if(type_encryption == 1){                                               // Number 1 Means That The Type Of Encryption Is Symmetric Encryption -.-
            SecretKey key1 =  Encryption.keygenerator();                        // generate a Key For The Symmetric Encryption -.-
            String iv = Base64.getEncoder().encodeToString(key1.getEncoded());  // generate initializing Vectore For The Symmetric Encryption , CBC Mode -.-
            String iv_16 = iv.substring(0, 16);                                 // Cut First 16 bit Of the IV -.-
            byte[] reason_en , mony_en , id_to_en;
            {
                ObjectOutputStream dos = new ObjectOutputStream(s.getOutputStream()); // To Write In The Socket s -.-                 
                dos.writeObject(key1.getEncoded());                             // Write The Symmetric Key To The Socket -.-
                mony_en = Encryption.Symmetric_encryption(mony+"", iv_16 , Encryption.key); // Encrypt The Value Of Deposited money To This destination Account -.-
                id_to_en = Encryption.Symmetric_encryption(id_to+"", iv_16,Encryption.key); // Encrypt The The id Of The destination User , Who I want to send a money order to him -.-
                reason_en = Encryption.Symmetric_encryption(reason_transfer, iv_16,Encryption.key); // Encrypt The reason for the transfer Process -.-
                dos.writeObject(mony_en);                                       // Write The Encrypted Value Of Money -.-
                dos.writeObject(id_to_en);                                      // Write The Encrypted id Of destination User -.-
                dos.writeObject(reason_en);                                     // Write The Encrypted reason for the transfer Process -.-
            }
            ObjectInputStream dis = new ObjectInputStream(s.getInputStream());  // To Read From The Socket s -.-
            byte[] result = (byte[]) dis.readObject();                          // Read The Acknowledgment From The Socket , Wich Is Sends From The Server -.-    
            byte[] result_de = Encryption.Symmetric_decoding(result, iv_16, Encryption.key); // and Decrypt The result By The Symmetric Key and IV -.-            
            JOptionPane.showMessageDialog(null, "man in the middle " + emojiAsString + emojiAsString + "\n" + new String (key1.getEncoded(),UTF_8) + new String (mony_en,UTF_8) + new String (id_to_en,UTF_8) + new String (reason_en,UTF_8) + new String (result,UTF_8));
            JOptionPane.showMessageDialog(null, new String (result_de));
        }
        else if(type_encryption == 2){                                          // Number 2 Means That The Type Of Encryption Is PGP Encryption -.-
            String session_key = send_session_key_en(s);                        // The key to this session -.-
            byte[] reason_en , mony_en , id_to_en;
            {                
                mony_en = Encryption.Symmetric_encryption(mony+"", "                " , session_key); // Encrypt The Value Of Money By The Session Key (Symmetric Key) -.-
                id_to_en = Encryption.Symmetric_encryption(id_to+"", "                ",session_key); // Encrypt The id Of The destination User By The Session Key (Symmetric Key) -.-
                reason_en = Encryption.Symmetric_encryption(reason_transfer, "                ",session_key); // Encrypt The reason for the transfer Process By The Session Key (Symmetric Key) -.-
                ObjectOutputStream dos = new ObjectOutputStream(s.getOutputStream()); // To Write In The Socket s -.-
                dos.writeObject(mony_en);                                       // Write The Encrypted Value Of Money To The Socket -.-
                dos.writeObject(id_to_en);                                      // Write The Encrypted id Of The destination User To The Socket -.-
                dos.writeObject(reason_en);                                     // Write The Encrypted reason for the transfer Process To The Socket -.-
            }
            ObjectInputStream dis = new ObjectInputStream(s.getInputStream());  // To Read From The Socket s -.-
            byte[] result = (byte[]) dis.readObject();                          // Read The Acknowledgment From The Socket , Wich Is Sends From The Server -.-
            byte[] result_de = Encryption.Symmetric_decoding(result, "                ", session_key); // and Decrypt The result By The Symmetric Key and IV -.-
            JOptionPane.showMessageDialog(null, "man in the middle " + emojiAsString + emojiAsString + "\n"  + new String (mony_en,UTF_8) + new String (id_to_en,UTF_8) + new String (reason_en,UTF_8) + new String (result,UTF_8));
            JOptionPane.showMessageDialog(null, new String (result_de));            
        }
    }
    
    // Deposits Function -.-
    public String Deposits() throws IOException, ClassNotFoundException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, Exception{
        {
        DataOutputStream dos = new DataOutputStream(s.getOutputStream());       // To Write In The Socket s -.-       
        dos.writeInt(deposits);                                                 // Write deposits Choice To The Socket, This Mean The Number (6) -.-
        dos.writeInt(id);                                                       // and Write The Id Of The User -.-
        dos.writeInt(type_encryption);                                          // and here we Write The Type Of Encryption For This Process -.-
        }
        if(type_encryption == 1){                                               // Number 1 Means That The Type Of Encryption Is Symmetric Encryption -.-
            ObjectInputStream dis = new ObjectInputStream(s.getInputStream());  // To Read From The Socket s -.-
            byte[] iv = (byte[]) dis.readObject();                              // Read The initializing Vectore For The Symmetric Encryption , CBC Mode -.-
            String iv_key = Base64.getEncoder().encodeToString(iv);             // and Convert The IV to String -.-
            iv_key = iv_key.substring(0, 16);                                   // Cut First 16 bit Of the IV -.-
            byte[] des_en = (byte[]) dis.readObject();                          // Read The Encrypted id Of The destination User -.-
            JOptionPane.showMessageDialog(null, "man in the middle " + emojiAsString + emojiAsString + "\n" + new String (iv,UTF_8) + new String (des_en,UTF_8) );
            return new String (Encryption.Symmetric_decoding(des_en, iv_key,Encryption.key));
        }
        else if(type_encryption == 2){                                          // Number 2 Means That The Type Of Encryption Is PGP Encryption -.-
            String session_key = send_session_key_en(s);                        // The key to this session -.-
            ObjectInputStream dis = new ObjectInputStream(s.getInputStream());  // To Read From The Socket s -.-
            byte[] des_en = (byte[]) dis.readObject();                          // Read The Encrypted id Of The destination User -.-
            JOptionPane.showMessageDialog(null, "man in the middle " + emojiAsString + emojiAsString  + new String (des_en,UTF_8) );
            return new String (Encryption.Symmetric_decoding(des_en, "                ",session_key));
        }
        return "";
    }
    
    // The Basic Work For This Function Is To Get The Public Key For The Server -.-
    PublicKey get_public_key_server() throws NoSuchAlgorithmException, InvalidKeySpecException{
        X509EncodedKeySpec spec = new X509EncodedKeySpec(Base64.getDecoder().decode(public_key_server));
        KeyFactory pub_key_s = KeyFactory.getInstance("RSA");
        PublicKey pubKeyServer = pub_key_s.generatePublic(spec);
        return pubKeyServer;
    }
    
    // The Basic Work For This Function Is To Generate and Send The Session Symmetric Key , This Is For The PGP Encryption Choice 2  -.-
    String send_session_key_en(Socket s) throws NoSuchAlgorithmException, InvalidKeySpecException, Exception{
        SecretKey session =  Encryption.keygenerator();
        String session_key = Base64.getEncoder().encodeToString(session.getEncoded());            
        PublicKey pubKeyServer = get_public_key_server();            
        byte[] key_session_enc = Encryption.PGP_encryption(session_key, pubKeyServer);
        {
            ObjectOutputStream dos = new ObjectOutputStream(s.getOutputStream());
            dos.writeObject(key_session_enc);
        }
        return session_key;
    }
}
//___________________________________________________________________________________________________________________
