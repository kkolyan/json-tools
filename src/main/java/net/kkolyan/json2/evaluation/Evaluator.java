package net.kkolyan.json2.evaluation;

import net.kkolyan.json2.introspection.DynamicTypeFactory;
import net.kkolyan.json2.parsing.EcmaScriptParser;
import net.kkolyan.json2.parsing.Node;
import net.kkolyan.json2.parsing.ParseException;
import net.kkolyan.json2.util.Generics;

import java.io.Reader;
import java.lang.reflect.Type;

/**
 * @author nplekhanov
 */
public class Evaluator {
    private DynamicTypeFactory dynamicTypeFactory = new DynamicTypeFactory();

    public Object evaluate(Reader reader, Type expectedType, EvaluationContext context) {
        EcmaScriptParser parser = new EcmaScriptParser(reader);
        try {
            parser.ExpressionStatement();
        } catch (ParseException e) {
            throw new IllegalStateException(e);
        }
        Node node = parser.getState().rootNode();
        EvaluatingVisitor visitor = new EvaluatingVisitor();
        visitor.setRootType(expectedType);
        visitor.setContext(context);
        visitor.setDynamicTypeFactory(dynamicTypeFactory);
        Object result = node.jjtAccept(visitor, null);
        if (expectedType != null) {
            result = Generics.getRawType(expectedType).cast(result);
        }
        return result;
    }

    public void setDynamicTypeFactory(DynamicTypeFactory dynamicTypeFactory) {
        this.dynamicTypeFactory = dynamicTypeFactory;
    }
}
