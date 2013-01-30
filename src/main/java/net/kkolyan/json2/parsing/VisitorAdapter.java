package net.kkolyan.json2.parsing;

/**
 * @author nplekhanov
 */
public class VisitorAdapter implements EcmaScriptParserVisitor {
    @Override
    public Object visit(SimpleNode node, Object data) {
        throw new IllegalStateException("Unsupported token: " + node.getClass().getSimpleName());
    }

    @Override
    public Object visit(ASTPrimaryExpression node, Object data) {
        throw new IllegalStateException("Unsupported token: " + node.getClass().getSimpleName());
    }

    @Override
    public Object visit(ASTThisReference node, Object data) {
        throw new IllegalStateException("Unsupported token: " + node.getClass().getSimpleName());
    }

    @Override
    public Object visit(ASTParenExpression node, Object data) {
        throw new IllegalStateException("Unsupported token: " + node.getClass().getSimpleName());
    }

    @Override
    public Object visit(ASTLiteral node, Object data) {
        throw new IllegalStateException("Unsupported token: " + node.getClass().getSimpleName());
    }

    @Override
    public Object visit(ASTIdentifier node, Object data) {
        throw new IllegalStateException("Unsupported token: " + node.getClass().getSimpleName());
    }

    @Override
    public Object visit(ASTArrayLiteral node, Object data) {
        throw new IllegalStateException("Unsupported token: " + node.getClass().getSimpleName());
    }

    @Override
    public Object visit(ASTObjectLiteral node, Object data) {
        throw new IllegalStateException("Unsupported token: " + node.getClass().getSimpleName());
    }

    @Override
    public Object visit(ASTLiteralField node, Object data) {
        throw new IllegalStateException("Unsupported token: " + node.getClass().getSimpleName());
    }

    @Override
    public Object visit(ASTCompositeReference node, Object data) {
        throw new IllegalStateException("Unsupported token: " + node.getClass().getSimpleName());
    }

    @Override
    public Object visit(ASTAllocationExpression node, Object data) {
        throw new IllegalStateException("Unsupported token: " + node.getClass().getSimpleName());
    }

    @Override
    public Object visit(ASTPropertyValueReference node, Object data) {
        throw new IllegalStateException("Unsupported token: " + node.getClass().getSimpleName());
    }

    @Override
    public Object visit(ASTPropertyIdentifierReference node, Object data) {
        throw new IllegalStateException("Unsupported token: " + node.getClass().getSimpleName());
    }

    @Override
    public Object visit(ASTFunctionCallParameters node, Object data) {
        throw new IllegalStateException("Unsupported token: " + node.getClass().getSimpleName());
    }

    @Override
    public Object visit(ASTPostfixExpression node, Object data) {
        throw new IllegalStateException("Unsupported token: " + node.getClass().getSimpleName());
    }

    @Override
    public Object visit(ASTOperator node, Object data) {
        throw new IllegalStateException("Unsupported token: " + node.getClass().getSimpleName());
    }

    @Override
    public Object visit(ASTUnaryExpression node, Object data) {
        throw new IllegalStateException("Unsupported token: " + node.getClass().getSimpleName());
    }

    @Override
    public Object visit(ASTBinaryExpressionSequence node, Object data) {
        throw new IllegalStateException("Unsupported token: " + node.getClass().getSimpleName());
    }

    @Override
    public Object visit(ASTAndExpressionSequence node, Object data) {
        throw new IllegalStateException("Unsupported token: " + node.getClass().getSimpleName());
    }

    @Override
    public Object visit(ASTOrExpressionSequence node, Object data) {
        throw new IllegalStateException("Unsupported token: " + node.getClass().getSimpleName());
    }

    @Override
    public Object visit(ASTConditionalExpression node, Object data) {
        throw new IllegalStateException("Unsupported token: " + node.getClass().getSimpleName());
    }

    @Override
    public Object visit(ASTAssignmentExpression node, Object data) {
        throw new IllegalStateException("Unsupported token: " + node.getClass().getSimpleName());
    }

    @Override
    public Object visit(ASTExpressionList node, Object data) {
        throw new IllegalStateException("Unsupported token: " + node.getClass().getSimpleName());
    }

    @Override
    public Object visit(ASTBlock node, Object data) {
        throw new IllegalStateException("Unsupported token: " + node.getClass().getSimpleName());
    }

    @Override
    public Object visit(ASTStatementList node, Object data) {
        throw new IllegalStateException("Unsupported token: " + node.getClass().getSimpleName());
    }

    @Override
    public Object visit(ASTVariableStatement node, Object data) {
        throw new IllegalStateException("Unsupported token: " + node.getClass().getSimpleName());
    }

