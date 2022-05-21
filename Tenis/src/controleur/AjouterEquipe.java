package controleur;


import java.util.List;

import classeMetier.*;
import coucheAccesDB.*;
import coucheMetier.*;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.*;


public class AjouterEquipe extends BaseFenetre
{
	@FXML private TextField TFNom;
	@FXML private ComboBox<Joueur> CBJ1;
	@FXML private ComboBox<Joueur> CBJ2;
	
	@FXML private Button BAjouter;
	@FXML private Button BFermer;

	/**
	 * Constructeur qui cree la fenetre
	 * @param fenParent  objet stage represantant la fenetre parent
	 */
	public AjouterEquipe(Stage fenParent) 
	{
		super(fenParent, "AjouterEquipeVue.xml", "Ajouter une equipe", 550, 165);
	
		//Ajouter les joueurs dans les ComboBox joueurs
		try
		{
			CBJ1.setItems(FXCollections.observableArrayList(FabriqueDAO.getInstance().getJoueurDAO().listerTous()));
			CBJ2.setItems(FXCollections.observableArrayList(FabriqueDAO.getInstance().getJoueurDAO().listerTous()));
		}
		catch(ExceptionAccesDB e)
		{
			new MessageBox(this, AlertType.ERROR,"Erreur de lecture dans la base de donne", e.getMessage());
			return;
		}
		
		if(CBJ1.getItems().size() == 0)
		{
			new MessageBox(this, AlertType.INFORMATION, "Information", "il n'y a aucun joueur dans la base de donne");
			return;
		}
		
		//Selectionner le 1er joueur dans le comboBox
		CBJ1.getSelectionModel().selectFirst();
		CBJ2.getSelectionModel().selectLast();
		
		//Afficher la fenetre
		showAndWait();
	}
	
	
	/**
	 * Methode qui est execute quand on clique sur le boutton ajouter et qui ajout l'equipe en DB ==============================
	 */
	@FXML
	private void BAjouterEquipe()
	{
		List<Equipe> lesEquipes = null;
		try
		{
			lesEquipes = FabriqueDAO.getInstance().getEquipeDAO().listerTous();
		}
		catch(ExceptionAccesDB e)
		{
			new MessageBox(this, AlertType.ERROR, "Erreur d'acces a la base de donne", e.getLocalizedMessage());
		}
		
		if(lesEquipes.size() >= 8)
		{
			new MessageBox(this, AlertType.WARNING, "ATTENTION !", "On a deja 8 equipe inscrite !");
			return;
		}
		
		Equipe equipe;
		int idJoueur1 = CBJ1.getSelectionModel().getSelectedItem().getId();
		int idJoueur2 = CBJ2.getSelectionModel().getSelectedItem().getId();
		int joueurPresant = 0;
		
		//verifie les donne sur le joueur
		try
		{
			equipe = CoucheMetier.getInstance().testerContraintesEquipe(TFNom.getText(), idJoueur1, idJoueur2);
		}
		catch(ExceptionMetier e)
		{
			new MessageBox(this, AlertType.WARNING, "Erreur de test", e.getMessage());
			return;
		}
		
		//tester si les joueur qu'on veut ajouter sont deja dans une autre equipe
		try
		{
			joueurPresant = FabriqueDAO.getInstance().getEquipeDAO().listerEquipeSelonJoueur(idJoueur1, idJoueur2);
		}
		catch(ExceptionAccesDB e)
		{
			new MessageBox(this, AlertType.ERROR, "Erreur d'acces a la base de donne", e.getLocalizedMessage());
		}
		
		if(joueurPresant > 0)
		{
			new MessageBox(this, AlertType.INFORMATION, "Information", "Un des joueurs entree est deja inscris dans une autre equipe" );
			return;
		}
		
		if(idJoueur1 == idJoueur2)
		{
			new MessageBox(this, AlertType.WARNING, "Attention", "Vous avez selectionner deux fois le meme joueur" );
			return;
		}
		
		if(joueurPresant == 0 && idJoueur1 != idJoueur2)
		{
			//ajouter l'equipe dans la DB========================================================================
			try
			{
				FabriqueDAO.getInstance().debuterTransaction();
				
				FabriqueDAO.getInstance().getEquipeDAO().ajouter(equipe);
				
				FabriqueDAO.getInstance().validerTransaction();
				
				close();
				return;
			}
			catch(ExceptionAccesDB e)
			{
				new MessageBox(this, AlertType.ERROR, "Erreur d'acces a la base de donne", e.getLocalizedMessage());
			}
			
			
			
			//Annuler l'ajout dans la DB s'il y a  un problem d'acces a la DB ou s'il y a une probleme  de copie de fichier==========
			try
			{
				FabriqueDAO.getInstance().annulerTransaction();
			}
			catch(ExceptionAccesDB e)
			{
				new MessageBox(this, AlertType.ERROR, "Erreur de transaction", e.getMessage());
			}
			
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
