package controleur;

import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.*;
import javafx.stage.Stage;

public class MessageBox extends BaseFenetre
{
	@FXML private  Label LMsgErreur;
	@FXML private  ImageView IVImage;
	

	/**
	 * Constructeur qui cree la fenetre
	 * @param fenParent 	objet stage represantant la fenetre parent
	 * @param typeErreur	la nature du message a afficher(information , attention ou erreur)
	 * @param msgTitre 		titre du message 
	 * @param message		contrenue du message
	 */
	public MessageBox(Stage fenParent, AlertType typeErreur, String msgTitre, String message) 
	{
		super(fenParent, "MessageBoxVue.xml", "", 570, 100);     
		
		//fixer l'image et le message dans la fenetre
		if(typeErreur == AlertType.INFORMATION)
		{
			IVImage.setImage(new Image("file:img/message/information.jpg"));
		}
		if(typeErreur == AlertType.WARNING)
		{
			IVImage.setImage(new Image("file:img/message/attention.jpg"));
		}
		if(typeErreur == AlertType.ERROR)
		{
			IVImage.setImage(new Image("file:img/message/erreur.jpg"));
		}
		
		setTitle(msgTitre);
		LMsgErreur.setText(message);
		
		//afficher la fenetre	
		showAndWait();
	}
	
	/**
	 * Methode qui est execute quand on clique sur le boutton fermer  et ferme la fenetre
	 */
	
	@FXML
	private void BFermer(ActionEvent event)
	{
		close();
	}

}
