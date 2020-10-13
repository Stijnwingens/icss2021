package nl.han.ica.icss.parser;

import nl.han.ica.datastructures.stack.HANStackImpl;
import nl.han.ica.datastructures.stack.IHANStack;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.*;
import nl.han.ica.icss.ast.operations.AddOperation;
import nl.han.ica.icss.ast.operations.MultiplyOperation;
import nl.han.ica.icss.ast.operations.SubtractOperation;
import nl.han.ica.icss.ast.selectors.ClassSelector;
import nl.han.ica.icss.ast.selectors.IdSelector;
import nl.han.ica.icss.ast.selectors.TagSelector;

import java.util.Collections;
import java.util.List;

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
        currentContainer = reverseStack(currentContainer);

        ast.setRoot(new Stylesheet());
        while (!currentContainer.isEmpty()) {
            ast.root.addChild(currentContainer.pop());
        }
    }

    @Override
    public void exitStylerule(ICSSParser.StyleruleContext ctx) {
        super.exitStylerule(ctx);
        IHANStack<ASTNode> stack = reverseStack(
                currentContainer,
                List.of(Stylerule.class, VariableAssignment.class)
        );

        Stylerule stylerule = new Stylerule();
        while (!stack.isEmpty()) {
            stylerule.addChild(stack.pop());
        }

        currentContainer.push(stylerule);
    }

    @Override
    public void exitVariableAssignment(ICSSParser.VariableAssignmentContext ctx) {
        super.exitVariableAssignment(ctx);
        IHANStack<ASTNode> stack = reverseStack(
                currentContainer,
                List.of(Stylerule.class, VariableAssignment.class)
        );

        VariableAssignment variableAssignment = new VariableAssignment();
        variableAssignment.addChild(stack.pop());
        variableAssignment.addChild(stack.pop());

        currentContainer.push(variableAssignment);
    }

    @Override
    public void exitDeclaration(ICSSParser.DeclarationContext ctx) {
        super.exitDeclaration(ctx);
        Declaration d = new Declaration();
        d.addChild(currentContainer.pop());
        d.addChild(currentContainer.pop());

        currentContainer.push(d);
    }

    @Override
    public void exitIfClause(ICSSParser.IfClauseContext ctx) {
        super.exitIfClause(ctx);
        IfClause ifClause = new IfClause();

        while (!currentContainer.isEmpty()) {
            if (currentContainer.peek() instanceof BoolLiteral ||
                    currentContainer.peek() instanceof VariableReference)
                break;

            ifClause.addChild(currentContainer.pop());
        }

        ifClause.addChild(currentContainer.pop());

        Collections.reverse(ifClause.body);
        currentContainer.push(ifClause);
    }

    @Override
    public void enterElseClause(ICSSParser.ElseClauseContext ctx) {
        super.enterElseClause(ctx);
        currentContainer.push(new ElseClause());
    }

    @Override
    public void exitElseClause(ICSSParser.ElseClauseContext ctx) {
        super.exitElseClause(ctx);

        ElseClause elseClause = new ElseClause();
        while (!currentContainer.isEmpty()) {
            if (currentContainer.peek() instanceof ElseClause) break;
            elseClause.addChild(currentContainer.pop());
        }
        currentContainer.pop();
        currentContainer.push(elseClause);
    }

    @Override
    public void exitSubAdd(ICSSParser.SubAddContext ctx) {
        super.exitSubAdd(ctx);
        Operation operation;

        if (ctx.getChild(1).getText().equals("+")) operation = new AddOperation();
        else operation = new SubtractOperation();

        addValuesToOperation(operation);
        currentContainer.push(operation);
    }

    @Override
    public void exitMultiplication(ICSSParser.MultiplicationContext ctx) {
        super.exitMultiplication(ctx);
        MultiplyOperation operation = new MultiplyOperation();

        addValuesToOperation(operation);
        currentContainer.push(operation);
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
    public void exitBool(ICSSParser.BoolContext ctx) {
        super.exitBool(ctx);
        currentContainer.push(new BoolLiteral(ctx.getChild(0).getText()));
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

    private IHANStack<ASTNode> reverseStack(IHANStack<ASTNode> stack, List<Class<? extends ASTNode>> exemptedInstances) {
        IHANStack<ASTNode> reversedStack = new HANStackImpl<>();

        while (!stack.isEmpty()) {
            boolean skip = false;
            Class<? extends ASTNode> currentElement = stack.peek().getClass();

            for (Class<? extends ASTNode> exemptedElement : exemptedInstances) {
                if (exemptedElement.equals(currentElement)) {
                    skip = true;
                    break;// stop looking through the list of exemptedInstances
                }
            }

            if (skip) break;// skip this currentElement from being reversed
            reversedStack.push(stack.pop());
        }

        return reversedStack;
    }

    private IHANStack<ASTNode> reverseStack(IHANStack<ASTNode> stack) {
        IHANStack<ASTNode> reversedStack = new HANStackImpl<>();

        while (!stack.isEmpty()) {
            reversedStack.push(stack.pop());
        }

        return reversedStack;
    }

    private void addValuesToOperation(Operation operation) {
        operation.rhs = (Expression) currentContainer.pop();
        operation.lhs = (Expression) currentContainer.pop();
    }
}