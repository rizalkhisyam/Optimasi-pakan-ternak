/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;
import com.mysql.jdbc.Util;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;
import View.HomeView;
import java.text.DecimalFormat;
import java.util.Random;
/**
 *
 * @author Muhammad Rizal
 */
public class GenetikModel {
    
    Random r = new Random();
    DecimalFormat df = new DecimalFormat("#.######");
    
    public Koneksi koneksi;
    private int result = 0;
    private static double bahan[][] = new double[1][7];
    private static DefaultTableModel model;
    private HomeView home = new HomeView();
    
    private double me;
    private double protein;
    private double lemak;
    private double serat;
    private double kalsium;
    private double fosfor;
    
    private int popSize;
    private int iterasi;
    private double pc;
    private double pm;
    private int seleksi;
    private int konsumsi;
    private int ayam;
    
    private double hargaKedelai;
    private double hargaKelapa;
    private double hargaKacang;
    private double hargaIkan;
    private double hargaUdang;
    
    private double meKedelai;
    private double proKedelai;
    private double serKedelai;
    private double lemKedelai;
    private double kalKedelai;
    private double fosKedelai;
    
    private double meKelapa;
    private double proKelapa;
    private double serKelapa;
    private double lemKelapa;
    private double kalKelapa;
    private double fosKelapa;
    
    private double meKacang;
    private double proKacang;
    private double serKacang;
    private double lemKacang;
    private double kalKacang;
    private double fosKacang;
    
    private double meIkan;
    private double proIkan;
    private double serIkan;
    private double lemIkan;
    private double kalIkan;
    private double fosIkan;
    
    private double meUdang;
    private double proUdang;
    private double serUdang;
    private double lemUdang;
    private double kalUdang;
    private double fosUdang;
    
    public GenetikModel() throws SQLException{
        koneksi = new Koneksi("optimasi", "root", "");
    }
    
    public void getData(){
        try{
            String query = "SELECT * from data_pakan";
            ResultSet rs = koneksi.getResult(query);
            
            while(rs.next()){
                Object[] o = new Object[7];
                o[0] = rs.getString("nama_bahan");
                o[1] = String.valueOf(rs.getDouble("energi_metabolis"));
                o[2] = String.valueOf(rs.getDouble("protein"));
                o[3] = String.valueOf(rs.getDouble("lemak"));
                o[4] = String.valueOf(rs.getDouble("serat_kasar"));
                o[5] = String.valueOf(rs.getDouble("kalsium"));
                o[6] = String.valueOf(rs.getDouble("fosfor"));
                
                model.addRow(o);
            }
            rs.close();
        }
        catch (Exception e){
            System.out.println(e);
        }
    }
    
    public void getKebNut() throws SQLException{
        try{
            String query = "SELECT * from kebutuhan_nutrisi";
            ResultSet rs = koneksi.getResult(query);
            
            while(rs.next()){
                me = rs.getDouble("energi_metabolis");
                protein = rs.getDouble("protein");
                lemak = rs.getDouble("lemak");
                serat = rs.getDouble("serat");
                kalsium = rs.getDouble("kalsium");
                fosfor = rs.getDouble("fosfor");
                
                System.out.println("");
                System.out.println("==== Kebutuhan Nutrisi ====");
                System.out.println("ME : "+me);
                System.out.println("Protein : "+protein);
                System.out.println("Lemak : "+lemak);
                System.out.println("Serat : "+serat);
                System.out.println("Kalsium : "+kalsium);
                System.out.println("Fosfor : "+fosfor);
            }
            rs.close();
        }catch (Exception e){
            System.out.println("ini error bro : "+e);
        }

    }
    
