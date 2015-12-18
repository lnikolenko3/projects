import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collections;
/**
 * Represents a Field that allows a player to buy cards for Keebles,
 *if they are available (Garden Bread is infinite)
 * @author Liubov Nikolenko
 * @version 1.0
 */
public class Field {
    private HashMap<Card, Integer> shop = new HashMap<>();
    /**
    *Constucts a Field object with fixed amount of cards available
    */
    public Field() {
        shop.put(new Gnome(), 10);
        shop.put(new ChaosEmerald(), 7);
        shop.put(new GardenBread(), 100);

    }
    /**
    *Buys a card from field
    *@param c the card you need to buy
    */
    public void buyCard(Card c) {
        if ((c instanceof Gnome) || (c instanceof ChaosEmerald)) {
            shop.put(c, shop.get(c) - 1);
        }
    }
    /**
    *Puts all of the available cards in an ArrayList
    *@return the ArrayList of available cards
    */
    public ArrayList<Card> cards() {
        ArrayList<Card> x = new ArrayList<>();
        for (Card c: shop.keySet()) {
            if (shop.get(c) != 0) {
                x.add(c);
            }
        }
        Collections.sort(x);
        return x;
    }


}
