/**
*Represents a Tree
*@author Liubov Nikolenko
*@version 1.0
*/
public class Tree extends Zone {
    /**
    *Creates a Zone with 9 Keebles and 1 Gnome
    */
    public Tree() {
        for (int i = 0; i < 9; i++) {
            this.add(new Keeble());
        }
        this.add(new Gnome());
    }

}
