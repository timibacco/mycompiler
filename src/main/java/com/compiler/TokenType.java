package com.compiler;


public enum TokenType {

    /* *************************************************
        THIS CATEGORIZES THE TOKENS MY LEXER RECOGNIZES
      **************************************************
       %% It helps the parser understand what kind of token it has scanned. Simple. %%
    */


    // Single-character tokens.
    LEFT_PAREN, RIGHT_PAREN, LEFT_BRACE, RIGHT_BRACE,
    COMMA, DOT, SEMICOLON, SLASH, STAR,

    // One or two character tokens.
    BANG, BANG_EQUAL,
    ASSIGNMENT, EQUAL_EQUAL,
    GREATER, GREATER_EQUAL,
    LESS, LESS_EQUAL,

    // Literals.
    IDENTIFIER, STRING,
    NUMBER, DIVISION,
    MULTIPLY, PLUS, MINUS,


    // Keywords.
    AND, CLASS, PUBLIC, PRIVATE, STATIC, PROTECTED, RECORD, ELSE, FALSE, SWITCH, FUN, FOR, IF, NULL, OR,
    PRINT, RETURN,CHAR, VOID, SUPER, THIS, TRUE, VAR, WHILE,

    // End of the line (null character).
    EOF,

    //Types
    INT, INTEGER_LITERAL, BIG_DECIMAL, LONG,
    // Added tokens
    COMMENT, COMMENT_BLOCK, COMMENT_BLOCK_END, COMMENT_BLOCK_START, COMMENT_LINE, WHITESPACE
}
