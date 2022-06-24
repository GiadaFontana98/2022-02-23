/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.yelp;

import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.yelp.model.Business;
import it.polito.tdp.yelp.model.Model;
import it.polito.tdp.yelp.model.Review;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnMiglioramento"
    private Button btnMiglioramento; // Value injected by FXMLLoader

    @FXML // fx:id="cmbCitta"
    private ComboBox<String> cmbCitta; // Value injected by FXMLLoader

    @FXML // fx:id="cmbLocale"
    private ComboBox<Business> cmbLocale; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader
    
    @FXML
    void doRiempiLocali(ActionEvent event) {
    	this.cmbLocale.getItems().clear();
    	String citta = this.cmbCitta.getValue();
    	if(citta != null) {
    		cmbLocale.getItems().addAll(model.getBusiness(citta));
    		this.btnCreaGrafo.setDisable(false);
    		
    	}
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	
    	txtResult.appendText(model.creaGrafo(cmbLocale.getValue()));
    }

    @FXML
    void doTrovaMiglioramento(ActionEvent event) {
    txtResult.clear();
    List<Review>miglioramento= model.calcolaPercorso();
    LocalDate ultima = miglioramento.get(miglioramento.size()-1).getDate();
    LocalDate prima = miglioramento.get(0).getDate();
    txtResult.appendText("Giorni prima e ultima recensione + " + ChronoUnit.DAYS.between(ultima, prima));
    
    for(Review r : miglioramento)
    {
    	txtResult.appendText(" " + r);
    }
    
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnMiglioramento != null : "fx:id=\"btnMiglioramento\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbCitta != null : "fx:id=\"cmbCitta\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbLocale != null : "fx:id=\"cmbLocale\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	this.cmbCitta.getItems().addAll(model.getCitta());
    	this.btnCreaGrafo.setDisable(true);
    	txtResult.appendText(model.creaGrafo(cmbLocale.getValue()));
    	txtResult.appendText(model.getUscenti().toString());
    }
}
