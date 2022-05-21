package controleur;

import classeMetier.*;
import coucheAccesDB.*;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class ListerArbitres extends BaseFenetre
{
	@FXML private TableView<Arbitre> TVArbitre;
	@FXML private Button BFermer;

	/**
	 * Consctructeur qui cree la fenetre
	 * @param fenParent    objet stage represantant la fentre parent
	 */
	public ListerArbitres(Stage fenParent) 
	{
		super(fenParent, "ListerArbitresVue.xml", "Lister les arbitres", 555, 280);
		
		//Ajouter la liste des arbitres a la table view
		try
		{
			TVArbitre.itemsProperty().setValue(FXCollections.observableArrayList( FabriqueDAO.getInstance().getArbitreDAO().listerTous()) );
		}
		catch(ExceptionAccesDB e)
		{
			new MessageBox(this, AlertType.ERROR, "Erreur d'acces a la base de donne", e.getMessage());
			return;
		}
		
		if(TVArbitre.getItems().size() == 0)
		{
			new MessageBox(this, AlertType.INFORMATION, "Information", "il n'y a aucun arbitres dans la base de donne");
			return;
		}
		
		//selectionner le 1er arbitre dans la table TVArbitre
		TVArbitre.getSelectionModel().selectFirst();
		
		//afficher la fenetre
		
		showAndWait();
	}
	
	/**
	 * Methode qui est execute quand on clique sur le boutton Fermer et qui ferme la fenetre
	 */
	@FXML
	private  void BFermer(ActionEvent event)
	{
		close();
	}
	

}
