package net.kkolyan.json2.rpc;

import net.kkolyan.json2.evaluation.Evaluator;
import net.kkolyan.json2.evaluation.SimpleEvaluationContext;
import net.kkolyan.json2.evaluation.EvaluationContext;
import net.kkolyan.json2.serialization.Serializer;

import java.io.IOException;
import java.io.Reader;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author nplekhanov
 */
public class LocalShell implements Shell {
    private EvaluationContext rootContext;
    private Evaluator evaluator;
    private Serializer serializer;

    @Override
    public void execute(Reader in, Appendable out) throws IOException {
        Map<String,Object> vars = new LinkedHashMap<String, Object>();

        EvaluationContext context = new SimpleEvaluationContext(vars, rootContext);

        evaluator.evaluate(in, null, context);

        for (Map.Entry<String, Object> var: vars.entrySet()) {
            out.append(var.getKey());
            out.append("=");
            serializer.serialize(var.getValue(), out);
            out.append(";\n");
        }
    }

    public void setRootContext(EvaluationContext rootContext) {
        this.rootContext = rootContext;
    }

    public void setEvaluator(Evaluator evaluator) {
        this.evaluator = evaluator;
    }

    public void setSerializer(Serializer serializer) {
        this.serializer = serializer;
    }
}
