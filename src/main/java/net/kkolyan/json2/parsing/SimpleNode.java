/* Generated By:JJTree: Do not edit this line. SimpleNode.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package net.kkolyan.json2.parsing;


import net.kkolyan.json2.util.Escaping;

import java.util.*;

public abstract class SimpleNode implements Node {

    protected Node parent;
    protected Node[] children;
    protected int id;
    protected Object value;
    private String file;
    protected EcmaScriptParser parser;
    public static final Object NULL = new Object() {
        @Override
        public String toString() {
            return "__null__";
        }
    };
    private Token beginToken;
    private Token endToken;

    public SimpleNode(int i) {
        id = i;
    }

    public SimpleNode(EcmaScriptParser p, int i) {
        this(i);
        parser = p;
    }

    public void jjtOpen() {
    }

    public void jjtClose() {
    }

    public void jjtSetParent(Node n) {
        parent = n;
    }

    public Node jjtGetParent() {
        return parent;
    }

    public void jjtAddChild(Node n, int i) {
        if (children == null) {
            children = new Node[i + 1];
        } else if (i >= children.length) {
            Node c[] = new Node[i + 1];
            System.arraycopy(children, 0, c, 0, children.length);
            children = c;
        }
        children[i] = n;
    }

    public Node jjtGetChild(int i) {
        return children[i];
    }

    public int jjtGetNumChildren() {
        return (children == null) ? 0 : children.length;
    }

    public void jjtSetValue(Object value) {
        this.value = value;
    }

    public Object jjtGetValue() {
        return value;
    }

    /**
     * Accept the visitor. *
     */
    public abstract Object jjtAccept(EcmaScriptParserVisitor visitor, Object data);

    /** You can override these two methods in subclasses of SimpleNode to
customize the way the node appears when the tree is dumped.  If
your output uses more than one line you should override
toString(String), otherwise overriding toString() is probably all
you need to do. */

    //  public String toString() { return EcmaScriptTreeConstants.jjtNodeName[id]; }

    public String toString() {
        if (value == null) {
            if (children == null) {
                return beginToken.toString();
            }
            String s = "";
            s += "(";
            for (Node aChildren : children) {
                s += " ";
                s += aChildren.toString();
            }
            s += ")";
            return s;
        }
        return value.toString();
    }

    public String toString(String prefix) {
        return prefix + toString();
    }

    /* Override this method if you want to customize how the node dumps
 out its children. */

    public void dump(String prefix) {
        System.out.println(toString(prefix));
        if (children != null) {
            for (Node n : children) {
                if (n != null) {
                    ((SimpleNode)n).dump(prefix + " ");
                }
            }
        }
    }


    public void setDecimalValue(String image) {
        try {
            value = new Long(image);
        } catch (NumberFormatException e) {
            // it's a floating point
            value = new Double(image);
        }
    }

    public void setHexValue(String image) {
        value = Long.parseLong(image.substring(2), 16);
    }

    public void setBooleanValue(String image) {
        value = Boolean.valueOf(image);

    }

    public void setNullValue() {
        value = NULL;
    }

    public void setStringValue(String image) throws ParseException {

        value = Escaping.unescapedString(image);

        /*
           * if (!expectActionScript) { if (value.equals("class") ||
           * value.equals("super")) { throw new ParseException( "reserved
           * identifier class or super used"); } }
           */
    }

    public void setRegexValue(String image) {
        value = image;
    }


    public void setName(String identifierName) {
        value = identifierName;
    }


    public void setBeginToken(Token beginToken) {
        this.beginToken = beginToken;
    }

    public void setEndToken(Token endToken) {
        this.endToken = endToken;
    }

    public List<Node> children() {
        if (children == null) {
            return Collections.emptyList();
        }
        return Arrays.asList(children);
    }

	public String getValue() {
        if (value == null) {
            return beginToken.toString();
        }
        return value.toString();
    }

    public Location getLocation() {
        int line = -1;
        int column = -1;
        if (beginToken != null) {
            line = beginToken.beginLine;
            column = beginToken.beginColumn;
        }
        return new Location(file, line, column);
    }

    public void setFile(String name) {
        file = name;
        for (Node child: children()) {
            ((SimpleNode) child).setFile(name);
        }
    }

}

/* JavaCC - OriginalChecksum=676a3f2c808e195174ea9dd5c75f55df (do not edit this line) */
