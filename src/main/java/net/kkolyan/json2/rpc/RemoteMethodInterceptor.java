package net.kkolyan.json2.rpc;

import net.kkolyan.json2.evaluation.EvaluationContext;
import net.kkolyan.json2.evaluation.Evaluator;
import net.kkolyan.json2.evaluation.SimpleEvaluationContext;
import net.kkolyan.json2.serialization.Serializer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.objectweb.asm.Type;

import java.io.StringReader;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
* @author nplekhanov
*/
public class RemoteMethodInterceptor implements MethodInterceptor {
    private String serviceName;
    private Shell shell;
    private Evaluator evaluator;
    private Serializer serializer;

    public RemoteMethodInterceptor(String serviceName, Shell shell, Evaluator evaluator, Serializer serializer) {
        this.serviceName = serviceName;
        this.shell = shell;
        this.evaluator = evaluator;
        this.serializer = serializer;
    }

    protected String borrowVar() {
        return "x";
    }

    @Override
    public Object intercept(Object obj, Method slowMethod, Object[] args, MethodProxy method) throws Throwable {
        StringBuilder message = new StringBuilder();

        String var = null;
        if (method.getSignature().getReturnType() != Type.VOID_TYPE) {
            var = borrowVar();
            message.append(var);
            message.append("=");
        }

        message.append(serviceName);
        message.append(".");
        message.append(method.getSignature().getName());
        message.append("(");
        for (int i = 0; i < args.length; i ++) {
            if (i != 0) {
                message.append(",");
            }
            serializer.serialize(args[i], message);
        }
        message.append(");\n");

        StringBuilder result = new StringBuilder();
        shell.execute(new StringReader(message.toString()), result);

        EvaluationContext context = new SimpleEvaluationContext(new HashMap<String, Object>(1), null);
        evaluator.evaluate(new StringReader(result.toString()), null, context);
        if (var == null) {
            return null;
        }
        return context.getValue(var);
    }

    public void setSerializer(Serializer serializer) {
        this.serializer = serializer;
    }

    public void setEvaluator(Evaluator evaluator) {
        this.evaluator = evaluator;
    }

    public void setShell(Shell shell) {
        this.shell = shell;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
}
