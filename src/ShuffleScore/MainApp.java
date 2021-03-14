package ShuffleScore;

// Imported libraries

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.*;

/*

    PROGRAM REQUIREMENTS - DELETE ONCE COMPLETE

    i.   Never clear screen. [/]
    ii.  Complete source code - Make sure the code can be compiled and run. []
    iii. Implement Comparable or Comparator interface for sorting. [/]
    iv.  Use different data structures : Stack / Queue & Set / Map [Queue (/) & Set (/)]
    v.   JavaFX GUI for the whole program. [X - Only Partial]
    vi.  A file named Group10.txt - The file shall list down the group members’ ID, name, and contribution. [~]

*/


public class MainApp extends Application {

    // GUI variables and methods - for player creation

    private Button button;
    private static TextField playerName1;
    private static TextField playerName2;
    private static TextField playerName3;

    @Override
    public void start(Stage primaryStage) throws Exception {

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(10);
        grid.setHgap(12);
        primaryStage.setTitle("Shuffle Score!");
        primaryStage.setScene(new Scene(grid, 420, 300));
        primaryStage.show();

        HBox buttonBox = new HBox();
        buttonBox.setSpacing(10.0);

        Label lab1 = new Label("PLAYER NAME");
        lab1.setMaxWidth(Double.MAX_VALUE);
        lab1.setFont(new Font("Arial", 24));
        //lab1.setAlignment(Pos.CENTER);
        grid.add(lab1, 2, 3);

        playerName1 = new TextField();
        playerName1.setPromptText("Enter Player 1 Name");
        playerName1.setMaxWidth(Double.MAX_VALUE);
        //play1.setAlignment(Pos.CENTER);
        grid.add(playerName1, 2, 5);

        playerName2 = new TextField();
        playerName2.setPromptText("Enter Player 2 Name");
        playerName2.setMaxWidth(Double.MAX_VALUE);
        //play2.setAlignment(Pos.CENTER);
        grid.add(playerName2, 2, 7);

        playerName3 = new TextField();
        playerName3.setPromptText("Enter Player 3 Name");
        playerName3.setMaxWidth(Double.MAX_VALUE);
        //play3.setAlignment(Pos.CENTER);
        grid.add(playerName3, 2, 9);

        button = new Button("Proceed");
        button.setOnAction(new EventHandler<ActionEvent>()
        {

            @Override
            public void handle(ActionEvent event) {

                Stage stage = (Stage) button.getScene().getWindow();
                stage.close();

            }

        } );

        grid.add(button, 2, 11);

    }

    // private members

    private static LinkedList<Card> deckOfCards;
    private static LinkedList<Player> players;

    // public members

    // methods

    // get player names

    /*
    public static String[] getPlayerNames() {

        String[] names = new String[3];
        Scanner input = new Scanner(System.in);

        for (int i = 1; i < 4; i++) {

            System.out.println("Enter player " + i + "'s name : ");
            names[i-1] = input.nextLine();

        }

        return names;

    }
    */

    // pre-round initialization

    public static void setUpPhaseOne(String[] playerNames, LinkedList<Card> deckOfCards) {

        LinkedList<Card> shuffledDeck = Card.shuffleDeck(deckOfCards);
        LinkedList<LinkedList<Card>> allPlayersCards = Card.dealToThree(shuffledDeck);

        Player.initializePlayers(playerNames, allPlayersCards);

        players = new LinkedList<Player>(Player.players);

        System.out.println("Available Cards:\n");

        for(Player player : players) {

            String output = player.toString();
            System.out.print(output + "\n");

        }

    }

    public static void setUpPhaseTwo(ArrayList<String> playerNames, ArrayList<Integer> playerScores, LinkedList<Card> deckOfCards) {

        LinkedList<Card> shuffledDeck = Card.shuffleDeck(deckOfCards);
        LinkedList<LinkedList<Card>> allPlayersCards = Card.dealToTwo(shuffledDeck);

        Player.initializePlayers(playerNames, playerScores, allPlayersCards);

        players = new LinkedList<Player>(Player.players);

        System.out.println("Available Cards:\n");

        for(Player player : players) {

            String output = player.toString();
            System.out.print(output + "\n");

        }

    }


    // main method - game flow

