package ShuffleScore;

// Imported libraries




public class cards {

    /*

    class contents :

    deck of ShuffleScore.cards
    shuffling of ShuffleScore.cards
    distribution of ShuffleScore.cards
    sorting of ShuffleScore.cards

    Sorting order - By suit then rank

    Suit hierarchy - Clubs
                     Diamonds
                     Hearts
                     Spades

    Rank value - A = 1; Number ShuffleScore.cards = Respective value; 10, J, Q, K = 10

     */

    // private members

    // Card Symbols

    private static final String clubsHex = "2663", diamondsHex = "2666", heartsHex = "2665", spadesHex = "2660";

    private static final int clubsHexVal = Integer.parseInt(clubsHex, 16);
    private static final int diamondsHexVal = Integer.parseInt(diamondsHex, 16);
    private static final int heartsHexVal = Integer.parseInt(heartsHex, 16);
    private static final int spadesHexVal = Integer.parseInt(spadesHex, 16);

    private static final char clubs = (char)clubsHexVal;
    private static final char diamonds = (char)diamondsHexVal;
    private static final char hearts = (char)heartsHexVal;
    private static final char spades = (char)spadesHexVal;

    // public members


    // constructors


    // methods

    // setters


    // getters


    // test main method

    public static void main(String[] args) {

        System.out.println(clubs + " " + diamonds + " " + hearts + " " + spades);

    }

}
