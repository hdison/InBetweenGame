package game;

/**
 * Assignment:  In-Between Game -- Game GUI. (Game Driver)
 * Description: This application takes the place of the game driver and makes
 * use of the House, Person and Card classes (which also create Deck 
 * objects) to allow users to interact with the game.  
 * -- GameGui creates all of the visual components (buttons, labels, images, 
 * textboxes, etc) to display the game pieces, and also includes the 
 * event handlers that allow users to interact with game and displays 
 * error messages (if any are needed).
 * GUI is setup so that any number from 1 to 4 players can choose to 
 * join the fun! 
 * @author Holly Dison
 * @date 7/18/2019
 *   
 */

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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.Group;
import javafx.scene.Scene;

import javafx.stage.Stage;

public class GameGui extends Application	
{
	// Declare variables related to the play of the game. 
	// *Note: visual component variables are defined with their corresponding
	// specifications and attributes in start method

	private String amount; 	 		// Stores amount that is bet as a string
	private String btwnMsg = "";	// Displays whether 3rd card is between
	private House house;			// House obect to use all house methods
	private Card Card1;				// Stores first card dealt
	private Card Card2;				// Stores second card dealt
	private Card Card3;				// Stores third card dealt
	private String p1Name;			// Stores first player's name
	private String p2Name;			// Stores second player's name
	private String p3Name;			// Stores third player's name
	private String p4Name;			// Stores fourth player's name
	private Person[] players = new Person[4]; // Array to hold up to 4 players
	private Person currentPlayer;	// Reference to Person in play
	private int playerIndex = 0;	// Index of player in player array
	private int numPlayers = 0; 	// Number of players at start of game
	private int  playerCount = 0;	// Number of players remaining in game 

	// Flag whether to initialize or reset player index 
	private boolean firstRound = true;	

	public static void main(String[] args) 
	{
		launch(args);	
	}

