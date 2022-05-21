package controleur;

import classeMetier.*;
import coucheAccesDB.*;
import coucheMetier.CoucheMetier;
import coucheMetier.ExceptionMetier;

import java.util.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class GenererTournoi extends BaseFenetre
{
	@FXML private TextField TFR1, TFR2, TFR3, TFR4, TFR5, TFR6, TFR7;
	@FXML private Button BGenererQF;
	@FXML private Button BGenererDF;	
	@FXML private Button BGenererF;
	@FXML private Button BFermer;
	
	public GenererTournoi(Stage fenParent) 
	{
		super(fenParent, "GenererTournoiVue.xml", "Generer tournoi", 670, 530);
		List<Rencontre> rencontres = null;
		
		//on recupere la liste des rencontres
		try 
		{
			rencontres = FabriqueDAO.getInstance().getRencontreDAO().listerTous();
		} 
		catch (ExceptionAccesDB e) 
		{
			new MessageBox(this, AlertType.ERROR, "Erreur d'acces a la base de donne", e.getMessage());
			return;
		}
		
		//on rempli les TF s'il y a deja des rencontre
		if(rencontres.size() > 0 )
		{
			BGenererChangerTF(rencontres);
		}
		
		//Afficher la fenetre
		showAndWait();
	}
	
	/** BOUTTON GENERER QUART DE FINAL =======================================================================================================
	 * Methode qui est execute quand on appuie sur le boutton Generer quart de final et qui
	 * prend la liste des equipe dans la DB , en fait des rencontre aleatoire et les met en DB et les affiche 
	 */
	@FXML
	private void BGenererQuart()
	{
		List<Equipe> lesEquipes = null;
		List<Arbitre>lesArbitres= null;
		List<Table>  lesTables  = null;
		List<Rencontre> lesRencontres = null;
		
		//Recupere la liste de toutes les equipes dans la DB
		try 
		{
			lesEquipes = FabriqueDAO.getInstance().getEquipeDAO().listerTous();
			lesArbitres= FabriqueDAO.getInstance().getArbitreDAO().listerTous();
			lesTables  = FabriqueDAO.getInstance().getTableDAO().listerTous();
			lesRencontres = FabriqueDAO.getInstance().getRencontreDAO().listerTous();
		} 
		catch (ExceptionAccesDB e) 
		{
			new MessageBox(this, AlertType.ERROR, "Erreur d'acces a la base de donne", e.getMessage());
			return;
		}
		
		if(lesEquipes.size() < 8)
		{
			new MessageBox(this, AlertType.WARNING, "ATTENTION !", "Il faut au minimum 8 equipes pour commencer un tournoi !");
			return;
		}
		
		//Generer les rencontre ALEATOIREMENT et les enregistrer en DB
		if(lesRencontres.size() < 4)
			GenereRencontreAleatoire(lesEquipes, lesArbitres, lesTables);
		
		//Recupere la listes des rencontre
		try 
		{
			lesRencontres = FabriqueDAO.getInstance().getRencontreDAO().listerTous();
		} 
		catch (ExceptionAccesDB e) 
		{
			new MessageBox(this, AlertType.ERROR, "Erreur d'acces a la base de donne", e.getMessage());
			return;
		}
		
		BGenererChangerTF(lesRencontres);		
	}
	
	/**
	 * Methode qui genere les rencontre en choisissant des equipes aleatoirement
	 * @param lesEquipes  liste des equipes
	 * @param lesTables   liste des arbitres
	 * @param lesArbitres liste des tables
	 */
	private void GenereRencontreAleatoire(List<Equipe> lesEquipes, List<Arbitre> lesArbitres, List<Table> lesTables)
	{
		//on fait une boucle de 4 car on a besoin de 4 rencontre pour les quart de final
		for(int nbRencontre = 0; nbRencontre < 4; nbRencontre++){ 
			
			
			Random rand = new Random();
			Rencontre rencontre = new Rencontre();
			Equipe equipeA = new Equipe();
			Equipe equipeB = new Equipe();
			Arbitre arbitre= new Arbitre();
			Table  table = new Table(); 
			int gagnant=0, resultatEA=0 , resultatEB=0;
			
			//Creation des valeurs selectionner aleatoirement pour cree une rencontre
			equipeA = lesEquipes.get( rand.nextInt(lesEquipes.size())); //la on aura une EQUIPE ALEATOIREMENT 
			lesEquipes.remove(equipeA);
			equipeB = lesEquipes.get( rand.nextInt(lesEquipes.size()));
			arbitre = lesArbitres.get(rand.nextInt(lesArbitres.size()));
			table = lesTables.get(  rand.nextInt(lesTables.size()));
			
			//enlever les equipes, arbitres et table deja utilise des list
			lesEquipes.remove(equipeB);
			lesArbitres.remove(arbitre);
			lesTables.remove(table);
			
			
			/*resultatEA = (int)(Math.random()*100)+1;
			resultatEB = (int)(Math.random()*100)+1;
			
			if(resultatEA < resultatEB)
				gagnant = equipeB.getId();
			else
				gagnant = equipeA.getId();*/
				
			//tester les contrainte metiers
			try 
			{
				rencontre = CoucheMetier.getInstance().testerContraintesRencontre("Quart de final", equipeA.getId(), equipeB.getId(), arbitre.getId(), table.getId(),
																					gagnant, resultatEA + " - " + resultatEB );
			} catch (ExceptionMetier e) 
			{
				new MessageBox(this, AlertType.WARNING, "Erreur de test", e.getMessage());
				return;
			}
			
			
			//Enregistrer la rencontre en DB
			try
			{
				FabriqueDAO.getInstance().debuterTransaction();
				
				FabriqueDAO.getInstance().getRencontreDAO().ajouter(rencontre);
				
				FabriqueDAO.getInstance().validerTransaction();
			}
			catch(ExceptionAccesDB e1)
			{
				new MessageBox(this, AlertType.ERROR, "Erreur d'acces a la base de donne", e1.getMessage());
				return;
			}
		}
		
	}
	

	/** BOUTTON GENERER DEMI FINAL et FINAL=======================================================================================================
	 * Methode qui est execute quand on appuie sur le boutton Generer demi de final et final et qui
	 * prend la liste des rencontre dans la DB , prend les vainceur de chaque rencontre et en cree des nouvelle rencontre et les met en DB et l'affiche
	 */
	@FXML
	private void BGenererDemiFEtFinal()
	{
		//Lister les arbitres, tables et rencontres  deja present cad les QF et/ou DF
		List<Arbitre>lesArbitres= null;
		List<Table>  lesTables  = null;
		
		List<Rencontre> lesRencontres = null;
		
		try 
		{
			lesArbitres   = FabriqueDAO.getInstance().getArbitreDAO().listerTous();
			lesTables     = FabriqueDAO.getInstance().getTableDAO().listerTous();
			lesRencontres = FabriqueDAO.getInstance().getRencontreDAO().listerTous();
		} 
		catch (ExceptionAccesDB e) 
		{
			new MessageBox(this, AlertType.ERROR, "Erreur d'acces a la base de donne", e.getMessage());
			return;
		}
		
		//tester si les rencontres precedentes ont toutes des gagnant
		for(Rencontre rencontre : lesRencontres)
		{
			if(rencontre.getGagnant() == 0)
			{
				new MessageBox(this, AlertType.WARNING, "ATTENTION !", "Les rencontres des matches precedent doivent TOUTES avoir un gagnant !\n" 
						+ "Allez dans Modifier une rencontre");
				return;
			}
		}
		
		
		if(lesRencontres.size() >= 4)
		{
			GenererRencontreApresQF(lesRencontres, lesArbitres, lesTables);
		}
		else
		{
			new MessageBox(this, AlertType.WARNING, "ATTENTION !", "Le tournoi n'est pas encore a l'etape que vous avez selectionner !");
		}
		
		//Recupere la listes des rencontre pour mettre a jour les TF
		try 
		{
			lesRencontres = FabriqueDAO.getInstance().getRencontreDAO().listerTous();
		} 
		catch (ExceptionAccesDB e) 
		{
			new MessageBox(this, AlertType.ERROR, "Erreur d'acces a la base de donne", e.getMessage());
			return;
		}
		
		BGenererChangerTF(lesRencontres);
	}
	
	
	/**
	 * Methode qui genere les rencontre apres les QF cad  demi final et final
	 * @param lesRencontres 
	 * @param lesTables 
	 * @param lesArbitres 
	 */
	private void GenererRencontreApresQF(List<Rencontre> lesRencontres, List<Arbitre> lesArbitres, List<Table> lesTables)
	{
		if(lesRencontres.size() >= 4 && lesRencontres.size() < 6)    //DEMI FINAL ======================================================
		{
			for(int nbRencontre = 0; nbRencontre < 2; nbRencontre++)
			{
				Random rand = new Random();
				Rencontre rencontre = new Rencontre();	
				Arbitre arbitre = new Arbitre();
				Table table		= new Table();
				int equipeA= 0, equipeB=0, gagnant=0, resultatEA=0 , resultatEB=0;
				
				//Creation des valeurs pour cree une rencontre
				if(nbRencontre == 0)
				{
					equipeA = lesRencontres.get(0).getGagnant();
					equipeB = lesRencontres.get(1).getGagnant();
				}
				if(nbRencontre == 1)
				{
					equipeA = lesRencontres.get(2).getGagnant();
					equipeB = lesRencontres.get(3).getGagnant();
				}
				
				arbitre = lesArbitres.get(rand.nextInt(lesArbitres.size()));
				table 	= lesTables.get(  rand.nextInt(lesTables.size()));
				
				//enlever les arbitres et tables deja utilise des list				
				lesArbitres.remove(arbitre);
				lesTables.remove(table);
				
				
				/*resultatEA = (int)(Math.random()*100);
				resultatEB = (int)(Math.random()*100);
				
				if(resultatEA < resultatEB)
					gagnant = equipeB;
				else
					gagnant = equipeA;*/
					
				//tester les contrainte metiers
				try 
				{
					rencontre = CoucheMetier.getInstance().testerContraintesRencontre("Demi final", equipeA, equipeB, arbitre.getId(), table.getId(),
																						gagnant, resultatEA + " - " + resultatEB );
				} catch (ExceptionMetier e) 
				{
					new MessageBox(this, AlertType.WARNING, "Erreur de test", e.getMessage());
					return;
				}
				
				
				//Enregistrer la rencontre en DB
				try
				{
					FabriqueDAO.getInstance().debuterTransaction();
					
					FabriqueDAO.getInstance().getRencontreDAO().ajouter(rencontre);
					
					FabriqueDAO.getInstance().validerTransaction();
				}
				catch(ExceptionAccesDB e1)
				{
					new MessageBox(this, AlertType.ERROR, "Erreur d'acces a la base de donne", e1.getMessage());
					return;
				}
			}
		}
		
		if(lesRencontres.size() >= 6 && lesRencontres.size() < 7)   						//FINAL =================================================================================
		{
			Random rand = new Random();
			Rencontre rencontre = new Rencontre();	
			Arbitre arbitre = new Arbitre();
			Table table		= new Table();
			int equipeA= 0, equipeB=0, gagnant=0, resultatEA=0 , resultatEB=0;
			
			//Creation des valeurs pour cree une rencontre			
			equipeA = lesRencontres.get(4).getGagnant();
			equipeB = lesRencontres.get(5).getGagnant();
			
			
			arbitre = lesArbitres.get(rand.nextInt(lesArbitres.size()));
			table 	= lesTables.get(  rand.nextInt(lesTables.size()));
			
			//enlever les arbitres et tables deja utilise des list				
			lesArbitres.remove(arbitre);
			lesTables.remove(table);
			
			
			/*resultatEA = (int)(Math.random()*100);
			resultatEB = (int)(Math.random()*100);
			
			if(resultatEA < resultatEB)
				gagnant = equipeB;
			else
				gagnant = equipeA;*/
				
			//tester les contrainte metiers
			try 
			{
				rencontre = CoucheMetier.getInstance().testerContraintesRencontre("Final", equipeA, equipeB, arbitre.getId(), table.getId(),
																					gagnant, resultatEA + " - " + resultatEB );
			} catch (ExceptionMetier e) 
			{
				new MessageBox(this, AlertType.WARNING, "Erreur de test", e.getMessage());
				return;
			}
			
			
			//Enregistrer la rencontre en DB
			try
			{
				FabriqueDAO.getInstance().debuterTransaction();
				
				FabriqueDAO.getInstance().getRencontreDAO().ajouter(rencontre);
				
				FabriqueDAO.getInstance().validerTransaction();
			}
			catch(ExceptionAccesDB e1)
			{
				new MessageBox(this, AlertType.ERROR, "Erreur d'acces a la base de donne", e1.getMessage());
				return;
			}
		}
	}
	
	
	/**
	 * Methode qui met a jour les TF quand on appuie sur le boutton Genere les rencontres
	 * @param rencontre   list des rencontre
	 */
	private void BGenererChangerTF(List<Rencontre> rencontre)
	{
		if(rencontre.size() == 4)
		{
			TFR1.setText(rencontre.get(0).toString());
			TFR2.setText(rencontre.get(1).toString());
			TFR3.setText(rencontre.get(2).toString());
			TFR4.setText(rencontre.get(3).toString());	
		}
		if(rencontre.size() > 4 && rencontre.size() <= 6 )
		{
			TFR1.setText(rencontre.get(0).toString());
			TFR2.setText(rencontre.get(1).toString());
			TFR3.setText(rencontre.get(2).toString());
			TFR4.setText(rencontre.get(3).toString());
			TFR5.setText(rencontre.get(4).toString());
			TFR6.setText(rencontre.get(5).toString());
		}
		if(rencontre.size() > 6)
		{
			TFR1.setText(rencontre.get(0).toString());
			TFR2.setText(rencontre.get(1).toString());
			TFR3.setText(rencontre.get(2).toString());
			TFR4.setText(rencontre.get(3).toString());
			TFR5.setText(rencontre.get(4).toString());
			TFR6.setText(rencontre.get(5).toString());
			TFR7.setText(rencontre.get(6).toString());
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
