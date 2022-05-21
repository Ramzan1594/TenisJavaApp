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


public class ListerRencontresEquipe extends BaseFenetre
{
	@FXML private ComboBox<Equipe> CBEquipe;
	@FXML private TableView<Rencontre> TVRencontre;
	@FXML private Button BFermer;

	/**
	 * Consctructeur qui cree la fenetre
	 * @param fenParent    objet stage represantant la fentre parent
	 */
	public ListerRencontresEquipe(Stage fenParent) 
	{
		super(fenParent, "ListerRencontresEquipeVue.xml", "Lister les rencontres d'une equipe", 710, 330);
		
		//ajouter la listes des equipes dans la CB
		try
		{
			CBEquipe.setItems(FXCollections.observableArrayList(FabriqueDAO.getInstance().getEquipeDAO().listerTous()));
		}
		catch(ExceptionAccesDB e)
		{
			new MessageBox(this, AlertType.ERROR,"Erreur de lecture dans la base de donne", e.getMessage());
			return;
		}
		
		//Ajouter la liste des rencontre a la table view
		try
		{
			TVRencontre.itemsProperty().setValue(FXCollections.observableArrayList( FabriqueDAO.getInstance().getRencontreDAO().listerTous()));
		}
		catch(ExceptionAccesDB e)
		{
			new MessageBox(this, AlertType.ERROR, "Erreur d'acces a la base de donne", e.getMessage());
			return;
		}  
		
		//on verifie s'il y a des rencontre
		if(TVRencontre.getItems().size() == 0)
		{
			new MessageBox(this, AlertType.INFORMATION, "Information", "il n'y a aucune rencontre dans la base de donne");
			return;
		}
		
		//selectionner le 1er joueur dans la table TVArbitre
		TVRencontre.getSelectionModel().selectFirst();
		
		//Selectionner la 1re equipe dans la CB
		CBEquipe.getSelectionModel().selectFirst();
		
		
		
		//gerer les changement des rencontre de la TV en fonction de l'equipe de la comboBox 
		CBEquipe.getSelectionModel().selectedItemProperty().addListener((obs, ancEquipe, newEquipe)->
		{
			if(newEquipe != null)
				CBChangerEquipe(newEquipe.getId());			
		});
		
		//Selectionner la 1re equipe dans la CB
		CBEquipe.getSelectionModel().selectFirst();
		
		//Mettre a jour une premiere fois la TV
		CBChangerEquipe(CBEquipe.getItems().get(0).getId());
		
		//afficher la fenetre		
		showAndWait();
		
	}
	
	
	/**
	 * Mettre ajour le contenue dans la fenetre quand on change l'equipe courante
	 * @param numEquipe  id de la nouvelle equipe
	 */
	private void CBChangerEquipe(int numEquipe)
	{
		//Creation de la liste a retourne
		ArrayList<Rencontre> ret = new ArrayList<Rencontre>();
		//on recupere la listes des rencontre base sur une equipe et on la met dans le TV
		try
		{
			List<Rencontre> lesRencontres = FabriqueDAO.getInstance().getRencontreDAO().chargerTous(numEquipe);
			
			for(Rencontre rencontre  : lesRencontres)
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
				r.setArbitreString(arbitre.getNom() + "  " + arbitre.getPrenom());
				r.setTable(rencontre.getTable());
				if(gagnant != null) 
				{
					r.setGagnantString(gagnant.getNom());
					r.setResultat(rencontre.getResultat());					
				}
				
				
				ret.add(r);																			
			}
			
			TVRencontre.itemsProperty().setValue(FXCollections.observableArrayList( ret ));
		}
		catch(ExceptionAccesDB e)
		{
			new MessageBox(this, AlertType.ERROR, "Erreur d'acces a la base de donne", e.getMessage());
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
