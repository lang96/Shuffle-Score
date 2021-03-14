package ShuffleScore;

// Imported libraries
import java.util.*;



public class Card {

    // private members

    private final static String[] suits = {"C", "D", "H", "S"};
    private final static String[] ranks = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
    private final static String[] suitSymbols = {"\u2663", "\u2666", "\u2665", "\u2660"};

    private String suit, rank, suitSymbol;
    private int value;

    // public members

    // using set to hold the deck of cards at the start of game

    public static HashSet<Card> deckOfCards = new HashSet<Card>();

    // constructors

    public Card(String suit, String rank, String suitSymbol, int value) {

        this.suit = suit;
        this.rank = rank;
        this.suitSymbol = suitSymbol;
        this.value = value;

    }

    // methods

    // getters

    public String getCard() {

        String card = this.suitSymbol + this.rank;

        return card;

    }

    public String getSuit() { return this.suit; }
    public String getRank() { return this.rank; }
    public int getValue() { return this.value; }


    // setters

    // toString override

    public String toString() {

        String card = this.suitSymbol + " " + this.rank;

        return String.format("%-2s", card);

    }

    // sorting and distributing cards

    // initialize deck of cards - only at the start of the game

    public static void initializeDeck() {

        for (int i = 0; i < suits.length; i++) {

            for (int j = 0; j < ranks.length; j++) {

                String card = suits[i] + ranks[j];
                int value = 0;
                ArrayList<String> numbers = new ArrayList<String>(Arrays.asList("2", "3", "4", "5", "6", "7", "8", "9", "10"));
                ArrayList<String> royalty = new ArrayList<String>(Arrays.asList("J", "Q", "K"));

                if (numbers.contains(ranks[j])) {
                    value += Integer.parseInt(ranks[j]);
                } else if (royalty.contains(ranks[j])) {
                    value += 10;
                } else {
                    value += 1;
                }

                deckOfCards.add(new Card(suits[i], ranks[j], suitSymbols[i], value));

            }

        }

    }

    // shuffle deck - only available at the start of each phase

    public static LinkedList<Card> shuffleDeck(LinkedList<Card> shuffledDeck) {

        Collections.shuffle(shuffledDeck);

        return shuffledDeck;

    }

    // deal deck of cards to 3 players - at the start of first phase

    public static LinkedList<LinkedList<Card>> dealToThree(LinkedList<Card> shuffledDeck) {

        LinkedList<LinkedList<Card>> dealtDeck = new LinkedList<LinkedList<Card>>();

        // first player gets 18 cards

        LinkedList<Card> player1 = new LinkedList<Card>();

        for(int i = 0; i < 18; i++) {
            player1.add(shuffledDeck.get(i));
        }

        // second and third player gets 17 cards

        LinkedList<Card> player2 = new LinkedList<Card>();

        for(int i = 18; i < 35; i++) {
            player2.add(shuffledDeck.get(i));
        }

        LinkedList<Card> player3 = new LinkedList<Card>();

        for(int i = 35; i < shuffledDeck.size(); i++) {
            player3.add(shuffledDeck.get(i));
        }

        dealtDeck.add(player1);
        dealtDeck.add(player2);
        dealtDeck.add(player3);

        return dealtDeck;

    }

    // deal deck of cards to 2 players - at the start of second phase

    public static LinkedList<LinkedList<Card>> dealToTwo(LinkedList<Card> shuffledDeck) {

        LinkedList<LinkedList<Card>> dealtDeck = new LinkedList<LinkedList<Card>>();

        // both players get 26 cards

        LinkedList<Card> player1 = new LinkedList<Card>();
        LinkedList<Card> player2 = new LinkedList<Card>();

        for(int i = 0; i < 26; i++) {
            player1.add(shuffledDeck.get(i));
        }

        for(int i = 26; i < shuffledDeck.size(); i++) {
            player2.add(shuffledDeck.get(i));
        }

        dealtDeck.add(player1);
        dealtDeck.add(player2);

        return dealtDeck;

    }

    // display the cards to be played in each round and remove them from available cards

    public static LinkedList<Card> cardsAtHand(LinkedList<Card> playerCards) {

        LinkedList<Card> fiveCards = new LinkedList<Card>();

        for (int i = 0; i < 5; i++) {
            fiveCards.add(playerCards.get(i));
        }

        if (playerCards.size() >= 5) {

            for (int i = 0; i < fiveCards.size(); i++) {
                if (playerCards.contains(fiveCards.get(i))) {
                    playerCards.remove(playerCards.indexOf(fiveCards.get(i)));
                }
            }

        }

        return fiveCards;

    }

    // sort cards by suit then rank - for cards to be played in each round

    private static final ArrayList<String> suitOrder = new ArrayList<>(Arrays.asList("D", "C", "H", "S"));

    private static final ArrayList<String> rankOrder = new ArrayList<>(Arrays.asList("A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"));

    static class CardCompare implements Comparator<Card> {

        private List<Comparator<Card>> listOfComparators;

        @SafeVarargs
        public CardCompare(Comparator<Card>... comparators) {
            this.listOfComparators = Arrays.asList(comparators);
        }

        @Override
        public int compare(Card card1, Card card2) {
            for (Comparator<Card> comparator : listOfComparators) {
                int result = comparator.compare(card1, card2);
                if (result != 0) {
                    return result;
                }
            }
            return 0;
        }
    }

    static class SuitCompare implements Comparator<Card> {

        private ArrayList<String> suitOrder;
        public SuitCompare (ArrayList<String> suitOrder) { this.suitOrder = suitOrder; }

        public int compare(Card card1, Card card2)
        {
            String suit1 = card1.getSuit();
            String suit2 = card2.getSuit();
            return suitOrder.indexOf(suit1) - suitOrder.indexOf(suit2);
        }
    }

    static class RankCompare implements Comparator<Card> {
        private ArrayList<String> rankOrder;
        public RankCompare (ArrayList<String> rankOrder) {
            this.rankOrder = rankOrder;
        }

        public int compare(Card card1, Card card2)
        {
            String rank1 = card1.getRank();
            String rank2 = card2.getRank();
            return rankOrder.indexOf(rank1) - rankOrder.indexOf(rank2);
        }
    }

    public static LinkedList<Card> sortCards(LinkedList<Card> sortedCards) {

        CardCompare cardCompare = new CardCompare(new SuitCompare(suitOrder), new RankCompare(rankOrder));
        Collections.sort(sortedCards, cardCompare);

        return sortedCards;

    }

    // calculate points of cards grouped by suit and return highest - during each round

    public static int calculatePoints(LinkedList<Card> fiveCards) {

        int diamondScore = 0, clubScore = 0, heartScore = 0, spadeScore = 0;

        ArrayList<Integer> points = new ArrayList<Integer>();

        for (int i = 0; i < fiveCards.size(); i++) {

            if(fiveCards.get(i).getSuit().equals("D")) {
                diamondScore += fiveCards.get(i).getValue();
            } else if (fiveCards.get(i).getSuit().equals("C")) {
                clubScore += fiveCards.get(i).getValue();
            } else if (fiveCards.get(i).getSuit().equals("H")) {
                heartScore += fiveCards.get(i).getValue();
            } else {
                spadeScore += fiveCards.get(i).getValue();
            }

        }

        points.add(diamondScore);
        points.add(clubScore);
        points.add(heartScore);
        points.add(spadeScore);

        return Collections.max(points);

    }

}
