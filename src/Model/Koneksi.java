/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
/**
 *
 * @author Muhammad Rizal
 */
public class Koneksi {
    Connection koneksi;
    public Statement stm;

    public Koneksi(String database, String user, String password) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/" + database;
        koneksi = DriverManager.getConnection(url, user, password);
        stm = koneksi.createStatement();
    }
    
    public ResultSet getResult(String sql) throws SQLException {
        ResultSet rs = stm.executeQuery(sql);
        return rs;
    }

    public void execute(String sql) throws SQLException {
        this.stm.executeUpdate(sql);
    }
}