    public void insertDataAlgo(int populasi, int iterasi, double probCross, double probMut, int seleksi, int konsumsi, int ayam)throws SQLException{
        try {
            String query = "INSERT INTO `data_algoritma` (`id_algo`, `populasi`, `iterasi`, "
                    + "`prob_crossover`, `prob_mutasi`,`seleksi`,`konsumsi`, `ayam`) VALUES "
                    + "(NULL, '" + populasi + "', '" + iterasi + "','" + probCross + "','" + probMut + "','" + seleksi + "','" + konsumsi + "','" + ayam + "');";
            System.out.println(query);
            koneksi.execute(query);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    public void updateHarga(double harga1){
        try {
            String query = "UPDATE `data_pakan` SET `harga`='" + harga1 + "' WHERE id_pakan = 2";
            System.out.println(query);
            koneksi.execute(query);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    public void updateHarga2(double harga2){
        try {
            String query = "UPDATE `data_pakan` SET `harga`='" + harga2 + "' WHERE id_pakan = 3";
            System.out.println(query);
            koneksi.execute(query);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    public void updateHarga3(double harga3){
        try {
            String query = "UPDATE `data_pakan` SET `harga`='" + harga3 + "' WHERE id_pakan = 4";
            System.out.println(query);
            koneksi.execute(query);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    public void updateHarga4(double harga4){
        try {
            String query = "UPDATE `data_pakan` SET `harga`='" + harga4 + "' WHERE id_pakan = 5";
            System.out.println(query);
            koneksi.execute(query);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    public void updateHarga5(double harga5){
        try {
            String query = "UPDATE `data_pakan` SET `harga`='" + harga5 + "' WHERE id_pakan = 6";
            System.out.println(query);
            koneksi.execute(query);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    public void getAlgo(){
        try{
            String query = "SELECT * from data_algoritma ORDER BY id_algo DESC limit 1";
            ResultSet rs = koneksi.getResult(query);
            
            while(rs.next()){
                popSize = rs.getInt("populasi");
                iterasi = rs.getInt("iterasi");
                pc = rs.getDouble("prob_crossover");
                pm = rs.getDouble("prob_mutasi");
                seleksi = rs.getInt("seleksi");
                konsumsi = rs.getInt("konsumsi");
                ayam = rs.getInt("ayam");
                
                System.out.println("");
                System.out.println("==== Data Algoritma ====");
                System.out.println("Ukuran Populasi : "+popSize);
                System.out.println("Banyak Generasi : "+iterasi);
                System.out.println("Probabilitas Crossover : "+pc);
                System.out.println("Probabilitas Mutasi : "+pm);
                System.out.println("Seleksi :"+seleksi);
                System.out.println("Konsumsi Pakan : "+konsumsi);
                System.out.println("Jumlah Ayam : "+ayam);
            }
            rs.close();
        }catch (Exception e){
            System.out.println("ini error bro : "+e);
        }
    }
    
    public void getKedelai(){
        try{
            String query = "SELECT * FROM `data_pakan` WHERE nama_bahan = 'Kedelai' ";
            ResultSet rs = koneksi.getResult(query);
            
            while(rs.next()){
                meKedelai = rs.getDouble("energi_metabolis");
                proKedelai = rs.getDouble("protein");
                lemKedelai = rs.getDouble("lemak");
                serKedelai = rs.getDouble("serat_kasar");
                kalKedelai = rs.getDouble("kalsium");
                fosKedelai = rs.getDouble("fosfor");
                hargaKedelai = rs.getDouble("harga");

                
            }
            rs.close();
        }catch (Exception e){
            System.out.println("ini error bro : "+e);
        }
    }
    
        public void getKelapa(){
        try{
            String query = "SELECT * FROM `data_pakan` WHERE nama_bahan = 'Bungkil kelapa' ";
            ResultSet rs = koneksi.getResult(query);
            
            while(rs.next()){
                meKelapa = rs.getDouble("energi_metabolis");
                proKelapa = rs.getDouble("protein");
                lemKelapa = rs.getDouble("lemak");
                serKelapa = rs.getDouble("serat_kasar");
                kalKelapa = rs.getDouble("kalsium");
                fosKelapa = rs.getDouble("fosfor");
                hargaKelapa = rs.getDouble("harga");
            }
            rs.close();
        }catch (Exception e){
            System.out.println("ini error bro : "+e);
        }
    }
        
    public void getKacang(){
        try{
            String query = "SELECT * FROM `data_pakan` WHERE nama_bahan = 'Bungkil kacang' ";
            ResultSet rs = koneksi.getResult(query);
            
            while(rs.next()){
                meKacang = rs.getDouble("energi_metabolis");
                proKacang = rs.getDouble("protein");
                lemKacang = rs.getDouble("lemak");
                serKacang = rs.getDouble("serat_kasar");
                kalKacang = rs.getDouble("kalsium");
                fosKacang = rs.getDouble("fosfor");
                hargaKacang = rs.getDouble("harga");

            }
            rs.close();
        }catch (Exception e){
            System.out.println("ini error bro : "+e);
        }
    }
    
        public void getIkan(){
        try{
            String query = "SELECT * FROM `data_pakan` WHERE nama_bahan = 'Tepung ikan' ";
            ResultSet rs = koneksi.getResult(query);
            
            while(rs.next()){
                meIkan = rs.getDouble("energi_metabolis");
                proIkan = rs.getDouble("protein");
                lemIkan = rs.getDouble("lemak");
                serIkan = rs.getDouble("serat_kasar");
                kalIkan = rs.getDouble("kalsium");
                fosIkan = rs.getDouble("fosfor");
                hargaIkan = rs.getDouble("harga");

            }
            rs.close();
        }catch (Exception e){
            System.out.println("ini error bro : "+e);
        }
    }
        
        public void getUdang(){
        try{
            String query = "SELECT * FROM `data_pakan` WHERE nama_bahan = 'Limbah udang' ";
            ResultSet rs = koneksi.getResult(query);
            
            while(rs.next()){
                meUdang = rs.getDouble("energi_metabolis");
                proUdang = rs.getDouble("protein");
                lemUdang = rs.getDouble("lemak");
                serUdang = rs.getDouble("serat_kasar");
                kalUdang = rs.getDouble("kalsium");
                fosUdang = rs.getDouble("fosfor");
                hargaUdang = rs.getDouble("harga");

            }
            rs.close();
        }catch (Exception e){
            System.out.println("ini error bro : "+e);
        }
    }
    
//        ------------------------- get Kebutuhan Nutrisi
    public Double getMe(){
        return me;
    }
    
    public Double getProtein(){
        return protein;
    }
    
    public Double getLemak(){
        return lemak;
    }
    
    public Double getSerat(){
        return serat;
    }
    
    public Double getKalsium(){
        return kalsium;
    }
    
    public Double getFosfor(){
        return fosfor;
    }
//    -------------------------------get harga bahan
    public Double getHarga1(){
        return hargaKedelai;
    }
    
    public Double getHarga2(){
        return hargaKelapa;
    }
    
    public Double getHarga3(){
        return hargaKacang;
    }
    
    public Double getHarga4(){
        return hargaIkan;
    }
    
    public Double getHarga5(){
        return hargaUdang;
    }
    

    
//    ------------------------------get Kandungan Pakan
    
    public Double getMe1(){
        return meKedelai;
    }
    public Double getPro1(){
        return proKedelai;
    }
    public Double getLem1(){
        return lemKedelai;
    }
    public Double getSer1(){
        return serKedelai;
    }
    public Double getkal1(){
        return kalKedelai;
    }
    public Double getFos1(){
        return fosKedelai;
    }
//    ------
    public Double getMe2(){
        return meKelapa;
    }
    public Double getPro2(){
        return proKelapa;
    }
    public Double getLem2(){
        return lemKelapa;
    }
    public Double getSer2(){
        return serKelapa;
    }
    public Double getkal2(){
        return kalKelapa;
    }
    public Double getFos2(){
        return fosKelapa;
    }
//    ------
    public Double getMe3(){
        return meKacang;
    }
    public Double getPro3(){
        return proKacang;
    }
    public Double getLem3(){
        return lemKacang;
    }
    public Double getSer3(){
        return serKacang;
    }
    public Double getkal3(){
        return kalKacang;
    }
    public Double getFos3(){
        return fosKacang;
    }
//    ------
    public Double getMe4(){
        return meIkan;
    }
    public Double getPro4(){
        return proIkan;
    }
    public Double getLem4(){
        return lemIkan;
    }
    public Double getSer4(){
        return serIkan;
    }
    public Double getkal4(){
        return kalIkan;
    }
    public Double getFos4(){
        return fosIkan;
    }
//    ------
    public Double getMe5(){
        return meUdang;
    }
    public Double getPro5(){
        return proUdang;
    }
    public Double getLem5(){
        return lemUdang;
    }
    public Double getSer5(){
        return serUdang;
    }
    public Double getkal5(){
        return kalUdang;
    }
    public Double getFos5(){
        return fosUdang;
    }
//    ------
    
    public int getPopSize(){
        return popSize;
    }
    public int getIterasi(){
        return iterasi;
    }
    public int getKonsumsi(){
        return konsumsi;
    }
    public double getProbCross(){
        return pc;
    }
    public double getProbMut(){
        return pm;
    }
    public int getSeleksi(){
        return seleksi;
    }
    public int getAyam(){
        return ayam;
    }
    
    public Double getBahan01(){
        return bahan[0][1];
    }
    
}
