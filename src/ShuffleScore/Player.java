package ShuffleScore;

// Imported libraries
import java.util.*;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;


public class Player{

    // private members

    private String name;
    private int score;
    private LinkedList<Card> cards;

    // public members

    // using queue to hold players created at the start of the game

    public static Queue<Player> players = new LinkedList<Player>();

    // constructors

    public Player(String playerName, LinkedList<Card> cards) {

        this.name = playerName;
        this.score = 0;
        this.cards = cards;

    }

    public Player(String playerName, int playerScore, LinkedList<Card> cards) {

        this.name = playerName;
        this.score = playerScore;
        this.cards = cards;

    }

    // methods

    // getters

    public String getName() { return this.name; }

    public int getScore() { return this.score; }

    public LinkedList<Card> getCards() { return this.cards; }

    // setters

    public void setCards(LinkedList<Card> cards) {

        this.cards = cards;

    }

    // toString override

    // for available cards

    public String toString() {

        String name = this.name;

        String displayCards = "";

        for (int i = 0; i < this.cards.size(); i++) {

            if ((i+1) % 5 == 0) {
                displayCards = displayCards + " " + this.cards.get(i).toString() + ",";
            } else {
                displayCards = displayCards + " " + this.cards.get(i).toString();
            }

        }

        return String.format("%-5s : %s", name, displayCards);

    }

    // for each round - add points after calculation and winner

    public String toString(LinkedList<Card> sortedFiveCards, int points, int winnerIndex) {

        String name = this.name;

        String displayCards = "";

        for (int i = 0; i < sortedFiveCards.size(); i++) {
            displayCards = displayCards + " " + sortedFiveCards.get(i).toString();
        }

        if (winnerIndex == 0)
            return String.format("%-5s : %-25s |  Point = %d  ", name, displayCards, points);
        else
            return String.format("%-5s : %-25s |  Point = %d  |  Win", name, displayCards, points);

    }

    // data manipulation

    // initialize players

    // phase 1

    public static void initializePlayers(String[] names, LinkedList<LinkedList<Card>> cards) {

        for (int i = 0; i < 3; i++) {

            players.add(new Player(names[i], cards.get(i)));

        }

    }

    // phase 2

    public static void initializePlayers(ArrayList<String> names, ArrayList<Integer> scores, LinkedList<LinkedList<Card>> cards) {

        for (int i = 0; i < 2; i++) {

            players.add(new Player(names.get(i), scores.get(i), cards.get(i)));

        }

    }

    // clear players - only for reshuffling

    public static void clearPlayers() {

        players.clear();

    }

    // update score of winning player in each round

    public void updateScore(int score) {

        this.score += score;

    }

    // decide winner for phase 1 if points are tied

    public static LinkedList<Player> decideWinner(LinkedList<Player> players, int numOfPlayers) {

        LinkedList<Player> qualifiedPlayers = new LinkedList<Player>();

        if (numOfPlayers == 2) {
            qualifiedPlayers.add(players.get(0));
            qualifiedPlayers.add(coinToss(players));
        } else {
            qualifiedPlayers = drawLots(players);
        }

        return qualifiedPlayers;

    }

    public static Player coinToss(LinkedList<Player> players) {

        Player player1 = players.get(1);
        Player player2 = players.get(2);

        System.out.println("\nCoin toss for second spot in 2-Player Phase\n");
        System.out.print("\n");
        System.out.println(String.format("%s (Heads) vs %s (Tails)", player1.getName(), player2.getName()));
        System.out.print("\n");
        System.out.println("\nPress ENTER to proceed.\n");

        Scanner input = new Scanner(System.in);
        String choice = input.nextLine();

        Random randInt = new Random();
        int result = randInt.nextInt(2);

        if (result == 0) {
            System.out.println("\nHeads\n");
            System.out.println(player1.getName() + " wins and proceeds to 2-Player Phase!\n");
            return player1;
        } else {
            System.out.println("\nTails\n");
            System.out.println(player2.getName() + " wins and proceeds to 2-Player Phase!\n");
            return player2;
        }

    }

