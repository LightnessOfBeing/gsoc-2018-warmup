package gsoc.jgrapht.warmup;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        String filename = args[0];
        String v1 = args[1];
        String v2 = args[2];
        FamilyTree tree = new FamilyTree(filename);
        tree.buildGraph();
        tree.createLcaFinder();
        for (String s : tree.findLcas(v1, v2)) {
            System.out.println(s);
        }
    }
}
