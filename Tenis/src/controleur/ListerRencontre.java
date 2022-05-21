package controleur;

import java.util.ArrayList;
import java.util.List;

import classeMetier.*;
import coucheAccesDB.*;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class ListerRencontre extends BaseFenetre
{
	@FXML private TableView<Rencontre> TVRencontre;
	@FXML private Button BFermer;

	/**
	 * Consctructeur qui cree la fenetre
	 * @param fenParent    objet stage represantant la fentre parent
	 */
	public ListerRencontre(Stage fenParent) 
	{
		super(fenParent, "ListerRencontresVue.xml", "Lister les rencontres", 650, 280);
		//Creation de la liste a retourne
		ArrayList<Rencontre> ret = new ArrayList<Rencontre>();
		
		//Ajouter la liste des rencontres a la table view
		try
		{
			List<Rencontre> rencontres = FabriqueDAO.getInstance().getRencontreDAO().listerTous();
			
			// on recupere les nom et prenom des 2 equipe et arbitrre on les ajoute a notre list ret
			for(Rencontre rencontre  : rencontres)
			{
				Equipe equipe1 = FabriqueDAO.getInstance().getEquipeDAO().charger(rencontre.getE1());         
				Equipe equipe2 = FabriqueDAO.getInstance().getEquipeDAO().charger(rencontre.getE2());
				Equipe gagnant = FabriqueDAO.getInstance().getEquipeDAO().charger(rencontre.getGagnant());
				Arbitre arbitre = FabriqueDAO.getInstance().getArbitreDAO().charger(rencontre.getArbitre());
				
				Rencontre r = new Rencontre();															
				
				r.setId(rencontre.getId());
				r.setPhase(rencontre.getPhase());
				r.setEquipe1(equipe1.getNom());
				r.setEquipe2(equipe2.getNom());
				if(gagnant != null)
					r.setGagnantString(gagnant.getNom());
				r.setArbitreString(arbitre.getNom() + "  " + arbitre.getPrenom());
				r.setTable(rencontre.getTable());;
				r.setResultat(rencontre.getResultat());
				
				ret.add(r);																			
			}
			
			TVRencontre.itemsProperty().setValue(FXCollections.observableArrayList(ret));
		}
		catch(ExceptionAccesDB e)
		{
			new MessageBox(this, AlertType.ERROR, "Erreur d'acces a la base de donne", e.getMessage());
			return;
		}  
		
		if(TVRencontre.getItems().size() == 0)
		{
			new MessageBox(this, AlertType.INFORMATION, "Information", "il n'y a aucune rencontre dans la base de donne");
			return;
		}
		//selectionner le 1er joueur dans la table TVArbitre
		TVRencontre.getSelectionModel().selectFirst();
		
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
