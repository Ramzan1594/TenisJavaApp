package controleur;

import classeMetier.*;
import coucheAccesDB.*;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.*;
import javafx.stage.Stage;

public class ListerJoueurs extends BaseFenetre
{
	@FXML private TableView<Joueur> TVJoueur;
	@FXML private Button BFermer;
	@FXML private ImageView IVImage;

	/**
	 * Consctructeur qui cree la fenetre
	 * @param fenParent    objet stage represantant la fentre parent
	 */
	public ListerJoueurs(Stage fenParent) 
	{
		super(fenParent, "ListerJoueursVue.xml", "Lister les joueurs", 650, 280);
		
		//Ajouter la liste des arbitres a la table view
		try
		{
			TVJoueur.itemsProperty().setValue(FXCollections.observableArrayList( FabriqueDAO.getInstance().getJoueurDAO().listerTous()) );
		}
		catch(ExceptionAccesDB e)
		{
			new MessageBox(this, AlertType.ERROR, "Erreur d'acces a la base de donne", e.getMessage());
			return;
		}  
		
		if(TVJoueur.getItems().size() == 0)
		{
			new MessageBox(this, AlertType.INFORMATION, "Information", "il n'y a aucun joueur dans la base de donne");
			return;
		}
		
		//gerer l'affichage de l'image quand on clique sur un joueur dans le tableau
		TVJoueur.getSelectionModel().selectedItemProperty().addListener((obs, ancJoueur, nouveauJoueur) -> 
		{
			if(nouveauJoueur != null)
				IVImage.setImage(new Image("file:img/joueur/" + nouveauJoueur.getPhoto()));
		});
		
		//selectionner le 1er joueur dans la table TVArbitre
		TVJoueur.getSelectionModel().selectFirst();
		
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
