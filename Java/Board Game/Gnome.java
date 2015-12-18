import java.util.ArrayList;
/**
*Represents a Gnome
*@author Liubov Nikolenko
*@version 1.0
*/
public class Gnome extends Card {
    /**
    *Creates a Gnome (a card that brings you victory points)
    */
    public Gnome() {
        super("Gnome", "Randomly discards a card when played",
                "Not everyone can capture a Gnome",
                true, 5);
    }
    /**
    *@param p game object to affect
    *Playes a Gnome card. When played, Gnome makes all the opponents to
    *randomly discard a card from their hand. After playing Gnome dissappears
    *from the game entierly
    */
    public void play(PlasterClash p) {
        super.play(p);
        ArrayList<Player> pl = p.opponents();
        for (Player pr: pl) {
            pr.discardRandom();
        }
    }
}
