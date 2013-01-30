package net.kkolyan.json2.parsing;

/**
 * @author nplekhanov
 */
public class ASTUtils {
    public static String printAST(Node node, String indent) {
        StringBuilder s = new StringBuilder();
        SimpleNode simpleNode = (SimpleNode) node;
        String name = simpleNode.getClass().getSimpleName().substring(3);
        if (simpleNode.children().size() > 0) {
            s.append(indent).append(name).append("\n");
            for (Node child : simpleNode.children()) {
                s.append(printAST(child, indent + "  "));
            }
        } else {
            s.append(indent).append(name).append(": ").append(simpleNode.getValue()).append("\n");
        }
        return s.toString();
    }
}
