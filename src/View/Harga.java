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
public class Harga extends javax.swing.JPanel {

    /**
     * Creates new form Harga
     */
    public Harga() {
        initComponents();
    }
    
    public JTextArea getArea_harga(){
        return text_harga;
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
        text_harga = new javax.swing.JTextArea();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        text_harga.setColumns(20);
        text_harga.setRows(5);
        jScrollPane1.setViewportView(text_harga);

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 480, 410));
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea text_harga;
    // End of variables declaration//GEN-END:variables
}