	// Setup and add all components to main panel
	@Override
	public void start(Stage stage) throws FileNotFoundException,  IllegalBetException 
	{
		stage.setTitle("Let's Play In-Between!");

		final Scene scene = new Scene(new Group(), 800, 550);
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
		grid.setHgap(10);
		grid.setVgap(5);
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
		HBox YesNoBtns = new HBox(5);
		YesNoBtns.setAlignment(Pos.TOP_CENTER);
		YesNoBtns.setPadding(new Insets(25));
		YesNoBtns.setVisible(false);

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

		YesNoBtns.getChildren().addAll(yesBtn, noBtn);

		//------------   PLAYER 1 LABELS, TEXTBOXES AND BUTTONS --------------//

		// Label for name of player
		Label p1Lbl = new Label("Player 1 \nWhat is your name?");
		p1Lbl.setFont(Font.font("Tahoma", FontWeight.BOLD, 16));
		p1Lbl.setWrapText(true);
		p1Lbl.setVisible(true); // Change text to name after it's entered

		// TextField for player to enter their name.
		TextField p1Txt = new TextField ();
		p1Txt.setMaxWidth(100);
		p1Txt.setVisible(true);  // Set false after name is entered

		// Place Bet message label
		Label betLbl1 = new Label();
		betLbl1.setAlignment(Pos.TOP_CENTER);
		betLbl1.setFont(Font.font("Tahoma", FontWeight.BOLD, 16));
		betLbl1.setWrapText(true);
		betLbl1.setVisible(false);

		// Label for player's bankroll
		Label p1Amt = new Label("Player 1 Bankroll = $100"); 
		p1Amt.setFont(Font.font("Tahoma", FontWeight.BOLD, 16));
		p1Amt.setTextFill( Color.BLACK );
		p1Amt.setWrapText(true);
		p1Amt.setVisible(false);

		//------------   PLAYER 2 LABELS, TEXTBOXES AND BUTTONS --------------//

		// Label for name of player
		Label p2Lbl = new Label("Player 2 \n What is your name?");
		p2Lbl.setFont(Font.font("Tahoma", FontWeight.BOLD, 16));
		p2Lbl.setWrapText(true);
		p2Lbl.setVisible(false); 

		// TextField for player to enter their name
		TextField p2Txt = new TextField ();
		p2Txt.setMaxWidth(100);
		p2Txt.setVisible(false);  

		// Place Bet message label
		Label betLbl2 = new Label();
		betLbl2.setAlignment(Pos.TOP_CENTER);
		betLbl2.setFont(Font.font("Tahoma", FontWeight.BOLD, 16));
		betLbl2.setWrapText(true);
		betLbl2.setVisible(false);

		// Label for player's bankroll
		Label p2Amt = new Label("Player 2 Bankroll = $100"); 
		p2Amt.setFont(Font.font("Tahoma", FontWeight.BOLD, 16));
		p2Amt.setTextFill( Color.BLACK );
		p2Amt.setWrapText(true);
		p2Amt.setVisible(false);

		//------------   PLAYER 3 LABELS, TEXTBOXES AND BUTTONS --------------//

		// Label for name of player
		Label p3Lbl = new Label("Player 3 \n What is your name?");
		p3Lbl.setFont(Font.font("Tahoma", FontWeight.BOLD, 16));
		p3Lbl.setAlignment(Pos.BOTTOM_CENTER);
		p3Lbl.setWrapText(true);
		p3Lbl.setVisible(false);

		// TextField for player to enter their name
		TextField p3Txt = new TextField ();
		p3Txt.setMaxWidth(100);
		p3Txt.setVisible(false);  

		// Place Bet message label
		Label betLbl3 = new Label();
		betLbl3.setAlignment(Pos.TOP_CENTER);
		betLbl3.setFont(Font.font("Tahoma", FontWeight.BOLD, 16));
		betLbl3.setWrapText(true);
		betLbl3.setVisible(false);

		// Label for player's bankroll
		Label p3Amt = new Label("Player 3 Bankroll = $100"); 
		p3Amt.setFont(Font.font("Tahoma", FontWeight.BOLD, 16));
		p3Amt.setTextFill( Color.BLACK );
		p3Amt.setWrapText(true);
		p3Amt.setVisible(false);

		//------------   PLAYER 4 LABELS, TEXTBOXES AND BUTTONS --------------//

		// Label for name of player
		Label p4Lbl = new Label("Player 4 \n What is your name?");
		p4Lbl.setFont(Font.font("Tahoma", FontWeight.BOLD, 16));
		p4Lbl.setWrapText(true);
		p4Lbl.setVisible(false); 

		// TextField for player to enter their name
		TextField p4Txt = new TextField ();
		p4Txt.setMaxWidth(100);
		p4Txt.setVisible(false);  

		// Place Bet message label
		Label betLbl4 = new Label();
		betLbl4.setAlignment(Pos.TOP_CENTER);
		betLbl4.setFont(Font.font("Tahoma", FontWeight.BOLD, 16));
		betLbl4.setWrapText(true);
		betLbl4.setVisible(false);

		// Label for player's bankroll
		Label p4Amt = new Label("Player 4 Bankroll = $100"); 
		p4Amt.setFont(Font.font("Tahoma", FontWeight.BOLD, 16));
		p4Amt.setTextFill( Color.BLACK );
		p4Amt.setWrapText(true);
		p4Amt.setVisible(false);

		// -----------------------------------------------------------//
		// Create small one column grids for P3 and P4 components
		GridPane subGrid1 = new GridPane();
		subGrid1.setAlignment(Pos.CENTER);
		//subGrid1.setPadding(new Insets(15));
		subGrid1.setHgap(10);
		subGrid1.setVgap(5);

		subGrid1.add(p3Lbl, 0, 0);
		subGrid1.add(p3Amt, 0, 1);
		subGrid1.add(p3Txt, 0, 2);
		subGrid1.add(betLbl3, 0, 2);  // Same location, alternate visiblity

		GridPane subGrid2 = new GridPane();
		subGrid2.setAlignment(Pos.CENTER);
		//subGrid2.setPadding(new Insets(15));
		subGrid2.setHgap(10);
		subGrid2.setVgap(5);

		subGrid2.add(p4Lbl, 0, 0);
		subGrid2.add(p4Amt, 0, 1);
		subGrid2.add(p4Txt, 0, 2);
		subGrid2.add(betLbl4, 0, 2);  // Same location, alternate visiblity
		// -----------------------------------------------------------//

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
		GridPane.setHalignment(msgLbl2a, HPos.CENTER);

		// Do you want to play message 
		Label msgLbl2b = new Label();
		msgLbl2b.setTextAlignment(TextAlignment.CENTER);
		msgLbl2b.setFont(Font.font("Tahoma", FontWeight.BOLD, 16));
		msgLbl2b.setWrapText(true);
		msgLbl2b.setVisible(false); // Reset visibility in handlers
		GridPane.setHalignment(msgLbl2b, HPos.CENTER);
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
		errLbl.setTextFill( Color.CRIMSON );
		errLbl.setWrapText(true);
		errLbl.setVisible(false);

		// Label to show current amount in pot
		Label houseAmt = new Label(); 
		houseAmt.setFont(Font.font("Tahoma", FontWeight.BOLD, 18));
		houseAmt.setTextFill( Color.BLACK );
		houseAmt.setStyle("-fx-background-color: YELLOW");
		houseAmt.setBorder(new Border(new BorderStroke(Color.YELLOW, 
				BorderStrokeStyle.SOLID, null, new BorderWidths(5))));
		houseAmt.setWrapText(true);
		houseAmt.setVisible(false);

		// Back of card image object
		Image backOfCard = new Image("card_back.png");

		// Heart, Diamond, Spade, Club and Suits Images for decoration
		Image heart = new Image("heart.png");
		ImageView heartView = new ImageView(heart);
		heartView.setFitHeight(85);
		heartView.setFitWidth(85);

		Image diamond = new Image("diamond.png");
		ImageView diamondView = new ImageView(diamond);
		diamondView.setFitHeight(85);
		diamondView.setFitWidth(75);

		Image club = new Image("club.png");
		ImageView clubView = new ImageView(club);
		clubView.setFitHeight(85);
		clubView.setFitWidth(75);

		Image spade = new Image("spade.png");
		ImageView spadeView = new ImageView(spade);
		spadeView.setFitHeight(85);
		spadeView.setFitWidth(105);

		Image suits = new Image("cards_suits.png");
		ImageView suitsView = new ImageView(suits);
		suitsView.setFitHeight(80);
		suitsView.setFitWidth(250);

		// Create HBox to put decorative heart and club together
		HBox HeartClubBox = new HBox(2);
		HeartClubBox.setAlignment(Pos.CENTER);
		HeartClubBox.setPadding(new Insets(25));
		HeartClubBox.getChildren().addAll(heartView, clubView);

		// Create HBox to put decorative spade and diamond together
		HBox DiamondSpadeBox = new HBox(2);
		DiamondSpadeBox.setAlignment(Pos.CENTER);
		DiamondSpadeBox.setPadding(new Insets(25));
		DiamondSpadeBox.getChildren().addAll(spadeView, diamondView);

		HBox FirstTwoCards = new HBox(2);
		FirstTwoCards.setAlignment(Pos.CENTER);
		FirstTwoCards.setPadding(new Insets(25));

		ImageView card1View = new ImageView(backOfCard);
		card1View.setFitHeight(130);
		card1View.setFitWidth(85);
		FirstTwoCards.getChildren().add(card1View);

		ImageView  card2View = new ImageView(backOfCard);
		card2View.setFitHeight(130);
		card2View.setFitWidth(85);
		FirstTwoCards.getChildren().add(card2View);
		FirstTwoCards.setVisible(false);

		ImageView thirdCard = new ImageView(backOfCard);
		thirdCard.setFitHeight(130);
		thirdCard.setFitWidth(85);
		thirdCard.setVisible(false);


		// ----------  ADD COMPONENTS TO THE GRID -----------  //

		// Left portion (Player 1 and Player 3)
		grid.add(p1Lbl, 0, 0);
		grid.add(p1Amt, 0, 1);
		grid.add(p1Txt, 0, 2);
		grid.add(betLbl1, 0, 2); // Same location, alternate visibility
		grid.add(msgLbl1, 0, 3);
		grid.add(HeartClubBox, 0, 3); // Same location, alternate visibility (Heart and Club)
		grid.add(subGrid1, 0, 4);


		// Middle portion (House Amount, Message to player and Card Display)	
		grid.add(houseAmt, 1, 0);
		grid.add(dealBtn, 1, 1);
		grid.add(errLbl, 1, 1);		// Same location, alternate visibility
		grid.add(betTxt, 1, 2);
		grid.add(msgLbl2a, 1, 2); 	// Same location, alternate visibility
		grid.add(msgLbl2b, 1, 3);
		grid.add(FirstTwoCards, 1, 3); 		// Same location, alternate visibility
		grid.add(YesNoBtns, 1, 4);
		grid.add(thirdCard, 1, 4);	// Same location, alternate visibility
		grid.add(suitsView, 1, 4); 	// Alternate visibility (Row of Suits)


		// Right portion (Player 2) 
		grid.add(p2Lbl, 2, 0);
		grid.add(p2Amt, 2, 1);
		grid.add(p2Txt, 2, 2);
		grid.add(betLbl2, 2, 2); 	//Same location, must alternate visibility
		grid.add(msgLbl3, 2, 3);
		grid.add(DiamondSpadeBox, 2, 3); //Same location, alternate visibility
		grid.add(subGrid2, 2, 4);

		grid.setGridLinesVisible(true);
		//subGrid1.setGridLinesVisible(true);
		//subGrid2.setGridLinesVisible(true);

		pane.getChildren().addAll(grid );
		stage.setScene(scene);
		stage.show();

		//-----------------------  EVENT HANDLERS  --------------------------//

		/**
		 * Read and set player 1 name, ask if they want to play
		 */
		p1Txt.setOnAction(new EventHandler<ActionEvent>() 
		{
			@Override   
			public void handle(ActionEvent event) 
			{
				p1Name = p1Txt.getText();
				p1Lbl.setText(p1Name);
				p1Txt.setVisible(false);
				suitsView.setVisible(false);
				msgLbl2b.setVisible(true);
				msgLbl2b.setText("You start with $100. \nThe buy-in is $20." + 
						"\nDo you wish to play?"); 
				YesNoBtns.setVisible(true);  	// Yes/No buttons
			}
		});

		/**
		 * Read and set player 2 name, ask if they want to play
		 */
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
				FirstTwoCards.setVisible(false);
				suitsView.setVisible(false);
				thirdCard.setVisible(false);
				msgLbl2b.setVisible(true);
				msgLbl2b.setText("You start with $100. \nThe buy-in is $20." + 
						"\nDo you wish to play?");
				YesNoBtns.setVisible(true); 	// Yes/No buttons
			}
		});

		/**
		 * Read and set player 3 name, ask if they want to play
		 */
		p3Txt.setOnAction(new EventHandler<ActionEvent>() 
		{
			@Override   
			public void handle(ActionEvent event) 
			{
				p3Name = p3Txt.getText();
				p3Lbl.setText(p3Name);
				p3Txt.setVisible(false);

				// Change visibility of middle panel components
				dealBtn.setVisible(false);
				FirstTwoCards.setVisible(false);
				thirdCard.setVisible(false);
				suitsView.setVisible(false);
				msgLbl2b.setVisible(true);
				msgLbl2b.setText("You start with $100. \nThe buy-in is $20." + 
						"\nDo you wish to play?");
				YesNoBtns.setVisible(true); 	// Yes/No buttons
			}
		});

		/**
		 * Read and set player 4 name, ask if they want to play
		 */
		p4Txt.setOnAction(new EventHandler<ActionEvent>() 
		{
			@Override   
			public void handle(ActionEvent event) 
			{
				p4Name = p4Txt.getText();
				p4Lbl.setText(p4Name);
				p4Txt.setVisible(false);

				// Change visibility of middle panel components
				dealBtn.setVisible(false);
				FirstTwoCards.setVisible(false);
				suitsView.setVisible(false);
				thirdCard.setVisible(false);
				msgLbl2b.setVisible(true);
				msgLbl2b.setText("You start with $100. \nThe buy-in is $20." + 
						"\nDo you wish to play?");
				YesNoBtns.setVisible(true); 	// Yes/No buttons
			}
		});

		/**
		 * YES button handler - If YES, then go on to next player to see if 
		 * they want to join game too. If all players have been invited, 
		 * then show deal button and backs of cards to begin game
		 */
		yesBtn.setOnAction((ActionEvent event) -> 
		{	
			// Setup Player 1 and then ask for Player 2s name
			if(playerIndex == 0)
			{
				players[0] = new Person(p1Name, 100);
				players[0].setBuyIn(20);

				p1Amt.setText(players[0].getName() + " Bankroll = $" 
						+ players[0].getBankroll()); 

				p1Amt.setVisible(true);

				betLbl1.setVisible(true);
				betLbl1.setTextFill( Color.BLUE );
				betLbl1.setText("Great! Let's play!");

				p2Lbl.setVisible(true); // Shows next player's invite
				p2Txt.setVisible(true); 
				numPlayers++;

			}
			// Setup Player 2 and then ask for Player 3s name
			else if (playerIndex == 1)
			{
				players[1] = new Person(p2Name, 100);
				players[1].setBuyIn(20);

				p2Amt.setText(players[1].getName() + " Bankroll = $" 
						+ players[1].getBankroll()); 

				p2Amt.setVisible(true);

				betLbl2.setVisible(true);
				betLbl2.setTextFill( Color.BLUE);
				betLbl2.setText("Great! Let's play!");

				p3Lbl.setVisible(true); // Shows next player's invite
				p3Txt.setVisible(true);
				numPlayers++;
			}

			// Setup Player 3 and then ask for Player 4s name
			else if (playerIndex == 2)
			{
				players[2] = new Person(p3Name, 100);
				players[2].setBuyIn(20);

				p3Amt.setText(players[2].getName() + " Bankroll = $" 
						+ players[2].getBankroll()); 
				p3Amt.setVisible(true);

				betLbl3.setVisible(true);
				betLbl3.setTextFill( Color.BLUE);
				betLbl3.setText("Great! Let's play!");

				p4Lbl.setVisible(true);  // Shows next player's invite
				p4Txt.setVisible(true);
				numPlayers++;
			}
			// Setup Player 4 and then start game!
			else if (playerIndex == 3)
			{
				players[3] = new Person(p4Name, 100);
				players[3].setBuyIn(20);

				p4Amt.setText(players[3].getName() + " Bankroll = $" 
						+ players[3].getBankroll()); 
				p4Amt.setVisible(true);

				betLbl4.setVisible(true);
				betLbl4.setTextFill( Color.BLUE);
				betLbl4.setText("Great! Let's play!");

				numPlayers++;
			}

			// Hide Yes/No buttons and starting buy-in message:
			msgLbl2b.setVisible(false);
			YesNoBtns.setVisible(false);

			if(playerIndex == 3) // If all players have been invited
			{
				//Initialize house object and show current pot
				house = new House(20, numPlayers);
				houseAmt.setVisible(true);
				houseAmt.setText(" Current Pot = $" + house.getPot() +  " ");

				//Show deal button, backs of first two cards, and current pot
				dealBtn.setVisible(true);
				FirstTwoCards.setVisible(true);			// Backs of first two cards
				thirdCard.setVisible(true); 	// Back of third card
			}

			playerIndex++;
			playerCount = numPlayers;  // 
		});

		/**
		 * NO button handler -- If first person says NO, then no one plays.
		 * If at least one person had already indicated yes, then show deal 
		 * button and backs of cards to begin game.
		 */
		noBtn.setOnAction((ActionEvent event) -> 
		{
			// Hide Yes/No buttons and starting buy-in message:
			msgLbl2b.setVisible(false);
			YesNoBtns.setVisible(false);

			// If Player 1 says no, display message and end game
			if (playerIndex == 0)
			{
				HeartClubBox.setVisible(false);
				msgLbl1.setVisible(true);
				msgLbl1.setText("Smart Choice! \nGambling is bad "
						+ "\nfor your health!");
				msgLbl1.setStyle("-fx-background-color: WHITE ");
				msgLbl1.setBorder(new Border(new BorderStroke(
						Color.RED, BorderStrokeStyle.SOLID, 
						null, new BorderWidths(5))));

			}

			// If previous player wanted to play, then display message and 
			// initialize items necessary to start game.
			else if(playerIndex == 1) // If both players have been invited
			{
				// Show message and hide Player2 if they chose not to play
				DiamondSpadeBox.setVisible(false);
				msgLbl3.setVisible(true);
				msgLbl3.setText("Smart Choice! \nGambling is bad "
						+ "\nfor your health!");
				msgLbl3.setStyle("-fx-background-color: WHITE ");
				msgLbl3.setBorder(new Border(new BorderStroke(
						Color.RED, BorderStrokeStyle.SOLID, 
						null, new BorderWidths(5))));
				p2Lbl.setVisible(false);
				p2Txt.setText("");
				p2Txt.setVisible(false);

				//Initialize house object and show current pot
				house = new House(20, numPlayers);
				houseAmt.setVisible(true);
				houseAmt.setText("Current Pot = $" + house.getPot()); 

				//Show deal button, backs of first two cards, and current pot
				dealBtn.setVisible(true);
				FirstTwoCards.setVisible(true);			// Backs of first two cards
				thirdCard.setVisible(true); 	// 
			}

			// If previous player wanted to play, then display message and 
			// initialize items necessary to start game.
			else if(playerIndex == 2) // If three players have been invited
			{
				// Show message and hide Player3 if they chose not to play
				HeartClubBox.setVisible(false);
				msgLbl1.setVisible(true);
				msgLbl1.setText("Smart Choice! \nGambling is bad "
						+ "\nfor your health!");
				p3Lbl.setVisible(false);
				p3Txt.setText("");
				p3Txt.setVisible(false);

				//Initialize house object and show current pot
				house = new House(20, numPlayers);
				houseAmt.setVisible(true);
				houseAmt.setText("Current Pot = $" + house.getPot()); 

				//Show deal button, backs of first two cards, and current pot
				dealBtn.setVisible(true);
				FirstTwoCards.setVisible(true);			// Backs of first two cards
				thirdCard.setVisible(true); 	// 
			}

			// If previous player wanted to play, then display message and 
			// initialize items necessary to start game.
			else if(playerIndex == 3) // If three players have been invited
			{
				// Show message and hide Player4 if they chose not to play
				DiamondSpadeBox.setVisible(false);
				msgLbl3.setVisible(true);
				msgLbl3.setText("Smart Choice! \nGambling is bad "
						+ "\nfor your health!");
				p4Lbl.setVisible(false);
				p4Txt.setText("");
				p4Txt.setVisible(false);

				//Initialize house object and show current pot
				house = new House(20, numPlayers);
				houseAmt.setVisible(true);
				houseAmt.setText("Current Pot = $" + house.getPot()); 

				//Show deal button, backs of first two cards, and current pot
				dealBtn.setVisible(true);
				FirstTwoCards.setVisible(true);			// Backs of first two cards
				thirdCard.setVisible(true); 	// Back of third card 
			}

		});

		/**
		 * deal button handler -- Gets and displays first two cards
		 * and asks for current player to place their bet. 
		 */
		dealBtn.setOnAction((ActionEvent event) -> 
		{// ** Get current player, show first two non-equal cards

			// Turn back on decorative images (if needed)
			HeartClubBox.setVisible(true);
			DiamondSpadeBox.setVisible(true);
			
			// Make sure first two cards are shown
			FirstTwoCards.setVisible(true);

			// Clear any textboxes and don't gamble messages if needed
			betTxt.setText("");
			msgLbl1.setText("");
			msgLbl3.setText("");
			msgLbl2a.setVisible(false);
			msgLbl2b.setVisible(false);
			betLbl2.setVisible(false);
			betLbl3.setVisible(false);
			betLbl4.setVisible(false);
			
			// Temporarily disable itself, then reactivate when 
			// bet is entered 
			dealBtn.setDisable(true);

			// If its first hand played or if we've reached last 
			// person in the array then go back to first player
			// make sure player is active before setting to current player
			do 
			{
				if(firstRound || playerIndex == (numPlayers - 1))
				{
					playerIndex = 0; 
					firstRound = false;
				}
				else if (numPlayers > 0)
				{
					playerIndex++;

				}
			} while(firstRound == false && !players[playerIndex].getActivePlayer() );			

			// ** Need to determine who is current player and set accordingly
			currentPlayer = players[playerIndex];

			// Call dealHandGUI to determine first and second cards
			house.dealHandGUI(currentPlayer);
			Card1 = house.getCard1();      	//Get first card         
			Card2 = house.getCard2();       //Get second card

			// Get card images to display
			Image card1 = new Image("" + Card1.getSuit() + Card1.getFace() + ".png");
			Image card2 = new Image("" + Card2.getSuit() + Card2.getFace() + ".png");
			card1View.setImage(card1);
			card2View.setImage(card2);
			thirdCard.setImage(new Image("card_back.png"));

			// Ask correct player for their bet
			if(playerIndex == 0)
			{
				betLbl1.setVisible(true);	// ONLY SHOW PLAYER 1
				betLbl2.setVisible(false);
				betLbl1.setText("Place your bet!");
			}
			else if(playerIndex == 1)
			{
				betLbl2.setVisible(true);	// ONLY SHOW PLAYER 2
				betLbl2.setText("Place your bet!");
			}
			else if(playerIndex == 2)  
			{
				betLbl3.setVisible(true); 	// ONLY SHOW PLAYER 3
				betLbl3.setText("Place your bet!");	
			}
			else if(playerIndex == 3)
			{
				betLbl4.setVisible(true); 	// ONLY SHOW PLAYER 4
				betLbl4.setText("Place your bet!");
			}

			// Show textbox for current player to enter bet amount
			betTxt.setVisible(true);

		});

		/**
		 * Get bet amount from player and perform error handling
		 * to make sure it is a valid bet amount, then display 
		 * third card and message as to whether card was in-between
		 * or not (i.e. did player win the current hand)
		 * Update current player bankroll and pot amounts
		 * Make the textField and label invisible again
		 */
		betTxt.setOnAction(new EventHandler<ActionEvent>() 
		{
			@Override   
			public void handle(ActionEvent event) 
			{
				// call playHandGUI which tests bet amount for validity,
				// determines whether or not player won current hand,
				// determines how much is won or lost, and sets bankroll 
				// and pot amount accordingly
				amount = betTxt.getText(); 
				try 
				{
					house.playHandGUI(currentPlayer, amount);	  
				}
				// Displays error message and stops flow to let user 
				// re-enter bet amount 
				catch (IllegalBetException e)
				{
					//System.out.println(e);
					msgLbl2a.setVisible(false); 
					betTxt.setText("");  	// Clear bet amount
					dealBtn.setVisible(false);
					errLbl.setVisible(true);
					errLbl.setText(e.getMessage()); 

					return;  
				}

				// Turn of error message when valid bet amount is entered
				errLbl.setVisible(false);
				dealBtn.setVisible(true);

				// Get and display third card
				Card3 = house.getCard3();
				Image card3 = new Image("" + Card3.getSuit() + 
						Card3.getFace() + ".png");
				thirdCard.setImage(card3);

				// Determine message to display to user based on 3rd card
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
				case 2:
					p3Amt.setText(players[2].getName() + " Bankroll = $" 
							+ players[2].getBankroll());
					break;
				case 3:
					p4Amt.setText(players[3].getName() + " Bankroll = $" 
							+ players[3].getBankroll());
				}

				// Get and re-display current amount in pot
				houseAmt.setText("Current Pot = $" + house.getPot()); 

				if(house.getPot() == 0)
				{
					//Determine winner by checking who has largest bankroll
					int i = findLargest(); 

					betTxt.setVisible(false);
					msgLbl2a.setVisible(true);
					msgLbl2a.setStyle("-fx-background-color: WHITE ");
					msgLbl2a.setText(players[i].getName() + " You WIN the game!");

					dealBtn.setVisible(false);

				}
				else if (currentPlayer.getBankroll() <= 0)  
				{ 
					betTxt.setVisible(false);
					msgLbl2a.setVisible(true);
					msgLbl2a.setText(currentPlayer.getName() + " YOU LOSE!");

					FirstTwoCards.setVisible(false);
					msgLbl2b.setVisible(true);
					msgLbl2b.setTextFill(Color.WHITE);
					msgLbl2b.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
					msgLbl2b.setText("Press DEAL to continue!");
					
					// Turn off "Place Bet" Message of player who went bust
					switch(playerIndex)
					{
					case 0:
						betLbl1.setVisible(false);
						break;
					case 1:
						betLbl2.setVisible(false);
						break;
					case 2:  
						betLbl3.setVisible(false);
						break; 
					case 3:
						betLbl4.setVisible(false);
					}

					//Decrease number of players remaining
					playerCount--;

					// If we are down to last player, then they win
					if(playerCount == 1) 
					{
						String winner = "";
						// Determine who the last man standing was!
						for(int i = 0; i < 4; i++)
						{
							if(players[i] != null && players[i].getActivePlayer())
								winner = players[i].getName();

						}

						// Update game view
						dealBtn.setVisible(false);
						msgLbl2b.setVisible(false);
						FirstTwoCards.setVisible(true);
						msgLbl2a.setVisible(true);
						msgLbl2a.setStyle("-fx-background-color: WHITE ");
						msgLbl2a.setBorder(new Border(new BorderStroke(
								Color.RED, BorderStrokeStyle.SOLID, 
								null, new BorderWidths(5))));
						msgLbl2a.setText("GAME OVER \n " + winner 
								+ " \n WINS \n THE GAME!!");	
						
					}
				}
				else // Continue game with remaining players.
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
					case 2:  
						betLbl3.setVisible(false);
						break; 
					case 3:
						betLbl4.setVisible(false);
					}
				}

				dealBtn.setDisable(false); // Reactivate deal button
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

