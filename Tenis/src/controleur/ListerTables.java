package controleur;

import classeMetier.*;
import coucheAccesDB.*;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class ListerTables extends BaseFenetre
{
	@FXML private TableView<Table> TVTable;
	@FXML private Button BFermer;

	/**
	 * Constructeur qui cree la fenetre
	 * @param fenPanrent
	 */
	public ListerTables(Stage fenParent)  
	{
		super(fenParent, "ListerTablesVue.xml", "Lister les tables", 560, 280);
		
		//Ajouter la liste des tables a la table view
		try
		{
			TVTable.itemsProperty().setValue(FXCollections.observableArrayList( FabriqueDAO.getInstance().getTableDAO().listerTous()) );
		}
		catch(ExceptionAccesDB e)
		{
			new MessageBox(this, AlertType.ERROR, "Erreur d'acces a la base de donne", e.getMessage());
			return;
		}  
		
		if(TVTable.getItems().size() == 0)
		{
			new MessageBox(this, AlertType.INFORMATION, "Information", "il n'y a aucune table dans la base de donne");
			return;
		}
		
		//selectionner la 1re table dans la table TVTable
		TVTable.getSelectionModel().selectFirst();
		
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