    public static LinkedList<Player> drawLots(LinkedList<Player> players) {

        LinkedList<Player> qualifiedPlayers = new LinkedList<Player>();

        Player player1 = players.get(1);
        Player player2 = players.get(2);
        Player player3 = players.get(3);

        System.out.println("\nDraw lots for both spots in 2-Player Phase\n");
        System.out.print("\n");
        System.out.println(String.format("%s (1) vs %s (2) vs %s (3)", player1.getName(), player2.getName(), player3.getName()));
        System.out.print("\n");
        System.out.println("\nPress ENTER to proceed.\n");

        Scanner input = new Scanner(System.in);
        String choice = input.nextLine();

        Random randInt = new Random();
        int result = randInt.nextInt(3);
        int result2 = randInt.nextInt(3);

        while (result2 == result) {
            result2 = randInt.nextInt(3);
        }

        if (result == 0) {

            System.out.println("\n1\n");
            System.out.println(player1.getName() + " is selected and proceeds to 2-Player Phase!\n");
            qualifiedPlayers.add(players.get(0));

            if (result2 == 1) {

                System.out.println("\n2\n");
                System.out.println(player2.getName() + " is selected and proceeds to 2-Player Phase!\n");
                qualifiedPlayers.add(players.get(1));

            } else {

                System.out.println("\n3\n");
                System.out.println(player3.getName() + " is selected and proceeds to 2-Player Phase!\n");
                qualifiedPlayers.add(players.get(2));

            }

        } else if (result == 1) {

            System.out.println("\n2\n");
            System.out.println(player2.getName() + " is selected and proceeds to 2-Player Phase!\n");
            qualifiedPlayers.add(players.get(1));

            if (result2 == 0) {

                System.out.println("\n1\n");
                System.out.println(player1.getName() + " is selected and proceeds to 2-Player Phase!\n");
                qualifiedPlayers.add(players.get(0));

            } else {

                System.out.println("\n3\n");
                System.out.println(player3.getName() + " is selected and proceeds to 2-Player Phase!\n");
                qualifiedPlayers.add(players.get(2));

            }

        } else {

            System.out.println("\n3\n");
            System.out.println(player3.getName() + " is selected and proceeds to 2-Player Phase!\n");
            qualifiedPlayers.add(players.get(2));

            if (result2 == 0) {

                System.out.println("\n1\n");
                System.out.println(player1.getName() + " is selected and proceeds to 2-Player Phase!\n");
                qualifiedPlayers.add(players.get(0));

            } else {

                System.out.println("\n2\n");
                System.out.println(player2.getName() + " is selected and proceeds to 2-Player Phase!\n");
                qualifiedPlayers.add(players.get(1));

            }

        }

        return qualifiedPlayers;

    }

    // decide winner of the game if points are tied

    public static Player endCoinToss(LinkedList<Player> players) {

        Player player1 = players.get(0);
        Player player2 = players.get(1);

        System.out.println("\nCoin toss for winner\n");
        System.out.print("\n");
        System.out.println(String.format("%s (Heads) vs %s (Tails)", player1.getName(), player2.getName()));
        System.out.print("\n");
        System.out.println("\nPress ENTER to proceed.\n");

        Scanner input = new Scanner(System.in);
        String choice = input.nextLine();

        Random randInt = new Random();
        int result = randInt.nextInt(2);

        if (result == 0) {
            System.out.println("\nHeads\n");
            System.out.println(player1.getName() + " wins the game!\n");
            return player1;
        } else {
            System.out.println("\nTails\n");
            System.out.println(player2.getName() + " wins the game!\n");
            return player2;
        }

    }

    // sort by score then name - to determine winner primitively

    static class PlayerCompare implements Comparator<Player> {

        private List<Comparator<Player>> listOfComparators;

        @SafeVarargs
        public PlayerCompare(Comparator<Player>... comparators) {
            this.listOfComparators = Arrays.asList(comparators);
        }

        @Override
        public int compare(Player player1, Player player2) {
            for (Comparator<Player> comparator : listOfComparators) {
                int result = comparator.compare(player1, player2);
                if (result != 0) {
                    return result;
                }
            }
            return 0;
        }
    }

    static class ScoreCompare implements Comparator<Player> {

        public int compare(Player player1, Player player2)
        {
            if (player1.getScore() > player2.getScore())
                return -1;
            if (player1.getScore() < player2.getScore())
                return 1;
            else
                return 0;
        }

    }

    static class NameCompare implements Comparator<Player> {

        public int compare(Player player1, Player player2)
        {
            return player1.getName().compareTo(player2.getName());
        }

    }

    public static LinkedList<Player> sortPlayers(LinkedList<Player> sortedPlayers) {

        PlayerCompare playerCompare = new PlayerCompare(new ScoreCompare(), new NameCompare());
        Collections.sort(sortedPlayers, playerCompare);

        return sortedPlayers;

    }

}