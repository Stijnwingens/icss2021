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
}