package com.compiler;


import java.util.ArrayList;
import java.util.List;

public class Parser {
    private final List<Token> tokens;
    private int current = 0;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    public ASTNode.Program parse() {
        List<ASTNode.ClassDeclaration> classes = new ArrayList<>();
        while (!isAtEnd()) {
            classes.add(parseClassDeclaration());
        }
        return new ASTNode.Program(classes);
    }

    private ASTNode.ClassDeclaration parseClassDeclaration() {
        consume(TokenType.CLASS, "Expected 'class' keyword.");
        String className = consume(TokenType.IDENTIFIER, "Expected class name.").lexeme();
        consume(TokenType.LEFT_BRACE, "Expected '{' before class body.");

        List<ASTNode.MethodDeclaration> methods = new ArrayList<>();
        while (!check(TokenType.RIGHT_BRACE) && !isAtEnd()) {
            methods.add(parseMethodDeclaration());
        }

        consume(TokenType.RIGHT_BRACE, "Expect '}' after class body.");
        return new ASTNode.ClassDeclaration(className, methods);
    }

    private ASTNode.MethodDeclaration parseMethodDeclaration() {
        consume(TokenType.PUBLIC, "Expected 'public' keyword.");
        consume(TokenType.STATIC, "Expected 'static' keyword.");
        String returnType = consume(TokenType.VOID, "Expected 'void' return type.").lexeme();
        String methodName = consume(TokenType.IDENTIFIER, "Expected method name.").lexeme();
        consume(TokenType.LEFT_PAREN, "Expect '(' after method name.");
        consume(TokenType.RIGHT_PAREN, "Expect ')' after parameters.");
        consume(TokenType.LEFT_BRACE, "Expect '{' before method body.");

        List<ASTNode.Statement> body = new ArrayList<>();
        while (!check(TokenType.RIGHT_BRACE) && !isAtEnd()) {
            body.add(parseStatement());
        }

        consume(TokenType.RIGHT_BRACE,
                "Expect '}' after method body.");
        return new ASTNode.MethodDeclaration(methodName, returnType, body);
    }

    private ASTNode.Statement parseStatement() {
        // For now, we'll only implement expression statements
        ASTNode.Expression expr = parseExpression();
        consume(TokenType.SEMICOLON, "Expected ';' after expression.");
        return new ASTNode.ExpressionStatement(expr);
    }

    private ASTNode.Expression parseExpression() {
        return parseTerm();
    }

    private ASTNode.Expression parseTerm() {
        ASTNode.Expression expr = parseFactor();

        while (match(TokenType.PLUS, TokenType.MINUS)) {
            String operator = previous().lexeme();
            ASTNode.Expression right = parseFactor();
            expr = new ASTNode.BinaryExpression(expr, operator, right);
        }

        return expr;
    }

    private ASTNode.Expression parseFactor() {
        ASTNode.Expression expr = parsePrimary();

        while (match(TokenType.MULTIPLY, TokenType.DIVISION)) {
            String operator = previous().lexeme();
            ASTNode.Expression right = parsePrimary();
            expr = new ASTNode.BinaryExpression(expr, operator, right);
        }

        return expr;
    }

    private ASTNode.Expression parsePrimary() {
        if (match(TokenType.INTEGER_LITERAL)) {
            return new ASTNode.IntegerLiteral(Integer.parseInt(previous().lexeme()));
        }

        if (match(TokenType.IDENTIFIER)) {
            return new ASTNode.Identifier(previous().lexeme());
        }

        throw error(peek(), "Expected expression.");
    }

    private boolean match(TokenType... types) {
        for (TokenType type : types) {
            if (check(type)) {
                advance();
                return true;
            }
        }
        return false;
    }

    private Token consume(TokenType type, String message) {
        if (check(type)) return advance();
        throw error(peek(), message);
    }

    private boolean check(TokenType type) {
        if (isAtEnd()) return false;
        return peek().type() == type;
    }

    private Token advance() {
        if (!isAtEnd()) current++;
        return previous();
    }

    private boolean isAtEnd() {
        return peek().type() == TokenType.EOF;
    }

    private Token peek() {
        return tokens.get(current);
    }

    private Token previous() {
        return tokens.get(current - 1);
    }

    private RuntimeException error(Token token, String message) {

        // In a real compiler, you'd want to handle errors more gracefully

        return new RuntimeException("Error at line " + token.line());
    }
}