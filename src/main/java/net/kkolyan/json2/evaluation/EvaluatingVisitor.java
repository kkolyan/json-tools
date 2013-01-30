package net.kkolyan.json2.evaluation;

import net.kkolyan.json2.introspection.DynamicTypeFactory;
import net.kkolyan.json2.introspection.Property;
import net.kkolyan.json2.parsing.ASTArrayLiteral;
import net.kkolyan.json2.parsing.ASTAssignmentExpression;
import net.kkolyan.json2.parsing.ASTCompositeReference;
import net.kkolyan.json2.parsing.ASTExpressionStatement;
import net.kkolyan.json2.parsing.ASTFunctionCallParameters;
import net.kkolyan.json2.parsing.ASTIdentifier;
import net.kkolyan.json2.parsing.ASTLiteral;
import net.kkolyan.json2.parsing.ASTLiteralField;
import net.kkolyan.json2.parsing.ASTObjectLiteral;
import net.kkolyan.json2.parsing.ASTPrimaryExpression;
import net.kkolyan.json2.parsing.ASTPropertyIdentifierReference;
import net.kkolyan.json2.parsing.ASTPropertyValueReference;
import net.kkolyan.json2.parsing.ASTStatementList;
import net.kkolyan.json2.parsing.ASTUtils;
import net.kkolyan.json2.util.Escaping;
import net.kkolyan.json2.parsing.Node;
import net.kkolyan.json2.parsing.SimpleNode;
import net.kkolyan.json2.parsing.VisitorAdapter;
import net.kkolyan.json2.util.Generics;

import java.lang.reflect.*;
import java.util.*;

/**
 * @author nplekhanov
 */
public class EvaluatingVisitor extends VisitorAdapter {
    private Deque<Type> requiredType = new ArrayDeque<Type>(Arrays.asList((Type)UndefinedClass.class));
    private List<TypeConverter> converters = new ArrayList<TypeConverter>(Arrays.asList(new PrimitiveTypeConverter()));
    private EvaluationContext context = new SimpleEvaluationContext(null);
    private DynamicTypeFactory dynamicTypeFactory;

    @Override
    public Object visit(ASTPrimaryExpression node, Object data) {
        // can't unwrap here
        return node.children().get(0).jjtAccept(this, data);
    }

    @Override
    public Object visit(ASTLiteral node, Object data) {
        Type type = requiredType.peek();
        if (type == UndefinedClass.class) {
            return node.jjtGetValue();
        }
        Class rawType = Generics.getRawType(type);
        if (node.jjtGetValue() == SimpleNode.NULL) {
            if (rawType.isPrimitive()) return (rawType == boolean.class) ? false : 0;
            return null;
        }
        for (TypeConverter converter: converters) {
            if (converter.isSupported(type)) {
                return converter.convertTo(node.getValue(), type);
            }
        }
        throw new IllegalStateException("can't convert literal to " + rawType.getName());
    }

    @Override
    public Object visit(ASTObjectLiteral node, Object data) {
        Type type = requiredType.peek();
        if (type == UndefinedClass.class) {
            type = UndefinedClass.RAW_MAP_TYPE;
        }
        Object instance = dynamicTypeFactory.getDynamicType(type).newInstance();

        for (Node aChild : node.children()) {
            ASTLiteralField field = (ASTLiteralField) aChild;

            if (field.children().size() != 2) {
                throw new IllegalStateException("" + ASTUtils.printAST(field, ""));
            }

            requiredType.push(String.class);
            String key;
            try {
                SimpleNode keyNode = (SimpleNode) field.children().get(0);
                key = keyNode.getValue();
            } finally {
                requiredType.pop();
            }

            if (key == null) {
                throw new IllegalStateException("object literal field with null key");
            }

            if (key.startsWith("\"")) {
                key = Escaping.unescapedString(key);
            }

            Property property = dynamicTypeFactory.getDynamicType(type).getProperty(key);

            if (property.getType() != null) {
                requiredType.push(property.getType());
            } else {
                requiredType.push(UndefinedClass.class);
            }
            Object value;
            try {
                value = field.children().get(1).jjtAccept(this, data);
            } finally {
                requiredType.pop();
            }

            property.setValue(instance, value);
        }
        return instance;
    }

