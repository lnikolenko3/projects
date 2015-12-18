import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
/**
*Creates a Zone, where cards can reside
*@author Liubov Nikolenko
*@version 1.0
*/
public class Zone implements Iterable<Card> {
    private ArrayList<Card> g;
    /**
    *Creates an empty zone
    */
    public Zone() {
        g = new ArrayList<Card>();
    }
    /**
    *Adds a card to the zone
    *@param c a card you want to add
    *@return boolean according to the ArrayList add method
    */
    public boolean add(Card c) {
        return g.add(c);
    }
    /**
    *Removes a card from the zone
    *@param c a card you want to remove
    *@return boolean according to the ArrayList remove method
    */
    public boolean remove(Card c) {
        return g.remove(c);
    }
    /**
    *Removes a card which has the specified index
    *@param i index of the card you want to remove
    *@return the card you have removed
    */
    public Card remove(int i) {
        return g.remove(i);
    }
    /**
    *Nicely displays zone to the user
    *@return String reprezentation of the zone
    */
    public String toString() {
        String s = "";
        Collections.sort(g);
        for (int i = 0; i < g.size(); i++) {
            s = s + i + ": " + g.get(i) + "\n";
        }
        return s;
    }
    /**
    *Gets the with the specified index
    *@param i an index of the card you want to get
    *@return a card with the given index
    */
    public Card get(int i) {
        return g.get(i);
    }
    /**
    *Shuffles the cards in the zone
    */
    public void shuffle() {
        Collections.shuffle(g);
    }
    /**
    *Returns the size of the zone
    *@return the size of the zone
    */
    public int size() {
        return g.size();
    }
    /**
    *Returns if the zone contains specified card
    *@param c card you want to check
    *@return if the zone contains specified card
    */
    public boolean contains(Card c) {
        return g.contains(c);
    }
    /**
    *Returns an iterator for the zone
    *@return an iterator for the zone
    */
    public Iterator<Card> iterator() {
        return g.iterator();
    }
    /**
    *Checks if the zone is empty
    *@return if the zone is empty
    */
    public boolean isEmpty() {
        return g.isEmpty();
    }
    /**
    *Moves cards to another Zone
    *@param res the zone you want to move your card to
    */
    public void moveCardsTo(Zone res) {
        int s = 0;
        if (res instanceof Tree) {
            s = Integer.min(g.size(), 10 - res.size());
        } else {
            s = g.size();
        }
        if (s > 0) {
            for (int i = 0; i < s; i++) {
                res.add(g.remove(0));
            }
        }

    }
    /**
    *Removes all cards from the zone
    *@return an ArrayList of the removed cards
    */
    public ArrayList<Card> discardAll() {
        ArrayList<Card> res = new ArrayList<>();
        int s = g.size();
        for (int i = 0; i < s; i++) {
            res.add(g.remove(0));
        }
        return res;
    }
    /**
    *Returns the number of Gnomes in the zone
    *@return the number of Gnomes in the zone
    */
    public int numGnomes() {
        int k = 0;
        for (Card x: g) {
            if (x.isScorable()) {
                k += 1;
            }

        }
        return k;
    }



}
