package game;

import javafx.application.Application;
import java.io.FileInputStream; 
import java.io.FileNotFoundException;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.layout.Pane;
import javafx.scene.Group;
import javafx.scene.Scene;

import javafx.stage.Stage;

public class GameGui2  extends Application	
{

	//private double amount;  // variable to store amount that is bet
	private String amount;  // variable to store amount that is bet
	private String btwnMsg = "";
	private House house;
	private Card Card1;
	private Card Card2;
	private Card Card3;
	private Person p1;
	private Person p2;
	private Person p3;
	private Person p4;
	private String p1Name;
	private String p2Name;
	private String p3Name;
	private String p4Name;
	private Person[] players = {p1, p2, p3, p4};
	private Person currentPlayer;
	private int playerIndex = 0;
	private int numPlayers = 0;
	private int count = 0;
	private boolean done = false;
	private boolean firstRound = true;


	public static void main(String[] args) 
	{
		launch(args);	
	}

	@Override
	public void start(Stage stage) throws FileNotFoundException,  IllegalBetException 
	{
		stage.setTitle("Let's Play In-Between!");

		final Scene scene = new Scene(new Group(), 700, 550);
		scene.setFill(Color.LIGHTGREEN);

		VBox pane = new VBox(2);
		pane.setAlignment(Pos.TOP_CENTER);
		((Group)scene.getRoot()).getChildren().add(pane);

		//Scene scene = new Scene(pane, 700, 550); // How I had it originally

		// Main grid to display cards and  include player names, input, 
		// error messages etc
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setPadding(new Insets(25));
		grid.setHgap(15);
		grid.setVgap(15);
		ColumnConstraints colConst = new ColumnConstraints();
		colConst.setHalignment(HPos.CENTER);
		grid.getColumnConstraints().add(colConst); // For column 1
		grid.getColumnConstraints().add(colConst); // For column 2
		grid.getColumnConstraints().add(colConst); // For column 3

		Label lbl = new Label("Play Game");
		lbl.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		lbl.setAlignment(Pos.TOP_CENTER);
		//pane.getChildren().add(lbl);

		// Create YES/NO Buttons for each player to choose 
		// whether or not to play.
		HBox hb1 = new HBox(5);
		hb1.setAlignment(Pos.TOP_CENTER);
		hb1.setPadding(new Insets(25));
		hb1.setVisible(false);

		Button yesBtn = new Button();
		yesBtn.setFont(Font.font("Tahoma", FontWeight.NORMAL, 18));
		yesBtn.setText("YES");
		yesBtn.setTextFill(Color.WHITE );
		yesBtn.setStyle("-fx-background-color: green");

		Button noBtn = new Button();
		noBtn.setFont(Font.font("Tahoma", FontWeight.NORMAL, 18));
		noBtn.setText("NO");
		noBtn.setTextFill(Color.WHITE );
		noBtn.setStyle("-fx-background-color: red");

		hb1.getChildren().addAll(yesBtn, noBtn);

		//------------   PLAYER 1 LABELS, TEXTBOXES AND BUTTONS --------------//
		// Label prompting player1 to enter their name.
		Label p1Lbl = new Label("Player 1 \nWhat is your name?");
		p1Lbl.setFont(Font.font("Tahoma", FontWeight.BOLD, 16));
		p1Lbl.setTextFill( Color.GREEN );
		p1Lbl.setWrapText(true);
		p1Lbl.setVisible(true); // Change text to name after it's entered

		// Textbox for player1 to enter their name.
		TextField p1Txt = new TextField ();
		p1Txt.setMaxWidth(100);
		p1Txt.setVisible(true);  // Set false after name is entered

		Label betLbl1 = new Label();
		betLbl1.setAlignment(Pos.TOP_CENTER);
		betLbl1.setFont(Font.font("Tahoma", FontWeight.BOLD, 16));
		betLbl1.setWrapText(true);
		betLbl1.setVisible(false);

		Label p1Amt = new Label("Player 1 Bankroll = $100"); 
		p1Amt.setFont(Font.font("Tahoma", FontWeight.BOLD, 16));
		p1Amt.setTextFill( Color.BLACK );
		p1Amt.setWrapText(true);
		p1Amt.setVisible(false);

		//------------   PLAYER 2 LABELS, TEXTBOXES AND BUTTONS --------------//
		// Textbox for player2 to enter their name
		Label p2Lbl = new Label("Player 2 \n What is your name?");
		p2Lbl.setFont(Font.font("Tahoma", FontWeight.BOLD, 16));
		p2Lbl.setTextFill( Color.GREEN );
		p2Lbl.setWrapText(true);
		p2Lbl.setVisible(false); 

		TextField p2Txt = new TextField ();
		p2Txt.setMaxWidth(100);
		p2Txt.setVisible(false);  

		Label betLbl2 = new Label();
		betLbl2.setAlignment(Pos.TOP_CENTER);
		betLbl2.setFont(Font.font("Tahoma", FontWeight.BOLD, 16));
		betLbl2.setWrapText(true);
		betLbl2.setVisible(false);

		Label p2Amt = new Label("Player 2 Bankroll = $100"); 
		p2Amt.setFont(Font.font("Tahoma", FontWeight.BOLD, 16));
		p2Amt.setTextFill( Color.BLACK );
		p2Amt.setWrapText(true);
		p2Amt.setVisible(false);

		// Left Message Label 
		Label msgLbl1 = new Label();
		msgLbl1.setAlignment(Pos.TOP_CENTER);
		msgLbl1.setFont(Font.font("Tahoma", FontWeight.BOLD, 16));
		msgLbl1.setWrapText(true);
		msgLbl1.setVisible(false); // Reset visibility in handlers

		// ----------------- Middle Message Labels -----------------//
		// Exception/Win/Lose messages
		Label msgLbl2a = new Label();
		msgLbl2a.setTextFill( Color.CRIMSON);
		msgLbl2a.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
		msgLbl2a.setWrapText(true);
		msgLbl2a.setVisible(false); // Reset visibility in handlers
		grid.setHalignment(msgLbl2a, HPos.CENTER);

		// Do you want to play message 
		Label msgLbl2b = new Label();
		msgLbl2b.setAlignment(Pos.TOP_CENTER);
		msgLbl2b.setFont(Font.font("Tahoma", FontWeight.BOLD, 16));
		msgLbl2b.setWrapText(true);
		msgLbl2b.setVisible(false); // Reset visibility in handlers
		// ---------------------------------------------------------//

		// Right Message Label
		Label msgLbl3 = new Label();
		msgLbl3.setAlignment(Pos.TOP_CENTER);
		msgLbl3.setFont(Font.font("Tahoma", FontWeight.BOLD, 16));
		msgLbl3.setWrapText(true);
		msgLbl3.setVisible(false); // Reset visibility in handlers

		// Deal button to play a hand
		Button dealBtn = new Button();
		dealBtn.setFont(Font.font("Tahoma", FontWeight.NORMAL, 18));
		dealBtn.setText("Deal Cards");
		dealBtn.setWrapText(true);
		dealBtn.setVisible(false); // Make visible when player says yes

		// Textbox for Player to enter their bet
		TextField betTxt = new TextField ();
		betTxt.setMaxWidth(100);
		betTxt.setAlignment(Pos.TOP_CENTER);
		betTxt.setVisible(false);  // Set true after deal

		// Label message for Player 2 to enter their bet
		Label bet2Lbl = new Label("Place your bet!");
		bet2Lbl.setFont(Font.font("Tahoma", FontWeight.BOLD, 16));
		bet2Lbl.setTextFill( Color.RED);
		bet2Lbl.setWrapText(true);
		bet2Lbl.setVisible(false); // Use later and set true on deal

		// Label to show error message after bet exception
		Label errLbl = new Label(); 
		errLbl.setFont(Font.font("Tahoma", FontWeight.BOLD, 16));
		errLbl.setTextFill( Color.FIREBRICK );
		errLbl.setWrapText(true);
		errLbl.setVisible(false);

		// Label to show current amount in pot
		Label houseAmt = new Label(); 
		houseAmt.setFont(Font.font("Tahoma", FontWeight.BOLD, 16));
		houseAmt.setTextFill( Color.BLACK );
		houseAmt.setWrapText(true);
		houseAmt.setVisible(false);

		// Back of card image object
		Image backOfCard = new Image("card_back.png");

		HBox hb2 = new HBox(2);
		hb2.setAlignment(Pos.CENTER);
		hb2.setPadding(new Insets(25));

		ImageView imageView2 = new ImageView(backOfCard);
		imageView2.setFitHeight(130);
		imageView2.setFitWidth(85);
		hb2.getChildren().add(imageView2);

		ImageView imageView3 = new ImageView(backOfCard);
		imageView3.setFitHeight(130);
		imageView3.setFitWidth(85);
		hb2.getChildren().add(imageView3);
		hb2.setVisible(false);

		ImageView imageView1 = new ImageView(backOfCard);
		imageView1.setFitHeight(130);
		imageView1.setFitWidth(85);
		imageView1.setVisible(false);


		// ----------  ADD COMPONENTS TO THE GRID -----------  //

		// Left portion (Player 1)
		grid.add(p1Lbl, 0, 0);
		grid.add(p1Txt, 0, 1);
		grid.add(betLbl1, 0, 1); // Same location, must alternate visibility
		grid.add(msgLbl1, 0, 2);
		grid.add(p1Amt, 0, 4);

		// Middle portion (Message to player and Card Display)	
		grid.add(dealBtn, 1, 0);
		grid.add(errLbl, 1, 0);		// Same location, alternate visibility
		grid.add(betTxt, 1, 1);
		grid.add(msgLbl2a, 1, 1); 	// Same location, alternate visibility
		grid.add(msgLbl2b, 1, 2);
		grid.add(hb2, 1, 2); 		// Same location, alternate visibility
		grid.add(hb1, 1, 3);
		grid.add(imageView1, 1, 3);	// Same location, alternate visibility
		grid.add(houseAmt, 1, 4);

		// Right portion (Player 2) 
		grid.add(p2Lbl, 2, 0);
		grid.add(p2Txt, 2, 1);
		grid.add(betLbl2, 2, 1); // Same location, must alternate visibility
		grid.add(msgLbl3, 2, 2);
		// Nothing in (2, 3) yet
		grid.add(p2Amt, 2, 4);

		grid.setGridLinesVisible(true);
		pane.getChildren().addAll(grid );
		stage.setScene(scene);
		stage.show();

		//-------------------- EVENT HANDLERS  --------------------------//

		// Read and set player's names
		p1Txt.setOnAction(new EventHandler<ActionEvent>() 
		{
			@Override   
			public void handle(ActionEvent event) 
			{
				p1Name = p1Txt.getText();
				p1Lbl.setText(p1Name);
				p1Txt.setVisible(false);
				msgLbl2b.setVisible(true);
				msgLbl2b.setText("You start with $100. \nThe buy-in is $20." + 
						"\nDo you wish to play?"); 
				hb1.setVisible(true);  	// Yes/No buttons
			}
		});

		p2Txt.setOnAction(new EventHandler<ActionEvent>() 
		{
			@Override   
			public void handle(ActionEvent event) 
			{
				p2Name = p2Txt.getText();
				p2Lbl.setText(p2Name);
				p2Txt.setVisible(false);

				// Change visibility of middle panel components
				dealBtn.setVisible(false);
				hb2.setVisible(false);
				imageView1.setVisible(false);
				msgLbl2b.setVisible(true);
				msgLbl2b.setText("You start with $100. \nThe buy-in is $20." + 
						"\nDo you wish to play?");
				hb1.setVisible(true); 	// Yes/No buttons
			}
		});

		yesBtn.setOnAction((ActionEvent event) -> 
		{	

			// Setup Player 1 and then ask for Player 2s name
			if(playerIndex == 0)
			{
				p1 = new Person(p1Name, 100);
				p1.setBuyIn(20);
				players[0] = p1;

				p1Amt.setText(players[0].getName() + " Bankroll = $" + p1.getBankroll()); 

				p1Amt.setVisible(true);

				msgLbl1.setTextFill( Color.RED );
				msgLbl1.setText("Great! Let's play!");

				p2Lbl.setVisible(true); 
				p2Txt.setVisible(true); 
				numPlayers++;

			}
			else if (playerIndex == 1)
			{
				p2 = new Person(p2Name, 100);
				p2.setBuyIn(20);
				players[1] = p2;

				p2Amt.setText(players[1].getName() + " Bankroll = $" 
						+ players[1].getBankroll()); 

				p2Amt.setVisible(true);

				msgLbl3.setTextFill( Color.DARKRED);
				msgLbl3.setText("Great! Let's play!");
				numPlayers++;
			}

			// Hide Yes/No buttons and starting buy-in message:
			msgLbl2b.setVisible(false);
			hb1.setVisible(false);

			if(playerIndex == 1) // If both players have been invited
			{
				//Initialize house object and show current pot
				house = new House(20, numPlayers);
				houseAmt.setVisible(true);
				houseAmt.setText("Current Pot = $" + house.getPot());

				//Show deal button, backs of first two cards, and current pot
				dealBtn.setVisible(true);
				hb2.setVisible(true);			// Backs of first two cards
				imageView1.setVisible(true); 	// Back of third card
			}

			playerIndex++;
		});

		noBtn.setOnAction((ActionEvent event) -> 
		{
			// Hide Yes/No buttons and starting buy-in message:
			msgLbl2b.setVisible(false);
			hb1.setVisible(false);

			if (playerIndex == 0)
			{
				msgLbl1.setVisible(true);
				msgLbl1.setText("Smart Choice! \nGambling is bad "
						+ "\nfor your health!");
			}
			else if(playerIndex == 1) // If both players have been invited
			{
				// Show message and hide Player2 if they chose not to play
				msgLbl3.setVisible(true);
				msgLbl3.setText("Smart Choice! \nGambling is bad "
						+ "\nfor your health!");
				p2Lbl.setVisible(false);
				p2Txt.setText("");
				p2Txt.setVisible(false);

				//Initialize house object and show current pot
				house = new House(20, numPlayers);
				houseAmt.setVisible(true);
				houseAmt.setText("Current Pot = $" + house.getPot()); 

				//Show deal button, backs of first two cards, and current pot
				dealBtn.setVisible(true);
				hb2.setVisible(true);			// Backs of first two cards
				imageView1.setVisible(true); 	// Back of third card 
			}

		});

		dealBtn.setOnAction((ActionEvent event) -> 
		{// ** Get current player, show first two non-equal cards

			System.out.println("numPlayers = " + numPlayers);

			// If its first hand played or if we've reached last player
			// then go back to first player

			if(firstRound || playerIndex == (numPlayers - 1))
			{
				playerIndex = 0; 
				firstRound = false;
			}
			else if (numPlayers > 0)
			{
				playerIndex++;
			}

			// Clear any textboxes and don't gamble message if needed
			betTxt.setText("");
			msgLbl3.setText("");
			msgLbl2a.setVisible(false);

			// ** Need to determine who is current player and set accordingly

			currentPlayer = players[playerIndex];

			System.out.println("playerIndex = " + playerIndex);
			System.out.println("player = " + currentPlayer.getName());

			house.dealHandGUI(currentPlayer);
			Card1 = house.getCard1();      	//Get first card         
			Card2 = house.getCard2();       //Get second card

			// Get card images to display
			Image card1 = new Image("" + Card1.getSuit() + Card1.getFace() + ".png");
			Image card2 = new Image("" + Card2.getSuit() + Card2.getFace() + ".png");
			imageView2.setImage(card1);
			imageView3.setImage(card2);
			imageView1.setImage(new Image("card_back.png"));


			// Ask correct player for their bet
			if(playerIndex == 0)
			{
				betLbl1.setVisible(true);
				//betLbl2.setVisible(false); // Should already be set false in bet
				betLbl1.setText("Place your bet!");
			}
			else if(playerIndex == 1)
			{
				//betLbl1.setVisible(false); // Should already be set false in bet
				betLbl2.setVisible(true);
				betLbl2.setText("Place your bet!");
			}
			//			else if{playerIndex == 2}  // DON'T HAVE PLAYER 3 or 4 YET
			//			{
			//				betLbl1.setVisible(false);
			//				betLbl2.setVisible(false);	
			//				betLbl3.setVisible(true); // ONLY SHOW PLAYER 3
			//				betLbl3.setText("Place your bet!");
			//			}
			//			else if{playerIndex == 3}
			//			{
			//				betLbl1.setVisible(false);
			//				betLbl2.setVisible(false);
			//				betLbl3.setVisible(false);
			//				betLbl4.setVisible(true);
			//				betLbl4.setText("Place your bet!");
			//			}

			// Show textbox for current player to enter bet amount
			betTxt.setVisible(true);

		});

		// Get bet amount from player and show third card
		// Update player bankroll and current pot amount accordingly
		// Make the textField and label invisible again //
		betTxt.setOnAction(new EventHandler<ActionEvent>() 
		{
			@Override   
			public void handle(ActionEvent event) 
			{
				amount = betTxt.getText(); 
				try 
				{
					house.playHandGUI(currentPlayer, amount);	  
				}
				catch (IllegalBetException e)
				{
					System.out.println(e);
					msgLbl2a.setVisible(false); 
					betTxt.setText("");  	// Clear bet amount
					dealBtn.setVisible(false);
					errLbl.setVisible(true);
					errLbl.setText(e.getMessage()); 

					return;  // Stop flow to let user re-enter bet amount 
				}

				errLbl.setVisible(false);
				dealBtn.setVisible(true);

				// Get and display third card
				Card3 = house.getCard3();
				Image card3 = new Image("" + Card3.getSuit() + 
						Card3.getFace() + ".png");
				imageView1.setImage(card3);

				if(house.isBtwn())
				{
					btwnMsg = " Your card IS in between ";
				}
				else
				{
					btwnMsg = " Your card is NOT in between ";
				}

				// Get and re-display amount for current player and pot

				switch(playerIndex)
				{
				case 0:
					p1Amt.setText(players[0].getName() + " Bankroll = $" 
							+ players[0].getBankroll());
					break;
				case 1:
					p2Amt.setText(players[1].getName() + " Bankroll = $" 
							+ players[1].getBankroll());
					break;
					//					case 2:
					//						p3Amt.setText(players[2].getName() + " Bankroll = $" 
					//								+ players[2].getBankroll());
					//						break;
					//					case 3:
					//						p4Amt.setText(players[3].getName() + " Bankroll = $" 
					//								+ players[3].getBankroll());
					//						break;
				}

				// Get and re-display current amount in pot
				houseAmt.setText("Current Pot = $" + house.getPot()); 


				if(house.getPot() == 0)
				{
					//Determine winner by checking who has largest bankroll
					int i = findLargest(); 

					System.out.println ("YOU WIN!");
					betTxt.setVisible(false);
					msgLbl2a.setVisible(true);
					msgLbl2a.setText(players[i].getName() + " You WIN the game!");
					dealBtn.setVisible(false);

				}
				else if (currentPlayer.getBankroll() <= 0)  
				{//*Note player bankroll can go negative if they lose double 

					betTxt.setVisible(false);
					msgLbl2a.setVisible(true);
					msgLbl2a.setText(currentPlayer.getName() + " YOU LOSE!");
					dealBtn.setVisible(false);
				}
				else 
				{
					betTxt.setVisible(false);
					msgLbl2a.setVisible(true);
					msgLbl2a.setText(btwnMsg);

					// Turn off "Place Bet" Message after current player
					// Completes their hand
					switch(playerIndex)
					{
					case 0:
						betLbl1.setVisible(false);
						break;
					case 1:
						betLbl2.setVisible(false);
						break;
						//						case 2:  // HAVEN'T MADE PLAYER 3 or 4 yet!
						//							betLbl3.setVisible(false);
						//							break;
						//						case 3:
						//							betLbl4.setVisible(false);
					}

				}
				// NOT DONE YET! STILL NEED TO EDIT TO GENERALIZE PLAYER!	
			}
		});
	}

	/**
	 * findLargest locates the largest integer in the array 
	 * @return index of largest value found after specified location
	 */
	private int findLargest() 
	{
		int largest = 0;

		for (int i = 1; i < numPlayers; i++) 
		{
			if(players[i].getActivePlayer())
			{
				if (players[i].getBankroll() > players[largest].getBankroll())
					largest = i;
			}
		}
		return largest;
	}
}