    @Override
    public Object visit(ASTArrayLiteral node, Object data) {
        List<Object> value = new ArrayList<Object>();
        Type type = requiredType.peek();
        if (type == UndefinedClass.class) {
            type = UndefinedClass.RAW_LIST_TYPE;
        }
        Type elementType = Generics.getGenericTypeParameter(type, 0);
        requiredType.push(elementType);
        try {
            for (Node child : node.children()) {
                value.add(child.jjtAccept(this, data));
            }
        } finally {
            requiredType.pop();
        }
        Class rawType = Generics.getRawType(type);
        if (rawType == List.class || rawType == Collection.class || rawType == Iterable.class) {
            return value;
        }
        if (rawType == Queue.class || rawType == Deque.class) {
            return new ArrayDeque<Object>(value);
        }
        if (rawType == Set.class) {
            return new LinkedHashSet<Object>(value);
        }
        if (rawType.isArray()) {
            Object[] array = (Object[]) Array.newInstance(rawType.getComponentType(), value.size());
            return value.toArray(array);
        }
        throw new IllegalStateException("can't map array literal to " + rawType.getName());
    }

    @Override
    public Object visit(ASTFunctionCallParameters node, Object data) {
        Expression target = (Expression) data;

        Callable callable = target.getAsCallable(node.children().size());
        Object[] args = new Object[node.children().size()];
        for (int i = 0; i < node.children().size(); i ++) {
            requiredType.push(callable.getArgType(i));
            args[i] = node.children().get(i).jjtAccept(this, data);
            requiredType.pop();
        }
        return callable.call(args);
    }

    @Override
    public Object visit(ASTAssignmentExpression node, Object data) {
        Expression var = (Expression) node.children().get(0).jjtAccept(this, data);
        Object value = node.children().get(2).jjtAccept(this, data);
        Type type = var.getDesiredType();
        if (type != null) {
            for (TypeConverter converter: converters) {
                if (converter.isSupported(type)) {
                    value = converter.convertTo(value, var.getDesiredType());
                }
            }
        }
        var.setValue(value);
        return value;
    }

    @Override
    public Object visit(ASTStatementList node, Object data) {
        Object result = null;
        for (Node child: node.children()) {
            result = child.jjtAccept(this, data);
        }
        return result;
    }

    @Override
    public Object visit(ASTExpressionStatement node, Object data) {
        Object result = node.children().get(0).jjtAccept(this, data);
        while (result instanceof Expression) {
            result = ((Expression) result).getValue();
        }
        return result;
    }

    @Override
    public Object visit(ASTCompositeReference node, Object data) {
        if (node.children().size() < 2) {
            throw new IllegalStateException();
        }
        Object o = node.children().get(0).jjtAccept(this, null);
        for (int i = 1; i < node.children().size(); i ++) {
            o = node.children().get(i).jjtAccept(this, o);
        }
        return o;
    }

    @Override
    public Object visit(ASTPropertyIdentifierReference node, Object data) {
        if (node.children().size() != 1) {
            throw new IllegalStateException();
        }
        data = unwrap(data);
        ASTIdentifier identifier = (ASTIdentifier) node.children().get(0);
        return new MemberExpression(data, identifier.getValue(), node.getLocation(), dynamicTypeFactory);
    }

    @Override
    public Object visit(ASTIdentifier node, Object data) {
        return new VarExpression(node.getValue(), context);
    }

    @Override
    public Object visit(ASTPropertyValueReference node, Object data) {
        if (node.children().size() != 1) {
            throw new IllegalStateException();
        }
        data = unwrap(data);
        Object member = node.children().get(0).jjtAccept(this, null);
        member = unwrap(member);
        return new MemberExpression(data, member.toString(), node.getLocation(), dynamicTypeFactory);
    }

    private static Object unwrap(Object o) {
        while (o instanceof Expression) {
            o = ((Expression) o).getValue();
        }
        return o;
    }

    public void setRootType(Type rootType) {
        if (requiredType.size() != 1) {
            throw new IllegalStateException();
        }
        requiredType = new ArrayDeque<Type>();
        requiredType.push(rootType);
    }

    public void setConverters(List<TypeConverter> converters) {
        this.converters = converters;
    }

    public void setContext(EvaluationContext context) {
        this.context = context;
    }

    public void setDynamicTypeFactory(DynamicTypeFactory dynamicTypeFactory) {
        this.dynamicTypeFactory = dynamicTypeFactory;
    }

    public List<TypeConverter> getConverters() {
        return converters;
    }
}