    @Override
    public Object visit(ASTVariableDeclarationList node, Object data) {
        throw new IllegalStateException("Unsupported token: " + node.getClass().getSimpleName());
    }

    @Override
    public Object visit(ASTVariableDeclaration node, Object data) {
        throw new IllegalStateException("Unsupported token: " + node.getClass().getSimpleName());
    }

    @Override
    public Object visit(ASTEmptyExpression node, Object data) {
        throw new IllegalStateException("Unsupported token: " + node.getClass().getSimpleName());
    }

    @Override
    public Object visit(ASTEmptyStatement node, Object data) {
        throw new IllegalStateException("Unsupported token: " + node.getClass().getSimpleName());
    }

    @Override
    public Object visit(ASTExpressionStatement node, Object data) {
        throw new IllegalStateException("Unsupported token: " + node.getClass().getSimpleName());
    }

    @Override
    public Object visit(ASTIfStatement node, Object data) {
        throw new IllegalStateException("Unsupported token: " + node.getClass().getSimpleName());
    }

    @Override
    public Object visit(ASTDoStatement node, Object data) {
        throw new IllegalStateException("Unsupported token: " + node.getClass().getSimpleName());
    }

    @Override
    public Object visit(ASTWhileStatement node, Object data) {
        throw new IllegalStateException("Unsupported token: " + node.getClass().getSimpleName());
    }

    @Override
    public Object visit(ASTForStatement node, Object data) {
        throw new IllegalStateException("Unsupported token: " + node.getClass().getSimpleName());
    }

    @Override
    public Object visit(ASTForVarStatement node, Object data) {
        throw new IllegalStateException("Unsupported token: " + node.getClass().getSimpleName());
    }

    @Override
    public Object visit(ASTForVarInStatement node, Object data) {
        throw new IllegalStateException("Unsupported token: " + node.getClass().getSimpleName());
    }

    @Override
    public Object visit(ASTForInStatement node, Object data) {
        throw new IllegalStateException("Unsupported token: " + node.getClass().getSimpleName());
    }

    @Override
    public Object visit(ASTContinueStatement node, Object data) {
        throw new IllegalStateException("Unsupported token: " + node.getClass().getSimpleName());
    }

    @Override
    public Object visit(ASTBreakStatement node, Object data) {
        throw new IllegalStateException("Unsupported token: " + node.getClass().getSimpleName());
    }

    @Override
    public Object visit(ASTReturnStatement node, Object data) {
        throw new IllegalStateException("Unsupported token: " + node.getClass().getSimpleName());
    }

    @Override
    public Object visit(ASTWithStatement node, Object data) {
        throw new IllegalStateException("Unsupported token: " + node.getClass().getSimpleName());
    }

    @Override
    public Object visit(ASTSwitchStatement node, Object data) {
        throw new IllegalStateException("Unsupported token: " + node.getClass().getSimpleName());
    }

    @Override
    public Object visit(ASTCaseGroups node, Object data) {
        throw new IllegalStateException("Unsupported token: " + node.getClass().getSimpleName());
    }

    @Override
    public Object visit(ASTCaseGroup node, Object data) {
        throw new IllegalStateException("Unsupported token: " + node.getClass().getSimpleName());
    }

    @Override
    public Object visit(ASTCaseGuard node, Object data) {
        throw new IllegalStateException("Unsupported token: " + node.getClass().getSimpleName());
    }

    @Override
    public Object visit(ASTThrowStatement node, Object data) {
        throw new IllegalStateException("Unsupported token: " + node.getClass().getSimpleName());
    }

    @Override
    public Object visit(ASTTryStatement node, Object data) {
        throw new IllegalStateException("Unsupported token: " + node.getClass().getSimpleName());
    }

    @Override
    public Object visit(ASTCatchClause node, Object data) {
        throw new IllegalStateException("Unsupported token: " + node.getClass().getSimpleName());
    }

    @Override
    public Object visit(ASTFinallyClause node, Object data) {
        throw new IllegalStateException("Unsupported token: " + node.getClass().getSimpleName());
    }

    @Override
    public Object visit(ASTFunctionDeclaration node, Object data) {
        throw new IllegalStateException("Unsupported token: " + node.getClass().getSimpleName());
    }

    @Override
    public Object visit(ASTFormalParameterList node, Object data) {
        throw new IllegalStateException("Unsupported token: " + node.getClass().getSimpleName());
    }

    @Override
    public Object visit(ASTProgram node, Object data) {
        throw new IllegalStateException("Unsupported token: " + node.getClass().getSimpleName());
    }
}
