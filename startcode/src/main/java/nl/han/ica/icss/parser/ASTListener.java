package nl.han.ica.icss.parser;

import nl.han.ica.datastructures.stack.HANStackImpl;
import nl.han.ica.datastructures.stack.IHANStack;
import nl.han.ica.icss.ast.AST;
import nl.han.ica.icss.ast.ASTNode;

/**
 * This class extracts the ICSS Abstract Syntax Tree from the Antlr Parse tree.
 */
public class ASTListener extends ICSSBaseListener {
    private final AST ast;
    private final IHANStack<ASTNode> currentContainer;

    public ASTListener() {
        ast = new AST();
        currentContainer = new HANStackImpl<>();
    }

    public AST getAST() {
        return ast;
    }

    @Override
    public void exitStylesheet(ICSSParser.StylesheetContext ctx) {
        super.exitStylesheet(ctx);
    }

    @Override
    public void exitStylerule(ICSSParser.StyleruleContext ctx) {
        super.exitStylerule(ctx);
    }

    @Override
    public void exitVariableAssignment(ICSSParser.VariableAssignmentContext ctx) {
        super.exitVariableAssignment(ctx);
    }

    @Override
    public void exitDeclaration(ICSSParser.DeclarationContext ctx) {
        super.exitDeclaration(ctx);
    }

    @Override
    public void exitIfClause(ICSSParser.IfClauseContext ctx) {
        super.exitIfClause(ctx);
    }

    @Override
    public void exitElseClause(ICSSParser.ElseClauseContext ctx) {
        super.exitElseClause(ctx);
    }

    @Override
    public void exitBody(ICSSParser.BodyContext ctx) {
        super.exitBody(ctx);
    }

    @Override
    public void exitColorLiteral(ICSSParser.ColorLiteralContext ctx) {
        super.exitColorLiteral(ctx);
    }

    @Override
    public void exitPixelLiteral(ICSSParser.PixelLiteralContext ctx) {
        super.exitPixelLiteral(ctx);
    }

    @Override
    public void exitPercentageLiteral(ICSSParser.PercentageLiteralContext ctx) {
        super.exitPercentageLiteral(ctx);
    }

    @Override
    public void exitScalarLiteral(ICSSParser.ScalarLiteralContext ctx) {
        super.exitScalarLiteral(ctx);
    }

    @Override
    public void exitBoolLiteral(ICSSParser.BoolLiteralContext ctx) {
        super.exitBoolLiteral(ctx);
    }

    @Override
    public void exitVariableValue(ICSSParser.VariableValueContext ctx) {
        super.exitVariableValue(ctx);
    }

    @Override
    public void exitSubAdd(ICSSParser.SubAddContext ctx) {
        super.exitSubAdd(ctx);
    }

    @Override
    public void exitMultiplication(ICSSParser.MultiplicationContext ctx) {
        super.exitMultiplication(ctx);
    }

    @Override
    public void exitLiteralValue(ICSSParser.LiteralValueContext ctx) {
        super.exitLiteralValue(ctx);
    }

    @Override
    public void exitBool(ICSSParser.BoolContext ctx) {
        super.exitBool(ctx);
    }

    @Override
    public void exitConditionalExpression(ICSSParser.ConditionalExpressionContext ctx) {
        super.exitConditionalExpression(ctx);
    }

    @Override
    public void exitVariableReference(ICSSParser.VariableReferenceContext ctx) {
        super.exitVariableReference(ctx);
    }

    @Override
    public void exitPropertyName(ICSSParser.PropertyNameContext ctx) {
        super.exitPropertyName(ctx);
    }

    @Override
    public void exitIdSelector(ICSSParser.IdSelectorContext ctx) {
        super.exitIdSelector(ctx);
    }

    @Override
    public void exitClassSelector(ICSSParser.ClassSelectorContext ctx) {
        super.exitClassSelector(ctx);
    }

    @Override
    public void exitTagSelector(ICSSParser.TagSelectorContext ctx) {
        super.exitTagSelector(ctx);
    }
}