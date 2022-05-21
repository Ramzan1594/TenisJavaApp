package controleur;

import classeMetier.*;
import coucheAccesDB.*;
import coucheMetier.*;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.*;
import javafx.stage.*;

import java.io.File;
import java.nio.file.Files;

public class AjouterJoueur extends BaseFenetre
{
	@FXML private TextField TFNom;
	@FXML private TextField TFPrenom;
	@FXML private ComboBox<String> CBStyle;
	@FXML private TextField TFNomImage;
	
	@FXML private Button BChargerImage;
	@FXML private Button BAjouter;
	@FXML private Button BFermer;
	
	@FXML private ImageView IVImage;
	
	private File FichierSrc = null;
	
	
	/**
	 * Constructeur qui cree la fenetre
	 * @param fenParent  objet stage represantant la fenetre parent
	 */
	public AjouterJoueur(Stage fenParent) 
	{
		super(fenParent, "AjouterJoueurVue.xml", "Ajouter un joueur", 550, 265);
		
		//Selectionner le 1er style dans le comboBox
		CBStyle.getSelectionModel().selectFirst();
		
		//Afficher la fenetre
		showAndWait();
	}
	
	/**
	 * Methode qui ouvre une boite de dialogue permettant de selection une image sur un disque=================================
	 */
	@FXML
	private void BOuvrirFichierImage()
	{
		FileChooser btImage = new FileChooser();
		btImage.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JPG", "*.jpg"));
		FichierSrc = btImage.showOpenDialog(null);
		
		if(FichierSrc != null)
		{
			TFNomImage.setText(FichierSrc.getName());
			IVImage.setImage(new Image("file:" + FichierSrc.getPath()));
		}
	}
	
	/**
	 * Methode qui est execute quand on clique sur le boutton ajouter et qui ajout le jouer en DB ==============================
	 */
	@FXML
	private void BAjouterJoueur()
	{
		Joueur joueur;
		
		//verifie les donne sur le joueur
		try
		{
			joueur = CoucheMetier.getInstance().testerContraintesJoueur(TFNom.getText(), TFPrenom.getText(), CBStyle.getValue(), TFNomImage.getText());
		}
		catch(ExceptionMetier e)
		{
			new MessageBox(this, AlertType.WARNING, "Erreur de test", e.getMessage());
			return;
		}
		
		
		//ajouter le joueur dans la DB et copie l'image ========================================================================
		try
		{
			FabriqueDAO.getInstance().debuterTransaction();
			
			FabriqueDAO.getInstance().getJoueurDAO().ajouter(joueur);
			
			if(FichierSrc != null)
			{
				File FichierDst = new File(System.getProperty("user.dir") + "/img/joueur/" + FichierSrc.getName());
				Files.copy(FichierSrc.toPath(), FichierDst.toPath(), REPLACE_EXISTING);
			}
			
			FabriqueDAO.getInstance().validerTransaction();
			
			close();
			return;
		}
		catch(ExceptionAccesDB e)
		{
			new MessageBox(this, AlertType.ERROR, "Erreur d'acces a la base de donne", e.getLocalizedMessage());
		}
		catch(Exception e)
		{
			new MessageBox(this, AlertType.ERROR, "Erreur", e.getMessage());
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
