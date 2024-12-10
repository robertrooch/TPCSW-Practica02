package org.uv.tpcsw.practica02;

import io.github.cdimascio.dotenv.Dotenv;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Statement;

public class ConexionBD {

    private static ConexionBD cx = null;

    public static ConexionBD getInstance() {
        if (cx == null) 
            cx = new ConexionBD();
        return cx;
    }

    private Connection con = null;

    public ConexionBD() {
        try {
            Dotenv dotenv = Dotenv.load();
            String url = "jdbc:postgresql://localhost:5432/ejemplo";
            
            String user = dotenv.get("DB_USERNAME");
            String password = dotenv.get("DB_PASSWORD");
            con = DriverManager.getConnection(url, user, password);
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //Intermediario entre transacciones
    public ResultSet select (String sql){
        try {
            Statement stm = con.createStatement();
            ResultSet reg=stm.executeQuery(sql);
            return reg;
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public boolean execute(TransaccionDB transaction){
        return transaction.execute(con);
    }

    public boolean execute(String sql) {
        Statement stm = null;
        try {
            stm = con.createStatement();
            return stm.execute(sql);
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } 
        finally {
            if (stm != null) {
                try {
                    stm.close();
                }catch (SQLException ex){
                    Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE,"Error",ex);
                }
            }
        }
    }

    public Connection getCon() {
        return con;
    }

    
}
