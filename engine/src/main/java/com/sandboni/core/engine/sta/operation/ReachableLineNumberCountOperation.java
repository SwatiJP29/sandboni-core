package com.sandboni.core.engine.sta.operation;

import com.sandboni.core.engine.sta.graph.Edge;
import com.sandboni.core.engine.sta.graph.vertex.TestVertex;
import com.sandboni.core.engine.sta.graph.vertex.Vertex;
import org.jgrapht.Graph;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class ReachableLineNumberCountOperation extends AbstractGraphOperation<LongResult> {

    private final Set<Edge> allReachableEdges;
    private final Set<TestVertex> allTests;

    ReachableLineNumberCountOperation(Set<Edge> allReachableEdges, Set<TestVertex> allTests) {
        Objects.requireNonNull(allReachableEdges, INVALID_ARGUMENT);
        Objects.requireNonNull(allTests, INVALID_ARGUMENT);
        this.allReachableEdges = new HashSet<>(allReachableEdges);
        this.allTests = new HashSet<>(allTests);
    }

    @Override
    public LongResult execute(Graph<Vertex, Edge> graph) {
        return new LongResult((long) allReachableEdges.stream()
                .flatMap(e -> Arrays.stream(new Vertex[]{e.getSource(), e.getTarget()}))
                .filter(v -> !allTests.contains(v) && !v.isLineNumbersEmpty())
                .mapToInt(v -> v.getLineNumbers().size())
                .sum());
    }
}
