/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.GenetikModel;
import View.Fitness;
import View.Harga;
import View.Hasil;
import View.HomeView;
import View.Mutasi; 
import View.PanelOptimasi;
import View.PanelPakan;
import View.Populasi;
import View.Seleksi;
import View.editHarga;
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
    DecimalFormat df2 = new DecimalFormat("#.##");
    DecimalFormat df3 = new DecimalFormat("####");
    
    private HomeView home = new HomeView();
    static GridBagLayout layout = new GridBagLayout();
    static PanelPakan feed = new PanelPakan();
    static PanelOptimasi result = new PanelOptimasi();
    
    static GridBagLayout layoutEdit = new GridBagLayout();
    
    static GridBagLayout layout2 = new GridBagLayout();
    static Populasi panel_pop = new Populasi();
    static Mutasi panel_mut = new Mutasi();
    static Seleksi panel_sel = new Seleksi();
    static Fitness panel_fit = new Fitness();
    static Hasil panel_hasil = new Hasil();
    static Harga panel_harga = new Harga();
    static editHarga panel_edit = new editHarga();
    
    private GenetikModel genModel;
    private DefaultTableModel model;
    
    private static int popSize;
    private static int crossover;
    private static int mutasi;
    private static int individu = 5;
    private static int jumlahOffspringC;
    private static int jumlahOffspringM;
    
    private double popAwal[][] = null;
    private double cc[][] = null;
    private double offspringC[][] = null;
    private double offspringM[][] = null;
    private double cm[][] = null;
    private double pTerbaik[];
    
    private static double pc;
    private static double pm;
    private double bestPen;
    private static int cekSel;
    
    public GenetikController(HomeView home, GenetikModel genModel)throws SQLException{
    this.home = home;
    this.genModel = genModel;
    popSize = genModel.getPopSize();
    
    genModel.getKebNut();
    genModel.getAlgo();
    
    genModel.getKedelai();
    genModel.getKelapa();
    genModel.getKacang();
    genModel.getIkan();
    genModel.getUdang();
    showHarga();
    home.getButton_Ga().setVisible(false);

    home.setVisible(true);
    home.getDynamicPanel().setLayout(layout);
    home.getDynamicPanel().add(feed);
    home.getDynamicPanel().add(result);
    home.getDynamicPanel().setVisible(false);
    
    home.getPanelEdit().setLayout(layoutEdit);
    home.getPanelEdit().add(panel_edit);
    home.getPanelEdit().setVisible(false);
    
    feed.getDynamicPanel2().setLayout(layout2);
    feed.getDynamicPanel2().add(panel_pop);
    feed.getDynamicPanel2().add(panel_mut);
    feed.getDynamicPanel2().add(panel_sel);
    feed.getDynamicPanel2().add(panel_fit);
    feed.getDynamicPanel2().add(panel_hasil);
    feed.getDynamicPanel2().add(panel_harga);
    feed.getDynamicPanel2().setVisible(false);
    
    home.OptimasiMouseListener(new OptimasiMouseListener());
    home.RunMouseListener(new RunMouseListener());
    home.ClearMouseListener(new ClearMouseListener());
    
    home.EditMouseListener(new EditMouseListener());
    panel_edit.SaveMouseListener(new SaveMouseListener());
    
    home.AlgoMouseListener(new AlgoMouseListener());
    home.HasilMouseListener(new HasilMouseListener());
    home.PakanMouseListener(new PakanMouseListener());
    
    feed.PopulasiMouseListener(new PopulasiMouseListener());
    feed.CrossoverMouseListener(new CrossoverMouseListener());
    feed.MutasiMouseListener(new MutasiMouseListener());
    feed.SeleksiMouseListener(new SeleksiMouseListener());
    feed.FitnessMouseListener(new FitnessMouseListener());
    feed.BestMouseListener(new BestMouseListener());
    feed.HargaMouseListener(new HargaMouseListener());
    }
    
    public void individu(){
//        System.out.println("ini individu");
        popSize = genModel.getPopSize();
//        System.out.println(popSize);
//        System.out.println(individu);
        this.popAwal = new double[popSize][individu+1];
        try{
            for (int i = 0; i < popSize; i++) {
//            System.out.println("== Kromosm P"+(i+1)+" ==");
            feed.getAreaKromosom().append("= Kromosom P"+(i+1)+" = "+"\n");
            for (int j = 0; j < 5; j++) {
            int random = r.nextInt(10)+1;
            popAwal[i][j] = random;
//            System.out.println("Kromosom :"+(j+1)+": "+popAwal[i][j]);
            feed.getAreaKromosom().append("- "+popAwal[i][j]+"\n");
            
            }
            feed.getAreaKromosom().append("\n");
        }
        }catch (Exception e){
            System.out.println(e);
        }
    }
    
    void showHarga(){
        double hargaKedelai = genModel.getHarga1();
        double hargaKelapa = genModel.getHarga2();
        double hargaKacang = genModel.getHarga3();
        double hargaIkan = genModel.getHarga4();
        double hargaUdang = genModel.getHarga5();
        
        home.getHargaKed().setText("Rp. "+df3.format(hargaKedelai));
        home.getHargaKel().setText("Rp. "+df3.format(hargaKelapa));
        home.getHargaKac().setText("Rp. "+df3.format(hargaKacang));
        home.getHargaIkan().setText("Rp. "+df3.format(hargaIkan));
        home.getHargaUdang().setText("Rp. "+df3.format(hargaUdang));
    }
    
    public void evaluasiKromosom(){
        
//        this.popAwal = new double[popSize][individu+1];
        int konsumsi = genModel.getKonsumsi();
        double hargaKedelai = genModel.getHarga1();
        System.out.println("harga ked: "+hargaKedelai);
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

        double kebNutMe = (a / 1000) * konsumsi;
        double kebNutPr = (b / 100)*konsumsi;
        double kebNutLe = (c / 100)*konsumsi;
        double kebNutSe = (d / 100)*konsumsi;
        double kebNutKa = (e / 100)*konsumsi;
        double kebNutFo = (f / 100)*konsumsi;
        
//        System.out.println("---------------- Evaluasi Kromosom ----------------");
        feed.getAreaKromosom().append("---------------- Evaluasi Kromosom ----------------"+"\n");
        for (int i = 0; i < popSize; i++) {
//            System.out.println("Kromosom P"+(i+1));
            feed.getAreaKromosom().append("Kromosom P"+(i+1));
            feed.getAreaKromosom().append("\n");
            for (int j = 0; j < individu; j++) {
//                System.out.println("- "+popAwal[i][j]);
            }
            
            double p1 = popAwal[i][0];
            double p2 = popAwal[i][1];
            double p3 = popAwal[i][2];
            double p4 = popAwal[i][3];
            double p5 = popAwal[i][4];
            double total = p1+p2+p3+p4+p5;
            feed.getAreaKromosom().append("Bobot kromosom: "+total+"\n");
            
            double harga1 = p1/total*konsumsi;
            double harga2 = p2/total*konsumsi;
            double harga3 = p3/total*konsumsi;
            double harga4 = p4/total*konsumsi;
            double harga5 = p5/total*konsumsi;
            double totalHarga = harga1+harga2+harga3+harga4+harga5;
            
//            System.out.println("Bobot :"+ total);
            feed.getAreaKromosom().append("Total Bobot Konsumsi : "+totalHarga+"\n");
//            System.out.println("tes tot: "+totalHarga);
            feed.getAreaKromosom().append("\n");
            
            feed.getAreaKromosom().append("Kromosom 1 dalam gr : "+harga1+" gram"+"\n");
            feed.getAreaKromosom().append("Kromosom 2 dalam gr : "+harga2+" gram"+"\n");
            feed.getAreaKromosom().append("Kromosom 3 dalam gr : "+harga3+" gram"+"\n");
            feed.getAreaKromosom().append("Kromosom 4 dalam gr : "+harga4+" gram"+"\n");
            feed.getAreaKromosom().append("Kromosom 5 dalam gr : "+harga5+" gram"+"\n");
            feed.getAreaKromosom().append("\n");
            
            double cost1 = harga1/1000*hargaKedelai;
            double cost2 = harga2/1000*hargaKelapa;
            double cost3 = harga3/1000*hargaKacang;
            double cost4 = harga4/1000*hargaIkan;
            double cost5 = harga5/1000*hargaUdang;
            double totalCost = cost1+cost2+cost3+cost4+cost5;
            
            feed.getAreaKromosom().append("harga k1 = RP. "+cost1+"\n");
            feed.getAreaKromosom().append("harga k2 = RP. "+cost2+"\n");
            feed.getAreaKromosom().append("harga k3 = RP. "+cost3+"\n");
            feed.getAreaKromosom().append("harga k4 = RP. "+cost4+"\n");
            feed.getAreaKromosom().append("harga k5 = RP. "+cost5+"\n");
            feed.getAreaKromosom().append("Total Harga Bahan P "+(i+1)+" :Rp. "+totalCost+"\n");
            
            double kedelai1 = harga1*me1/1000;
            double kedelai2 = harga1*pro1/100;
            double kedelai3 = harga1*lem1/100;
            double kedelai4 = harga1*ser1/100;
            double kedelai5 = harga1*kal1/100;
            double kedelai6 = harga1*fos1/100;

            
            feed.getAreaKromosom().append("-----------------------------------------------------------------"+"\n");
            feed.getAreaKromosom().append("Kandungan Nutrisi P"+(i+1)+"\n");
            feed.getAreaKromosom().append("=========== bahan 1 ============"+"\n");
            feed.getAreaKromosom().append("Kedelai"+"\n");
            feed.getAreaKromosom().append("ME : "+kedelai1+"\n");
            feed.getAreaKromosom().append("Protein : "+kedelai2+"\n");
            feed.getAreaKromosom().append("Lemak : "+kedelai3+"\n");
            feed.getAreaKromosom().append("Serat : "+kedelai4+"\n");
            feed.getAreaKromosom().append("Kalsium : "+kedelai5+"\n");
            feed.getAreaKromosom().append("Fosfor : "+kedelai6+"\n");
            feed.getAreaKromosom().append("\n");
            
            double kelapa1 = harga2*me2/1000;
            double kelapa2 = harga2*pro2/100;
            double kelapa3 = harga2*lem2/100;
            double kelapa4 = harga2*ser2/100;
            double kelapa5 = harga2*kal2/100;
            double kelapa6 = harga2*fos2/100;
            
            feed.getAreaKromosom().append("Kandungan Nutrisi P"+(i+1)+"\n");
            feed.getAreaKromosom().append("=========== bahan 2 ============"+"\n");
            feed.getAreaKromosom().append("Bungkil Kelapa"+"\n");
            feed.getAreaKromosom().append("ME : "+kelapa1+"\n");
            feed.getAreaKromosom().append("Protein : "+kelapa2+"\n");
            feed.getAreaKromosom().append("Lemak : "+kelapa3+"\n");
            feed.getAreaKromosom().append("Serat : "+kelapa4+"\n");
            feed.getAreaKromosom().append("Kalsium : "+kelapa5+"\n");
            feed.getAreaKromosom().append("Fosfor : "+kelapa6+"\n");
            feed.getAreaKromosom().append("\n");
            
            double kacang1 = harga3*me3/1000;
            double kacang2 = harga3*pro3/100;
            double kacang3 = harga3*lem3/100;
            double kacang4 = harga3*ser3/100;
            double kacang5 = harga3*kal3/100;
            double kacang6 = harga3*fos3/100;
            
            feed.getAreaKromosom().append("Kandungan Nutrisi P"+(i+1)+"\n");
            feed.getAreaKromosom().append("=========== bahan 3 ============"+"\n");
            feed.getAreaKromosom().append("Bungkil Kacang"+"\n");
            feed.getAreaKromosom().append("ME : "+kacang1+"\n");
            feed.getAreaKromosom().append("Protein : "+kacang2+"\n");
            feed.getAreaKromosom().append("Lemak : "+kacang3+"\n");
            feed.getAreaKromosom().append("Serat : "+kacang4+"\n");
            feed.getAreaKromosom().append("Kalsium : "+kacang5+"\n");
            feed.getAreaKromosom().append("Fosfor : "+kacang6+"\n");
            feed.getAreaKromosom().append("\n");
            
            double ikan1 = harga4*me4/1000;
            double ikan2 = harga4*pro4/100;
            double ikan3 = harga4*lem4/100;
            double ikan4 = harga4*ser4/100;
            double ikan5 = harga4*kal4/100;
            double ikan6 = harga4*fos4/100;
            
            feed.getAreaKromosom().append("Kandungan Nutrisi P"+(i+1)+"\n");
            feed.getAreaKromosom().append("=========== bahan 4 ============"+"\n");
            feed.getAreaKromosom().append("Tepung Ikan"+"\n");
            feed.getAreaKromosom().append("ME : "+ikan1+"\n");
            feed.getAreaKromosom().append("Protein : "+ikan2+"\n");
            feed.getAreaKromosom().append("Lemak : "+ikan3+"\n");
            feed.getAreaKromosom().append("Serat : "+ikan4+"\n");
            feed.getAreaKromosom().append("Kalsium : "+ikan5+"\n");
            feed.getAreaKromosom().append("Fosfor : "+ikan6+"\n");
            feed.getAreaKromosom().append("\n");
            
            double udang1 = harga5*me5/1000;
            double udang2 = harga5*pro5/100;
            double udang3 = harga5*lem5/100;
            double udang4 = harga5*ser5/100;
            double udang5 = harga5*kal5/100;
            double udang6 = harga5*fos5/100;
            
            feed.getAreaKromosom().append("Kandungan Nutrisi P"+(i+1)+"\n");
            feed.getAreaKromosom().append("=========== bahan 5 ============"+"\n");
            feed.getAreaKromosom().append("Limbah Udang"+"\n");
            feed.getAreaKromosom().append("ME : "+udang1+"\n");
            feed.getAreaKromosom().append("Protein : "+udang2+"\n");
            feed.getAreaKromosom().append("Lemak : "+udang3+"\n");
            feed.getAreaKromosom().append("Serat : "+udang4+"\n");
            feed.getAreaKromosom().append("Kalsium : "+udang5+"\n");
            feed.getAreaKromosom().append("Fosfor : "+udang6+"\n");
            feed.getAreaKromosom().append("\n");
            
            double totalMe = kedelai1+kelapa1+kacang1+ikan1+udang1;
            double totalProtein = kedelai2+kelapa2+kacang2+ikan2+udang2;
            double totalLemak = kedelai3+kelapa3+kacang3+ikan3+udang3;
            double totalSerat = kedelai4+kelapa4+kacang4+ikan4+udang4;
            double totalKalsium = kedelai5+kelapa5+kacang5+ikan5+udang5;
            double totalFosfor = kedelai6+kelapa6+kacang6+ikan6+udang6;
            
            feed.getAreaKromosom().append("Total Kandungan Nutrisi"+"\n");
            feed.getAreaKromosom().append("Total ME : "+totalMe+"\n");
            feed.getAreaKromosom().append("Total Protein : "+totalProtein+"\n");
            feed.getAreaKromosom().append("Total Lemak : "+totalLemak+"\n");
            feed.getAreaKromosom().append("Total Serat : "+totalSerat+"\n");
            feed.getAreaKromosom().append("Total Kalsium : "+totalKalsium+"\n");
            feed.getAreaKromosom().append("Total Fosfor : "+totalFosfor+"\n");
            feed.getAreaKromosom().append("\n");
            
//            hitung penalty
            double penalty1=0;
            double penalty2=0;
            double penalty3=0;
            double penalty4=0;
            double penalty5=0;
            double penalty6=0;
            
//            System.out.println("");
//            System.out.println("Hitung Penalty");
            feed.getAreaKromosom().append("Hitung Penalty"+"\n");
            if (totalMe > kebNutMe) {
//                System.out.println("1. Kebutuhan ME terpenuhi");
                feed.getAreaKromosom().append("1. Kebutuhan ME terpenuhi"+"\n");
            }else{
//                System.out.println("1. Kebutuhan ME tidak terpenuhi");
                penalty1 = kebNutMe - totalMe;
//                System.out.println("  Kekurangan Nutrisi ME : "+penalty1);
                
                feed.getAreaKromosom().append("1. Kebutuhan ME tidak terpenuhi"+"\n");
                feed.getAreaKromosom().append("  Kekurangan Nutrisi ME : "+penalty1+"\n");
            }
            
            if (totalProtein > kebNutPr) {
//                System.out.println("2. Kebutuhan Protein Terpenuhi");
                feed.getAreaKromosom().append("2. Kebutuhan Protein terpenuhi"+"\n");
            }else{
//                System.out.println("2. Kebutuhan Protein tidak terpenuhi");
                penalty2 = kebNutPr - totalProtein;
//                System.out.println("  Kekurangan Nutrisi Protein : "+penalty2);
                
                feed.getAreaKromosom().append("2. Kebutuhan Protein tidak terpenuhi"+"\n");
                feed.getAreaKromosom().append("  Kekurangan Nutrisi Protein : "+penalty2+"\n");
            }
            
            if (totalLemak > kebNutLe) {
//                System.out.println("3. Kebutuhan Lemak Terpenuhi");
                feed.getAreaKromosom().append("3. Kebutuhan Lemak terpenuhi"+"\n");
            }else{
//                System.out.println("3. Kebutuhan Lemak tidak terpenuhi");
                penalty3 = kebNutLe - totalLemak;
//                System.out.println("  Kekurangan Nutrisi Lemak :"+penalty3);
                
                feed.getAreaKromosom().append("3. Kebutuhan Lemak tidak terpenuhi"+"\n");
                feed.getAreaKromosom().append("  Kekurangan Nutrisi Lemak :"+penalty3+"\n");
            }
            
            if (totalSerat > kebNutSe) {
//                System.out.println("4. Kebutuhan Serat Terpenuhi");
                feed.getAreaKromosom().append("4. Kebutuhan Serat terpenuhi"+"\n");
            }else{
//                System.out.println("4. Kebutuhan Serat tidak terpenuhi");
                penalty4 = kebNutSe - totalSerat;
//                System.out.println("  Kekurangan Nutrisi Serat :"+penalty4);
                
                feed.getAreaKromosom().append("4. Kebutuhan Serat tidak terpenuhi"+"\n");
                feed.getAreaKromosom().append("  Kekurangan Nutrisi Serat :"+penalty4+"\n");
            }
            
            if (totalKalsium > kebNutKa) {
//                System.out.println("5. Kebutuhan Kalsium Terpenuhi");
                feed.getAreaKromosom().append("5. Kebutuhan Kalsium terpenuhi"+"\n");
            }else{
//                System.out.println("5. Kebutuhan Kalsium tidak terpenuhi");
                penalty5 = kebNutKa - totalKalsium;
//                System.out.println("  Kekurangan Nutrisi Kalsium :"+penalty5);
                
                feed.getAreaKromosom().append("5. Kebutuhan Kalsium tidak terpenuhi"+"\n");
                feed.getAreaKromosom().append("  Kekurangan Nutrisi Kalsium :"+penalty5+"\n");
            }
            
            if (totalFosfor > kebNutFo) {
//                System.out.println("6. Kebutuhan Fosfor Terpenuhi");
                feed.getAreaKromosom().append("6. Kebutuhan Fosfor terpenuhi"+"\n");
            }else{
//                System.out.println("6. Kebutuhan Fosfor tidak terpenuhi");
                penalty6 = kebNutFo - totalFosfor;
//                System.out.println("  Kekurangan Nutrisi Fosfor :"+penalty6);
                
                feed.getAreaKromosom().append("6. Kebutuhan Fosfor tidak terpenuhi"+"\n");
                feed.getAreaKromosom().append("  Kekurangan Nutrisi Fosfor :"+penalty6+"\n");
            }
            double totalPenalty = penalty1+penalty2+penalty3+penalty4+penalty5+penalty6;
//            System.out.println("Total Penalty : "+totalPenalty);
//            System.out.println("");
            
            feed.getAreaKromosom().append("Total Penalty : "+totalPenalty+"\n");
            feed.getAreaKromosom().append("\n");
//            hitung Fitness
//            System.out.println("---------Fitness--------");
            feed.getAreaKromosom().append("---------Fitness--------"+"\n");
            panel_fit.getTextFitness().append("--------- Fitness Populasi --------"+"\n");
            double fitness = 100 /(totalCost +(totalPenalty*100));
            popAwal[i][5] = fitness;
            
//            System.out.println("tot cost:"+totalCost);
//            System.out.println("tot Pen:"+totalPenalty);
//            System.out.println("Nilai Fitness P"+(i+1)+" :"+df.format(fitness));
//            System.out.println("");
//            System.out.println("=================================================================");
            
            feed.getAreaKromosom().append("Nilai Fitness P"+(i+1)+" :"+df.format(fitness)+"\n");
            feed.getAreaKromosom().append("\n");
            feed.getAreaKromosom().append("//============================================================//"+"\n");
            
            panel_fit.getTextFitness().append("Nilai Fitness P"+(i+1)+" :"+df.format(fitness)+"\n");
            panel_fit.getTextFitness().append(" "+"\n");
        }
        panel_fit.getTextFitness().append("//============================================================//"+"\n");
    }
    
    public void crossover(){
        popSize = genModel.getPopSize();
        pc = genModel.getProbCross();
        int osCrossover = (int) Math.ceil(pc*popSize);
        
        if (osCrossover % 2 == 0) {
                crossover = osCrossover;
                jumlahOffspringC = osCrossover * 2;
            }else{
                crossover = (osCrossover + 1);
                jumlahOffspringC = (osCrossover + 1) * 2;
            }
        this.cc = new double[crossover][individu + 1];
        this.offspringC = new double[jumlahOffspringC][individu + 1];
        panel_pop.getTextPop().append("Crossover Rate : "+pc+"\n");
        panel_pop.getTextPop().append("Offspring Crossover yang dibangkitakan sebanyak : "+crossover+"\n");
        for ( int j = 0; j < crossover; j++) {
            
            // pilih populasi dengan acak        
            int p1 = r.nextInt(popSize);
            int p2 = r.nextInt(popSize);
            int a = 0;
            while(p2 == p1){
                p2 = r.nextInt(popSize);
                a++;
            }
            
//            System.out.println("Proses Crossover one-cut-point");
//            System.out.println("Individu acak 1: P"+(p1+1));
//            System.out.println("Individu acak 2: P" +(p2+1));

            
            panel_pop.getTextPop().append("Proses Crossover one-cut-point"+"\n");
            panel_pop.getTextPop().append("Individu acak 1: P"+(p1+1)+"\n");
            panel_pop.getTextPop().append("Individu acak 2: P"+(p2+1)+"\n");
            
            // acak titik potong
            int cutPoint = r.nextInt(5);
//            System.out.println("cut: "+cutPoint);
            panel_pop.getTextPop().append("Titik potong berada pada index : "+cutPoint+"\n");
            panel_pop.getTextPop().append(" "+"\n");
            
            //proses crossover one-cut-point-crossover
            double tempCC[][] = new double[cc.length][individu + 1];
            System.arraycopy(popAwal[p1], 0, tempCC[0], 0, individu);
//            System.arraycopy(popAwal[p2], 0, tempCC[1], 0, individu);
            for (int i = 0; i < individu; i++) {
                if (i >= cutPoint) {
                    tempCC[0][i] = popAwal[p2][i];
//                    tempCC[1][i] = popAwal[p1][i];
                }
            }
            System.arraycopy(tempCC, 0,this.offspringC, j, tempCC.length);
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

        double kebNutMe = (a / 1000) * konsumsi;
        double kebNutPr = (b / 100)*konsumsi;
        double kebNutLe = (c / 100)*konsumsi;
        double kebNutSe = (d / 100)*konsumsi;
        double kebNutKa = (e / 100)*konsumsi;
        double kebNutFo = (f / 100)*konsumsi;

        panel_pop.getTextPop().append("Crossover Offspring : "+"\n");
//        System.out.println("Crossover Offspring : ");
        for (int x = 0; x < crossover; x++) {
            panel_pop.getTextPop().append("Offspring : "+"\n");
            for (int j = 0; j < individu; j++) {
//                System.out.print(offspringC[x][j] + " ");
                panel_pop.getTextPop().append("- "+offspringC[x][j]+", "+"\n");
                panel_pop.getTextPop().append(" "+"\n");
            }
//            System.out.println();
        }
        for (int i = 0; i < crossover; i++) {
            
//        System.out.println("---------------- Evaluasi Crossover ----------------");
        
            double p1 = offspringC[i][0];
            double p2 = offspringC[i][1];
            double p3 = offspringC[i][2];
            double p4 = offspringC[i][3];
            double p5 = offspringC[i][4];
            double total = p1+p2+p3+p4+p5;

            double harga1 = p1/total*konsumsi;
            double harga2 = p2/total*konsumsi;
            double harga3 = p3/total*konsumsi;
            double harga4 = p4/total*konsumsi;
            double harga5 = p5/total*konsumsi;
            double totalHarga = harga1+harga2+harga3+harga4+harga5;
       
            double cost1 = harga1/1000*hargaKedelai;
            double cost2 = harga2/1000*hargaKelapa;
            double cost3 = harga3/1000*hargaKacang;
            double cost4 = harga4/1000*hargaIkan;
            double cost5 = harga5/1000*hargaUdang;
            double totalCost = cost1+cost2+cost3+cost4+cost5;
            
            double kedelai1 = harga1*me1/1000;
            double kedelai2 = harga1*pro1/100;
            double kedelai3 = harga1*lem1/100;
            double kedelai4 = harga1*ser1/100;
            double kedelai5 = harga1*kal1/100;
            double kedelai6 = harga1*fos1/100;
               
            double kelapa1 = harga2*me2/1000;
            double kelapa2 = harga2*pro2/100;
            double kelapa3 = harga2*lem2/100;
            double kelapa4 = harga2*ser2/100;
            double kelapa5 = harga2*kal2/100;
            double kelapa6 = harga2*fos2/100;
                     
            double kacang1 = harga3*me3/1000;
            double kacang2 = harga3*pro3/100;
            double kacang3 = harga3*lem3/100;
            double kacang4 = harga3*ser3/100;
            double kacang5 = harga3*kal3/100;
            double kacang6 = harga3*fos3/100;

            double ikan1 = harga4*me4/1000;
            double ikan2 = harga4*pro4/100;
            double ikan3 = harga4*lem4/100;
            double ikan4 = harga4*ser4/100;
            double ikan5 = harga4*kal4/100;
            double ikan6 = harga4*fos4/100;

            double udang1 = harga5*me5/1000;
            double udang2 = harga5*pro5/100;
            double udang3 = harga5*lem5/100;
            double udang4 = harga5*ser5/100;
            double udang5 = harga5*kal5/100;
            double udang6 = harga5*fos5/100;
            
            double totalMe = kedelai1+kelapa1+kacang1+ikan1+udang1;
            double totalProtein = kedelai2+kelapa2+kacang2+ikan2+udang2;
            double totalLemak = kedelai3+kelapa3+kacang3+ikan3+udang3;
            double totalSerat = kedelai4+kelapa4+kacang4+ikan4+udang4;
            double totalKalsium = kedelai5+kelapa5+kacang5+ikan5+udang5;
            double totalFosfor = kedelai6+kelapa6+kacang6+ikan6+udang6;
            
//            hitung penalty
            double penalty1=0;
            double penalty2=0;
            double penalty3=0;
            double penalty4=0;
            double penalty5=0;
            double penalty6=0;
            
//            System.out.println("");
//            System.out.println("Hitung Penalty");
            
            if (totalMe > kebNutMe) {
//                System.out.println("1. Kebutuhan ME terpenuhi");
            }else{
//                System.out.println("1. Kebutuhan ME tidak terpenuhi");
                penalty1 = kebNutMe - totalMe;
//                System.out.println("  Kekurangan Nutrisi ME : "+penalty1);
            }
            
            if (totalProtein > kebNutPr) {
//                System.out.println("2. Kebutuhan Protein Terpenuhi");

            }else{
//                System.out.println("2. Kebutuhan Protein tidak terpenuhi");
                penalty2 = kebNutPr - totalProtein;
//                System.out.println("  Kekurangan Nutrisi Protein : "+penalty2);
            }
            
            if (totalLemak > kebNutLe) {
//                System.out.println("3. Kebutuhan Lemak Terpenuhi");
            }else{
//                System.out.println("3. Kebutuhan Lemak tidak terpenuhi");
                penalty3 = kebNutLe - totalLemak;
//                System.out.println("  Kekurangan Nutrisi Lemak :"+penalty3);

            }
            
            if (totalSerat > kebNutSe) {
//                System.out.println("4. Kebutuhan Serat Terpenuhi");
            }else{
//                System.out.println("4. Kebutuhan Serat tidak terpenuhi");
                penalty4 = kebNutSe - totalSerat;
//                System.out.println("  Kekurangan Nutrisi Serat :"+penalty4);
            }
            
            if (totalKalsium > kebNutKa) {
//                System.out.println("5. Kebutuhan Kalsium Terpenuhi");
            }else{
//                System.out.println("5. Kebutuhan Kalsium tidak terpenuhi");
                penalty5 = kebNutKa - totalKalsium;
//                System.out.println("  Kekurangan Nutrisi Kalsium :"+penalty5);
            }
            
            if (totalFosfor > kebNutFo) {
//                System.out.println("6. Kebutuhan Fosfor Terpenuhi");
            }else{
//                System.out.println("6. Kebutuhan Fosfor tidak terpenuhi");
                penalty6 = kebNutFo - totalFosfor;
//                System.out.println("  Kekurangan Nutrisi Fosfor :"+penalty6);
                
            }
            double totalPenalty = penalty1+penalty2+penalty3+penalty4+penalty5+penalty6;
//            System.out.println("Total Penalty : "+totalPenalty);
//            System.out.println("");
            
//            hitung Fitness
//            System.out.println("---------Fitness--------");
            double fitness = 100 /(totalCost +(totalPenalty*100));
            offspringC[i][5] = fitness;
            
            df = new DecimalFormat("#.######");
//            System.out.println("tot cost:"+totalCost);
//            System.out.println("tot Pen:"+totalPenalty);
//            System.out.println("Nilai Fitness :"+df.format(fitness));
//            System.out.println("");
            
//            System.out.println("Crossover dan fitness");
//            for (int k = 0; k < crossover; k++) {
//            for (int j = 0; j < individu + 1; j++) {
//                System.out.print(df.format(offspringC[k][j])+ " ");
//            }
//            System.out.println();
//            }
            
//            System.out.println("//============================================================//");
        }
    }
    
    public void printFitnessCrossover(){
        for (int k = 0; k < crossover; k++) {
                panel_fit.getTextFitness().append("Offspring :"+"\n");
            for (int j = 0; j < individu + 1; j++) {
                panel_fit.getTextFitness().append("- "+df.format(offspringC[k][j])+"\n");
            }
                panel_fit.getTextFitness().append(" "+"\n");
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
        mutasi = osMutation;
        jumlahOffspringM = mutasi * 2;
        
        this.cm = new double[mutasi][individu + 1];
        this.offspringM = new double[jumlahOffspringM][individu + 1];
        
        panel_mut.getTextMutasi().append("Mutation Rate : "+pm+"\n");
        panel_mut.getTextMutasi().append("Offspring mutasi yang dibangkitkan sebanyak : "+mutasi+"\n");
        for (int i = 0; i < mutasi; i++) {
            int b = 0;
            int p1 = new Random().nextInt(popSize);
            
//            System.out.println("Proses Mutasi Reciprocal-exchange-mutation");
            panel_mut.getTextMutasi().append("Proses Mutasi Reciprocal-exchange-mutation"+"\n");
//            System.out.println("Mutasi pada kromosom : P"+(p1+1));
            panel_mut.getTextMutasi().append("Mutasi pada kromosom : P"+(p1+1)+"\n");
            
            int pos1 = new Random().nextInt(5);
//            System.out.println("Mutation point 1 : index "+pos1);
            panel_mut.getTextMutasi().append("Mutation point 1 : index "+pos1+"\n");
            
//            int pos2 = getRandomWithExclusion(r, 0, individu - 1, pos1);
            int pos2 = new Random().nextInt(5);
            while(pos2 == pos1){
                pos2 = getRandomWithExclusion(r, 0, individu - 1, pos1);
                b++;
            }
            
//            System.out.println("Mutation point 2 : index "+pos2);
            panel_mut.getTextMutasi().append("Mutation point 2 : index "+pos2+"\n");
            
//          proses mutasi reciprocal exchange mutation
            double tempM[][] = new double [cm.length][individu + 1];
            System.arraycopy(popAwal[p1], 0, tempM[0], 0, individu);
           
            tempM[0][pos1] = popAwal[p1][pos2];
            tempM[0][pos2] = popAwal[p1][pos1];
            
            System.arraycopy(tempM, 0, offspringM, i, tempM.length);
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

        double kebNutMe = (a / 1000) * konsumsi;
        double kebNutPr = (b / 100)*konsumsi;
        double kebNutLe = (c / 100)*konsumsi;
        double kebNutSe = (d / 100)*konsumsi;
        double kebNutKa = (e / 100)*konsumsi;
        double kebNutFo = (f / 100)*konsumsi;
        
//        System.out.println("Mutation: ");
        for (int z = 0; z < mutasi; z++) {
            panel_mut.getTextMutasi().append("Offspring Mutasi : "+"\n");
            for (int j = 0; j < individu + 1; j++) {
//                System.out.print(offspringM[z][j] + " ");
                panel_mut.getTextMutasi().append("- "+offspringM[z][j]+", "+"\n");
                panel_mut.getTextMutasi().append(" "+"\n");
            }
//            System.out.println();
        }
        
        for (int i = 0; i < mutasi; i++) {
            double p1 = offspringM[i][0];
            double p2 = offspringM[i][1];
            double p3 = offspringM[i][2];
            double p4 = offspringM[i][3];
            double p5 = offspringM[i][4];
            double total = p1+p2+p3+p4+p5;

            double harga1 = p1/total*konsumsi;
            double harga2 = p2/total*konsumsi;
            double harga3 = p3/total*konsumsi;
            double harga4 = p4/total*konsumsi;
            double harga5 = p5/total*konsumsi;
            double totalHarga = harga1+harga2+harga3+harga4+harga5;
 
            double cost1 = harga1/1000*hargaKedelai;
            double cost2 = harga2/1000*hargaKelapa;
            double cost3 = harga3/1000*hargaKacang;
            double cost4 = harga4/1000*hargaIkan;
            double cost5 = harga5/1000*hargaUdang;
            double totalCost = cost1+cost2+cost3+cost4+cost5;
    
            double kedelai1 = harga1*me1/1000;
            double kedelai2 = harga1*pro1/100;
            double kedelai3 = harga1*lem1/100;
            double kedelai4 = harga1*ser1/100;
            double kedelai5 = harga1*kal1/100;
            double kedelai6 = harga1*fos1/100;

            double kelapa1 = harga2*me2/1000;
            double kelapa2 = harga2*pro2/100;
            double kelapa3 = harga2*lem2/100;
            double kelapa4 = harga2*ser2/100;
            double kelapa5 = harga2*kal2/100;
            double kelapa6 = harga2*fos2/100;

            double kacang1 = harga3*me3/1000;
            double kacang2 = harga3*pro3/100;
            double kacang3 = harga3*lem3/100;
            double kacang4 = harga3*ser3/100;
            double kacang5 = harga3*kal3/100;
            double kacang6 = harga3*fos3/100;

            double ikan1 = harga4*me4/1000;
            double ikan2 = harga4*pro4/100;
            double ikan3 = harga4*lem4/100;
            double ikan4 = harga4*ser4/100;
            double ikan5 = harga4*kal4/100;
            double ikan6 = harga4*fos4/100;

            double udang1 = harga5*me5/1000;
            double udang2 = harga5*pro5/100;
            double udang3 = harga5*lem5/100;
            double udang4 = harga5*ser5/100;
            double udang5 = harga5*kal5/100;
            double udang6 = harga5*fos5/100;

            double totalMe = kedelai1+kelapa1+kacang1+ikan1+udang1;
            double totalProtein = kedelai2+kelapa2+kacang2+ikan2+udang2;
            double totalLemak = kedelai3+kelapa3+kacang3+ikan3+udang3;
            double totalSerat = kedelai4+kelapa4+kacang4+ikan4+udang4;
            double totalKalsium = kedelai5+kelapa5+kacang5+ikan5+udang5;
            double totalFosfor = kedelai6+kelapa6+kacang6+ikan6+udang6;

//            hitung penalty
            double penalty1=0;
            double penalty2=0;
            double penalty3=0;
            double penalty4=0;
            double penalty5=0;
            double penalty6=0;
            
//            System.out.println("");
//            System.out.println("Hitung Penalty");
            
            if (totalMe > kebNutMe) {
//                System.out.println("1. Kebutuhan ME terpenuhi");
            }else{
//                System.out.println("1. Kebutuhan ME tidak terpenuhi");
                penalty1 = kebNutMe - totalMe;
//                System.out.println("  Kekurangan Nutrisi ME : "+penalty1);
                
            }
            
            if (totalProtein > kebNutPr) {
//                System.out.println("2. Kebutuhan Protein Terpenuhi");

            }else{
//                System.out.println("2. Kebutuhan Protein tidak terpenuhi");
                penalty2 = kebNutPr - totalProtein;
//                System.out.println("  Kekurangan Nutrisi Protein : "+penalty2);
                
            }
            
            if (totalLemak > kebNutLe) {
//                System.out.println("3. Kebutuhan Lemak Terpenuhi");

            }else{
//                System.out.println("3. Kebutuhan Lemak tidak terpenuhi");
                penalty3 = kebNutLe - totalLemak;
//                System.out.println("  Kekurangan Nutrisi Lemak :"+penalty3);
                
            }
            
            if (totalSerat > kebNutSe) {
//                System.out.println("4. Kebutuhan Serat Terpenuhi");

            }else{
//                System.out.println("4. Kebutuhan Serat tidak terpenuhi");
                penalty4 = kebNutSe - totalSerat;
//                System.out.println("  Kekurangan Nutrisi Serat :"+penalty4);
                
            }
            
            if (totalKalsium > kebNutKa) {
//                System.out.println("5. Kebutuhan Kalsium Terpenuhi");

            }else{
//                System.out.println("5. Kebutuhan Kalsium tidak terpenuhi");
                penalty5 = kebNutKa - totalKalsium;
//                System.out.println("  Kekurangan Nutrisi Kalsium :"+penalty5);
                
            }
            
            if (totalFosfor > kebNutFo) {
//                System.out.println("6. Kebutuhan Fosfor Terpenuhi");
            }else{
//                System.out.println("6. Kebutuhan Fosfor tidak terpenuhi");
                penalty6 = kebNutFo - totalFosfor;
//                System.out.println("  Kekurangan Nutrisi Fosfor :"+penalty6);
                
            }
            double totalPenalty = penalty1+penalty2+penalty3+penalty4+penalty5+penalty6;
//            System.out.println("Total Penalty : "+totalPenalty);

//            System.out.println("");
            
//            hitung Fitness
//            System.out.println("---------Fitness--------");

            double fitness = 100 /(totalCost +(totalPenalty*100));
            offspringM[i][5] = fitness;
            
            df = new DecimalFormat("#.######");
//            System.out.println("tot cost:"+totalCost);
//            System.out.println("tot Pen:"+totalPenalty);
//            System.out.println("Nilai Fitness :"+df.format(fitness));

//            System.out.println("");
//            System.out.println("Mutasi dan fitness");
//            for (int y = 0; y < mutasi; y++) {
//            for (int j = 0; j < individu + 1; j++) {
//                System.out.print(df.format(offspringM[y][j]) + " ");
//            }
//            System.out.println();
//            }
//            System.out.println();
        }
        
    }
    
    public void printFitnessMutasi(){
        for (int y = 0; y < mutasi; y++) {
                panel_fit.getTextFitness().append("Offspring : "+"\n");
            for (int j = 0; j < individu + 1; j++) {
//                System.out.print(df.format(offspringM[y][j]) + " ");
                panel_fit.getTextFitness().append("- "+df.format(offspringM[y][j]) +"\n");
            }
        }
    }
    
    public void seleksiRoullete(){
        popSize = genModel.getPopSize();
        double popSeleksi[][] = new double[popSize+this.offspringC.length/2+this.offspringM.length/2]
                                [individu + 1];
        
        System.arraycopy(popAwal, 0, popSeleksi, 0, popSize);
        System.arraycopy(offspringC, 0, popSeleksi, popSize, offspringC.length/2);
        System.arraycopy(offspringM, 0, popSeleksi, popSize + offspringC.length/2, offspringM.length/2);
        
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
        
//        System.out.println("Probabilitas: ");
        panel_sel.getTextSeleksi().append("Probabilitas Seleksi: "+"\n");
        for (double[] prob1 : prob) {
            for (int j = 0; j < prob1.length; j++) {
//                System.out.printf("%-20.10f", prob1[j]);
                panel_sel.getTextSeleksi().append(df.format(prob1[j])+"\n");
            }
//            System.out.println();
        }
//        System.out.println();
//        
        // lakukan roulette wheel
        double p_rw[][];
        p_rw = rouletteWheel(popSeleksi, prob);
        
//        System.out.println("** Hasil Roulette Wheel **");
        
        panel_sel.getTextSeleksi().append("** Hasil Roulette Wheel **"+"\n");
        panel_sel.getTextSeleksi().append("\n");
        
        for (int i = 0; i <popSize; i++) {
            panel_sel.getTextSeleksi().append("Seleksi :"+(i+1)+"\n");
            for (int j = 0; j < individu + 1; j++) {
                
                panel_sel.getTextSeleksi().append("- "+df.format(p_rw[i][j])+"\n");
                panel_sel.getTextSeleksi().append("\n");
            }
        }
        
        for (double[] p_rw1 : p_rw) {
            for (int j = 0; j < p_rw1.length; j++) {
//                System.out.print(df.format(p_rw1[j]) + " ");
            }
//            System.out.println();
        }
//        System.out.println();
        
        // update populasi
        updatePopulasi(p_rw);
    }
    
    public void seleksiElitism(){
        popSize = genModel.getPopSize();
        double popSeleksi[][] = new double[popSize+this.offspringC.length/2+this.offspringM.length/2]
                                [individu + 1];
        
        System.arraycopy(popAwal, 0, popSeleksi, 0, popSize);
        System.arraycopy(offspringC, 0, popSeleksi, popSize, offspringC.length/2);
        System.arraycopy(offspringM, 0, popSeleksi, popSize + offspringC.length/2, offspringM.length/2);
        
        double p_rw[][];
        p_rw = elitism(popSeleksi);
        
        
        
        panel_sel.getTextSeleksi().append("** Hasil ELITISM SELECTION **"+"\n");
        panel_sel.getTextSeleksi().append("\n");
        
        for (int i = 0; i <popSize; i++) {
            panel_sel.getTextSeleksi().append("Ranking Ke-"+(i+1)+"\n");
            
            for (int j = 0; j < individu + 1; j++) {
                panel_sel.getTextSeleksi().append("- "+df.format(p_rw[i][j])+"\n");
                panel_sel.getTextSeleksi().append("\n");
            }
        }
//        System.out.println("\n");
//        System.out.println("** Hasil ELITISM SELECTION **");
        for (double[] p_rw1 : p_rw) {
            for (int j = 0; j < p_rw1.length; j++) {
//                System.out.print(df.format(p_rw1[j]) + " ");
            }
//            System.out.println();
        }
//        System.out.println();
        
        // update populasi
        updatePopulasi(p_rw);
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
        System.arraycopy(temp, 0, hasil, 0, popSize);
        return hasil;
    }
    
    public double[][] rouletteWheel(double sel[][], double prob[][]){
        double hasil[][] = new double[this.popAwal.length][this.popAwal[0].length];
        for (int i = 0; i < hasil.length; i++) {
            double r = Math.random();
//            System.out.println((i+1)+". tes nilai random: "+r);
            for (int j = sel.length-1; j >= 0; j--) {
                if(r <= prob[j][2]){
                    hasil[i] = sel[j];
                }
            }
        }
        return hasil;
    }
    
    public void populasiBaru(){
        panel_sel.getTextSeleksi().append("---------- POPULASI BARU SETELAH ITERASI -----------"+"\n");
        feed.getAreaKromosom().append("---------- POPULASI BARU SETELAH ITERASI -----------"+"\n");
        for (int i = 0; i < popSize; i++) {
            panel_sel.getTextSeleksi().append("--- Kromosom Baru ---"+"\n");
            feed.getAreaKromosom().append("--- Kromosom Baru ---"+"\n");
            for (int j = 0; j < individu + 1; j++) {
                panel_sel.getTextSeleksi().append("- "+df.format(popAwal[i][j])+"\n");
                panel_sel.getTextSeleksi().append(" "+"\n");
                
                feed.getAreaKromosom().append("- "+df.format(popAwal[i][j])+"\n");
                feed.getAreaKromosom().append(" "+"\n");
            }
        }
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
//         System.out.println("Individu Terbaik: ");
        for (int j = 0; j < individu; j++) {
//            System.out.print(df.format(pTerbaik[j])+" ");
        }  
    }
    
    public void hasilOptimasi(){
        int konsumsi = genModel.getKonsumsi();
        int banyakAyam = genModel.getAyam();
        
        double hargaKedelai = genModel.getHarga1();
        double hargaKelapa = genModel.getHarga2();
        double hargaKacang = genModel.getHarga3();
        double hargaIkan = genModel.getHarga4();
        double hargaUdang = genModel.getHarga5();
        
//        System.out.println("Individu Terbaik: ");
        panel_hasil.getTexthasil().append("-- Individu Terbaik --"+"\n");
        for (int j = 0; j < individu; j++) {
//            System.out.print(df.format(pTerbaik[j])+" ");
            panel_hasil.getTexthasil().append(df.format(pTerbaik[j])+", ");
        }
        panel_hasil.getTexthasil().append(" "+"\n");
        panel_hasil.getTexthasil().append("Dengan Nilai Fitness : "+df.format(pTerbaik[5]));
        
        String kedelai = Double.toString(pTerbaik[0]);
//        result.getPersenKedelai().setText(kedelai+" %");
        
        String kelapa = Double.toString(pTerbaik[1]);
//        result.getPersenKelapa().setText(kelapa+" %");
        
        String kacang = Double.toString(pTerbaik[2]);
//        result.getPersenKacang().setText(kacang+" %");
        
        String ikan = Double.toString(pTerbaik[3]);
//        result.getPersenIkan().setText(ikan+" %");
        
        String udang = Double.toString(pTerbaik[4]);
//        result.getPersenUdang().setText(udang+" %");
                    
        double bahan1 = pTerbaik[0];
        double bahan2 = pTerbaik[1];
        double bahan3 = pTerbaik[2];
        double bahan4 = pTerbaik[3];
        double bahan5 = pTerbaik[4];
        
//        System.out.println("");
        double totalBahan = bahan1+bahan2+bahan3+bahan4+bahan5;

            String pakan = Integer.toString(konsumsi);
            result.getBeratPakan().setText(pakan+" gram");

            String ayam = Integer.toString(banyakAyam);
            result.getBanyakAyam().setText(ayam+" ekor");
            
        double b1 = (bahan1/totalBahan*konsumsi);
        String be1 = Double.toString(Math.round(b1));
        result.getBeratKedelai().setText((be1)+" gram");
        
        double b2 = (bahan2/totalBahan*konsumsi);
        String be2 = Double.toString(Math.round(b2));
        result.getBeratKelapa().setText((be2)+" gram");
        
        double b3 = (bahan3/totalBahan*konsumsi);
        String be3 = Double.toString(Math.round(b3));
        result.getBeratKacang().setText((be3)+" gram");
        
        double b4 = (bahan4/totalBahan*konsumsi);
        String be4 = Double.toString(Math.round(b4));
        result.getBeratIkan().setText((be4)+" gram");
        
        double b5 = (bahan5/totalBahan*konsumsi);
        String be5 = Double.toString(Math.round(b5));
        result.getBeratUdang().setText((be5)+" gram");
            
        double konversi1 = (bahan1/totalBahan*konsumsi)*banyakAyam;
        double konversi2 = (bahan2/totalBahan*konsumsi)*banyakAyam;
        double konversi3 = (bahan3/totalBahan*konsumsi)*banyakAyam;
        double konversi4 = (bahan4/totalBahan*konsumsi)*banyakAyam;
        double konversi5 = (bahan5/totalBahan*konsumsi)*banyakAyam;
        
        double bobot1 = bahan1/totalBahan*konsumsi;
        double bobot2 = bahan2/totalBahan*konsumsi;
        double bobot3 = bahan3/totalBahan*konsumsi;
        double bobot4 = bahan4/totalBahan*konsumsi;
        double bobot5 = bahan5/totalBahan*konsumsi;
        
            double biaya1 = bobot1/1000*hargaKedelai;
            double biaya2 = bobot2/1000*hargaKelapa;
            double biaya3 = bobot3/1000*hargaKacang;
            double biaya4 = bobot4/1000*hargaIkan;
            double biaya5 = bobot5/1000*hargaUdang;
        
        
        result.getPersenKedelai().setText("Rp. "+df2.format(biaya1));
        result.getPersenKelapa().setText("Rp. "+df2.format(biaya2));
        result.getPersenKacang().setText("Rp. "+df2.format(biaya3));
        result.getPersenIkan().setText("Rp. "+df2.format(biaya4));
        result.getPersenUdang().setText("Rp. "+df2.format(biaya5));
//            System.out.println("");
             panel_harga.getArea_harga().append(" Komposisi Bahan Pakan Untuk "+banyakAyam+" ekor ayam"+"\n");
//            System.out.println("Kedelai : "+konversi1+" gram");
            String kon1 = Double.toString(Math.round(konversi1));
            result.getTotalKedelai().setText((kon1)+" gram");
             panel_harga.getArea_harga().append(" Total Kedelai : "+kon1+" gram"+"\n");
//            System.out.println("Bungkil Kelapa : "+konversi2+" gram");

            String kon2 = Double.toString(Math.round(konversi2));
            result.getTotalKelapa().setText((kon2)+" gram");
             panel_harga.getArea_harga().append(" Total Bungkil kelapa : "+kon2+" gram"+"\n");
//            System.out.println("Bungkil Kacang : "+konversi3+" gram");

            String kon3 = Double.toString(Math.round(konversi3));
            result.getTotalKacang().setText((kon3)+" gram");
             panel_harga.getArea_harga().append(" Total Bungkil kacang : "+kon3+" gram"+"\n");
             
//            System.out.println("Tepung Ikan : "+konversi4+" gram");
            String kon4 = Double.toString(Math.round(konversi4));
            result.getTotalIkan().setText((kon4)+" gram");
             panel_harga.getArea_harga().append(" Total Tepung ikan : "+kon4+" gram"+"\n");
             
//            System.out.println("Limbah Udang : "+konversi5+" gram");
            String kon5 = Double.toString(Math.round(konversi5));
            result.getTotalUdang().setText((kon5)+" gram");
             panel_harga.getArea_harga().append(" Total Limbah udang : "+kon5+" gram"+"\n");
             panel_harga.getArea_harga().append(" "+"\n");
             
//            System.out.println("");
            double cost1 = konversi1/1000*hargaKedelai;
            double cost2 = konversi2/1000*hargaKelapa;
            double cost3 = konversi3/1000*hargaKacang;
            double cost4 = konversi4/1000*hargaIkan;
            double cost5 = konversi5/1000*hargaUdang;
            double totalCost = cost1+cost2+cost3+cost4+cost5;
            double konversiCost = (int) Math.round(totalCost);
            
             panel_harga.getArea_harga().append(" Total harga bahan pakan adalah : "+"\n");
//            System.out.println("Harga komposisi kedelai : Rp. "+Math.round(cost1));
            String price1 = Double.toString(Math.round(cost1));
            result.getHargaKedelai().setText("Rp. "+price1);
             panel_harga.getArea_harga().append(" Kedelai : Rp. "+price1+"\n");
            
//            System.out.println("Harga komposisi bungkil kelapa : Rp. "+Math.round(cost2));
            String price2 = Double.toString(Math.round(cost2));
            result.getHargaKelapa().setText("Rp. "+price2);
             panel_harga.getArea_harga().append(" Bungkil kelapa : Rp. "+price2+"\n");
            
//            System.out.println("Harga komposisi bungkil kacang : Rp. "+Math.round(cost3));
            String price3 = Double.toString(Math.round(cost3));
            result.getHargaKacang().setText("Rp. "+price3);
             panel_harga.getArea_harga().append(" Bungkil kacang : Rp. "+price3+"\n");
            
//            System.out.println("Harga komposisi tepung ikan : Rp. "+Math.round(cost4));
            String price4 = Double.toString(Math.round(cost4));
            result.getHargaIkan().setText("Rp. "+price4);
             panel_harga.getArea_harga().append(" Bungkil kacang : Rp. "+price4+"\n");
            
//            System.out.println("Harga komposisi limbah udang : Rp. "+Math.round(cost5));
            String price5 = Double.toString(Math.round(cost5));
            result.getHargaUdang().setText("Rp. "+price5);
             panel_harga.getArea_harga().append(" Limbah udang : Rp. "+price5+"\n");
            
//            System.out.println("");
//            System.out.println("Total Harga Bahan Pakan yang optimal : Rp. "+konversiCost);
            String totalPrice = Double.toString(Math.round(konversiCost));
            result.getTotalHarga().setText("Rp. "+totalPrice);
            
             panel_harga.getArea_harga().append(" "+"\n");
              panel_harga.getArea_harga().append(" Total harga komposisi bahan pakan : Rp. "+totalPrice+"\n");
    }
    
    public void printHasil(){
        int konsumsi = genModel.getKonsumsi();
        int ayam = genModel.getAyam();
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

        double kebNutMe = (a / 1000) * konsumsi;
        double kebNutPr = (b / 100)*konsumsi;
        double kebNutLe = (c / 100)*konsumsi;
        double kebNutSe = (d / 100)*konsumsi;
        double kebNutKa = (e / 100)*konsumsi;
        double kebNutFo = (f / 100)*konsumsi;
        
            double p1 = pTerbaik[0];
            double p2 = pTerbaik[1];
            double p3 = pTerbaik[2];
            double p4 = pTerbaik[3];
            double p5 = pTerbaik[4];
            double total = p1+p2+p3+p4+p5;

            double harga1 = p1/total*konsumsi;
            double harga2 = p2/total*konsumsi;
            double harga3 = p3/total*konsumsi;
            double harga4 = p4/total*konsumsi;
            double harga5 = p5/total*konsumsi;
            double totalHarga = harga1+harga2+harga3+harga4+harga5;
            
            //untuk peritungan harga
            panel_harga.getArea_harga().append("  PENENTUAN HARGA BAHAN PAKAN "+"\n");
            panel_harga.getArea_harga().append(" "+"\n");
            panel_harga.getArea_harga().append(" Banyak Ayam = "+ayam+"\n");
            panel_harga.getArea_harga().append(" "+"\n");
            panel_harga.getArea_harga().append("Harga Kedelai : Rp. "+hargaKedelai+" /kg"+"\n");
            panel_harga.getArea_harga().append("Harga Bungkil kelapa : Rp. "+hargaKelapa+" /kg"+"\n");
            panel_harga.getArea_harga().append("Harga Bungkil kacang : Rp. "+hargaKacang+" /kg"+"\n");
            panel_harga.getArea_harga().append("Harga Tepung ikan : Rp. "+hargaIkan+" /kg"+"\n");
            panel_harga.getArea_harga().append("Harga Limbah udang : Rp. "+hargaUdang+" /kg"+"\n");
            panel_harga.getArea_harga().append(" "+"\n");
            panel_harga.getArea_harga().append(" "+"\n");
            
            panel_harga.getArea_harga().append(" Komposisi Bahan Pakan Untuk 1 ekor ayam"+"\n");
            panel_harga.getArea_harga().append(" "+"\n");
            panel_harga.getArea_harga().append(" Kedelai : "+df2.format(harga1)+" gram"+"\n");
            panel_harga.getArea_harga().append(" Bungkil kelapa : "+df2.format(harga2)+" gram"+"\n");
            panel_harga.getArea_harga().append(" Bungkil kacang : "+df2.format(harga3)+" gram"+"\n");
            panel_harga.getArea_harga().append(" Tepung ikan : "+df2.format(harga4)+" gram"+"\n");
            panel_harga.getArea_harga().append(" Limbah udang : "+df2.format(harga5)+" gram"+"\n");
            //
            
            panel_hasil.getTexthasil().append(" "+"\n");
            panel_hasil.getTexthasil().append(" "+"\n");
            panel_hasil.getTexthasil().append(" Bobot Kromosom 1 : "+df2.format(harga1)+" gram"+"\n");
            panel_hasil.getTexthasil().append(" Bobot Kromosom 2 : "+df2.format(harga2)+" gram"+"\n");
            panel_hasil.getTexthasil().append(" Bobot Kromosom 3 : "+df2.format(harga3)+" gram"+"\n");
            panel_hasil.getTexthasil().append(" Bobot Kromosom 4 : "+df2.format(harga4)+" gram"+"\n");
            panel_hasil.getTexthasil().append(" Bobot Kromosom 5 : "+df2.format(harga5)+" gram"+"\n");
            panel_hasil.getTexthasil().append(" "+"\n");
            
            double cost1 = harga1/1000*hargaKedelai;
            double cost2 = harga2/1000*hargaKelapa;
            double cost3 = harga3/1000*hargaKacang;
            double cost4 = harga4/1000*hargaIkan;
            double cost5 = harga5/1000*hargaUdang;
            double totalCost = cost1+cost2+cost3+cost4+cost5;
            
            panel_harga.getArea_harga().append(" Komposisi Bahan Pakan Untuk 1 ekor ayam"+"\n");
            panel_harga.getArea_harga().append(" "+"\n");
            panel_harga.getArea_harga().append(" Kedelai : Rp. "+df2.format(cost1)+"\n");
            panel_harga.getArea_harga().append(" Bungkil kelapa : Rp. "+df2.format(cost2)+"\n");
            panel_harga.getArea_harga().append(" Bungkil kacang : Rp. "+df2.format(cost3)+"\n");
            panel_harga.getArea_harga().append(" Tepung ikan : Rp. "+df2.format(cost4)+"\n");
            panel_harga.getArea_harga().append(" Limbah udang : Rp. "+df2.format(cost5)+"\n");
             panel_harga.getArea_harga().append(" "+"\n");
             
            panel_hasil.getTexthasil().append(" "+"\n");
            panel_hasil.getTexthasil().append(" harga bahan 1 = Rp. "+df2.format(cost1)+"\n");
            panel_hasil.getTexthasil().append(" harga bahan 2 = Rp. "+df2.format(cost2)+"\n");
            panel_hasil.getTexthasil().append(" harga bahan 3 = Rp. "+df2.format(cost3)+"\n");
            panel_hasil.getTexthasil().append(" harga bahan 4 = Rp. "+df2.format(cost4)+"\n");
            panel_hasil.getTexthasil().append(" harga bahan 5 = Rp. "+df2.format(cost5)+"\n");
            panel_hasil.getTexthasil().append(" "+"\n");
            
            double kedelai1 = harga1*me1/1000;
            double kedelai2 = harga1*pro1/100;
            double kedelai3 = harga1*lem1/100;
            double kedelai4 = harga1*ser1/100;
            double kedelai5 = harga1*kal1/100;
            double kedelai6 = harga1*fos1/100;
            panel_hasil.getTexthasil().append(" "+"\n");
            panel_hasil.getTexthasil().append("======== Kandungan Nutrisi Bahan 1 ============"+"\n");
            panel_hasil.getTexthasil().append(" Kedelai"+"\n");
            panel_hasil.getTexthasil().append(" ME : "+df2.format(kedelai1)+" Kkal/gram"+"\n");
            panel_hasil.getTexthasil().append(" Protein : "+df2.format(kedelai2)+" gram"+"\n");
            panel_hasil.getTexthasil().append(" Lemak : "+df2.format(kedelai3)+" gram"+"\n");
            panel_hasil.getTexthasil().append(" Serat : "+df2.format(kedelai4)+" gram"+"\n");
            panel_hasil.getTexthasil().append(" Kalsium : "+df2.format(kedelai5)+" gram"+"\n");
            panel_hasil.getTexthasil().append(" Fosfor : "+df2.format(kedelai6)+" gram"+"\n");
            panel_hasil.getTexthasil().append(" "+"\n");
            
            double kelapa1 = harga2*me2/1000;
            double kelapa2 = harga2*pro2/100;
            double kelapa3 = harga2*lem2/100;
            double kelapa4 = harga2*ser2/100;
            double kelapa5 = harga2*kal2/100;
            double kelapa6 = harga2*fos2/100;
            
            panel_hasil.getTexthasil().append(" "+"\n");
            panel_hasil.getTexthasil().append("======== Kandungan Nutrisi Bahan 2 ============"+"\n");
            panel_hasil.getTexthasil().append(" Bungkil Kelapa"+"\n");
            panel_hasil.getTexthasil().append(" ME : "+df2.format(kelapa1)+" Kkal/gram"+"\n");
            panel_hasil.getTexthasil().append(" Protein : "+df2.format(kelapa2)+" gram"+"\n");
            panel_hasil.getTexthasil().append(" Lemak : "+df2.format(kelapa3)+" gram"+"\n");
            panel_hasil.getTexthasil().append(" Serat : "+df2.format(kelapa4)+" gram"+"\n");
            panel_hasil.getTexthasil().append(" Kalsium : "+df2.format(kelapa5)+" gram"+"\n");
            panel_hasil.getTexthasil().append(" Fosfor : "+df2.format(kelapa6)+" gram"+"\n");
            panel_hasil.getTexthasil().append(" "+"\n");
            
            double kacang1 = harga3*me3/1000;
            double kacang2 = harga3*pro3/100;
            double kacang3 = harga3*lem3/100;
            double kacang4 = harga3*ser3/100;
            double kacang5 = harga3*kal3/100;
            double kacang6 = harga3*fos3/100;
            panel_hasil.getTexthasil().append(" "+"\n");
            panel_hasil.getTexthasil().append("======== Kandungan Nutrisi Bahan 3 ============"+"\n");
            panel_hasil.getTexthasil().append(" Bungkil Kacang"+"\n");
            panel_hasil.getTexthasil().append(" ME : "+df2.format(kacang1)+" Kkal/gram"+"\n");
            panel_hasil.getTexthasil().append(" Protein : "+df2.format(kacang2)+" gram"+"\n");
            panel_hasil.getTexthasil().append(" Lemak : "+df2.format(kacang3)+" gram"+"\n");
            panel_hasil.getTexthasil().append(" Serat : "+df2.format(kacang4)+" gram"+"\n");
            panel_hasil.getTexthasil().append(" Kalsium : "+df2.format(kacang5)+" gram"+"\n");
            panel_hasil.getTexthasil().append(" Fosfor : "+df2.format(kacang6)+" gram"+"\n");
            panel_hasil.getTexthasil().append(" "+"\n");
            
            double ikan1 = harga4*me4/1000;
            double ikan2 = harga4*pro4/100;
            double ikan3 = harga4*lem4/100;
            double ikan4 = harga4*ser4/100;
            double ikan5 = harga4*kal4/100;
            double ikan6 = harga4*fos4/100;
            panel_hasil.getTexthasil().append(" "+"\n");
            panel_hasil.getTexthasil().append("======== Kandungan Nutrisi Bahan 4 ============"+"\n");
            panel_hasil.getTexthasil().append(" Tepung Ikan"+"\n");
            panel_hasil.getTexthasil().append(" ME : "+df2.format(ikan1)+" Kkal/gram"+"\n");
            panel_hasil.getTexthasil().append(" Protein : "+df2.format(ikan2)+" gram"+"\n");
            panel_hasil.getTexthasil().append(" Lemak : "+df2.format(ikan3)+" gram"+"\n");
            panel_hasil.getTexthasil().append(" Serat : "+df2.format(ikan4)+" gram"+"\n");
            panel_hasil.getTexthasil().append(" Kalsium : "+df2.format(ikan5)+" gram"+"\n");
            panel_hasil.getTexthasil().append(" Fosfor : "+df2.format(ikan6)+" gram"+"\n");
            panel_hasil.getTexthasil().append(" "+"\n");
            
            double udang1 = harga5*me5/1000;
            double udang2 = harga5*pro5/100;
            double udang3 = harga5*lem5/100;
            double udang4 = harga5*ser5/100;
            double udang5 = harga5*kal5/100;
            double udang6 = harga5*fos5/100;
            panel_hasil.getTexthasil().append(" "+"\n");
            panel_hasil.getTexthasil().append("======== Kandungan Nutrisi Bahan 5 ============"+"\n");
            panel_hasil.getTexthasil().append(" Limbah Udang"+"\n");
            panel_hasil.getTexthasil().append(" ME : "+df2.format(udang1)+" Kkal/gram"+"\n");
            panel_hasil.getTexthasil().append(" Protein : "+df2.format(udang2)+" gram"+"\n");
            panel_hasil.getTexthasil().append(" Lemak : "+df2.format(udang3)+" gram"+"\n");
            panel_hasil.getTexthasil().append(" Serat : "+df2.format(udang4)+" gram"+"\n");
            panel_hasil.getTexthasil().append(" Kalsium : "+df2.format(udang5)+" gram"+"\n");
            panel_hasil.getTexthasil().append(" Fosfor : "+df2.format(udang6)+" gram"+"\n");
            panel_hasil.getTexthasil().append(" "+"\n");
            
            double totalMe = kedelai1+kelapa1+kacang1+ikan1+udang1;
            double totalProtein = kedelai2+kelapa2+kacang2+ikan2+udang2;
            double totalLemak = kedelai3+kelapa3+kacang3+ikan3+udang3;
            double totalSerat = kedelai4+kelapa4+kacang4+ikan4+udang4;
            double totalKalsium = kedelai5+kelapa5+kacang5+ikan5+udang5;
            double totalFosfor = kedelai6+kelapa6+kacang6+ikan6+udang6;
            
            panel_hasil.getTexthasil().append("===== PERHITUNGAN KEBUTUHAN NUTRISI YANG OPTIMAL ====="+"\n");
            panel_hasil.getTexthasil().append(" "+"\n");
            panel_hasil.getTexthasil().append(" Total Kandungan Nutrisi Bahan Pakan"+"\n");
            panel_hasil.getTexthasil().append(" "+"\n");
            panel_hasil.getTexthasil().append(" Total ME : "+df2.format(totalMe)+" Kkal/gram"+"\n");
            panel_hasil.getTexthasil().append(" Total Protein : "+df2.format(totalProtein)+" gram"+"\n");
            panel_hasil.getTexthasil().append(" Total Lemak : "+df2.format(totalLemak)+" gram"+"\n");
            panel_hasil.getTexthasil().append(" Total Serat : "+df2.format(totalSerat)+" gram"+"\n");
            panel_hasil.getTexthasil().append(" Total Kalsium : "+df2.format(totalKalsium)+" gram"+"\n");
            panel_hasil.getTexthasil().append(" Total Fosfor : "+df2.format(totalFosfor)+" gram"+"\n");
            panel_hasil.getTexthasil().append(" "+"\n");
            panel_hasil.getTexthasil().append("-- Kandungan nutrisi bahan pakan dibandingkan dengan kebutuhan nutrisi per hari"+"\n");
            
//            hitung penalty
            double penalty1=0;
            double penalty2=0;
            double penalty3=0;
            double penalty4=0;
            double penalty5=0;
            double penalty6=0;
            panel_hasil.getTexthasil().append(" "+"\n");
            panel_hasil.getTexthasil().append("Kebutuhan Nutrisi"+"\n");
            panel_hasil.getTexthasil().append(" "+"\n");
            
            if (totalMe > kebNutMe) {
                panel_hasil.getTexthasil().append(" 1. Kebutuhan Metabolis Energi"+"\n");
                panel_hasil.getTexthasil().append("   ME bahan pakan "+df2.format(totalMe)+" > "+kebNutMe+" = TRUE"+"\n");
                panel_hasil.getTexthasil().append("--- Kebutuhan Metabolis Energi Terpenuhi"+"\n");
                panel_hasil.getTexthasil().append("\n");
            }else{
                penalty1 = kebNutMe - totalMe;
                
                panel_hasil.getTexthasil().append(" 1. Kebutuhan ME"+"\n");
                panel_hasil.getTexthasil().append("   ME bahan pakan "+df2.format(totalMe)+" > "+kebNutMe+" = FALSE"+"\n");
                panel_hasil.getTexthasil().append("--- Kebutuhan ME tidak terpenuhi"+"\n");
                panel_hasil.getTexthasil().append("  Kekurangan Nutrisi ME : "+df2.format(penalty1)+" Kkal/gram"+"\n");
                panel_hasil.getTexthasil().append("\n");
            }
            
            if (totalProtein > kebNutPr) {
                panel_hasil.getTexthasil().append(" 2. Kebutuhan Protein Terpenuhi"+"\n");
                panel_hasil.getTexthasil().append("   Protein bahan pakan "+df2.format(totalProtein)+" > "+df2.format(kebNutPr)+" = TRUE"+"\n");
                panel_hasil.getTexthasil().append("--- Kebutuhan Protein Terpenuhi"+"\n");
                panel_hasil.getTexthasil().append("\n");
                
            }else{
                penalty2 = kebNutPr - totalProtein;
                
                panel_hasil.getTexthasil().append(" 2. Kebutuhan Protein"+"\n");
                panel_hasil.getTexthasil().append("   Protein bahan pakan "+df2.format(totalProtein)+" > "+df2.format(kebNutPr)+" : FALSE"+"\n");
                panel_hasil.getTexthasil().append("--- Kebutuhan Protein tidak terpenuhi"+"\n");
                panel_hasil.getTexthasil().append("  Kekurangan Nutrisi Protein : "+df2.format(penalty2)+" gram"+"\n");
                panel_hasil.getTexthasil().append("\n");
            }
            
            if (totalLemak > kebNutLe) {
                panel_hasil.getTexthasil().append(" 3. Kebutuhan Lemak Terpenuhi"+"\n");
                panel_hasil.getTexthasil().append("   Lemak bahan pakan "+df2.format(totalLemak)+" > "+kebNutLe+" : TRUE"+"\n");
                panel_hasil.getTexthasil().append("--- Kebutuhan Lemak Terpenuhi"+"\n");
                panel_hasil.getTexthasil().append("\n");
                
            }else{
                penalty3 = kebNutLe - totalLemak;
                
                panel_hasil.getTexthasil().append(" 3. Kebutuhan Lemak"+"\n");
                panel_hasil.getTexthasil().append("   Lemak bahan pakan "+df2.format(totalLemak)+" > "+kebNutLe+" = FALSE"+"\n");
                panel_hasil.getTexthasil().append("--- Kebutuhan Lemak tidak terpenuhi"+"\n");
                panel_hasil.getTexthasil().append("  Kekurangan Nutrisi Lemak :"+df2.format(penalty3)+" gram"+"\n");
                panel_hasil.getTexthasil().append("\n");
            }
            
            if (totalSerat > kebNutSe) {
                panel_hasil.getTexthasil().append(" 4. Kebutuhan Serat Terpenuhi"+"\n");
                panel_hasil.getTexthasil().append("   Serat bahan pakan "+df2.format(totalSerat)+" > "+kebNutSe+" : TRUE"+"\n");
                panel_hasil.getTexthasil().append("--- Kebutuhan Serat Terpenuhi"+"\n");
                panel_hasil.getTexthasil().append("\n");
                
            }else{
                penalty4 = kebNutSe - totalSerat;
                
                panel_hasil.getTexthasil().append(" 4. Kebutuhan Serat"+"\n");
                panel_hasil.getTexthasil().append("   Serat bahan pakan "+df2.format(totalSerat)+" > "+kebNutSe+" = FALSE"+"\n");
                panel_hasil.getTexthasil().append("--- Kebutuhan Serat tidak terpenuhi"+"\n");
                panel_hasil.getTexthasil().append("  Kekurangan Nutrisi Serat :"+df2.format(penalty4)+" gram"+"\n");
                panel_hasil.getTexthasil().append("\n");
            }
            
            if (totalKalsium > kebNutKa) {
                panel_hasil.getTexthasil().append(" 5. Kebutuhan Kalsium Terpenuhi"+"\n");
                panel_hasil.getTexthasil().append("   Kalsium bahan pakan "+df2.format(totalKalsium)+" > "+kebNutKa+" : TRUE"+"\n");
                panel_hasil.getTexthasil().append("--- Kebutuhan Kalsium Terpenuhi"+"\n");
                panel_hasil.getTexthasil().append("\n");
                
            }else{
                penalty5 = kebNutKa - totalKalsium;
                
                panel_hasil.getTexthasil().append(" 5. Kebutuhan Kalsium"+"\n");
                panel_hasil.getTexthasil().append("   Kalsium bahan pakan "+df2.format(totalKalsium)+" > "+kebNutKa+" = FALSE"+"\n");
                panel_hasil.getTexthasil().append("--- Kebutuhan Kalsium tidak terpenuhi"+"\n");
                panel_hasil.getTexthasil().append("  Kekurangan Nutrisi Kalsium :"+df2.format(penalty5)+" gram"+"\n");
                panel_hasil.getTexthasil().append("\n");
            }
            
            if (totalFosfor > kebNutFo) {
                panel_hasil.getTexthasil().append(" 6. Kebutuhan Fosfor Terpenuhi"+"\n");
                panel_hasil.getTexthasil().append("   Fosfor bahan pakan "+df2.format(totalFosfor)+" > "+kebNutFo+" : TRUE"+"\n");
                panel_hasil.getTexthasil().append("--- Kebutuhan Fosfor Terpenuhi"+"\n");
                panel_hasil.getTexthasil().append("\n");
                
            }else{
                penalty6 = kebNutFo - totalFosfor;
                
                panel_hasil.getTexthasil().append(" 6. Kebutuhan Fosfor"+"\n");
                panel_hasil.getTexthasil().append("   Fosfor bahan pakan "+df2.format(totalFosfor)+" > "+kebNutFo+" = FALSE"+"\n");
                panel_hasil.getTexthasil().append("--- Kebutuhan Fosfor tidak terpenuhi"+"\n");
                panel_hasil.getTexthasil().append("  Kekurangan Nutrisi Fosfor :"+df2.format(penalty6)+" gram"+"\n");
                panel_hasil.getTexthasil().append("\n");
            }
            double totalPenalty = penalty1+penalty2+penalty3+penalty4+penalty5+penalty6;
            panel_hasil.getTexthasil().append("Total Penalty : "+df.format(totalPenalty)+"\n");
            
//            hitung Fitness
            panel_hasil.getTexthasil().append(" "+"\n");
            double fitness = 100 /(totalCost +(totalPenalty*100));
            panel_hasil.getTexthasil().append("Fitness : "+df.format(fitness)+"\n");

        
    }
    
    private void setIcon(JButton button, String resource) {
        button.setIcon(new ImageIcon(getClass().getResource(resource)));
    }

    private static class HargaMouseListener implements MouseListener {

        public HargaMouseListener() {
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            feed.getLabelSeleksi().setText("Perhitungan harga");
            feed.getDynamicPanel2().setVisible(true);
            panel_mut.setVisible(false);
            panel_pop.setVisible(false);
            panel_sel.setVisible(false);
            panel_fit.setVisible(false);
            panel_hasil.setVisible(false);
            panel_harga.setVisible(true);
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

    private class EditMouseListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            home.getPanelEdit().setVisible(true);
            panel_edit.setVisible(true);
            
            double hargaKedelai = genModel.getHarga1();
            double hargaKelapa = genModel.getHarga2();
            double hargaKacang = genModel.getHarga3();
            double hargaIkan = genModel.getHarga4();
            double hargaUdang = genModel.getHarga5();
            
            String harga1 = String.valueOf(hargaKedelai);
            String harga2 = String.valueOf(hargaKelapa);
            String harga3 = String.valueOf(hargaKacang);
            String harga4 = String.valueOf(hargaIkan);
            String harga5 = String.valueOf(hargaUdang);
            panel_edit.setHarga1(harga1);
            panel_edit.setHarga2(harga2);
            panel_edit.setHarga3(harga3);
            panel_edit.setHarga4(harga4);
            panel_edit.setHarga5(harga5);
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

    private class SaveMouseListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            double hargaKedelai = Double.parseDouble(panel_edit.getInputHarga1());
            double hargaKelapa = Double.parseDouble(panel_edit.getInputHarga2());
            double hargaKacang = Double.parseDouble(panel_edit.getInputHarga3());
            double hargaIkan = Double.parseDouble(panel_edit.getInputHarga4());
            double hargaUdang = Double.parseDouble(panel_edit.getInputHarga5());
            
            genModel.updateHarga(hargaKedelai);
            genModel.updateHarga2(hargaKelapa);
            genModel.updateHarga3(hargaKacang);
            genModel.updateHarga4(hargaIkan);
            genModel.updateHarga5(hargaUdang);
            JOptionPane.showMessageDialog(home, "Harga baru berhasil disimpan");
            
            genModel.getKedelai();
            genModel.getKelapa();
            genModel.getKacang();
            genModel.getIkan();
            genModel.getUdang();
            
            showHarga();
            home.getPanelEdit().setVisible(false);
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

    private class BestMouseListener implements MouseListener {

        public BestMouseListener() {
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            feed.getLabelSeleksi().setText("Hasil Optimasi & Individu Terbaik");
            feed.getDynamicPanel2().setVisible(true);
            panel_mut.setVisible(false);
            panel_pop.setVisible(false);
            panel_sel.setVisible(false);
            panel_fit.setVisible(false);
            panel_hasil.setVisible(true);
            panel_harga.setVisible(false);
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

    private class CrossoverMouseListener implements MouseListener {

        public CrossoverMouseListener() {
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            feed.getLabelSeleksi().setText("Proses Crossover");
            feed.getDynamicPanel2().setVisible(true);
            panel_mut.setVisible(false);
            panel_pop.setVisible(true);
            panel_sel.setVisible(false);
            panel_fit.setVisible(false);
            panel_hasil.setVisible(false);
            panel_harga.setVisible(false);
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

    private class SeleksiMouseListener implements MouseListener {

        public SeleksiMouseListener() {
            
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            
            switch (cekSel) {
                case 1:
                    feed.getLabelSeleksi().setText("Proses Seleksi Elitism");
                    break;
                case 2:
                    feed.getLabelSeleksi().setText("Proses Seleksi Roullete Wheel");
                    break;
                default:
                    feed.getLabelSeleksi().setText("Proses Seleksi");
                    break;
            }
            
            feed.getDynamicPanel2().setVisible(true);
            panel_mut.setVisible(false);
            panel_pop.setVisible(false);
            panel_sel.setVisible(true);
            panel_fit.setVisible(false);
            panel_hasil.setVisible(false);
            panel_harga.setVisible(false);
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

    private class FitnessMouseListener implements MouseListener {

        public FitnessMouseListener() {
            
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            feed.getLabelSeleksi().setText("Nilai Fitness");
            feed.getDynamicPanel2().setVisible(true);
            panel_mut.setVisible(false);
            panel_pop.setVisible(false);
            panel_sel.setVisible(false);
            panel_fit.setVisible(true);
            panel_hasil.setVisible(false);
            panel_harga.setVisible(false);
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

    private class MutasiMouseListener implements MouseListener {

        public MutasiMouseListener() {
            
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            feed.getLabelSeleksi().setText("Proses Mutasi");
            feed.getDynamicPanel2().setVisible(true);
            panel_mut.setVisible(true);
            panel_pop.setVisible(false);
            panel_sel.setVisible(false);
            panel_fit.setVisible(false);
            panel_hasil.setVisible(false);
            panel_harga.setVisible(false);
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

    private class PopulasiMouseListener implements MouseListener {

        public PopulasiMouseListener() {
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            feed.getLabelSeleksi().setText("Populasi Individu");
            feed.getDynamicPanel2().setVisible(false);
            panel_mut.setVisible(false);
            panel_pop.setVisible(false);
            panel_sel.setVisible(false);
            panel_fit.setVisible(false);
            panel_hasil.setVisible(false);
            panel_harga.setVisible(false);
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

    private class RunMouseListener implements MouseListener {

        public RunMouseListener() {
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            JOptionPane.showMessageDialog(home, "Sistem sedang melakukan optimasi");
            int iterasi = genModel.getIterasi();
            int seleksi = genModel.getSeleksi();
            cekSel = seleksi;
            
            individu();
            evaluasiKromosom();
            for (int i = 0; i < iterasi; i++) {
//            System.out.println("||---- GENERASI "+(i+1)+" ----||");
            panel_pop.getTextPop().append("||---- CROSSOVER GENERASI "+(i+1)+" ----||"+"\n");
            crossover();
            panel_fit.getTextFitness().append(" "+"\n");
            panel_fit.getTextFitness().append("||---- GENERASI "+(i+1)+" ----||"+"\n");
            feed.getAreaKromosom().append("||---- GENERASI "+(i+1)+" ----||"+"\n");
            panel_fit.getTextFitness().append("-------------- Nilai Fitness Offspring Crossover --------------"+"\n");
            printCrossover();
            printFitnessCrossover();
            panel_mut.getTextMutasi().append("||---- MUTASI GENERASI "+(i+1)+" ----||"+"\n");
            mutation();
            evaluasiMutasi();
            panel_fit.getTextFitness().append("-------------- Nilai Fitness Offspring Mutasi --------------"+"\n");
            printFitnessMutasi();
            if (seleksi == 1) {
                panel_sel.getTextSeleksi().append("||---- ELITISM GENERASI "+(i+1)+" ----||"+"\n");
                    seleksiElitism();
                    populasiBaru();
                }else{
                panel_sel.getTextSeleksi().append("||---- ROULLETE WHEEL GENERASI "+(i+1)+" ----||"+"\n");
                    seleksiRoullete();
                    populasiBaru();
                }
            cariIndividuTerbaik();
            individuTerbaik();
            
            }
            printHasil();
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
            setIcon(home.getButton_Ga(), "/View/Button/optimasi-hover.png");
            }

        @Override
        public void mouseExited(MouseEvent e) {
            setIcon(home.getButton_Ga(), "/View/Button/optimasi-awal.png");
            }
    }
    
    public void clearInput(){
//        home.getPopulasi().setText("");
//        home.getIterasi().setText("");
//        home.getInputCross().setText("");
//        home.getInputMut().setText("");
//        home.getKonsumsi().setText("");
//        home.getAyam().setText("");
        feed.getAreaKromosom().setText("");
        panel_pop.getTextPop().setText("");
        panel_mut.getTextMutasi().setText("");
        panel_sel.getTextSeleksi().setText("");
        panel_fit.getTextFitness().setText("");
        panel_hasil.getTexthasil().setText("");
        home.getButton_Ga().setVisible(false);
    }

    private class ClearMouseListener implements MouseListener {

        public ClearMouseListener() {
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            clearInput();
            }

        @Override
        public void mousePressed(MouseEvent e) {
            }

        @Override
        public void mouseReleased(MouseEvent e) {
            }

        @Override
        public void mouseEntered(MouseEvent e) {
            setIcon(home.getButton_Clear(), "/View/Button/trash-hover.png");
            }

        @Override
        public void mouseExited(MouseEvent e) {
            setIcon(home.getButton_Clear(), "/View/Button/trash-awal.png");
            }
    }

    private class PakanMouseListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            home.getDynamicPanel().setVisible(false);
            home.getButton_Edit().setVisible(true);
            home.getScroll().setVisible(true);
            }

        @Override
        public void mousePressed(MouseEvent e) {
            }

        @Override
        public void mouseReleased(MouseEvent e) {
            }

        @Override
        public void mouseEntered(MouseEvent e) {
            setIcon(home.getButton_Pakan(), "/View/Button/btn-bahan-hover-b.png");
            }

        @Override
        public void mouseExited(MouseEvent e) {
            setIcon(home.getButton_Pakan(), "/View/Button/btn-feed.png");
            }
    }

    private class HasilMouseListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            home.getDynamicPanel().setVisible(true);
            home.getPanelEdit().setVisible(false);
            feed.setVisible(false);
            result.setVisible(true);
            home.getButton_Edit().setVisible(false);
            home.getScroll().setVisible(false);
//            hasilOptimasi();
            }

        @Override
        public void mousePressed(MouseEvent e) {
            }

        @Override
        public void mouseReleased(MouseEvent e) {
            }

        @Override
        public void mouseEntered(MouseEvent e) {
            setIcon(home.getButton_Hasil(), "/View/Button/btn-optimasi-hover-o.png");
            }

        @Override
        public void mouseExited(MouseEvent e) {
            setIcon(home.getButton_Hasil(), "/View/Button/btn-result.png");
            }
    }

    private class AlgoMouseListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            home.getDynamicPanel().setVisible(true);
            home.getPanelEdit().setVisible(false);
            feed.setVisible(true);
            result.setVisible(false);
            home.getButton_Edit().setVisible(false);
            home.getScroll().setVisible(false);
            
            }

        @Override
        public void mousePressed(MouseEvent e) {
            }

        @Override
        public void mouseReleased(MouseEvent e) {
            }

        @Override
        public void mouseEntered(MouseEvent e) {
                setIcon(home.getButton_Algo(), "/View/Button/btn-hitung-hover-c.png");
            }

        @Override
        public void mouseExited(MouseEvent e) {
                setIcon(home.getButton_Algo(), "/View/Button/btn-calculate.png");
            }
    }
    
    private class OptimasiMouseListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            
            int popSize = Integer.parseInt(home.getInputPopulasi());
            int iterasi = Integer.parseInt(home.getInputIterasi());
            double pc = Double.parseDouble(home.getInputProbCross());
            double pm = Double.parseDouble(home.getInputProbMut());
            int seleksi;
            int konsumsi = Integer.parseInt(home.getInputKonsumsi());
            int ayam = Integer.parseInt(home.getInputAyam());
            if (home.getElitism().isSelected()) {
                seleksi = 1;
            }else{
                seleksi = 2;
            }
            
                try {
                genModel.insertDataAlgo(popSize, iterasi, pc, pm, seleksi, konsumsi, ayam);
                JOptionPane.showMessageDialog(home, "Berhasil disimpan, klik Perhitungan Algoritma");
                genModel.getAlgo();
                home.getButton_Ga().setVisible(true);
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
            setIcon(home.getButton_Optimasi(), "/View/Button/simpan-hover.png");
        }

        @Override
        public void mouseExited(MouseEvent e) {
            setIcon(home.getButton_Optimasi(), "/View/Button/simpan-awal.png");
        }
    }
    
}
