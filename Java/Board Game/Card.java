/**
*Represents Card in the game
* @author Liubov Nikolenko
* @version 1.0
*/
public abstract class Card implements Comparable<Card> {
    private String name;
    private String decription;
    private String flavor;
    private boolean scorable;
    private int cost;
    /**
    *Creates a Card
    *@param name name
    *@param decription decription
    *@param flavor flavor text
    *@param scorable if the card brings you victory points or not
    *@param cost cost
    */
    public Card(String name, String decription, String flavor,
        boolean scorable, int cost) {
        this.name = name;
        this.decription = decription;
        this.flavor = flavor;
        this.scorable = scorable;
        this.cost = cost;


    }
    /**
    *Returns the name of the card
    *@return name
    */
    public String getName() {
        return name;
    }
    /**
    *Returns the decription of the card
    *@return decription
    */
    public String getDescription() {
        return decription;
    }
    /**
    *Returns the flavor text of the card
    *@return flavor text
    */
    public String getFlavorText() {
        return flavor;
    }
    /**
    *Returns if the card is scorable
    *@return if the card is scorable
    */
    public boolean isScorable() {
        return scorable;
    }
    /**
    *Returns the cost of the card
    *@return cost
    */
    public int getCost() {
        return cost;
    }
    /**
    *Checks if two cards are equal (have the same name)
    *@param o another object
    *@return if two cards are equal
    */
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (!(o instanceof Card)) {
            return false;
        }
        Card other = (Card) o;
        return this.name.equals(other.name);
    }
    /**
    *Returns the hash code of the card
    *@return hash code
    */
    public int hashCode() {
        return name.hashCode();
    }
    /**
    *Compares two cards by their names
    *@param other another card
    *@return the value 0 if the argument string is equal to this string;
    *a value less than 0 if this string is lexicographically less
    *than the string argument; and a value greater
    *than 0 if this string is lexicographically greater than the string
    *argument.
    */
    public int compareTo(Card other) {
        return this.name.compareTo(other.name);
    }
    /**
    *Displays the main information about the card nicely to the user
    *@return String representation of a card
    */
    public String toString() {
        return name + " (" + cost + ")";
    }
    /**
    *Implements the default behaviour of plaing a card
    *@param p game object
    */
    public void play(PlasterClash p) {
        Player per = p.currentPlayer();
        Zone hand = per.getHand();
        hand.remove(this);
    }
    /**
    *Moves a card to the play zone of the given game
    *@param p game object
    */
    public void playToPlayZone(PlasterClash p) {
        Zone z = p.getPlayZone();
        z.add(this);
    }
}
