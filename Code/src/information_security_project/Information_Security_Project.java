    package information_security_project;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.Scanner;
import org.json.*;

public class Information_Security_Project {

    static int id = 0;
    public static void main(String[] args) throws IOException, SQLException, JSONException {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost/banke","root",""); // Connection With The Database -.-
        know_id(con);
        ServerSocket ss=new ServerSocket(3333);                                 // Creating Server Socket -.-        
        while(true){
            Socket s;
            s=ss.accept(); 
            System.out.println("Connection");
            Server serv = new Server(s,con);
            serv.start();
        }
    }
    static public void know_id(Connection con ) throws SQLException{
        String query = " select id from user";
        PreparedStatement stmt = con.prepareStatement(query);                 
        ResultSet rs = stmt.executeQuery(query);
        while(rs.next()){
        id++;
        }
    }
    
}