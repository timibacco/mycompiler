package com.compiler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Lexer {

    /* ************************************************************
     *  THE LEXICAL ANALYZER CLASS. SCANS THROUGH THE SOURCE CODE.
     *  READS THE STREAM OF CHARACTERS THAT MAKE UP THE SOURCE CODE.
     *  AND THEN GROUPS INTO MEANINGFUL SEQUENCES CALLED LEXEMES.
     *  GENERATING TOKENS FROM THE LEXEMES (EACH).
     * *************************************************************
    * */

    private final String source;
    private final List<Token> tokens = new ArrayList<>();
    private int current = 0;
    private int start = 0;
    private static int line = 1;

    public Lexer(String source) {
        this.source = source;
    }

    static final Map<String,TokenType> keywords = new HashMap<>();

    static {

        keywords.put("class", TokenType.CLASS );
        keywords.put("int", TokenType.INT);
        keywords.put("public", TokenType.PUBLIC);
        keywords.put("char", TokenType.CHAR);
        keywords.put("Integer", TokenType.INT);
        keywords.put("static", TokenType.STATIC);
        keywords.put("void", TokenType.VOID);
        keywords.put("if", TokenType.IF);
        keywords.put("else", TokenType.ELSE);
        keywords.put("while", TokenType.WHILE);
        keywords.put("for", TokenType.FOR);
        keywords.put("switch", TokenType.SWITCH);
        keywords.put("null", TokenType.NULL);

    }



    public List<Token> scan() {
        while (!isAtEnd()) {
            start = current;
            char c = advance();
            switch (c) {
                case ' ':
                case '\r':
                case '\t':
                    break;
                case '\n':
                    line++;
                    break;

                case '+': addToken(TokenType.PLUS, String.valueOf(c)); break;
                case '-': addToken(TokenType.MINUS, String.valueOf(c)); break;
                case '=': addToken(TokenType.ASSIGNMENT, String.valueOf(c)); break;
                case '*': addToken(TokenType.MULTIPLY, String.valueOf(c)); break;
                case '/': addToken(TokenType.DIVISION, String.valueOf(c)); break;


                case ';': addToken(TokenType.SEMICOLON, String.valueOf(c)); break;
                case '{': addToken(TokenType.LEFT_BRACE, String.valueOf(c)); break;
                case '}': addToken(TokenType.RIGHT_BRACE, String.valueOf(c)); break;
                case '(': addToken(TokenType.LEFT_PAREN, String.valueOf(c)); break;
                case ')': addToken(TokenType.RIGHT_PAREN, String.valueOf(c)); break;

                default:
                    if (characterIsDigit(c)) {
                        produceTokenForNumber();
                    } else if (characterIsAlpha(c)) {
                        produceTokenForIdentifier();
                    } else {
                        throw new RuntimeException("Unexpected character: " + c);
                    }
                    break;
            }

        }
        tokens.add(new Token(TokenType.EOF, "", line));
        return tokens;
    }

    private boolean isAtEnd() {
        return current >= source.length();
    }

    private char advance() {
        return source.charAt(current++);
    }

    private char peek() {
        return isAtEnd() ? '\0' : source.charAt(current);
    }

    private char peep(){
        return isAtEnd() ? '\0': source.charAt(current - 1);
    }

    private void addToken(TokenType type, String lexeme) {
        tokens.add(new Token(type, lexeme, line));
    }

    private void produceTokenForNumber() {
        StringBuilder number = new StringBuilder();

        while (!isAtEnd() && characterIsDigit(peep())) {

            number.append(peep());
            advance();

        }
        addToken(TokenType.NUMBER, number.toString());
    }

    private void produceTokenForIdentifier() {
        StringBuilder identifier = new StringBuilder();

        while (!isAtEnd() && characterIsAlphaNumeric(peep())) {

            identifier.append(peep());
            advance();
        }
            TokenType type = keywords.getOrDefault(identifier.toString(), TokenType.IDENTIFIER);
            addToken(type, identifier.toString());
        /*
         *  whatever is the outcome of this loop above will determine if our identifier is a keyword or not.
         *  we then check our map, if we have it, give it's real identity , otherwise return TokenType.IDENTIFIER.
         */
    }

    private boolean characterIsDigit(char c){

        /* this here, is the equivalent of Character.isDigit(). */
        return c >= '0' && c <= '9';
    }

    private boolean characterIsAlpha(char c){

        /* this here, is the equivalent of Character.isLetter() */

        return (c >= 'a' && c <= 'z')  ||
                (c >= 'A' && c <= 'Z') ||
                c == '_';

    }

    private boolean characterIsAlphaNumeric(char c){
        /* this here, is the equivalent of Character.isLetterOrDigit() */
        return characterIsAlpha(c) || characterIsDigit(c);
    }



}
