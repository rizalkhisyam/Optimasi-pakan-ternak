/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
/**
 *
 * @author Muhammad Rizal
 */
public class HomeView extends javax.swing.JFrame {

    /**
     * Creates new form HomeView
     */
    public HomeView() {
        initComponents();
        this.setLocationRelativeTo(this);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Button_Optimasi = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        inputPopulasi = new javax.swing.JTextField();
        inputGenerasi = new javax.swing.JTextField();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        inputAlgoritma = new javax.swing.JLabel();
        header = new javax.swing.JLabel();
        Button_Pakan = new javax.swing.JButton();
        Button_Algo = new javax.swing.JButton();
        Button_Hasil = new javax.swing.JButton();
        background = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setMaximumSize(new java.awt.Dimension(1100, 720));
        setMinimumSize(new java.awt.Dimension(1100, 720));
        setSize(new java.awt.Dimension(1100, 720));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Button_Optimasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Button/btn-optimasi.png"))); // NOI18N
        Button_Optimasi.setBorder(null);
        Button_Optimasi.setBorderPainted(false);
        Button_Optimasi.setContentAreaFilled(false);
        Button_Optimasi.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        getContentPane().add(Button_Optimasi, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 550, 160, -1));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "No", "Nama Pakan", "Protein", "Kalsium", "Fosfor", "Lemak", "Serat"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 160, 690, 190));

        inputPopulasi.setBorder(null);
        inputPopulasi.setOpaque(false);
        inputPopulasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inputPopulasiActionPerformed(evt);
            }
        });
        getContentPane().add(inputPopulasi, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 140, 80, 30));

        inputGenerasi.setBorder(null);
        inputGenerasi.setOpaque(false);
        getContentPane().add(inputGenerasi, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 200, 80, 30));

        jTextField1.setBorder(null);
        jTextField1.setOpaque(false);
        getContentPane().add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 260, 80, 20));

        jTextField2.setBorder(null);
        jTextField2.setOpaque(false);
        getContentPane().add(jTextField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 310, 80, 30));

        jTextField3.setBorder(null);
        jTextField3.setOpaque(false);
        getContentPane().add(jTextField3, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 420, 60, 30));

        jTextField4.setBorder(null);
        jTextField4.setOpaque(false);
        getContentPane().add(jTextField4, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 480, 60, 20));

        inputAlgoritma.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Home/menu-input.png"))); // NOI18N
        getContentPane().add(inputAlgoritma, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 70, 330, 600));

        header.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Home/header.png"))); // NOI18N
        getContentPane().add(header, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1100, 110));

        Button_Pakan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Button/btn-feed.png"))); // NOI18N
        Button_Pakan.setBorderPainted(false);
        Button_Pakan.setContentAreaFilled(false);
        Button_Pakan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        getContentPane().add(Button_Pakan, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 110, 200, -1));

        Button_Algo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Button/btn-calculate.png"))); // NOI18N
        Button_Algo.setBorderPainted(false);
        Button_Algo.setContentAreaFilled(false);
        Button_Algo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        getContentPane().add(Button_Algo, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 110, -1, -1));

        Button_Hasil.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Button/btn-result.png"))); // NOI18N
        Button_Hasil.setBorderPainted(false);
        Button_Hasil.setContentAreaFilled(false);
        Button_Hasil.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        getContentPane().add(Button_Hasil, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 110, -1, -1));

        background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Home/bg-home.png"))); // NOI18N
        getContentPane().add(background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1100, 720));

        pack();
    }// </editor-fold>//GEN-END:initComponents
    //button menu Optimasi=========================
    public void OptimasiMouseListener(MouseListener l){
        this.Button_Optimasi.addMouseListener(l);
    }
    
    public JButton getButton_Optimasi() {
        return Button_Optimasi;
    }

    public void setButton_Optimasi(JButton Button_Optimasi) {
        this.Button_Optimasi = Button_Optimasi;
    }
    
    //button pakan============================
    public void PakanMouseListener(MouseListener l){
        this.Button_Pakan.addMouseListener(l);
    }
    
    public JButton getButton_Pakan() {
        return Button_Pakan;
    }

    public void setButton_pakan(JButton Button_Optimasi) {
        this.Button_Pakan = Button_Pakan;
    }
    
    //button algoritma=============================
    public void AlgoMouseListener(MouseListener l){
        this.Button_Algo.addMouseListener(l);
    }
    
    public JButton getButton_Algo() {
        return Button_Algo;
    }

    public void setButton_Algo(JButton Button_Optimasi) {
        this.Button_Algo = Button_Algo;
    }
    
    //Button hasil optimasi===========================
    public void HasilMouseListener(MouseListener l){
        this.Button_Hasil.addMouseListener(l);
    }
    
    public JButton getButton_Hasil() {
        return Button_Hasil;
    }

    public void setButton_Hasil(JButton Button_Optimasi) {
        this.Button_Hasil = Button_Hasil;
    }
    private void inputPopulasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inputPopulasiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_inputPopulasiActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(HomeView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HomeView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HomeView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HomeView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new HomeView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Button_Algo;
    private javax.swing.JButton Button_Hasil;
    private javax.swing.JButton Button_Optimasi;
    private javax.swing.JButton Button_Pakan;
    private javax.swing.JLabel background;
    private javax.swing.JLabel header;
    private javax.swing.JLabel inputAlgoritma;
    private javax.swing.JTextField inputGenerasi;
    private javax.swing.JTextField inputPopulasi;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    // End of variables declaration//GEN-END:variables
}
