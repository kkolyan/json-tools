package net.kkolyan.json2;

import net.kkolyan.json2.evaluation.EvaluatingVisitor;
import net.kkolyan.json2.evaluation.EvaluationContext;
import net.kkolyan.json2.evaluation.SimpleEvaluationContext;
import net.kkolyan.json2.parsing.ASTUtils;
import net.kkolyan.json2.parsing.EcmaScriptParser;
import net.kkolyan.json2.parsing.ParseException;

import java.io.StringReader;
import java.util.List;
import java.util.Map;

/**
 * @author nplekhanov
 */
public class JsonParsing {

    private static void printJsonAST(String js) {
        try {
            EcmaScriptParser parser = new EcmaScriptParser(new StringReader(js));
            parser.Expression();

            ASTUtils.printAST(parser.getState().rootNode(), "");
            EvaluationContext context = new SimpleEvaluationContext();
            context.setValue("serviceBean", new ServiceBean());
            EvaluatingVisitor visitor = new EvaluatingVisitor();
            visitor.setContext(context);
            parser.getState().rootNode().jjtAccept(visitor, null);
        } catch (ParseException e) {
            throw new IllegalStateException(e);
        }
    }

    public static class ServiceBean {
        public void executeAction(Map map, String s, List<Box> boxes) {
            System.out.println(map + "," + s + "," + boxes);
        }
    }

    public static class Box {
        private int size;
        private String color;

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        @Override
        public String toString() {
            return "Box{" +
                    "size=" + size +
                    ", color='" + color + '\'' +
                    '}';
        }
    }

    public static void main(String[] args) throws ParseException, InterruptedException {
        printJsonAST("serviceBean.executeAction({\"someData\":[1,2,3,true,null,\"something\"]}, \"secondArg\", " +
                "[{\"size\":2,\"color\":\"white\"},{\"size\":4,\"color\":\"red\"}]);");
//        printJsonAST("serviceBean[\"xxx\"].executeAction({\"someData\":[1,2,3,true,null]}, \"secondArg\");");
        printJsonAST("{\"someData\":[1,2,3,true,null]}");
        Thread.sleep(10);
    }
}
