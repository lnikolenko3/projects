import java.util.Random;
import java.util.ArrayList;
/**
*Represents a Chaos Emerald in the game (there are 7 of them)
* @author Liubov Nikolenko
* @version 1.0
*/
public class ChaosEmerald extends Card {
    private Random o = new Random();
    /**
    *Creates a Chaos Emerald
    */
    public ChaosEmerald() {
        super("Chaos Emerald",
        "Adds all gnomes from other player's tree to your trash heap",
        "Chaos is power, enriched by the heart", false, 3);
    }
    /**
    *When you use a Chaos Emerald it picks a tree from a randomly
    *chosen player and transfers all the gnomes from the tree
    *to your trash heap. Once played Chaos Emerald dissappears
    *from the game entirely.
    *@param p gane object to affect
    */
    public void play(PlasterClash p) {
        super.play(p);
        int s = o.nextInt(3);
        Player pl1 = p.currentPlayer();
        ArrayList<Player> players = p.opponents();
        Player pl2 = players.get(s);
        Zone trash1 = pl1.getTrashHeap();
        Zone tree2 = pl2.getTree();
        //System.out.println(tree2.size());
        if (!(tree2.isEmpty())) {
            int gn = tree2.numGnomes();
            if (gn > 0) {
                for (int i = 0; i < gn; i++) {
                    trash1.add(new Gnome());
                }
                Zone temp = new Zone();
                for (Card c: tree2) {
                    if (!c.isScorable()) {
                        temp.add(c);
                    }
                }
                tree2.discardAll();
                for (Card c: temp) {
                    tree2.add(c);
                }
            }

        }

    }

}
