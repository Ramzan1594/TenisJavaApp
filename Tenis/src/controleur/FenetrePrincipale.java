package controleur;

import coucheAccesDB.*;
import java.net.URL;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class FenetrePrincipale extends Application
{
	Stage Fenetre;
	/**
	 * Methode execute au chargement , ELLE CHARGE LA VUE ET AFFICHE LA FENETRE PRINCPALE
	 * @param fenetre l'objet Stage represantant la fenetre principale
	 */
	
	@Override
	public void start(Stage fenetre) throws Exception	
	{
		Fenetre = fenetre;
		
		//charger la vue dans la scenen  et associer l'instance courante en tant que controleura a cette vue		
		try
		{
			FXMLLoader loader = new  FXMLLoader(new URL("file:src/vue/FenetrePrincipaleVue.xml"));
			loader.setController(this);
			Scene scene = new Scene(loader.load(), 610, 365, Color.WHITE);
			scene.getStylesheets().add("vue/styleCSS.css");
			fenetre.setScene(scene);
						
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			System.exit(0);
		}			
		
		
		
		//parametre et afficher la fenetre principale
		fenetre.getIcons().add(new Image("file:/img/logo.jpg"));   // -- ne fonctionne pas
		fenetre.setTitle("Tournoi AVENGERS");
		fenetre.setResizable(false);
		fenetre.centerOnScreen();
		fenetre.show();
	}

	
	
	/**
	 * Metodde execute au demarrage de l'application 
	 * Elle cree la connection a la base de donne, puis elle charge la fenetre principale
	 */
	public static void main(String[] args) 
	{
		try
		{
			FabriqueDAO.getInstance().creeConnexion();
		}
		catch(ExceptionAccesDB e)
		{
			System.out.println(e.getMessage());
			System.exit(0);
		}
		
		launch(args);
	}
	
	/**
	 * Methode execute  lorsque l'utilisateur clique sur les rubriques des menus
	 */
	//==================================================TOURNOI
	@FXML 
	private void MGenererTournoi(ActionEvent event)
	{
		new GenererTournoi(Fenetre);
	}
	@FXML 
	private void MListerRencontres(ActionEvent event)
	{
		new ListerRencontre(Fenetre);
	}
	@FXML 
	private void MListerRencontresEquipe(ActionEvent event)
	{
		new ListerRencontresEquipe(Fenetre);
	}
	@FXML 
	private void MModifierRencontre(ActionEvent event)
	{
		new ModifierRencontre(Fenetre);
	}
	@FXML 
	private void MTerminerTournoi(ActionEvent event)
	{
		new TerminerTournoi(Fenetre);		
	}
		
	//====================================================JOUEUR
	@FXML
	private void MListerJoueurs(ActionEvent event)
	{
		new ListerJoueurs(Fenetre);
	}
	@FXML
	private void MAjouterJoueur(ActionEvent event)
	{
		new AjouterJoueur(Fenetre);
	}
	@FXML
	private void MSupprimerJoueur(ActionEvent event)
	{
		new SupprimerJoueur(Fenetre);
	}
	
	//====================================================EQUIPES
	@FXML
	private void MListerEquipes(ActionEvent event)
	{
		new ListerEquipes(Fenetre);
	}
	@FXML
	private void MAjouterEquipe(ActionEvent event)
	{
		new AjouterEquipe(Fenetre);
	}
	@FXML
	private void MSupprimerEquipe(ActionEvent event)
	{
		new SupprimerEquipe(Fenetre);
	}
	
	//====================================================ARBITRE TABLE
	@FXML
	private void MListerArbitres(ActionEvent event)
	{
		new ListerArbitres(Fenetre);
	}
	@FXML
	private void MListerTables(ActionEvent event)
	{
		new ListerTables(Fenetre);
	}
	
	//====================================================QUITTER
	@FXML
	private void MQuitter(ActionEvent event)
	{
		System.exit(0);
	}
	
	
	
}
