/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import javax.swing.JTextArea;

/**
 *
 * @author Muhammad Rizal
 */
public class Seleksi extends javax.swing.JPanel {

    /**
     * Creates new form Seleksi
     */
    public Seleksi() {
        initComponents();
    }
    
    public JTextArea getTextSeleksi(){
        return txt_seleksi;
    }    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        txt_seleksi = new javax.swing.JTextArea();

        setPreferredSize(new java.awt.Dimension(520, 450));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txt_seleksi.setEditable(false);
        txt_seleksi.setColumns(20);
        txt_seleksi.setRows(5);
        jScrollPane1.setViewportView(txt_seleksi);

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 480, 410));
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea txt_seleksi;
    // End of variables declaration//GEN-END:variables
}
