package controleur;

import classeMetier.*;
import coucheAccesDB.*;

import java.util.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.*;

public class SupprimerJoueur extends BaseFenetre
{
	@FXML private Button BFermer;
	@FXML private Button BSupprimer;	
	@FXML private ComboBox<Joueur> CBJoueur;

	/**
	 * Constructeur qui cree la fenetre
	 * @param fenParent   objet stage represantant ka fenetre parent
	 */
	public SupprimerJoueur(Stage fenParent) 
	{
		super(fenParent, "SupprimerJoueurVue.xml", "Supprimer un joueur", 320, 100);
		
		//Ajouter les joueurs dans la COMBOBOX
		try
		{
			CBJoueur.setItems(FXCollections.observableArrayList(FabriqueDAO.getInstance().getJoueurDAO().listerTous()));
		}
		catch(ExceptionAccesDB e)
		{
			new MessageBox(this, AlertType.ERROR, "Erreur d'acces a la base de donne", e.getMessage());
			return;
		}
		
		if(CBJoueur.getItems().size() == 0)
		{
			new MessageBox(this, AlertType.INFORMATION, "Information", "Il n'y a aucun joueur dans la base de donnee!");
			return;
		}
		
		//Selectionner le 1re joueur dans la COMBOBOX
		CBJoueur.getSelectionModel().selectFirst();
		
		//Affciher la fenetre 
		showAndWait();
	}
	
	/**
	 * Methode execute quand on clique sur le boutton supprimer et qui supprime le joueur
	 */
	@FXML
	private void BSupprimerJoueur()
	{
		int numJoueur = CBJoueur.getSelectionModel().getSelectedItem().getId();
		List<Rencontre> rencontre = null; // pour voir si le tournoi a commence
		
		try
		{
			rencontre = FabriqueDAO.getInstance().getRencontreDAO().listerTous();
		}
		catch(ExceptionAccesDB e)
		{
			new MessageBox(this, AlertType.ERROR, "Erreur d'acces a la base de donnee", e.getMessage());
			return;
		}
		
		if(rencontre.size() > 0)
		{
			new MessageBox(this, AlertType.WARNING, "Attention", "Le tournoi a deja commence, aucune suppression n'est permise.");
			return;
		}
			
		try
		{
			FabriqueDAO.getInstance().debuterTransaction();
			FabriqueDAO.getInstance().getEquipeDAO().supprimer(numJoueur);
			FabriqueDAO.getInstance().getJoueurDAO().supprimer(numJoueur);
			
			FabriqueDAO.getInstance().validerTransaction();
		}
		catch(ExceptionAccesDB e)
		{
			try
			{
				FabriqueDAO.getInstance().annulerTransaction();
			}
			catch(ExceptionAccesDB ex){}
			
			new MessageBox(this, AlertType.ERROR, "Erreur d'acces a la base de donnee", e.getMessage());
		}
		close();
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
