package net.kkolyan.json2;

import net.kkolyan.json2.evaluation.Evaluator;
import net.kkolyan.json2.introspection.DynamicTypeFactory;
import net.kkolyan.json2.serialization.Serializer;

/**
 * @author nplekhanov
 */
public class Json {
    private static final DynamicTypeFactory dynamicTypeFactory = new DynamicTypeFactory();
    private static final Serializer serializer = new Serializer();
    private static final Evaluator evaluator = new Evaluator();

    static {
        serializer.setDynamicTypeFactory(dynamicTypeFactory);
        evaluator.setDynamicTypeFactory(dynamicTypeFactory);
    }

    public static Serializer getSerializer() {
        return serializer;
    }

    public static Evaluator getEvaluator() {
        return evaluator;
    }
}
