/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package optimasipakan;

import Controller.GenetikController;
import View.HomeView;
import Model.GenetikModel;
import java.sql.SQLException;
/**
 *
 * @author Muhammad Rizal
 */
public class OptimasiPakan {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {
        HomeView home = new HomeView();
        GenetikModel genModel = new GenetikModel();
        new GenetikController(home,genModel);
    }
    
}
