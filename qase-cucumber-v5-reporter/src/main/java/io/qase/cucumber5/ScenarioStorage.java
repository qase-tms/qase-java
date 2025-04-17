package io.qase.cucumber5;

import gherkin.AstBuilder;
import gherkin.Parser;
import gherkin.ParserException;
import gherkin.TokenMatcher;
import gherkin.ast.*;
import io.cucumber.plugin.event.TestSourceRead;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class ScenarioStorage {
    private final Map<URI, TestSourceRead> pathToScenarioMap = new HashMap<>();
    private final Map<URI, Map<Integer, CucumberNode>> pathToNodeMap = new HashMap<>();

    public static ScenarioDefinition getScenarioDefinition(final CucumberNode cucumberNode) {
        return cucumberNode.node instanceof ScenarioDefinition ? (ScenarioDefinition) cucumberNode.node
                : (ScenarioDefinition) cucumberNode.parent.parent.node;
    }

    public void addScenarioEvent(final URI path, final TestSourceRead event) {
        pathToScenarioMap.put(path, event);
    }

    public CucumberNode getCucumberNode(final URI path, final int line) {
        if (!pathToNodeMap.containsKey(path)) {
            parseGherkinSource(path);
        }
        if (pathToNodeMap.containsKey(path)) {
            return pathToNodeMap.get(path).get(line);
        }
        return null;
    }

    private void parseGherkinSource(final URI path) {
        if (!pathToScenarioMap.containsKey(path)) {
            return;
        }
        final Parser<GherkinDocument> parser = new Parser<>(new AstBuilder());
        final TokenMatcher matcher = new TokenMatcher();
        try {
            final GherkinDocument gherkinDocument = parser.parse(pathToScenarioMap.get(path).getSource(),
                    matcher);
            final Map<Integer, CucumberNode> nodeMap = new HashMap<>();
            final CucumberNode currentParent = new CucumberNode(gherkinDocument.getFeature(), null);
            for (ScenarioDefinition child : gherkinDocument.getFeature().getChildren()) {
                processScenarioDefinition(nodeMap, child, currentParent);
            }
            pathToNodeMap.put(path, nodeMap);
        } catch (ParserException e) {
            throw new IllegalStateException("You are using a plugin that only supports till Gherkin 5.\n"
                    + "Please check if the Gherkin provided follows the standard of Gherkin 5\n", e
            );
        }
    }

    private void processScenarioDefinition(final Map<Integer, CucumberNode> nodeMap, final ScenarioDefinition child,
                                           final CucumberNode currentParent) {
        final CucumberNode childNode = new CucumberNode(child, currentParent);
        nodeMap.put(child.getLocation().getLine(), childNode);
        for (Step step : child.getSteps()) {
            nodeMap.put(step.getLocation().getLine(), createCucumberNode(step, childNode));
        }
        if (child instanceof ScenarioOutline) {
            processScenarioOutlineExamples(nodeMap, (ScenarioOutline) child, childNode);
        }
    }

    private void processScenarioOutlineExamples(final Map<Integer, CucumberNode> nodeMap,
                                                final ScenarioOutline scenarioOutline,
                                                final CucumberNode childNode) {
        for (Examples examples : scenarioOutline.getExamples()) {
            final CucumberNode examplesNode = createCucumberNode(examples, childNode);
            final TableRow headerRow = examples.getTableHeader();
            final CucumberNode headerNode = createCucumberNode(headerRow, examplesNode);
            nodeMap.put(headerRow.getLocation().getLine(), headerNode);
            for (int i = 0; i < examples.getTableBody().size(); ++i) {
                final TableRow examplesRow = examples.getTableBody().get(i);
                final Node rowNode = createExamplesRowWrapperNode(examplesRow, i);
                final CucumberNode expandedScenarioNode = createCucumberNode(rowNode, examplesNode);
                nodeMap.put(examplesRow.getLocation().getLine(), expandedScenarioNode);
            }
        }
    }

    private static ExamplesRowWrapperNode createExamplesRowWrapperNode(final Node examplesRow, final int bodyRowIndex) {
        return new ExamplesRowWrapperNode(examplesRow, bodyRowIndex);
    }

    private static CucumberNode createCucumberNode(final Node node, final CucumberNode astNode) {
        return new CucumberNode(node, astNode);
    }

    public static class ExamplesRowWrapperNode extends Node {
        private final int bodyRowIndex;

        public int getBodyRowIndex() {
            return bodyRowIndex;
        }

        ExamplesRowWrapperNode(final Node examplesRow, final int bodyRowIndex) {
            super(examplesRow.getLocation());
            this.bodyRowIndex = bodyRowIndex;
        }
    }

    public static class CucumberNode {
        private final Node node;
        private final CucumberNode parent;

        CucumberNode(final Node node, final CucumberNode parent) {
            this.node = node;
            this.parent = parent;
        }
    }
}
