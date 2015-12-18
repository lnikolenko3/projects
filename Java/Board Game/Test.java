import java.util.ArrayList;

public class Test{
    public static void main(String[] args) {
        Zone trash1  = new Zone();
        trash1.add(new Keeble());
        Zone tree2 = new Tree();
        tree2.remove(new Keeble());
        tree2.add(new Gnome());
        System.out.println(tree2);
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
        System.out.println(trash1);
        System.out.println(tree2);



    }

}
