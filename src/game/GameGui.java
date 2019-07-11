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
import javafx.scene.Scene;

import javafx.stage.Stage;

public class GameGui extends Application	
{

	private double amount;  // variable to store amount that is bet
	private String btwn = "Not between";
	private House house;
	private Card Card1;
	private Card Card2;
	private Card Card3;
	private Person p1;
	private Person p2;
	private String p1Name;
	private String p2Name;
	private boolean done = false;

	public static void main(String[] args) 
	{
		launch(args);	
	}

	public void gameDriver() throws IllegalBetException 
	{
		Scanner stdin = new Scanner(System.in);
		String input;

		do {
			house.playHand(p1);
			if (!p1.getActivePlayer()) {
				done = true;
			} else if (house.getPot() == 0) {
				done = true;
			}
			else {
				System.out.println("The pot is now at " + house.getPot());
				System.out.println("Press return to continue or any key " +
						"to quit");
				input = stdin.nextLine();
				if (input.length() > 0) {
					done = true;
				}
			}
		} while (!done);
		if (p1.getBankroll() <= 0) {
			System.out.println("You lose! Better luck next time.");
		} else if (house.getPot() <= 0) {
			System.out.println("You win!");
		}	
	}

	@Override
	public void start(Stage stage) throws FileNotFoundException,  IllegalBetException 
	{
		//gameDriver();

		stage.setTitle("Let's Play In-Between!");

		VBox pane = new VBox(2);
		pane.setAlignment(Pos.TOP_CENTER);
		Scene scene = new Scene(pane, 700, 550);

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

		//------------   PLAYER 2 LABELS, TEXTBOXES AND BUTTONS --------------//
		// Label and textbox for player2 to enter their name
		Label p2Lbl = new Label("Player 2 \n What is your name?");
		p2Lbl.setFont(Font.font("Tahoma", FontWeight.BOLD, 16));
		p2Lbl.setTextFill( Color.GREEN );
		p2Lbl.setWrapText(true);
		p2Lbl.setVisible(true); // Change text to name after it's entered

		TextField p2Txt = new TextField ();
		p2Txt.setMaxWidth(100);
		p2Txt.setVisible(true);  // Set false after name is entered

		Label p2Amt = new Label("Player 2 Bankroll = $100"); 
		p2Amt.setFont(Font.font("Tahoma", FontWeight.BOLD, 16));
		p2Amt.setTextFill( Color.BLACK );
		p2Amt.setWrapText(true);

		// Left Message Label 
		Label msgLbl1 = new Label();
		msgLbl1.setAlignment(Pos.TOP_CENTER);
		msgLbl1.setFont(Font.font("Tahoma", FontWeight.BOLD, 16));
		msgLbl1.setWrapText(true);
		msgLbl1.setVisible(false); // Use later and set visibility in handlers

		// Middle Message Label 
		Label msgLbl2 = new Label();
		msgLbl2.setAlignment(Pos.TOP_CENTER);
		msgLbl2.setFont(Font.font("Tahoma", FontWeight.BOLD, 16));
		msgLbl2.setWrapText(true);
		msgLbl2.setVisible(false); // Use later and set visibility in handlers

		// Textbox for Player to enter their bet
		TextField bet1Txt = new TextField ();
		bet1Txt.setMaxWidth(100);
		bet1Txt.setAlignment(Pos.TOP_CENTER);
		bet1Txt.setVisible(false);  // Set true after deal

		// Label and textbox for Player 2 to enter their bet
		Label bet2Lbl = new Label("Place your bet!");
		bet2Lbl.setFont(Font.font("Tahoma", FontWeight.BOLD, 16));
		bet2Lbl.setTextFill( Color.RED);
		bet2Lbl.setWrapText(true);
		bet2Lbl.setVisible(false); // Use later and set true on deal

		TextField bet2Txt = new TextField ();
		bet2Txt.setMaxWidth(100);
		bet2Txt.setVisible(false);  // Set true after deal

		// Deal button to play a hand
		Button dealBtn = new Button();
		dealBtn.setFont(Font.font("Tahoma", FontWeight.NORMAL, 18));
		dealBtn.setText("Deal Cards");
		dealBtn.setWrapText(true);
		dealBtn.setVisible(false); // Make visible when player says yes

		Label houseAmt = new Label(); 
		houseAmt.setFont(Font.font("Tahoma", FontWeight.BOLD, 16));
		houseAmt.setTextFill( Color.BLACK );
		houseAmt.setWrapText(true);
		houseAmt.setVisible(false);

		Image image1 = new Image("card_back.png");
		ImageView imageView1 = new ImageView(image1);
		imageView1.setFitHeight(130);
		imageView1.setFitWidth(85);
		imageView1.setVisible(false);

		HBox hb2 = new HBox(2);
		hb2.setAlignment(Pos.CENTER);
		hb2.setPadding(new Insets(25));

		ImageView imageView2 = new ImageView(image1);
		imageView2.setFitHeight(130);
		imageView2.setFitWidth(85);
		hb2.getChildren().add(imageView2);

		ImageView imageView3 = new ImageView(image1);
		imageView3.setFitHeight(130);
		imageView3.setFitWidth(85);
		hb2.getChildren().add(imageView3);
		hb2.setVisible(false);


		// ----------  ADD COMPONENTS TO THE GRID -----------  //

		// Left portion (Player 1)
		grid.add(p1Lbl, 0, 0);
		grid.add(p1Txt, 0, 1);
		grid.add(betLbl1, 0, 1); // Same location, must alternate visibility
		grid.add(msgLbl1, 0, 2);
		grid.add(p1Amt, 0, 4);

		// Middle portion (Message to player and Card Display)	
		grid.add(bet1Txt, 1, 1);
		grid.add(dealBtn, 1, 0);
		grid.add(msgLbl2, 1, 2);
		grid.add(hb2, 1, 2); 		// Same location, alternate visibility
		grid.add(hb1, 1, 3);
		grid.add(imageView1, 1, 3);	// Same location, alternate visibility
		grid.add(houseAmt, 1, 4);

		// Right portion (Player 2) 
		grid.add(p2Lbl, 2, 0);
		grid.add(p2Txt, 2, 1);
		grid.add(bet2Lbl, 2, 2);
		grid.add(bet2Txt, 2, 3);
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
				msgLbl2.setVisible(true);
				msgLbl2.setText("You start with $100. \nThe buy-in is $20." + 
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
				msgLbl2.setVisible(true);
				msgLbl2.setText("You start with $100. \nThe buy-in is $20." + 
						"\nDo you wish to play?");
				hb1.setVisible(true); 	// Yes/No buttons
			}
		});

		yesBtn.setOnAction((ActionEvent event) -> 
		{
			// Initialize player and pot
			// ** Later will need to specify which player **
			p1 = new Person(p1Name, 100);
			p1.setBuyIn(20);
			house = new House(20);
			p1Amt.setText(p1.getName() + " Bankroll = $" + p1.getBankroll()); 
			houseAmt.setText("Current Pot = $" + house.getPot()); 

			//yesBtn.setVisible(false);
			//noBtn.setVisible(false);
			msgLbl1.setTextFill( Color.RED );
			msgLbl1.setText("Great! Let's play!");

			// Hide Yes/No buttons and starting buy-in message:
			msgLbl2.setVisible(false);
			hb1.setVisible(false);

			//Show deal button, backs of first two cards, and current pot
			dealBtn.setVisible(true);
			hb2.setVisible(true);			// Backs of first two cards
			imageView1.setVisible(true); 	// Back of third card
			houseAmt.setVisible(true);

		});

		noBtn.setOnAction((ActionEvent event) -> 
		{
			yesBtn.setVisible(false);
			noBtn.setVisible(false);
			msgLbl2.setText("Smart Choice! \nGambling is bad "
					+ "\nfor your health!");

		});

		dealBtn.setOnAction((ActionEvent event) -> 
		{
			// ** Get current player, show first two non-equal cards
			Person currentPlayer = p1; // ** Need to determine who is current player

			if (!currentPlayer.getActivePlayer()) 
			{
				return;     // If the current player isn't active, skip them
			}
			do {
				Card1 = house.dealCard();       // Deal first card
				Card2 = house.dealCard();       // Deal second card
			} while (Card1.equals(Card2)); 

			// Get card images to display
			Image card1 = new Image("" + Card1.getSuit() + Card1.getFace() + ".png");
			Image card2 = new Image("" + Card2.getSuit() + Card2.getFace() + ".png");
			imageView2.setImage(card1);
			imageView3.setImage(card2);
			imageView1.setImage(new Image("card_back.png"));

			// Show textbox for player to enter bet amount
			betLbl1.setVisible(true);
			betLbl1.setText("Place your bet!");
			bet1Txt.setVisible(true);
		});

		// Get bet amount from player and show third card
		// Update player bankroll and current pot amount accordingly
		// Make the textField and label invisible again //
		bet1Txt.setOnAction(new EventHandler<ActionEvent>() 
		{
			@Override   
			public void handle(ActionEvent event) 
			{
				// ?? Error handling??
				// ?? How do I repeat input for text box ?? 
				//boolean isValid = false;
				//do {

				amount = Double.parseDouble(bet1Txt.getText()); 
				try 
				{
					p1.setBet(amount); 
				}
				catch (IllegalBetException e)
				{
					//System.out.println(e);
					msgLbl1.setText(e.getMessage());
				}

				//	} while (!isValid);
				msgLbl1.setText("Thanks for your $" + amount + " donation!");

				// Get and display third card
				Card3 = house.dealCard();
				Image card3 = new Image("" + Card3.getSuit() + 
						Card3.getFace() + ".png");

				imageView1.setImage(card3);

				//(THIS NEEDS TO USE A HOUSE METHOD RATHER THAN DO IT HERE!) 
				// This is incomplete and doesn't account for match etc
				//Is third card in between  
				if (Card3.compareTo(Card1) == 1
						&& Card3.compareTo(Card2) == -1
						|| Card3.compareTo(Card1) == -1
						&& Card3.compareTo(Card2) == 1) 
				{ 
					btwn = "The third card IS in between";
					
					p1.setBankroll(amount); // Why isn't this right? 
					p1Amt.setText(p1.getName() + " Bankroll = $" 
							+ p1.getBankroll());
				}
				else
				{
					btwn = "The third card is NOT in between";
					p1.setBankroll(-amount); // Why isn't this right? 
					p1Amt.setText(p1.getName() + " Bankroll = $" 
							+ p1.getBankroll());	
				}

				bet1Txt.setVisible(false);
				msgLbl1.setVisible(true);
				msgLbl1.setText(btwn);
			}
		});
	}
}

