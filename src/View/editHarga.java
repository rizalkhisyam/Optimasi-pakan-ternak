/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JTextField;

/**
 *
 * @author Muhammad Rizal
 */
public class editHarga extends javax.swing.JPanel {

    /**
     * Creates new form editHarga
     */
    
    public editHarga() {
        initComponents();
    }
    
    public void SaveMouseListener(MouseListener l){
        this.button_save.addMouseListener(l);
    }
    
    public void setButton_save(JButton button_save){
        this.button_save = button_save;
    }
    
    public JButton getButton_save(){
        return button_save;
    }
    
    public String getInputHarga1(){
        return input_kedelai.getText();
    }
    public void setHarga1(String harga1){
        this.input_kedelai.setText(harga1);
    }
    
    
    public String getInputHarga2(){
        return input_kelapa.getText();
    }
    public void setHarga2(String harga2){
        this.input_kelapa.setText(harga2);
    }
    
    public String getInputHarga3(){
        return input_kacang.getText();
    }
    public void setHarga3(String harga3){
        this.input_kacang.setText(harga3);
    }
    
    public String getInputHarga4(){
        return input_ikan.getText();
    }
    public void setHarga4(String harga4){
        this.input_ikan.setText(harga4);
    }
    
    public String getInputHarga5(){
        return input_udang.getText();
    }
    public void setHarga5(String harga5){
        this.input_udang.setText(harga5);
    }
    
    public JTextField getInputHargaKedelai(){
        return input_kedelai;
    }
    
    public void setButton_pakan(JTextField input_kedelai) {
        this.input_kedelai = input_kedelai;
    }
    
    public JTextField getInputHargaKelapa(){
        return input_kelapa;
    }
    
    public JTextField getInputKacang(){
        return input_kacang;
    }
    
    public JTextField getInputIkan(){
        return input_ikan;
    }
    
    public JTextField getInputUdang(){
        return input_udang;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        input_kedelai = new javax.swing.JTextField();
        input_kelapa = new javax.swing.JTextField();
        input_kacang = new javax.swing.JTextField();
        input_ikan = new javax.swing.JTextField();
        input_udang = new javax.swing.JTextField();
        button_save = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(290, 280));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        add(input_kedelai, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 30, 140, 30));
        add(input_kelapa, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 70, 140, 30));
        add(input_kacang, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 110, 140, 30));
        add(input_ikan, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 150, 140, 30));
        add(input_udang, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 190, 140, 30));

        button_save.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Button/button_OK.png"))); // NOI18N
        button_save.setBorderPainted(false);
        button_save.setContentAreaFilled(false);
        button_save.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        add(button_save, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 230, -1, 50));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Home/bg_edit_baru.png"))); // NOI18N
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 270, 300));
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton button_save;
    private javax.swing.JTextField input_ikan;
    private javax.swing.JTextField input_kacang;
    private javax.swing.JTextField input_kedelai;
    private javax.swing.JTextField input_kelapa;
    private javax.swing.JTextField input_udang;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
