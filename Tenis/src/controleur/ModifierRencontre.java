package controleur;

import classeMetier.*;
import coucheAccesDB.*;
import coucheMetier.*;

import java.util.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.*;

public class ModifierRencontre extends BaseFenetre
{
	@FXML private ComboBox<Rencontre> CBId;
	@FXML private TextField TFPhase;
	@FXML private TextField TFEquipe1;
	@FXML private TextField TFEquipe2;
	@FXML private TextField TFArbitre;
	@FXML private TextField TFTable;
	@FXML private TextField TFGagnant;
	@FXML private TextField TFResultat;
	
	@FXML private TextField TFResultE1;
	@FXML private TextField TFResultE2;
	
	@FXML private Button BModifier;
	@FXML private Button BFermer;

	/**
	 * Constructeur qui cree la fenetre
	 * @param fenParent  objet stage represantant la fenetre parent
	 */
	public ModifierRencontre(Stage fenParent) 
	{
		super(fenParent, "ModifierRencontreVue.xml", "Modifier une rencontre", 700, 580);
		
		List<Rencontre> rencontres = null;
		
		//on ajoute les rencontre dans la CB
		try 
		{
			rencontres = FabriqueDAO.getInstance().getRencontreDAO().listerTous();
			CBId.setItems(FXCollections.observableArrayList(rencontres));
		} 
		catch (ExceptionAccesDB e) 
		{
			new MessageBox(this, AlertType.ERROR, "Erreur d'acces a la base de donne", e.getMessage());
			return;
		}
		
		//Verifie s il y a des rencontre
		if(rencontres.size() == 0 )
		{
			new MessageBox(this, AlertType.INFORMATION, "Information", "il n'y a aucun rencontre dans la base de donne");
			return;
		}
		
		//Selectionner le premier dans la CB
		CBId.getSelectionModel().selectFirst();
		
		
		//gerer les changement des rencontre de la TV en fonction de l'equipe de la comboBox 
		CBId.getSelectionModel().selectedItemProperty().addListener((obs, ancRencontre, newRencontre)->
		{
			if(newRencontre != null)
				CBChangerRencontre(newRencontre.getId());			
		});
		
		//Mettre a jour une premiere fois les TF
		CBChangerRencontre(CBId.getItems().get(0).getId());
		
		//Afficher la fenetre
		showAndWait();
	}
		
	
		
	/**
	 * Methode qui est execute quand on appuie sur le boutton Modifier
	 */
	@FXML
	private void BModifier()
	{
		//on cree la modification de la rencontre
		Rencontre rencontre = CBId.getSelectionModel().getSelectedItem();
		if( TFResultE1.getText().compareTo("") == 0 || TFResultE2.getText().compareTo("") == 0)
		{
			new MessageBox(this, AlertType.WARNING, "ATTENTION", "Les resultats sont vide");
			return;	
		}
		if((Integer.parseInt(TFGagnant.getText()) == rencontre.getE1() && Integer.parseInt(TFResultE1.getText()) < Integer.parseInt(TFResultE2.getText())) ||
		   (Integer.parseInt(TFGagnant.getText()) == rencontre.getE2() && Integer.parseInt(TFResultE2.getText()) < Integer.parseInt(TFResultE1.getText()))	)
		{
			new MessageBox(this, AlertType.WARNING, "ATTENTION", "Le gagant ne concorde pas avec les points dans le resultat");
			return;	
		}
		
		
		try
		{
			rencontre = CoucheMetier.getInstance().testerContraintesModifierRencontre(rencontre.getId(), TFPhase.getText(), TFEquipe1.getText(), TFEquipe2.getText(), TFArbitre.getText(),
																						TFTable.getText(), TFGagnant.getText(), TFResultE1.getText() + " - " + TFResultE2.getText() );
		}
		catch(ExceptionMetier e)
		{
			new MessageBox(this, AlertType.WARNING, "Erreur de test", e.getMessage());
			return;			
		}
		
		
		//Modifier la rencontre  dans la DB et  ========================================================================
		try
		{
			FabriqueDAO.getInstance().debuterTransaction();
			
			FabriqueDAO.getInstance().getRencontreDAO().modifier(rencontre);
						
			FabriqueDAO.getInstance().validerTransaction();
			
			//rafraichir la ComboBox
			List<Rencontre>  rencontres = FabriqueDAO.getInstance().getRencontreDAO().listerTous();
			CBId.setItems(FXCollections.observableArrayList(rencontres));
			CBId.getSelectionModel().selectFirst();
					
			//close();
			return;
		}
		catch(ExceptionAccesDB e)
		{
			new MessageBox(this, AlertType.ERROR, "Erreur d'acces a la base de donne", e.getMessage());
		}					
	}
	
	/**
	 * Mettre ajour le contenue dans la fenetre quand on change la CBID des rencontre courante
	 * @param numRencontre  id de la nouvelle recontre
	 */
	private void CBChangerRencontre(int numRencontre)
	{
		Rencontre rencontre = new Rencontre();
		//on recupere la rencontre selon l'numRencontre
		try
		{
			rencontre = FabriqueDAO.getInstance().getRencontreDAO().charger(numRencontre);
		}
		catch(ExceptionAccesDB e)
		{
			new MessageBox(this, AlertType.ERROR, "Erreur d'acces a la base de donne", e.getMessage());
			return;
		}  
		
		TFPhase.setText(rencontre.getPhase());
		TFEquipe1.setText(Integer.toString(rencontre.getE1()));
		TFEquipe2.setText(Integer.toString(rencontre.getE2()));
		TFArbitre.setText(Integer.toString(rencontre.getArbitre()));
		TFTable.setText(Integer.toString(rencontre.getTable()));
		TFGagnant.setText(Integer.toString(rencontre.getGagnant()));
		TFResultat.setText(rencontre.getResultat());		
		
		TFResultE1.setText("");
		TFResultE2.setText("");
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
