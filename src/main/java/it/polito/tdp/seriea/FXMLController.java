package it.polito.tdp.seriea;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.seriea.model.Model;
import it.polito.tdp.seriea.model.Season;
import it.polito.tdp.seriea.model.Team;
import it.polito.tdp.seriea.model.TeamPeso;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;

//controller turno A --> switchare al branch master_turnoB o master_turnoC per turno B o C

public class FXMLController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ChoiceBox<Team> boxSquadra;

    @FXML
    private ChoiceBox<Season> boxStagione;

    @FXML
    private Button btnCalcolaConnessioniSquadra;

    @FXML
    private Button btnSimulaTifosi;

    @FXML
    private Button btnAnalizzaSquadre;

    @FXML
    private TextArea txtResult;

    @FXML
    void doAnalizzaSquadre(ActionEvent event) {
    	txtResult.clear();
    	this.model.creaGrafo();
    	this.boxSquadra.getItems().clear();
    	this.boxSquadra.getItems().addAll(this.model.getListaVertici());
    	txtResult.appendText("Grafo Creato!");
    }

    @FXML
    void doCalcolaConnessioniSquadra(ActionEvent event) {
    	txtResult.clear();
    	Team t=this.boxSquadra.getValue();
    	if(t==null) {
    		txtResult.appendText("Crea un grafo e inserisci una squadra");
    		return;
    	}
    	List<TeamPeso> lista=new ArrayList<>(this.model.connessioniSquadra(t));
    	txtResult.appendText("Squadre affrontate:\n");
    	for(TeamPeso tp:lista) {
    		txtResult.appendText(tp.toString()+"\n");
    	}
    }

    @FXML
    void doSimulaTifosi(ActionEvent event) {
    	txtResult.clear();
    	Season se=boxStagione.getValue();
    	if(se==null) {
    		txtResult.appendText("Inserisci una stagione");
    		return;
    	}
    	String s="Campionato Simulato:\n";
    	s+=this.model.simula(se.getSeason());
    	txtResult.appendText(s);
    }

    @FXML
    void initialize() {
        assert boxSquadra != null : "fx:id=\"boxSquadra\" was not injected: check your FXML file 'SerieA.fxml'.";
        assert boxStagione != null : "fx:id=\"boxStagione\" was not injected: check your FXML file 'SerieA.fxml'.";
        assert btnCalcolaConnessioniSquadra != null : "fx:id=\"btnCalcolaConnessioniSquadra\" was not injected: check your FXML file 'SerieA.fxml'.";
        assert btnSimulaTifosi != null : "fx:id=\"btnSimulaTifosi\" was not injected: check your FXML file 'SerieA.fxml'.";
        assert btnAnalizzaSquadre != null : "fx:id=\"btnAnalizzaSquadre\" was not injected: check your FXML file 'SerieA.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'SerieA.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
		this.boxStagione.getItems().addAll(this.model.listaStagioni());
	}
}