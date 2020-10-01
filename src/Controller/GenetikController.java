/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.GenetikModel;
import View.HomeView;
import View.PanelOptimasi;
import View.PanelPakan;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Muhammad Rizal
 */
public class GenetikController {
    Random r = new Random();
    DecimalFormat df = new DecimalFormat("#.######");
    
    private HomeView home = new HomeView();
    static GridBagLayout layout = new GridBagLayout();
    static PanelPakan feed = new PanelPakan();
    static PanelOptimasi result = new PanelOptimasi();
    
    private GenetikModel genModel;
    private DefaultTableModel model;
    
    private static int popSize;
    private static int individu = 5;
    private double popAwal[][];
    
    public GenetikController(HomeView home, GenetikModel genModel)throws SQLException{
    this.home = home;
    this.genModel = genModel;
    
    
    genModel.getData();
    
    genModel.getKebNut();
    genModel.getAlgo();
    
    genModel.getKedelai();
    genModel.getKelapa();
    genModel.getKacang();
    genModel.getIkan();
    genModel.getUdang();
//    String s = Double.toString(genModel.getMe());
//    home.getData().setText(s);
    home.setVisible(true);
    home.getDynamicPanel().setLayout(layout);
    home.getDynamicPanel().add(feed);
    home.getDynamicPanel().add(result);
    home.getDynamicPanel().setVisible(false);
    
    home.OptimasiMouseListener(new OptimasiMouseListener());
    home.AlgoMouseListener(new AlgoMouseListener());
    home.HasilMouseListener(new HasilMouseListener());
    home.PakanMouseListener(new PakanMouseListener());
    }
    
    public void individu(){
        System.out.println("ini individu");
        popSize = genModel.getPopSize();
        System.out.println(popSize);
        try{
            for (int i = 0; i < popSize; i++) {
            System.out.println("== Kromosm P"+(i+1)+" ==");
            for (int j = 0; j < 5; j++) {
            int random = r.nextInt(10)+1;
            this.popAwal[i][j] = 1;
                System.out.println(random);
//                System.out.println("Kromosom :"+(j+1)+": "+popAwal[i][j]); 
            }
        }
        }catch (Exception e){
            System.out.println(e);
        }
        
    }
    
    private void setIcon(JButton button, String resource) {
        button.setIcon(new ImageIcon(getClass().getResource(resource)));
    }

    private class PakanMouseListener implements MouseListener {

        public PakanMouseListener() {
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            home.getDynamicPanel().setVisible(false);
            }

        @Override
        public void mousePressed(MouseEvent e) {
            }

        @Override
        public void mouseReleased(MouseEvent e) {
            }

        @Override
        public void mouseEntered(MouseEvent e) {
            }

        @Override
        public void mouseExited(MouseEvent e) {
            }
    }

    private class HasilMouseListener implements MouseListener {

        public HasilMouseListener() {
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            home.getDynamicPanel().setVisible(true);
            feed.setVisible(false);
            result.setVisible(true);
            }

        @Override
        public void mousePressed(MouseEvent e) {
            }

        @Override
        public void mouseReleased(MouseEvent e) {
            }

        @Override
        public void mouseEntered(MouseEvent e) {
            }

        @Override
        public void mouseExited(MouseEvent e) {
            }
    }

    private class AlgoMouseListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            home.getDynamicPanel().setVisible(true);
            feed.setVisible(true);
            result.setVisible(false);
            
            String s = Double.toString(genModel.getMe());
            home.getData().setText(s);
            
            individu();
            try {
                genModel.getKebNut();
            } catch (SQLException ex) {
                Logger.getLogger(GenetikController.class.getName()).log(Level.SEVERE, null, ex);
            }
            }

        @Override
        public void mousePressed(MouseEvent e) {
            }

        @Override
        public void mouseReleased(MouseEvent e) {
            }

        @Override
        public void mouseEntered(MouseEvent e) {
                setIcon(home.getButton_Algo(), "/View/Button/btn-calculate-hover.png");
            }

        @Override
        public void mouseExited(MouseEvent e) {
                setIcon(home.getButton_Algo(), "/View/Button/btn-calculate.png");
            }
    }
    
    private class OptimasiMouseListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            System.out.println("tes bisa");
            int popSize = Integer.parseInt(home.getInputPopulasi());
            int iterasi = Integer.parseInt(home.getInputIterasi());
            double pc = Double.parseDouble(home.getInputProbCross());
            double pm = Double.parseDouble(home.getInputProbMut());
            int konsumsi = Integer.parseInt(home.getInputKonsumsi());
            int ayam = Integer.parseInt(home.getInputAyam());
   
            try {
                genModel.insertDataAlgo(popSize, iterasi, pc, pm, konsumsi, ayam);
                JOptionPane.showMessageDialog(home, "Berhasil disimpan, klik Perhitungan Algoritma");
            } catch (SQLException ex) {
                Logger.getLogger(GenetikController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            setIcon(home.getButton_Optimasi(), "/View/Button/btn-optimasi-hover.png");
        }

        @Override
        public void mouseExited(MouseEvent e) {
            setIcon(home.getButton_Optimasi(), "/View/Button/btn-optimasi.png");
        }
    }
    
}
