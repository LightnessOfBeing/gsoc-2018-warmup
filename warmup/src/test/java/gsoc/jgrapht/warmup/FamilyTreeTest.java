package gsoc.jgrapht.warmup;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FamilyTreeTest {

    private FamilyTree tree;

    @Before
    public void init() throws IOException {
        tree = new FamilyTree("test.txt");
        tree.buildGraph();
        tree.createLcaFinder();
    }

    @Test
    public void containsAllVertexStark() {
        assertTrue(tree.containsVertex("Rickard"));
        assertTrue(tree.containsVertex("Eddard"));
        assertTrue(tree.containsVertex("Benjen"));
        assertTrue(tree.containsVertex("Lyanna"));
        assertTrue(tree.containsVertex("Brandon"));
        assertTrue(tree.containsVertex("Robb"));
        assertTrue(tree.containsVertex("Sansa"));
        assertTrue(tree.containsVertex("Arya"));
        assertTrue(tree.containsVertex("Rickon"));
        assertTrue(tree.containsVertex("Jon"));
    }

    @Test
    public void containsAllVertexTargaryen() {
        assertTrue(tree.containsVertex("Maekar I"));
        assertTrue(tree.containsVertex("Maester Aemon"));
        assertTrue(tree.containsVertex("Aegon V"));
        assertTrue(tree.containsVertex("Jaehaerys II"));
        assertTrue(tree.containsVertex("Rhaelle"));
        assertTrue(tree.containsVertex("Aerys II the Mad"));
        assertTrue(tree.containsVertex("Rhaegar"));
        assertTrue(tree.containsVertex("Viserys"));
        assertTrue(tree.containsVertex("Daenerys"));
        assertTrue(tree.containsVertex("Aegon"));
    }

    @Test
    public void containsAllVertexBaratheon() {
        assertTrue(tree.containsVertex("Ormund"));
        assertTrue(tree.containsVertex("Steffon"));
        assertTrue(tree.containsVertex("Stannis"));
        assertTrue(tree.containsVertex("Robert"));
        assertTrue(tree.containsVertex("Renly"));
        assertTrue(tree.containsVertex("Shireen"));
        assertTrue(tree.containsVertex("Joffrey"));
        assertTrue(tree.containsVertex("Myrcellar"));
        assertTrue(tree.containsVertex("Tommen"));
    }

    @Test
    public void containsAllVertexLannister() {
        assertTrue(tree.containsVertex("Tywin"));
        assertTrue(tree.containsVertex("Joanna"));
        assertTrue(tree.containsVertex("Jaime"));
        assertTrue(tree.containsVertex("Tyrion"));
        assertTrue(tree.containsVertex("Cersei"));
    }

    @Test
    public void checkVertexSize() {
        assertEquals(34, tree.getGraph().vertexSet().size());
    }

    @Test
    public void containsAllEdgesStark() {
        assertTrue(tree.containsEdge("Rickard", "Brandon"));
        assertTrue(tree.containsEdge("Rickard", "Eddard"));
        assertTrue(tree.containsEdge("Rickard", "Benjen"));
        assertTrue(tree.containsEdge("Rickard", "Lyanna"));
        assertTrue(tree.containsEdge("Eddard", "Brandon"));
        assertTrue(tree.containsEdge("Eddard", "Robb"));
        assertTrue(tree.containsEdge("Eddard", "Sansa"));
        assertTrue(tree.containsEdge("Eddard", "Arya"));
        assertTrue(tree.containsEdge("Eddard", "Rickon"));
        assertTrue(tree.containsEdge("Eddard", "Jon"));
        assertTrue(tree.containsEdge("Lyanna", "Jon"));
    }

    @Test
    public void containsAllEdgesTargaryen() {
        assertTrue(tree.containsEdge("Maekar I", "Maester Aemon"));
        assertTrue(tree.containsEdge("Maekar I", "Aegon V"));
        assertTrue(tree.containsEdge("Aegon V", "Jaehaerys II"));
        assertTrue(tree.containsEdge("Aegon V", "Rhaelle"));
        assertTrue(tree.containsEdge("Jaehaerys II", "Aerys II the Mad"));
        assertTrue(tree.containsEdge("Aerys II the Mad", "Rhaegar"));
        assertTrue(tree.containsEdge("Aerys II the Mad", "Viserys"));
        assertTrue(tree.containsEdge("Aerys II the Mad", "Daenerys"));
        assertTrue(tree.containsEdge("Rhaegar", "Aegon"));
    }

    @Test
    public void containsAllEdgesBaratheon() {
        assertTrue(tree.containsEdge("Ormund", "Steffon"));
        assertTrue(tree.containsEdge("Steffon", "Stannis"));
        assertTrue(tree.containsEdge("Steffon", "Robert"));
        assertTrue(tree.containsEdge("Steffon", "Renly"));
        assertTrue(tree.containsEdge("Stannis", "Shireen"));
        assertTrue(tree.containsEdge("Robert", "Joffrey"));
        assertTrue(tree.containsEdge("Robert", "Myrcellar"));
        assertTrue(tree.containsEdge("Robert", "Tommen"));
    }

    @Test
    public void containsAllEdgesLannister() {
        assertTrue(tree.containsEdge("Tywin", "Jaime"));
        assertTrue(tree.containsEdge("Tywin", "Tyrion"));
        assertTrue(tree.containsEdge("Tywin", "Cersei"));
        assertTrue(tree.containsEdge("Joanna", "Jaime"));
        assertTrue(tree.containsEdge("Joanna", "Tyrion"));
        assertTrue(tree.containsEdge("Joanna", "Cersei"));
    }

    @Test
    public void containsAllCrossEdges() {
        assertTrue(tree.containsEdge("Rhaegar", "Jon"));
        assertTrue(tree.containsEdge("Rhaelle", "Steffon"));
        assertTrue(tree.containsEdge("Jaime", "Joffrey"));
        assertTrue(tree.containsEdge("Jaime", "Myrcellar"));
        assertTrue(tree.containsEdge("Jaime", "Tommen"));
        assertTrue(tree.containsEdge("Cersei", "Joffrey"));
        assertTrue(tree.containsEdge("Cersei", "Myrcellar"));
        assertTrue(tree.containsEdge("Cersei", "Tommen"));
    }

    @Test
    public void checkEdgeSetSize() {
        int stark = 11;
        int targaryen = 9;
        int baratheon = 8;
        int lannister = 6;
        int crossed = 8;
        assertEquals(stark + targaryen
                        + baratheon + lannister + crossed,
                tree.getGraph().edgeSet().size());
    }

    @Test
    public void findLcaStark1() {
        Set<String> set = tree.findLcas("Sansa", "Arya");
        assertEquals(1, set.size());
        assertEquals("Eddard", set.iterator().next());
    }

    @Test
    public void findLcaStark2() {
        Set<String> set = tree.findLcas("Brandon", "Eddard");
        assertEquals(1, set.size());
        assertEquals("Eddard", set.iterator().next());
    }

    @Test
    public void findLcaStark3() {
        Set<String> lcaRobbLyanna = tree.findLcas("Robb", "Lyanna");
        assertEquals(1, lcaRobbLyanna.size());
        assertEquals("Rickard", lcaRobbLyanna.iterator().next());
    }

    @Test
    public void findLcaStark4() {
        Set<String> set = tree.findLcas("Brandon", "Jon");
        assertEquals(1, set.size());
        assertEquals("Eddard", set.iterator().next());
    }

    @Test
    public void findLcaCrossed1() {
        Set<String> set = tree.findLcas("Jon", "Aegon");
        assertEquals(1, set.size());
        assertEquals("Rhaegar", set.iterator().next());
    }

    @Test
    public void findLcaTargaryen1() {
        Set<String> set = tree.findLcas("Rhaelle", "Aegon");
        assertEquals(1, set.size());
        assertEquals("Aegon V", set.iterator().next());
    }

    @Test
    public void findLcaTargaryen2() {
        Set<String> set = tree.findLcas("Jaehaerys II", "Aegon");
        assertEquals(1, set.size());
        assertEquals("Jaehaerys II", set.iterator().next());
    }

    @Test
    public void findLcaStarkBaratheon1() {
        Set<String> set = tree.findLcas("Jon", "Shireen");
        assertEquals(1, set.size());
        assertEquals("Aegon V", set.iterator().next());
    }

    @Test
    public void findLcaStarkBaratheon2() {
        Set<String> set = tree.findLcas("Lyanna", "Shireen");
        assertEquals(0, set.size());
    }

    @Test
    public void findLcaStarkBaratheon3() {
        Set<String> set = tree.findLcas("Rickard", "Shireen");
        assertEquals(0, set.size());
    }

    @Test
    public void findLcaStarkBaratheon4() {
        Set<String> set = tree.findLcas("Brandon", "Shireen");
        assertEquals(0, set.size());
    }

    @Test
    public void findLcaStarkBaratheon5() {
        Set<String> set = tree.findLcas("Jon", "Tommen");
        assertEquals(1, set.size());
        assertEquals("Aegon V", set.iterator().next());
    }

    @Test
    public void findLcaMultiple1() {
        Set<String> set = tree.findLcas("Joffrey", "Tommen");
        assertEquals(3, set.size());
        assertTrue(set.contains("Jaime"));
        assertTrue(set.contains("Cersei"));
        assertTrue(set.contains("Robert"));
    }

    @Test
    public void findLcaMultiple2() {
        Set<String> set = tree.findLcas("Tommen", "Myrcellar");
        assertEquals(3, set.size());
        assertTrue(set.contains("Jaime"));
        assertTrue(set.contains("Cersei"));
        assertTrue(set.contains("Robert"));
    }

}