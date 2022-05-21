package controleur;

import java.util.List;

import classeMetier.*;
import coucheAccesDB.*;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.*;

public class SupprimerEquipe extends BaseFenetre
{
	@FXML private Button BFermer;
	@FXML private Button BSupprimer;	
	@FXML private ComboBox<Equipe> CBEquipe;

	public SupprimerEquipe(Stage fenParent) 
	{
		super(fenParent, "SupprimerEquipeVue.xml", "Supprimer une equipe", 320, 100);
		
		//Ajouter les equipes dans la COMBOBOX
		try
		{
			CBEquipe.setItems(FXCollections.observableArrayList(FabriqueDAO.getInstance().getEquipeDAO().listerTous()));
		}
		catch(ExceptionAccesDB e)
		{
			new MessageBox(this, AlertType.ERROR, "Erreur d'acces a la base de donne", e.getMessage());
			return;
		}
		
		if(CBEquipe.getItems().size() == 0)
		{
			new MessageBox(this, AlertType.INFORMATION, "Information", "Il n'y a aucune equipe dans la base de donnee!");
			return;
		}
		
		//Selectionner le 1re joueur dans la COMBOBOX
		CBEquipe.getSelectionModel().selectFirst();
		
		//Affciher la fenetre 
		showAndWait();
		
	}
	
	/**
	 * Methode execute quand on clique sur le boutton supprimer et qui supprime l'equipe
	 */
	@FXML
	private void BSupprimerEquipe()
	{
		int numEquipe = CBEquipe.getSelectionModel().getSelectedItem().getId();
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
			FabriqueDAO.getInstance().getEquipeDAO().supprimerEquipe(numEquipe);
			
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
