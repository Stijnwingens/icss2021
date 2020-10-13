package nl.han.ica.icss.parser;

import nl.han.ica.datastructures.stack.HANStackImpl;
import nl.han.ica.datastructures.stack.IHANStack;
import nl.han.ica.datastructures.stack.ReverseStack;
import nl.han.ica.datastructures.stack.ReverseStackImpl;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.ColorLiteral;
import nl.han.ica.icss.ast.literals.PercentageLiteral;
import nl.han.ica.icss.ast.literals.PixelLiteral;
import nl.han.ica.icss.ast.literals.ScalarLiteral;
import nl.han.ica.icss.ast.selectors.ClassSelector;
import nl.han.ica.icss.ast.selectors.IdSelector;
import nl.han.ica.icss.ast.selectors.TagSelector;

/**
 * This class extracts the ICSS Abstract Syntax Tree from the Antlr Parse tree.
 */
public class ASTListener extends ICSSBaseListener {
    private final AST ast;
    private IHANStack<ASTNode> currentContainer;

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
        ast.setRoot(new Stylesheet());
        ReverseStack<ASTNode> reverseStack = new ReverseStackImpl<>();
        currentContainer = reverseStack.reverse(currentContainer);
        while (!currentContainer.isEmpty())
            ast.root.addChild(currentContainer.pop());

        //TODO refactor reversing
    }

    @Override
    public void exitStylerule(ICSSParser.StyleruleContext ctx) {
        super.exitStylerule(ctx);
        Stylerule stylerule = new Stylerule();
        IHANStack<ASTNode> stack = new HANStackImpl<>();

        while (!currentContainer.isEmpty()) {
            if (currentContainer.peek() instanceof Stylerule) break;
            if (currentContainer.peek() instanceof VariableAssignment) break;

            stack.push(currentContainer.pop());
        }

        while (!stack.isEmpty()) {
            stylerule.addChild(stack.pop());
        }
        //TODO refactor reversing
        currentContainer.push(stylerule);
    }

    @Override
    public void exitVariableAssignment(ICSSParser.VariableAssignmentContext ctx) {
        super.exitVariableAssignment(ctx);

    }

    @Override
    public void exitDeclaration(ICSSParser.DeclarationContext ctx) {
        Declaration d = new Declaration();
        d.addChild(currentContainer.pop());
        d.addChild(currentContainer.pop());
        currentContainer.push(d);
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
        currentContainer.push(new ColorLiteral(ctx.getChild(0).getText()));
    }

    @Override
    public void exitPixelLiteral(ICSSParser.PixelLiteralContext ctx) {
        super.exitPixelLiteral(ctx);
        currentContainer.push(new PixelLiteral(ctx.getChild(0).getText()));
    }

    @Override
    public void exitPercentageLiteral(ICSSParser.PercentageLiteralContext ctx) {
        super.exitPercentageLiteral(ctx);
        currentContainer.push(new PercentageLiteral(ctx.getChild(0).getText()));
    }

    @Override
    public void exitScalarLiteral(ICSSParser.ScalarLiteralContext ctx) {
        super.exitScalarLiteral(ctx);
        currentContainer.push(new ScalarLiteral(ctx.getChild(0).getText()));
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
    public void exitVariableReference(ICSSParser.VariableReferenceContext ctx) {
        super.exitVariableReference(ctx);
        currentContainer.push(new VariableReference(ctx.getChild(0).getText()));
    }

    @Override
    public void exitPropertyName(ICSSParser.PropertyNameContext ctx) {
        super.exitPropertyName(ctx);
        currentContainer.push(new PropertyName(ctx.getChild(0).getText()));
    }

    @Override
    public void exitIdSelector(ICSSParser.IdSelectorContext ctx) {
        super.exitIdSelector(ctx);
        currentContainer.push(new IdSelector(ctx.getChild(0).getText()));
    }

    @Override
    public void exitClassSelector(ICSSParser.ClassSelectorContext ctx) {
        super.exitClassSelector(ctx);
        currentContainer.push(new ClassSelector(ctx.getChild(0).getText()));
    }

    @Override
    public void exitTagSelector(ICSSParser.TagSelectorContext ctx) {
        super.exitTagSelector(ctx);
        currentContainer.push(new TagSelector(ctx.getChild(0).getText()));
    }
}