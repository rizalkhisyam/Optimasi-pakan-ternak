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
import java.util.Arrays;
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
    private double popAwal[][] = null;
    private double cc[][] = null;
    private double cm[][] = null;
    private double pTerbaik[];
    private static double pc;
    private static double pm;
    
    public GenetikController(HomeView home, GenetikModel genModel)throws SQLException{
    this.home = home;
    this.genModel = genModel;
    popSize = genModel.getPopSize();
    
    genModel.getData();
    
    genModel.getKebNut();
    genModel.getAlgo();
    
    genModel.getKedelai();
    genModel.getKelapa();
    genModel.getKacang();
    genModel.getIkan();
    genModel.getUdang();
    genModel.tampilBahan();

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
        System.out.println(individu);
        this.popAwal = new double[popSize][individu+1];
        try{
            for (int i = 0; i < popSize; i++) {
            System.out.println("== Kromosm P"+(i+1)+" ==");
            feed.getAreaKromosom().setText("Kromosom P"+(i+1));
            for (int j = 0; j < 5; j++) {
            int random = r.nextInt(10)+1;
            popAwal[i][j] = random;
            System.out.println("Kromosom :"+(j+1)+": "+popAwal[i][j]);
//            feed.getAreaKromosom().setText("Kromosom P"+(i+1)+": "+popAwal[i][j]);
            }
        }
        }catch (Exception e){
            System.out.println(e);
        }
        
    }
    
    public void evaluasiKromosom(){
        
//        this.popAwal = new double[popSize][individu+1];
        int konsumsi = genModel.getKonsumsi();
        double hargaKedelai = genModel.getHarga1();
        double hargaKelapa = genModel.getHarga2();
        double hargaKacang = genModel.getHarga3();
        double hargaIkan = genModel.getHarga4();
        double hargaUdang = genModel.getHarga5();
        
        double me1 = genModel.getMe1();
        double me2 = genModel.getMe2();
        double me3 = genModel.getMe3();
        double me4 = genModel.getMe4();
        double me5 = genModel.getMe5();
        
        double pro1 = genModel.getPro1();
        double pro2 = genModel.getPro2();
        double pro3 = genModel.getPro3();
        double pro4 = genModel.getPro4();
        double pro5 = genModel.getPro5();
        
        double lem1 = genModel.getLem1();
        double lem2 = genModel.getLem2();
        double lem3 = genModel.getLem3();
        double lem4 = genModel.getLem4();
        double lem5 = genModel.getLem5();
        
        double ser1 = genModel.getSer1();
        double ser2 = genModel.getSer2();
        double ser3 = genModel.getSer3();
        double ser4 = genModel.getSer4();
        double ser5 = genModel.getSer5();
        
        double kal1 = genModel.getkal1();
        double kal2 = genModel.getkal2();
        double kal3 = genModel.getkal3();
        double kal4 = genModel.getkal4();
        double kal5 = genModel.getkal5();
        
        double fos1 = genModel.getFos1();
        double fos2 = genModel.getFos2();
        double fos3 = genModel.getFos3();
        double fos4 = genModel.getFos4();
        double fos5 = genModel.getFos5();
        
//        ---get KebNut---
        
        double a = genModel.getMe();
        double b = genModel.getProtein();
        double c = genModel.getLemak();
        double d = genModel.getSerat();
        double e = genModel.getKalsium();
        double f = genModel.getFosfor();

        double kebNutMe = a * konsumsi;
        double kebNutPr = (b / 100)*konsumsi;
        double kebNutLe = (c / 100)*konsumsi;
        double kebNutSe = (d / 100)*konsumsi;
        double kebNutKa = (e / 100)*konsumsi;
        double kebNutFo = (f / 100)*konsumsi;
        
        System.out.println("-------- Evaluasi Kromosom -------");
        for (int i = 0; i < popSize; i++) {
            System.out.println("Kromosom P"+(i+1));
            for (int j = 0; j < individu; j++) {
                System.out.println("- "+popAwal[i][j]);
            }
            
            double p1 = popAwal[i][0];
            double p2 = popAwal[i][1];
            double p3 = popAwal[i][2];
            double p4 = popAwal[i][3];
            double p5 = popAwal[i][4];
            double total = p1+p2+p3+p4+p5;

            double harga1 = p1/total*konsumsi;
            double harga2 = p2/total*konsumsi;
            double harga3 = p3/total*konsumsi;
            double harga4 = p4/total*konsumsi;
            double harga5 = p5/total*konsumsi;
            double totalHarga = harga1+harga2+harga3+harga4+harga5;
            System.out.println("Bobot :"+ total);
            System.out.println("tes tot: "+totalHarga);
            System.out.println("Kromosom 1 dalam gr : "+harga1+" gram");
            System.out.println("Kromosom 2 dalam gr : "+harga2+" gram");
            System.out.println("Kromosom 3 dalam gr : "+harga3+" gram");
            System.out.println("Kromosom 4 dalam gr : "+harga4+" gram");
            System.out.println("Kromosom 5 dalam gr : "+harga5+" gram");
            System.out.println("-------------------------------");
            
            double cost1 = harga1/1000*hargaKedelai;
            double cost2 = harga2/1000*hargaKelapa;
            double cost3 = harga3/1000*hargaKacang;
            double cost4 = harga4/1000*hargaIkan;
            double cost5 = harga5/1000*hargaUdang;
            double totalCost = cost1+cost2+cost3+cost4+cost5;
            
            System.out.println("harga k1 = RP. "+cost1);
            System.out.println("harga k2 = RP. "+cost2);
            System.out.println("harga k3 = RP. "+cost3);
            System.out.println("harga k4 = RP. "+cost4);
            System.out.println("harga k5 = RP. "+cost5);
            System.out.println("Total Harga Bahan Pakan P"+(i+1)+" :Rp. "+totalCost);
            System.out.println("");
            double kedelai1 = harga1*me1;
            double kedelai2 = harga1*pro1/100;
            double kedelai3 = harga1*lem1/100;
            double kedelai4 = harga1*ser1/100;
            double kedelai5 = harga1*kal1/100;
            double kedelai6 = harga1*fos1/100;
            System.out.println("<------------------------>");
            System.out.println("Kandungan Nutrisi P"+(i+1));
            System.out.println("===========bahan 1============");
            System.out.println("Kedelai");
            System.out.println("ME : "+kedelai1);
            System.out.println("Protein : "+kedelai2);
            System.out.println("Lemak : "+kedelai3);
            System.out.println("Serat : "+kedelai4);
            System.out.println("Kalsium : "+kedelai5);
            System.out.println("Fosfor : "+kedelai6);
            
            double kelapa1 = harga2*me2;
            double kelapa2 = harga2*pro2/100;
            double kelapa3 = harga2*lem2/100;
            double kelapa4 = harga2*ser2/100;
            double kelapa5 = harga2*kal2/100;
            double kelapa6 = harga2*fos2/100;
            System.out.println("===========bahan 2============");
            System.out.println("Bungkil Kelapa");
            System.out.println("ME : "+kelapa1);
            System.out.println("Protein : "+kelapa2);
            System.out.println("Lemak : "+kelapa3);
            System.out.println("Serat : "+kelapa4);
            System.out.println("Kalsium : "+kelapa5);
            System.out.println("Fosfor : "+kelapa6);
            
            double kacang1 = harga3*me3;
            double kacang2 = harga3*pro3/100;
            double kacang3 = harga3*lem3/100;
            double kacang4 = harga3*ser3/100;
            double kacang5 = harga3*kal3/100;
            double kacang6 = harga3*fos3/100;
            System.out.println("===========bahan 3============");
            System.out.println("Bungkil Kacang");
            System.out.println("ME : "+kacang1);
            System.out.println("Protein : "+kacang2);
            System.out.println("Lemak : "+kacang3);
            System.out.println("Serat : "+kacang4);
            System.out.println("Kalsium : "+kacang5);
            System.out.println("Fosfor : "+kacang6);
            
            double ikan1 = harga4*me4;
            double ikan2 = harga4*pro4/100;
            double ikan3 = harga4*lem4/100;
            double ikan4 = harga4*ser4/100;
            double ikan5 = harga4*kal4/100;
            double ikan6 = harga4*fos4/100;
            System.out.println("===========bahan 4============");
            System.out.println("Tepung Ikan");
            System.out.println("ME : "+ikan1);
            System.out.println("Protein : "+ikan2);
            System.out.println("Lemak : "+ikan3);
            System.out.println("Serat : "+ikan4);
            System.out.println("Kalsium : "+ikan5);
            System.out.println("Fosfor : "+ikan6);
            
            double udang1 = harga5*me5;
            double udang2 = harga5*pro5/100;
            double udang3 = harga5*lem5/100;
            double udang4 = harga5*ser5/100;
            double udang5 = harga5*kal5/100;
            double udang6 = harga5*fos5/100;
            
            System.out.println("===========bahan 5============");
            System.out.println("Limbah Udang");
            System.out.println("ME : "+udang1);
            System.out.println("Protein : "+udang2);
            System.out.println("Lemak : "+udang3);
            System.out.println("Serat : "+udang4);
            System.out.println("Kalsium : "+udang5);
            System.out.println("Fosfor : "+udang6);
            
            double totalMe = kedelai1+kelapa1+kacang1+ikan1+udang1;
            double totalProtein = kedelai2+kelapa2+kacang2+ikan2+udang2;
            double totalLemak = kedelai3+kelapa3+kacang3+ikan3+udang3;
            double totalSerat = kedelai4+kelapa4+kacang4+ikan4+udang4;
            double totalKalsium = kedelai5+kelapa5+kacang5+ikan5+udang5;
            double totalFosfor = kedelai6+kelapa6+kacang6+ikan6+udang6;
            System.out.println("");
            System.out.println("Total Kandungan Nutrisi");
            System.out.println("Total ME : "+totalMe);
            System.out.println("Total Protein : "+totalProtein);
            System.out.println("Total Lemak : "+totalLemak);
            System.out.println("Total Serat : "+totalSerat);
            System.out.println("Total Kalsium : "+totalKalsium);
            System.out.println("Total Fosfor : "+totalFosfor);
            
//            hitung penalty
            double penalty1=0;
            double penalty2=0;
            double penalty3=0;
            double penalty4=0;
            double penalty5=0;
            double penalty6=0;
            
            System.out.println("");
            System.out.println("Hitung Penalty");
            if (totalMe > kebNutMe) {
                System.out.println("1. Kebutuhan ME terpenuhi");
            }else{
                System.out.println("1. Kebutuhan ME tidak terpenuhi");
                penalty1 = kebNutMe - totalMe;
                System.out.println("  Kekurangan Nutrisi ME : "+penalty1);
            }
            
            if (totalProtein > kebNutPr) {
                System.out.println("2. Kebutuhan Protein Terpenuhi");
            }else{
                System.out.println("2. Kebutuhan Protein tidak terpenuhi");
                penalty2 = kebNutPr - totalProtein;
                System.out.println("  Kekurangan Nutrisi Protein : "+penalty2);
            }
            
            if (totalLemak > kebNutLe) {
                System.out.println("3. Kebutuhan Lemak Terpenuhi");
            }else{
                System.out.println("3. Kebutuhan Lemak tidak terpenuhi");
                penalty3 = kebNutLe - totalLemak;
                System.out.println("  Kekurangan Nutrisi Lemak :"+penalty3);
            }
            
            if (totalSerat > kebNutSe) {
                System.out.println("4. Kebutuhan Serat Terpenuhi");
            }else{
                System.out.println("4. Kebutuhan Serat tidak terpenuhi");
                penalty4 = kebNutSe - totalSerat;
                System.out.println("  Kekurangan Nutrisi Serat :"+penalty4);
            }
            
            if (totalKalsium > kebNutKa) {
                System.out.println("5. Kebutuhan Kalsium Terpenuhi");
            }else{
                System.out.println("5. Kebutuhan Kalsium tidak terpenuhi");
                penalty5 = kebNutKa - totalKalsium;
                System.out.println("  Kekurangan Nutrisi Kalsium :"+penalty5);
            }
            
            if (totalFosfor > kebNutFo) {
                System.out.println("6. Kebutuhan Fosfor Terpenuhi");
            }else{
                System.out.println("6. Kebutuhan Fosfor tidak terpenuhi");
                penalty6 = kebNutFo - totalFosfor;
                System.out.println("  Kekurangan Nutrisi Fosfor :"+penalty6);
            }
            double totalPenalty = penalty1+penalty2+penalty3+penalty4+penalty5+penalty6;
            System.out.println("Total Penalty : "+totalPenalty);
            System.out.println("");
            
//            hitung Fitness
            System.out.println("---------Fitness--------");
            double fitness = 100 /(totalCost +(totalPenalty*100));
            popAwal[i][5] = fitness;
            
            System.out.println("tot cost:"+totalCost);
            System.out.println("tot Pen:"+totalPenalty);
            System.out.println("Nilai Fitness P"+(i+1)+" :"+df.format(fitness));
            System.out.println("");
            System.out.println("//============================================================//");
        }
    }
    
    public void crossover(){
        popSize = genModel.getPopSize();
        pc = genModel.getProbCross();
        int osCrossover = (int) Math.ceil(pc*popSize);
        this.cc = new double[osCrossover][individu + 1];
        
        if (pc > 0) {
            
            // pilih populasi dengan acak        
            int p1 = r.nextInt(popSize);
            int p2 = r.nextInt(popSize);
            System.out.println("Proses Crossover one-cut-point");
            System.out.println("Individu acak 1: P"+(p1+1));
            System.out.println("Individu acak 2: P" +(p2+1));
            
            // acak titik potong
            int cutPoint = r.nextInt(5);
            System.out.println("cut: "+cutPoint);
            
            //proses crossover one-cut-point-crossover
            double tempCC[][] = new double[cc.length][individu + 1];
            System.arraycopy(popAwal[p1], 0, tempCC[0], 0, individu);
            System.arraycopy(popAwal[p2], 0, tempCC[1], 0, individu);
            for (int i = 0; i < individu; i++) {
                if (i >= cutPoint) {
                    tempCC[0][i] = popAwal[p2][i];
                    tempCC[1][i] = popAwal[p1][i];
                }
            }
            System.arraycopy(tempCC, 0, this.cc, 0, tempCC.length);
            System.out.println("tes crossover : "+cc[0][1]);
        }
    }
    
    public void printCrossover(){
        int konsumsi = genModel.getKonsumsi();
        double hargaKedelai = genModel.getHarga1();
        double hargaKelapa = genModel.getHarga2();
        double hargaKacang = genModel.getHarga3();
        double hargaIkan = genModel.getHarga4();
        double hargaUdang = genModel.getHarga5();
        
        double me1 = genModel.getMe1();
        double me2 = genModel.getMe2();
        double me3 = genModel.getMe3();
        double me4 = genModel.getMe4();
        double me5 = genModel.getMe5();
        
        double pro1 = genModel.getPro1();
        double pro2 = genModel.getPro2();
        double pro3 = genModel.getPro3();
        double pro4 = genModel.getPro4();
        double pro5 = genModel.getPro5();
        
        double lem1 = genModel.getLem1();
        double lem2 = genModel.getLem2();
        double lem3 = genModel.getLem3();
        double lem4 = genModel.getLem4();
        double lem5 = genModel.getLem5();
        
        double ser1 = genModel.getSer1();
        double ser2 = genModel.getSer2();
        double ser3 = genModel.getSer3();
        double ser4 = genModel.getSer4();
        double ser5 = genModel.getSer5();
        
        double kal1 = genModel.getkal1();
        double kal2 = genModel.getkal2();
        double kal3 = genModel.getkal3();
        double kal4 = genModel.getkal4();
        double kal5 = genModel.getkal5();
        
        double fos1 = genModel.getFos1();
        double fos2 = genModel.getFos2();
        double fos3 = genModel.getFos3();
        double fos4 = genModel.getFos4();
        double fos5 = genModel.getFos5();
        
//        ---get KebNut---
        
        double a = genModel.getMe();
        double b = genModel.getProtein();
        double c = genModel.getLemak();
        double d = genModel.getSerat();
        double e = genModel.getKalsium();
        double f = genModel.getFosfor();

        double kebNutMe = a * konsumsi;
        double kebNutPr = (b / 100)*konsumsi;
        double kebNutLe = (c / 100)*konsumsi;
        double kebNutSe = (d / 100)*konsumsi;
        double kebNutKa = (e / 100)*konsumsi;
        double kebNutFo = (f / 100)*konsumsi;
        
        System.out.println("=================");
        System.out.println("Crossover Offspring : ");
        for (double[] cc1 : this.cc) {
            for (int j = 0; j < individu + 1; j++) {
                System.out.print(cc1[j] + " ");
            }
            System.out.println();
        }
        for (int i = 0; i < 2; i++) {
            System.out.println("hmmm");
            for (int j = 0; j < individu; j++) {
            System.out.println("hai ini tes: "+cc[i][j]); 
            }
            double p1 = cc[i][0];
            double p2 = cc[i][1];
            double p3 = cc[i][2];
            double p4 = cc[i][3];
            double p5 = cc[i][4];
            double total = p1+p2+p3+p4+p5;

            double harga1 = p1/total*konsumsi;
            double harga2 = p2/total*konsumsi;
            double harga3 = p3/total*konsumsi;
            double harga4 = p4/total*konsumsi;
            double harga5 = p5/total*konsumsi;
            double totalHarga = harga1+harga2+harga3+harga4+harga5;
            System.out.println("Bobot :"+ total);
            System.out.println("tes tot: "+totalHarga);
            System.out.println("Kromosom 1 dalam gr : "+harga1+" gram");
            System.out.println("Kromosom 2 dalam gr : "+harga2+" gram");
            System.out.println("Kromosom 3 dalam gr : "+harga3+" gram");
            System.out.println("Kromosom 4 dalam gr : "+harga4+" gram");
            System.out.println("Kromosom 5 dalam gr : "+harga5+" gram");
            System.out.println("-------------------------------");
            
            double cost1 = harga1/1000*hargaKedelai;
            double cost2 = harga2/1000*hargaKelapa;
            double cost3 = harga3/1000*hargaKacang;
            double cost4 = harga4/1000*hargaIkan;
            double cost5 = harga5/1000*hargaUdang;
            double totalCost = cost1+cost2+cost3+cost4+cost5;
            
            System.out.println("harga k1 = RP. "+cost1);
            System.out.println("harga k2 = RP. "+cost2);
            System.out.println("harga k3 = RP. "+cost3);
            System.out.println("harga k4 = RP. "+cost4);
            System.out.println("harga k5 = RP. "+cost5);
            System.out.println("Total Harga Bahan Pakan P"+(i+1)+" :Rp. "+totalCost);
            System.out.println("");
            double kedelai1 = harga1*me1;
            double kedelai2 = harga1*pro1/100;
            double kedelai3 = harga1*lem1/100;
            double kedelai4 = harga1*ser1/100;
            double kedelai5 = harga1*kal1/100;
            double kedelai6 = harga1*fos1/100;
            System.out.println("<------------------------>");
            System.out.println("Kandungan Nutrisi P"+(i+1));
            System.out.println("===========bahan 1============");
            System.out.println("Kedelai");
            System.out.println("ME : "+kedelai1);
            System.out.println("Protein : "+kedelai2);
            System.out.println("Lemak : "+kedelai3);
            System.out.println("Serat : "+kedelai4);
            System.out.println("Kalsium : "+kedelai5);
            System.out.println("Fosfor : "+kedelai6);
            
            double kelapa1 = harga2*me2;
            double kelapa2 = harga2*pro2/100;
            double kelapa3 = harga2*lem2/100;
            double kelapa4 = harga2*ser2/100;
            double kelapa5 = harga2*kal2/100;
            double kelapa6 = harga2*fos2/100;
            System.out.println("===========bahan 2============");
            System.out.println("Bungkil Kelapa");
            System.out.println("ME : "+kelapa1);
            System.out.println("Protein : "+kelapa2);
            System.out.println("Lemak : "+kelapa3);
            System.out.println("Serat : "+kelapa4);
            System.out.println("Kalsium : "+kelapa5);
            System.out.println("Fosfor : "+kelapa6);
            
            double kacang1 = harga3*me3;
            double kacang2 = harga3*pro3/100;
            double kacang3 = harga3*lem3/100;
            double kacang4 = harga3*ser3/100;
            double kacang5 = harga3*kal3/100;
            double kacang6 = harga3*fos3/100;
            System.out.println("===========bahan 3============");
            System.out.println("Bungkil Kacang");
            System.out.println("ME : "+kacang1);
            System.out.println("Protein : "+kacang2);
            System.out.println("Lemak : "+kacang3);
            System.out.println("Serat : "+kacang4);
            System.out.println("Kalsium : "+kacang5);
            System.out.println("Fosfor : "+kacang6);
            
            double ikan1 = harga4*me4;
            double ikan2 = harga4*pro4/100;
            double ikan3 = harga4*lem4/100;
            double ikan4 = harga4*ser4/100;
            double ikan5 = harga4*kal4/100;
            double ikan6 = harga4*fos4/100;
            System.out.println("===========bahan 4============");
            System.out.println("Tepung Ikan");
            System.out.println("ME : "+ikan1);
            System.out.println("Protein : "+ikan2);
            System.out.println("Lemak : "+ikan3);
            System.out.println("Serat : "+ikan4);
            System.out.println("Kalsium : "+ikan5);
            System.out.println("Fosfor : "+ikan6);
            
            double udang1 = harga5*me5;
            double udang2 = harga5*pro5/100;
            double udang3 = harga5*lem5/100;
            double udang4 = harga5*ser5/100;
            double udang5 = harga5*kal5/100;
            double udang6 = harga5*fos5/100;
            
            System.out.println("===========bahan 5============");
            System.out.println("Limbah Udang");
            System.out.println("ME : "+udang1);
            System.out.println("Protein : "+udang2);
            System.out.println("Lemak : "+udang3);
            System.out.println("Serat : "+udang4);
            System.out.println("Kalsium : "+udang5);
            System.out.println("Fosfor : "+udang6);
            
            double totalMe = kedelai1+kelapa1+kacang1+ikan1+udang1;
            double totalProtein = kedelai2+kelapa2+kacang2+ikan2+udang2;
            double totalLemak = kedelai3+kelapa3+kacang3+ikan3+udang3;
            double totalSerat = kedelai4+kelapa4+kacang4+ikan4+udang4;
            double totalKalsium = kedelai5+kelapa5+kacang5+ikan5+udang5;
            double totalFosfor = kedelai6+kelapa6+kacang6+ikan6+udang6;
            System.out.println("");
            System.out.println("Total Kandungan Nutrisi");
            System.out.println("Total ME : "+totalMe);
            System.out.println("Total Protein : "+totalProtein);
            System.out.println("Total Lemak : "+totalLemak);
            System.out.println("Total Serat : "+totalSerat);
            System.out.println("Total Kalsium : "+totalKalsium);
            System.out.println("Total Fosfor : "+totalFosfor);
            
//            hitung penalty
            double penalty1=0;
            double penalty2=0;
            double penalty3=0;
            double penalty4=0;
            double penalty5=0;
            double penalty6=0;
            
            System.out.println("");
            System.out.println("Hitung Penalty");
            if (totalMe > kebNutMe) {
                System.out.println("1. Kebutuhan ME terpenuhi");
            }else{
                System.out.println("1. Kebutuhan ME tidak terpenuhi");
                penalty1 = kebNutMe - totalMe;
                System.out.println("  Kekurangan Nutrisi ME : "+penalty1);
            }
            
            if (totalProtein > kebNutPr) {
                System.out.println("2. Kebutuhan Protein Terpenuhi");
            }else{
                System.out.println("2. Kebutuhan Protein tidak terpenuhi");
                penalty2 = kebNutPr - totalProtein;
                System.out.println("  Kekurangan Nutrisi Protein : "+penalty2);
            }
            
            if (totalLemak > kebNutLe) {
                System.out.println("3. Kebutuhan Lemak Terpenuhi");
            }else{
                System.out.println("3. Kebutuhan Lemak tidak terpenuhi");
                penalty3 = kebNutLe - totalLemak;
                System.out.println("  Kekurangan Nutrisi Lemak :"+penalty3);
            }
            
            if (totalSerat > kebNutSe) {
                System.out.println("4. Kebutuhan Serat Terpenuhi");
            }else{
                System.out.println("4. Kebutuhan Serat tidak terpenuhi");
                penalty4 = kebNutSe - totalSerat;
                System.out.println("  Kekurangan Nutrisi Serat :"+penalty4);
            }
            
            if (totalKalsium > kebNutKa) {
                System.out.println("5. Kebutuhan Kalsium Terpenuhi");
            }else{
                System.out.println("5. Kebutuhan Kalsium tidak terpenuhi");
                penalty5 = kebNutKa - totalKalsium;
                System.out.println("  Kekurangan Nutrisi Kalsium :"+penalty5);
            }
            
            if (totalFosfor > kebNutFo) {
                System.out.println("6. Kebutuhan Fosfor Terpenuhi");
            }else{
                System.out.println("6. Kebutuhan Fosfor tidak terpenuhi");
                penalty6 = kebNutFo - totalFosfor;
                System.out.println("  Kekurangan Nutrisi Fosfor :"+penalty6);
            }
            double totalPenalty = penalty1+penalty2+penalty3+penalty4+penalty5+penalty6;
            System.out.println("Total Penalty : "+totalPenalty);
            System.out.println("");
            
//            hitung Fitness
            System.out.println("---------Fitness--------");
            double fitness = 100 /(totalCost +(totalPenalty*100));
            cc[i][5] = fitness;
            
            df = new DecimalFormat("#.######");
            System.out.println("tot cost:"+totalCost);
            System.out.println("tot Pen:"+totalPenalty);
            System.out.println("Nilai Fitness P"+(i+1)+" :"+df.format(fitness));
            System.out.println("");
            System.out.println("Crossover dan fitness");
            for (double[] cc1 : this.cc) {
            for (int j = 0; j < individu + 1; j++) {
                System.out.print(df.format(cc1[j])+ " ");
            }
            System.out.println();
        }
            System.out.println("//============================================================//");
        }
    }
    
    public int getRandomWithExclusion(Random rnd, int start, int end, int... exclude) {
        int random = start + rnd.nextInt(end - start + 1 - exclude.length);
        for (int ex : exclude) {
            if (random < ex) {
                break;
            }
            random++;
        }
        return random;
    }
    
    public void mutation(){
        pm = genModel.getProbMut();
        int osMutation = (int) Math.ceil(pm*popSize);
        this.cm = new double[osMutation][individu + 1];
        
        if (pm > 0) {
            int p1 = new Random().nextInt(popSize);
            System.out.println("Proses Mutasi Reciprocal-exchange-mutation");
            System.out.println("Mutasi pada kromosom : P"+(p1+1));
            
            int pos1 = new Random().nextInt(5);
            System.out.println("Mutation point 1 : index "+pos1);
            int pos2 = getRandomWithExclusion(r, 0, individu - 1, pos1);
            System.out.println("Mutation point 2 : index "+pos2);
            
//          proses mutasi reciprocal exchange mutation
            System.arraycopy(popAwal[p1], 0, cm[0], 0, individu);
            cm[0][pos1] = popAwal[p1][pos2];
            cm[0][pos2] = popAwal[p1][pos1];

        }
    }
    
    public void evaluasiMutasi(){
        int konsumsi = genModel.getKonsumsi();
        double hargaKedelai = genModel.getHarga1();
        double hargaKelapa = genModel.getHarga2();
        double hargaKacang = genModel.getHarga3();
        double hargaIkan = genModel.getHarga4();
        double hargaUdang = genModel.getHarga5();
        
        double me1 = genModel.getMe1();
        double me2 = genModel.getMe2();
        double me3 = genModel.getMe3();
        double me4 = genModel.getMe4();
        double me5 = genModel.getMe5();
        
        double pro1 = genModel.getPro1();
        double pro2 = genModel.getPro2();
        double pro3 = genModel.getPro3();
        double pro4 = genModel.getPro4();
        double pro5 = genModel.getPro5();
        
        double lem1 = genModel.getLem1();
        double lem2 = genModel.getLem2();
        double lem3 = genModel.getLem3();
        double lem4 = genModel.getLem4();
        double lem5 = genModel.getLem5();
        
        double ser1 = genModel.getSer1();
        double ser2 = genModel.getSer2();
        double ser3 = genModel.getSer3();
        double ser4 = genModel.getSer4();
        double ser5 = genModel.getSer5();
        
        double kal1 = genModel.getkal1();
        double kal2 = genModel.getkal2();
        double kal3 = genModel.getkal3();
        double kal4 = genModel.getkal4();
        double kal5 = genModel.getkal5();
        
        double fos1 = genModel.getFos1();
        double fos2 = genModel.getFos2();
        double fos3 = genModel.getFos3();
        double fos4 = genModel.getFos4();
        double fos5 = genModel.getFos5();
        
//        ---get KebNut---
        
        double a = genModel.getMe();
        double b = genModel.getProtein();
        double c = genModel.getLemak();
        double d = genModel.getSerat();
        double e = genModel.getKalsium();
        double f = genModel.getFosfor();

        double kebNutMe = a * konsumsi;
        double kebNutPr = (b / 100)*konsumsi;
        double kebNutLe = (c / 100)*konsumsi;
        double kebNutSe = (d / 100)*konsumsi;
        double kebNutKa = (e / 100)*konsumsi;
        double kebNutFo = (f / 100)*konsumsi;
        
        System.out.println("Mutation: ");
        for (double[] cm1 : this.cm) {
            for (int j = 0; j < individu + 1; j++) {
                System.out.print(cm1[j] + " ");
            }
            System.out.println();
        }
        
        for (int i = 0; i < 1; i++) {
            System.out.println("ini tes mutasi");
            for (int j = 0; j < individu; j++) {
                System.out.println("mutasi : "+cm[i][j]);
            }
            
            double p1 = cm[i][0];
            double p2 = cm[i][1];
            double p3 = cm[i][2];
            double p4 = cm[i][3];
            double p5 = cm[i][4];
            double total = p1+p2+p3+p4+p5;

            double harga1 = p1/total*konsumsi;
            double harga2 = p2/total*konsumsi;
            double harga3 = p3/total*konsumsi;
            double harga4 = p4/total*konsumsi;
            double harga5 = p5/total*konsumsi;
            double totalHarga = harga1+harga2+harga3+harga4+harga5;
            System.out.println("Bobot :"+ total);
            System.out.println("tes tot: "+totalHarga);
            System.out.println("Kromosom 1 dalam gr : "+harga1+" gram");
            System.out.println("Kromosom 2 dalam gr : "+harga2+" gram");
            System.out.println("Kromosom 3 dalam gr : "+harga3+" gram");
            System.out.println("Kromosom 4 dalam gr : "+harga4+" gram");
            System.out.println("Kromosom 5 dalam gr : "+harga5+" gram");
            System.out.println("-------------------------------");
            
            double cost1 = harga1/1000*hargaKedelai;
            double cost2 = harga2/1000*hargaKelapa;
            double cost3 = harga3/1000*hargaKacang;
            double cost4 = harga4/1000*hargaIkan;
            double cost5 = harga5/1000*hargaUdang;
            double totalCost = cost1+cost2+cost3+cost4+cost5;
            
            System.out.println("harga k1 = RP. "+cost1);
            System.out.println("harga k2 = RP. "+cost2);
            System.out.println("harga k3 = RP. "+cost3);
            System.out.println("harga k4 = RP. "+cost4);
            System.out.println("harga k5 = RP. "+cost5);
            System.out.println("Total Harga Bahan Pakan P"+(i+1)+" :Rp. "+totalCost);
            System.out.println("");
            double kedelai1 = harga1*me1;
            double kedelai2 = harga1*pro1;
            double kedelai3 = harga1*lem1;
            double kedelai4 = harga1*ser1;
            double kedelai5 = harga1*kal1;
            double kedelai6 = harga1*fos1;
            System.out.println("<------------------------>");
            System.out.println("Kandungan Nutrisi P"+(i+1));
            System.out.println("===========bahan 1============");
            System.out.println("Kedelai");
            System.out.println("ME : "+kedelai1);
            System.out.println("Protein : "+kedelai2);
            System.out.println("Lemak : "+kedelai3);
            System.out.println("Serat : "+kedelai4);
            System.out.println("Kalsium : "+kedelai5);
            System.out.println("Fosfor : "+kedelai6);
            
            double kelapa1 = harga2*me2;
            double kelapa2 = harga2*pro2/100;
            double kelapa3 = harga2*lem2/100;
            double kelapa4 = harga2*ser2/100;
            double kelapa5 = harga2*kal2/100;
            double kelapa6 = harga2*fos2/100;
            System.out.println("===========bahan 2============");
            System.out.println("Bungkil Kelapa");
            System.out.println("ME : "+kelapa1);
            System.out.println("Protein : "+kelapa2);
            System.out.println("Lemak : "+kelapa3);
            System.out.println("Serat : "+kelapa4);
            System.out.println("Kalsium : "+kelapa5);
            System.out.println("Fosfor : "+kelapa6);
            
            double kacang1 = harga3*me3;
            double kacang2 = harga3*pro3/100;
            double kacang3 = harga3*lem3/100;
            double kacang4 = harga3*ser3/100;
            double kacang5 = harga3*kal3/100;
            double kacang6 = harga3*fos3/100;
            System.out.println("===========bahan 3============");
            System.out.println("Bungkil Kacang");
            System.out.println("ME : "+kacang1);
            System.out.println("Protein : "+kacang2);
            System.out.println("Lemak : "+kacang3);
            System.out.println("Serat : "+kacang4);
            System.out.println("Kalsium : "+kacang5);
            System.out.println("Fosfor : "+kacang6);
            
            double ikan1 = harga4*me4;
            double ikan2 = harga4*pro4/100;
            double ikan3 = harga4*lem4/100;
            double ikan4 = harga4*ser4/100;
            double ikan5 = harga4*kal4/100;
            double ikan6 = harga4*fos4/100;
            System.out.println("===========bahan 4============");
            System.out.println("Tepung Ikan");
            System.out.println("ME : "+ikan1);
            System.out.println("Protein : "+ikan2);
            System.out.println("Lemak : "+ikan3);
            System.out.println("Serat : "+ikan4);
            System.out.println("Kalsium : "+ikan5);
            System.out.println("Fosfor : "+ikan6);
            
            double udang1 = harga5*me5;
            double udang2 = harga5*pro5/100;
            double udang3 = harga5*lem5/100;
            double udang4 = harga5*ser5/100;
            double udang5 = harga5*kal5/100;
            double udang6 = harga5*fos5/100;
            
            System.out.println("===========bahan 5============");
            System.out.println("Limbah Udang");
            System.out.println("ME : "+udang1);
            System.out.println("Protein : "+udang2);
            System.out.println("Lemak : "+udang3);
            System.out.println("Serat : "+udang4);
            System.out.println("Kalsium : "+udang5);
            System.out.println("Fosfor : "+udang6);
            
            double totalMe = kedelai1+kelapa1+kacang1+ikan1+udang1;
            double totalProtein = kedelai2+kelapa2+kacang2+ikan2+udang2;
            double totalLemak = kedelai3+kelapa3+kacang3+ikan3+udang3;
            double totalSerat = kedelai4+kelapa4+kacang4+ikan4+udang4;
            double totalKalsium = kedelai5+kelapa5+kacang5+ikan5+udang5;
            double totalFosfor = kedelai6+kelapa6+kacang6+ikan6+udang6;
            System.out.println("");
            System.out.println("Total Kandungan Nutrisi");
            System.out.println("Total ME : "+totalMe);
            System.out.println("Total Protein : "+totalProtein);
            System.out.println("Total Lemak : "+totalLemak);
            System.out.println("Total Serat : "+totalSerat);
            System.out.println("Total Kalsium : "+totalKalsium);
            System.out.println("Total Fosfor : "+totalFosfor);
            
//            hitung penalty
            double penalty1=0;
            double penalty2=0;
            double penalty3=0;
            double penalty4=0;
            double penalty5=0;
            double penalty6=0;
            
            System.out.println("");
            System.out.println("Hitung Penalty");
            if (totalMe > kebNutMe) {
                System.out.println("1. Kebutuhan ME terpenuhi");
            }else{
                System.out.println("1. Kebutuhan ME tidak terpenuhi");
                penalty1 = kebNutMe - totalMe;
                System.out.println("  Kekurangan Nutrisi ME : "+penalty1);
            }
            
            if (totalProtein > kebNutPr) {
                System.out.println("2. Kebutuhan Protein Terpenuhi");
            }else{
                System.out.println("2. Kebutuhan Protein tidak terpenuhi");
                penalty2 = kebNutPr - totalProtein;
                System.out.println("  Kekurangan Nutrisi Protein : "+penalty2);
            }
            
            if (totalLemak > kebNutLe) {
                System.out.println("3. Kebutuhan Lemak Terpenuhi");
            }else{
                System.out.println("3. Kebutuhan Lemak tidak terpenuhi");
                penalty3 = kebNutLe - totalLemak;
                System.out.println("  Kekurangan Nutrisi Lemak :"+penalty3);
            }
            
            if (totalSerat > kebNutSe) {
                System.out.println("4. Kebutuhan Serat Terpenuhi");
            }else{
                System.out.println("4. Kebutuhan Serat tidak terpenuhi");
                penalty4 = kebNutSe - totalSerat;
                System.out.println("  Kekurangan Nutrisi Serat :"+penalty4);
            }
            
            if (totalKalsium > kebNutKa) {
                System.out.println("5. Kebutuhan Kalsium Terpenuhi");
            }else{
                System.out.println("5. Kebutuhan Kalsium tidak terpenuhi");
                penalty5 = kebNutKa - totalKalsium;
                System.out.println("  Kekurangan Nutrisi Kalsium :"+penalty5);
            }
            
            if (totalFosfor > kebNutFo) {
                System.out.println("6. Kebutuhan Fosfor Terpenuhi");
            }else{
                System.out.println("6. Kebutuhan Fosfor tidak terpenuhi");
                penalty6 = kebNutFo - totalFosfor;
                System.out.println("  Kekurangan Nutrisi Fosfor :"+penalty6);
            }
            double totalPenalty = penalty1+penalty2+penalty3+penalty4+penalty5+penalty6;
            System.out.println("Total Penalty : "+totalPenalty);
            System.out.println("");
            
//            hitung Fitness
            System.out.println("---------Fitness--------");
            double fitness = 100 /(totalCost +(totalPenalty*100));
            cm[i][5] = fitness;
            
            df = new DecimalFormat("#.######");
            System.out.println("tot cost:"+totalCost);
            System.out.println("tot Pen:"+totalPenalty);
            System.out.println("Nilai Fitness P"+(i+1)+" :"+df.format(fitness));
            System.out.println("");
            System.out.println("Mutasi dan fitness");
            for (double[] cm1 : this.cm) {
            for (int j = 0; j < individu + 1; j++) {
                System.out.print(df.format(cm1[j]) + " ");
            }
            System.out.println();
        }
            System.out.println("//============================================================//");
        }
        
    }
    
    public void seleksi(){
        popSize = genModel.getPopSize();
        double popSeleksi[][] = new double[popSize+this.cc.length+this.cm.length]
                                [individu + 1];
        
        System.arraycopy(popAwal, 0, popSeleksi, 0, popSize);
        System.arraycopy(cc, 0, popSeleksi, popSize, cc.length);
        System.arraycopy(cm, 0, popSeleksi, popSize + cc.length, cm.length);
        
        // hitung total fitness
        double totFitness = 0;
        for (double[] popSeleksi1 : popSeleksi) {
            totFitness += popSeleksi1[individu];
        }
        
        double prob[][] = new double[popSeleksi.length][3];
        double probCum = 0;
        for (int i = 0; i < prob.length; i++) {
            for (int j = 0; j < 3; j++) {
                switch (j) {
                    case 0:
                        prob[i][j] = popSeleksi[i][individu];
                        break;
                    case 1:
                        prob[i][j] = popSeleksi[i][individu] / totFitness;
                        break;
                    case 2:                
                        probCum += prob[i][1];
                        prob[i][j] = probCum;
                        break;
                    default:
                        break;
                }
            }
        }
        
        System.out.println("Probabilitas: ");
        for (double[] prob1 : prob) {
            for (int j = 0; j < prob1.length; j++) {
                System.out.printf("%-20.10f", prob1[j]);
            }
            System.out.println();
        }
        System.out.println();
        
        // lakukan roulette wheel
        double p_rw[][];
        //p_rw = rouletteWheel(popSeleksi, prob);
        p_rw = elitism(popSeleksi);
        
        System.out.println("Hasil Roulette Wheel dengan penambahan Operator ELITISM : ");
        for (double[] p_rw1 : p_rw) {
            for (int j = 0; j < p_rw1.length; j++) {
                System.out.print(df.format(p_rw1[j]) + " ");
            }
            System.out.println();
        }
        System.out.println();
        
        // update populasi
        updatePopulasi(p_rw);
        //p_rw = elitism(popSeleksi);
    }
    
    public void updatePopulasi(double pSeleksi[][]){
        popAwal = pSeleksi;
    }
    
    public double[][] elitism(double sel[][]){
        popSize = genModel.getPopSize();
        double temp[][] = sel;
        double hasil[][] = new double[popSize][this.popAwal[0].length];
        Arrays.sort(temp, new java.util.Comparator<double[]>() {
            public int compare(double[] a, double[] b) {
                return Double.compare(b[b.length - 1], a[a.length - 1]);
            }
        });
        System.arraycopy(temp, 0, hasil, 0, this.popSize);
        return hasil;
    }
    
    public double[][] rouletteWheel(double sel[][], double prob[][]){
        double hasil[][] = new double[this.popAwal.length][this.popAwal[0].length];
        for (int i = 0; i < hasil.length; i++) {
            double r = Math.random();
            for (int j = sel.length-1; j >= 0; j--) {
                if(r <= prob[j][2]){
                    hasil[i] = sel[j];
                }
            }
        }
        return hasil;
    }
    
    public void cariIndividuTerbaik(){
        this.pTerbaik = new double[individu + 1];
        popSize = genModel.getPopSize();
        double max = 0.0;
        double tempPTerbaik[] = new double[popAwal[0].length];
        // cari individu terbaik dalam populasi
        for (int i = 0; i < popSize; i++) {
            if(max < this.popAwal[i][this.popAwal[i].length-1]){
                max = this.popAwal[i][this.popAwal[i].length-1];
                tempPTerbaik = this.popAwal[i];
            }
        }
        // cek jika individu sebelumnya pertama lebih baik
        if (tempPTerbaik[tempPTerbaik.length-1] < this.pTerbaik[this.pTerbaik.length-1]) {
            this.pTerbaik = this.pTerbaik;
        }else{
            this.pTerbaik = tempPTerbaik;
        }
    }
    
    public void individuTerbaik(){
         System.out.println("Individu Terbaik: ");
        for (int j = 0; j < individu+1; j++) {
            System.out.print(df.format(pTerbaik[j])+" ");
        }
    }
    
    public void hasilOptimasi(){

        int banyakAyam = genModel.getAyam();
        
        double hargaKedelai = genModel.getHarga1();
        double hargaKelapa = genModel.getHarga2();
        double hargaKacang = genModel.getHarga3();
        double hargaIkan = genModel.getHarga4();
        double hargaUdang = genModel.getHarga5();
        
        System.out.println("Individu Terbaik: ");
        for (int j = 0; j < individu+1; j++) {
            System.out.print(df.format(pTerbaik[j])+" ");
        }
        System.out.println("\n");
        System.out.println("------ Hasil Optimasi ------");
        System.out.println("- Kedelai : "+pTerbaik[0]+"%");
        System.out.println("- Bungkil Kelapa : "+pTerbaik[1]+"%");
        System.out.println("- Bungkil Kacang : "+pTerbaik[2]+"%");
        System.out.println("- Tepung Ikan : "+pTerbaik[3]+"%");
        System.out.println("- Limbah Udang : "+pTerbaik[4]+"%");
                    
        double bahan1 = pTerbaik[0];
        double bahan2 = pTerbaik[1];
        double bahan3 = pTerbaik[2];
        double bahan4 = pTerbaik[3];
        double bahan5 = pTerbaik[4];
        
            System.out.println("");
        double totalBahan = bahan1+bahan2+bahan3+bahan4+bahan5;
            System.out.println("Total bobot : "+totalBahan);
            System.out.println("Kemampuan Ayam dalam mengkonsumsi pakan : 75 gram");
            System.out.println("Banyak Ayam : "+banyakAyam+" ekor");
            System.out.println("");
            
        double konversi1 = (bahan1/totalBahan*75)*banyakAyam;
        double konversi2 = (bahan2/totalBahan*75)*banyakAyam;
        double konversi3 = (bahan3/totalBahan*75)*banyakAyam;
        double konversi4 = (bahan4/totalBahan*75)*banyakAyam;
        double konversi5 = (bahan5/totalBahan*75)*banyakAyam;
            System.out.println("");
            System.out.println("Kedelai : "+konversi1+" gram");
            System.out.println("Bungkil Kelapa : "+konversi2+" gram");
            System.out.println("Bungkil Kacang : "+konversi3+" gram");
            System.out.println("Tepung Ikan : "+konversi4+" gram");
            System.out.println("Limbah Udang : "+konversi5+" gram");
            
            System.out.println("");
            double cost1 = konversi1/1000*hargaKedelai;
            double cost2 = konversi2/1000*hargaKelapa;
            double cost3 = konversi3/1000*hargaKacang;
            double cost4 = konversi4/1000*hargaIkan;
            double cost5 = konversi5/1000*hargaUdang;
            double totalCost = cost1+cost2+cost3+cost4+cost5;
            double konversiCost = (int) Math.round(totalCost);
            System.out.println("Harga komposisi kedelai : Rp. "+Math.round(cost1));
            System.out.println("Harga komposisi bungkil kelapa : Rp. "+Math.round(cost2));
            System.out.println("Harga komposisi bungkil kacang : Rp. "+Math.round(cost3));
            System.out.println("Harga komposisi tepung ikan : Rp. "+Math.round(cost4));
            System.out.println("Harga komposisi limbah udang : Rp. "+Math.round(cost5));
            System.out.println("");
            System.out.println("Total Harga Bahan Pakan yang optimal : Rp. "+konversiCost);
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
            int iterasi = genModel.getIterasi();
            
            System.out.println("||---- GENERASI 1 ----||");
            individu();
            evaluasiKromosom();
            for (int i = 1; i < iterasi; i++) {
            System.out.println("||---- GENERASI "+(i+1)+" ----||");
            crossover();
            printCrossover();
            mutation();
            evaluasiMutasi();
            seleksi();
            cariIndividuTerbaik();
            individuTerbaik();
            }
            hasilOptimasi();
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
