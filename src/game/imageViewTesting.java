package game;

import javafx.application.Application;
import java.io.FileInputStream; 
import java.io.FileNotFoundException; 

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.layout.Pane;
import javafx.scene.Scene;

import javafx.stage.Stage;

public class imageViewTesting extends Application	
{
	private String amount;  // variable to store amount that is bet

	public static void main(String[] args) 
	{
		launch(args);
	}

	@Override
	public void start(Stage stage) throws FileNotFoundException 
	{
		stage.setTitle("Playing card image viewer");

		VBox pane = new VBox(5);
		pane.setAlignment(Pos.TOP_CENTER);
		Scene scene = new Scene(pane, 700, 550);

		Label lbl = new Label("Play Game");
		lbl.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		lbl.setAlignment(Pos.TOP_CENTER);
		//pane.getChildren().add(lbl);

		// Create YES/NO Buttons to play or not
		HBox hb1 = new HBox(5);
		hb1.setAlignment(Pos.CENTER);
		hb1.setPadding(new Insets(25));

		// Textbox (with label) for player to enter their bet
		Label betLbl = new Label("Place your bet!");
		betLbl.setFont(Font.font("Tahoma", FontWeight.BOLD, 16));
		betLbl.setTextFill( Color.RED );
		betLbl.setWrapText(true);
		betLbl.setVisible(false); // Use later and set true on deal

		TextField betAmt = new TextField ();
		betAmt.setMaxWidth(100);
		betAmt.setVisible(false);  // Use later and set true on deal

		Button dealBtn = new Button();
		dealBtn.setFont(Font.font("Tahoma", FontWeight.NORMAL, 18));
		dealBtn.setText("Deal Cards");
		dealBtn.setWrapText(true);

		// Button to shuffle deck 
		Button shuffleBtn = new Button("Shuffle Cards");
		shuffleBtn.setFont(Font.font("Tahoma", FontWeight.NORMAL, 18));
		shuffleBtn.setStyle("-fx-background-color: yellow");

		TextField filler = new TextField ();
		filler.setMaxWidth(200);
		filler.setVisible(false);

		hb1.getChildren().addAll(betLbl, betAmt, dealBtn, shuffleBtn, filler);

		Image image1 = new Image("card_back.png");
		ImageView imageView1 = new ImageView(image1);
		imageView1.setFitHeight(130);
		imageView1.setFitWidth(85);

		HBox hb2 = new HBox(2);
		hb2.setAlignment(Pos.CENTER);
		hb2.setPadding(new Insets(25));

		Image image2 = new Image("hearts1.png"); // Set after deal button pressed
		ImageView imageView2 = new ImageView(image1);
		imageView2.setFitHeight(130);
		imageView2.setFitWidth(85);
		hb2.getChildren().add(imageView2);

		Image image3 = new Image("clubs11.png"); // Set after deal button pressed
		ImageView imageView3 = new ImageView(image1);
		imageView3.setFitHeight(130);
		imageView3.setFitWidth(85);
		hb2.getChildren().add(imageView3);

		Button showBtn = new Button();
		showBtn.setFont(Font.font("Tahoma", FontWeight.NORMAL, 18));
		showBtn.setText("Show Card");

		// build array of Strings holding card image file names
		String[] suits = {"hearts", "diamonds", "spades", "clubs"};
		String[] cards = new String[52];
		int count = 0;

		for(int j = 0; j < 4; j++)
		{
			for(int i = 1; i < 14; i++)
			{
				cards[count] = suits[j] + i; // + ".png";
				count++;
			}
		}

		//imageView4.setImage(new Image("diamonds5.png")); // This works
		//		String card = "diamonds7";
		//		Image myCard = new Image(card + ".png"); // This works
		//		imageView4.setImage(myCard);

		//		FileInputStream inputstream = new FileInputStream("diamonds5.png"); 
		//		Image myCard = new Image(inputstream); 
		//		imageView4.setImage(myCard);

		//		// Pick an create a card to display
		//		int val = (int)(Math.random() * 51 + 1);
		//		Image card = new Image(cards[val]);  // Why doesn't this work?!
		//		imageView4.setImage(card);

		//		// Display image names to error check.
		//		for(int i = 0; i < 52; i++)
		//			System.out.println(cards[i]);

		//-------------------- EVENT HANDLERS  --------------------------//

		// Read bet amount and DO STUFF
		// Make the textField and label invisible again //
		betAmt.setOnAction(new EventHandler<ActionEvent>() 
		{
			@Override   
			public void handle(ActionEvent event) 
			{
				amount = betAmt.getText();
				betLbl.setText("Thanks for your $" + amount + " donation!");
			}
		});

		//use Lambda expressions for button event handling
		showBtn.setOnAction((ActionEvent event) -> 
		{
			// Pick an create a card to display
			int val = (int)(Math.random() * 51 + 1);
			Image card = new Image(cards[val] + ".png");
			imageView1.setImage(card);
			betAmt.setVisible(false);
			betLbl.setVisible(false);
		});

		dealBtn.setOnAction((ActionEvent event) -> 
		{
			// Pick and create a card to display
			int val1 = (int)(Math.random() * 51 + 1);
			int val2 = (int)(Math.random() * 51 + 1);
			Image card1 = new Image(cards[val1] + ".png");
			Image card2 = new Image(cards[val2] + ".png");
			imageView2.setImage(card1);
			imageView3.setImage(card2);
			imageView1.setImage(new Image("card_back.png"));
			betAmt.setVisible(true);
			betLbl.setVisible(true);
		});

		pane.getChildren().addAll(lbl, hb1, hb2, imageView1, showBtn);
		stage.setScene(scene);
		stage.show();
	}

}