    public static void main(String[] args) {

        System.out.println("\n****************************");
        System.out.println("* WELCOME TO SHUFFLE SCORE! *");
        System.out.println("****************************\n");

        Scanner input = new Scanner(System.in);
        String choice;

        launch(args);

        String[] playerNames = new String[3];
        playerNames[0] = playerName1.getText();
        playerNames[1] = playerName2.getText();
        playerNames[2] = playerName3.getText();

        Card.initializeDeck();
        deckOfCards = new LinkedList<Card>(Card.deckOfCards);
        LinkedList<Player> qualifiedPlayers = new LinkedList<Player>();

        // Phase 1

        System.out.println("\n******************");
        System.out.println("* 3-Player Phase *");
        System.out.println("******************\n");

        setUpPhaseOne(playerNames, deckOfCards);

        System.out.println("\nPress S to Shuffle or ENTER to start.\n");
        choice = input.nextLine();

        do {

            if (!choice.equalsIgnoreCase("s")) {
                if (choice.isBlank()) {
                    break;
                } else {
                    System.out.println("\nInvalid input! Please enter a valid option.\n");
                    System.out.println("Press S to Shuffle or ENTER to start.\n");
                    choice = input.nextLine();
                }
            } else {
                Player.clearPlayers();
                System.out.print("\n");
                setUpPhaseOne(playerNames, deckOfCards);
                System.out.println("\nPress S to Shuffle or ENTER to start.\n");
                choice = input.nextLine();
            }

        } while (!choice.equalsIgnoreCase("s") || !choice.isBlank());

        // Rounds 1 - 3

        for (int round = 1; round < 4; round++) {

            Scanner proceed = new Scanner(System.in);

            System.out.println(String.format("*** ROUND %d ***\n", round));
            System.out.println("Cards at Hand : \n");

            // Display cards at hand

            LinkedList<LinkedList<Card>> fiveCardsArr = new LinkedList<LinkedList<Card>>();
            LinkedList<Integer> points = new LinkedList<Integer>();
            int winnerPoints;
            ArrayList<Integer> winnerIndex = new ArrayList<Integer>();

            for (int player = 0; player < 3; player++) {

                fiveCardsArr.add(Card.cardsAtHand(players.get(player).getCards()));
                points.add(Card.calculatePoints(fiveCardsArr.get(player)));

            }

            winnerPoints = Collections.max(points);

            for (int i = 0; i < 3; i++) {

                if (winnerPoints == points.get(i)) {
                    winnerIndex.add(i);
                }

            }

            // Display each player's points

            // Display winning player

            for(int player = 0; player < 3; player++) {

                if (winnerIndex.contains(player)) {
                    players.get(player).updateScore(winnerPoints);
                    String output = players.get(player).toString(Card.sortCards(fiveCardsArr.get(player)), points.get(player), 1);
                    System.out.print(output + "\n");
                } else {
                    String output = players.get(player).toString(Card.sortCards(fiveCardsArr.get(player)), points.get(player), 0);
                    System.out.print(output + "\n");
                }

            }

            // Display scores after round

            System.out.println("\nScore : \n");
            for (Player player : players) {

                System.out.println(String.format("%-5s =  %-3d ", player.getName(), player.getScore()));

            }

            // Display available cards for next round

            System.out.println("\nAvailable Cards : \n");
            for (Player player : players) {

                String output = player.toString();
                System.out.print(output + "\n");

            }

            System.out.print("\n");

            if (round < 3) {
                System.out.println("\nPress ENTER to proceed to next round.\n");
                choice = proceed.nextLine();
            } else {

                // Display top two players proceeding after conditionals

                players = Player.sortPlayers(players);

                // all tied
                if (players.get(0).getScore() == players.get(1).getScore() && players.get(1).getScore() == players.get(2).getScore()) {

                    qualifiedPlayers = Player.decideWinner(players, 3);

                } // one winner and tied runner up
                else if (players.get(0).getScore() > players.get(1).getScore() && players.get(1).getScore() == players.get(2).getScore()) {

                    qualifiedPlayers = Player.decideWinner(players, 2);

                } // one winner and one runner up OR tied winner
                else {
                    qualifiedPlayers.add(players.get(0));
                    qualifiedPlayers.add(players.get(1));
                }

                System.out.println(String.format("\n***** %s and %s proceed to 2-Player Phase *****\n", qualifiedPlayers.get(0).getName(), qualifiedPlayers.get(1).getName()));

            }

        }

        // Phase 2

        ArrayList<String> playerNames2 = new ArrayList<String>(Arrays.asList(qualifiedPlayers.get(0).getName(), qualifiedPlayers.get(1).getName()));
        ArrayList<Integer> playerScores = new ArrayList<Integer>(Arrays.asList(qualifiedPlayers.get(0).getScore(), qualifiedPlayers.get(1).getScore()));

        System.out.println("\n******************");
        System.out.println("* 2-Player Phase *");
        System.out.println("******************\n");

        Player.clearPlayers();
        players.clear();
        setUpPhaseTwo(playerNames2, playerScores, deckOfCards);

        System.out.println("\nPress S to Shuffle or ENTER to start.\n");
        Scanner input2 = new Scanner(System.in);
        String choice2;
        choice2 = input2.nextLine();

        do {

            if (!choice2.equalsIgnoreCase("s")) {
                if (choice2.isBlank()) {
                    break;
                } else {
                    System.out.println("\nInvalid input! Please enter a valid option.\n");
                    System.out.println("Press S to Shuffle or ENTER to start.\n");
                    choice2 = input2.nextLine();
                }
            } else {
                Player.clearPlayers();
                System.out.print("\n");
                setUpPhaseTwo(playerNames2, playerScores, deckOfCards);
                System.out.println("\nPress S to Shuffle or ENTER to start.\n");
                choice2 = input2.nextLine();
            }

        } while (!choice2.equalsIgnoreCase("s") || !choice2.isBlank());

        // Rounds 1 - 4

        for (int round = 1; round < 5; round++) {

            Scanner proceed = new Scanner(System.in);

            System.out.println(String.format("*** ROUND %d ***\n", round));
            System.out.println("Cards at Hand : \n");

            // Display cards at hand

            LinkedList<LinkedList<Card>> fiveCardsArr = new LinkedList<LinkedList<Card>>();
            LinkedList<Integer> points = new LinkedList<Integer>();
            int winnerPoints;
            ArrayList<Integer> winnerIndex = new ArrayList<Integer>();

            for (int player = 0; player < 2; player++) {

                fiveCardsArr.add(Card.cardsAtHand(players.get(player).getCards()));
                points.add(Card.calculatePoints(fiveCardsArr.get(player)));

            }

            winnerPoints = Collections.max(points);

            for (int i = 0; i < 2; i++) {

                if (winnerPoints == points.get(i)) {
                    winnerIndex.add(i);
                }

            }

            // Display each player's points

            // Display winning player

            for(int player = 0; player < 2; player++) {

                if (winnerIndex.contains(player)) {
                    players.get(player).updateScore(winnerPoints);
                    String output = players.get(player).toString(Card.sortCards(fiveCardsArr.get(player)), points.get(player), 1);
                    System.out.print(output + "\n");
                } else {
                    String output = players.get(player).toString(Card.sortCards(fiveCardsArr.get(player)), points.get(player), 0);
                    System.out.print(output + "\n");
                }

            }

            // Display scores after round

            System.out.println("\nScore : \n");
            for (Player player : players) {

                System.out.println(String.format("%-5s =  %-3d ", player.getName(), player.getScore()));

            }

            // Display available cards for next round

            System.out.println("\nAvailable Cards : \n");
            for (Player player : players) {

                String output = player.toString();
                System.out.print(output + "\n");

            }

            System.out.print("\n");

            if (round < 4) {
                System.out.println("\nPress ENTER to proceed to next round.\n");
                choice2 = proceed.nextLine();
            } else {

                // Display winner, if tied then display tie

                players = Player.sortPlayers(players);

                Player winner;

                Scanner endScan = new Scanner(System.in);
                String endChoice = "";

                if (players.get(0).getScore() == players.get(1).getScore()) {

                    do {

                        System.out.println("\nDo you want to end in a tie or do you want to toss a coin to determine the winner?\n");
                        System.out.println("Press T to end in a tie or C for a coin toss.\n");

                        endChoice = endScan.nextLine();

                        if (endChoice.equalsIgnoreCase("t")) {

                            System.out.println("It's a tie! Well played.");
                            break;

                        } else if (endChoice.equalsIgnoreCase("c")) {

                            winner = Player.endCoinToss(players);
                            System.out.println(String.format("\n***** %s is the WINNER! *****\n", winner.getName()));
                            break;

                        } else {

                            System.out.println("\n Invalid input! Please enter a valid option.");
                            continue;

                        }

                    } while (!endChoice.equalsIgnoreCase("t") || !endChoice.equalsIgnoreCase("c"));

                } else {

                    System.out.println(String.format("\n***** %s is the WINNER! *****\n", players.get(0).getName()));

                }

            }

        }

    }

}
