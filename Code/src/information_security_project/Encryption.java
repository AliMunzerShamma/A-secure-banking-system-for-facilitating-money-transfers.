package information_security_project;

import static java.nio.charset.StandardCharsets.UTF_8;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Encryption {
    
    // Generate Symmetric Key In Type Block Ciphers (AES) -.-
    static SecretKey keygenerator() throws NoSuchAlgorithmException{
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");            // Define A Key Generator AES Type -.-
        SecureRandom secureRandom = new SecureRandom();                         // Define Random Secure Value -.-
        int keyBitSize = 128;                                                   // Key Size  = 128 bit -.-
        keyGenerator.init(keyBitSize, secureRandom);                            // initialize The Key -.-
        SecretKey secretKey = keyGenerator.generateKey();                       // and Generate The Key -.-
        return secretKey;
    }
    
    // Encrypt Using Symmetric Cryptography -.-
    static byte[] Symmetric_encryption(String value , String initVector , String key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException{
        
        IvParameterSpec iv = new IvParameterSpec(initVector.getBytes());        // Define The Initialization Vector -.-
        SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(), "AES");      // Set The Cipher Symmetric Type = AES For The Encrypt Key -.-
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");             // Set The Cipher Symmetric Type = AES and Mode = CBC For The Encrypt Function -.-
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);                         // Initialize The Cipher With ENCRYPT MODE -.-
        byte[] encrypted = cipher.doFinal(value.getBytes());                    // and The Value is Encrypted Using This Cipher -.-
        return encrypted;
    }
    
    // Decrypt Using Symmetric Cryptography -.-
    static byte[] Symmetric_decoding(byte[] value , String initVector ,String key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException{
        
        IvParameterSpec iv = new IvParameterSpec(initVector.getBytes());        // Define The Initialization Vector -.- 
        SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(), "AES");      // Set The Cipher Symmetric Type = AES For The Decrypt Key -.-
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");             // Set The Cipher Symmetric Type = AES and Mode = CBC For The Decrypt Function -.- 
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);                         // Initialize The Cipher With DECRYPT MODE -.-
        byte[] decrypt = cipher.doFinal(value);                                 // and The Value is Decrypted Using This Cipher -.-
        return decrypt;
    }
    
    // Encrypt Using PGP Hybrid Encryption  -.-
    public static byte[] PGP_encryption(String plainText, PublicKey publicKey) throws Exception {
        Cipher encryptCipher = Cipher.getInstance("RSA");                       // Set The Cipher PGP Type = RSA For The Encrypt Function -.-
        encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey);                     // Initialize The Cipher With ENCRYPT MODE -.-
        byte[] cipherText = encryptCipher.doFinal(plainText.getBytes(UTF_8));   // and The plainText is Encrypted Using This Cipher -.-
        return cipherText ;
    }
    
    // Decrypt Using PGP Hybrid Encryption  -.-
    public static byte[] PGP_decoding(byte[] cipherText, PrivateKey privateKey) throws Exception {
       // byte[] bytes = Base64.getDecoder().decode(cipherText);

        Cipher decriptCipher = Cipher.getInstance("RSA");                       // Set The Cipher PGP Type = RSA For The Decrypt Function -.-
        decriptCipher.init(Cipher.DECRYPT_MODE, privateKey);                    // Initialize The Cipher With DECRYPT MODE -.-
        cipherText = decriptCipher.doFinal(cipherText);                         // and The cipherText is Decrypted Using This Cipher -.-
        return cipherText;
    }
}