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

public class ListerEquipes extends BaseFenetre
{
	@FXML private TableView<Equipe> TVEquipe;
	@FXML private Button BFermer;

	public ListerEquipes(Stage fenParent) 
	{
		super(fenParent, "ListerEquipesVue.xml", "Lister les equipes", 560, 280);
		//Creation de la liste a retourne
		ArrayList<Equipe> ret = new ArrayList<Equipe>();    
		
		//Ajouter la liste des tables a la table view
		try
		{
			List<Equipe> equipes = FabriqueDAO.getInstance().getEquipeDAO().listerTous();      
			
			// on recupere les nom et prenom des 2 joueur et on les ajoute a notre list ret
			for(Equipe equipe  : equipes)
			{
				Joueur joueur1 = FabriqueDAO.getInstance().getJoueurDAO().charger(equipe.getJ1());         
				Joueur joueur2 = FabriqueDAO.getInstance().getJoueurDAO().charger(equipe.getJ2());
				
				Equipe e = new Equipe();															
				
				e.setId(equipe.getId());
				e.setNom(equipe.getNom());
				e.setJoueur1(joueur1.getNom() + " " + joueur1.getPrenom());
				e.setJoueur2(joueur2.getNom() + " " + joueur2.getPrenom());
				
				ret.add(e);																			
			}
			TVEquipe.itemsProperty().setValue(FXCollections.observableArrayList( ret) );
		}
		catch(ExceptionAccesDB e)
		{
			new MessageBox(this, AlertType.ERROR, "Erreur d'acces a la base de donne", e.getMessage());
			return;
		}  
		
		if(TVEquipe.getItems().size() == 0)
		{
			new MessageBox(this, AlertType.INFORMATION, "Information", "il n'y a aucune equipe dans la base de donne");
			return;
		}
		
		//selectionner la 1re equipe dans la table TVTable
		TVEquipe.getSelectionModel().selectFirst();
		
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
