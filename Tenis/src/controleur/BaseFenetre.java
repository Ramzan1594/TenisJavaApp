package controleur;

import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.*;


public class BaseFenetre extends Stage
{
	/**
	 * Constructeur qui charge  la vue courante, associe le controleur et parametre la fenetre
	 * @param fenParent l'objet represante la fenetre parent
	 * @param vue   	nom du fichier  XML stockant la vue
	 * @param titre		titre de la fenetre
	 * @param largeur	largeur de la fenetre 
	 * @param hauteur	hauteur de la fenetre
	 */
	protected BaseFenetre(Stage fenParent, String vue, String titre, int largeur, int hauteur) 
	{
		try
		{
			FXMLLoader loader = new FXMLLoader(new URL("file:src/vue/"+ vue));
			loader.setController(this);
			Scene scene = new Scene(loader.load(), largeur, hauteur);
			scene.getStylesheets().add("vue/styleCSS.css");
			setScene(scene);
			
		}
		catch(Exception e)
		{
			System.out.println(("Probleme : " + e.getMessage()));
			System.exit(0);
		}
		setTitle(titre);
		setResizable(false);
		setX(fenParent.getX() + (fenParent.getWidth()  - largeur)/2);
		setY(fenParent.getY() + (fenParent.getHeight() - hauteur)/2);
		initOwner(fenParent);
		initModality(Modality.APPLICATION_MODAL);
	}
}
