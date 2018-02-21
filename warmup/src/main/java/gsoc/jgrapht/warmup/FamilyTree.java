package gsoc.jgrapht.warmup;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jgrapht.alg.NaiveLcaFinder;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Class that represents a family tree.
 */

public class FamilyTree {

    /**
     * Graph where each vertex is a person and
     * each edge is a relation between two persons.
     */
    @NotNull
    private DefaultDirectedGraph<String, DefaultEdge> graph;

    /**
     * File which we want to read.
     */
    @NotNull
    private final String filename;

    /**
     * LcaFinder which we will use in order to find lca's of two vertices.
     */
    @Nullable
    private NaiveLcaFinder<String, DefaultEdge> lcaFinder;

    /**
     * Constructor
     * @param file input filename.
     */

    public FamilyTree(@NotNull String file) {
        filename = file;
        graph = new DefaultDirectedGraph<>(DefaultEdge.class);
    }

    /**
     * Read nodes from file.
     * @return list of nodes.
     */

    private @NotNull List<String> readNodes() throws IOException {
        return Files.lines(Paths.get(filename)).
                filter(s -> s.matches("((node\\s*\\[.*];)*\\s*[A-Z][a-z]+[_a-zA-z\\s]*;)+")).
                flatMap(Pattern.compile(";")::splitAsStream).
                map(s -> s.replaceAll("(node\\s*.*)*", "")).
                map(s -> s.replaceAll("\\s+","")).
                map(s -> s.replaceAll("_"," ")).
                filter(p -> !p.isEmpty()).
                collect(Collectors.toList());
    }

    /**
     * Read all edges from file.
     * @return list of edges in format "a->b".
     */

    private @NotNull List<String> readEdges() throws IOException {
        return Files.lines(Paths.get(filename)).
                filter(s -> s.matches("\\s*[A-Z][_A-Za-z]+\\s*->\\s*[A-Z][_A-Za-z]+[_[a-z]]\\s*[\\s?\"\\[\\],=A-Za-z0-9]*;")).
                map(s -> s.replaceAll("\\[.*]", "")).
                map(s -> s.replaceAll(";", "")).
                map(s -> s.replaceAll("\\s", "")).
                map(s -> s.replaceAll("_"," ")).
                collect(Collectors.toList());
    }

    /**
     * Builds a graph.
     * Note that we have to delete all arcs such that
     * there exist arc (i, j) and (j, i).
     */

    public void buildGraph() throws IOException {
        List<String> nodes = readNodes();
        List<String> edges = readEdges();

        graph = new DefaultDirectedGraph<>(DefaultEdge.class);

        for (String s : nodes) {
            graph.addVertex(s);
        }

        //It is  the only name that is set inside square brackets.

        graph.removeVertex("Aerys II");
        graph.addVertex("Aerys II the Mad");

        for (String s : edges) {
            String v1 = s.split("->")[0];
            String v2 = s.split("->")[1];

            if (v1.equals("Aerys II")) {
                v1 = "Aerys II the Mad";
            }

            if (v2.equals("Aerys II")) {
                v2 = "Aerys II the Mad";
            }

            // if there is already an arc (i, j),
            // we delete it and do not add (j, i)

            if (graph.containsEdge(v2, v1)) {
                graph.removeEdge(v2, v1);
                continue;
            }

            graph.addEdge(v1, v2);
        }
    }

    /**
     * Checks if a graph contains a vertex.
     * @param s name of vertex.
     * @return true if graph contains a vertex, otherwise false.
     */

    public boolean containsVertex(@NotNull String s) {
        return graph.containsVertex(s);
    }

    /**
     * Checks if a graph contains an edge.
     * @param v1 source vertex.
     * @param v2 target vertex.
     * @return true if graph contains an edge, otherwise false.
     */

    public boolean containsEdge(@NotNull String v1, @NotNull String v2) {
        return graph.containsEdge(v1, v2);
    }

    /**
     * Returns a graph.
     * @return graph that represent family tree.
     */

    @NotNull
    public DefaultDirectedGraph<String, DefaultEdge> getGraph() {
        return graph;
    }

    /**
     * Creates lcaFinder instance.
     */

    public void createLcaFinder() {
        lcaFinder = new NaiveLcaFinder<>(graph);
    }

    /**
     * Finds all lca's between two given vertices.
     * @param v1 first vertex.
     * @param v2 second vertex.
     * @return set of all lca's of v1 and v2.
     */

    public Set<String> findLcas(@NotNull String v1,@NotNull String v2) {
        return Objects.requireNonNull(lcaFinder).findLcas(v1, v2);
    }

}