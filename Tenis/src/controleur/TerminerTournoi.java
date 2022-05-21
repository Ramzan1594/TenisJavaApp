package controleur;

import coucheAccesDB.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.*;

public class TerminerTournoi extends BaseFenetre
{
	@FXML private Button BConfirmer;
	@FXML private Button BFermer;

	/**
	 * Constructeur qui cree la fenetre
	 * @param fenParent objet satge 
	 */
	public TerminerTournoi(Stage fenParent)
	{
		super(fenParent, "TerminerTournoiVue.xml", "Terminer tournoi", 180, 80);
		//Afficher la fenetre
		showAndWait();
	}
	
	/**
	 * Methode qui confirme la fin du tournoi
	 */
	@FXML
	private void BConfirmer() 
	{
		try
		{
			FabriqueDAO.getInstance().debuterTransaction();
			
			FabriqueDAO.getInstance().getRencontreDAO().supprimerTous();
			
			FabriqueDAO.getInstance().validerTransaction();
			
			close();
			return;			
		}
		catch(ExceptionAccesDB e)
		{
			new MessageBox(this, AlertType.ERROR, "Erreur lors d'acces a la base de donne", e.getMessage());
			return;
		}		
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
