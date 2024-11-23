package com.compiler;

public record Token(

        TokenType type,

        String lexeme,

        int line
        )
{

    /* **************************************************************************************
        THIS CLASS DEFINES THE TOKENS LEXER PRODUCES AFTER SCANNING, TO BE USED BY THE PARSER
       ****************************************************************************************

       <> A lexeme is a sequence of characters of a program that is grouped together
        >> as a single unit in the process of scanning.

       <> The compiler breaks the source code down into smaller units called "lexemes."
        >> These lexemes will help the compiler to analyze and process the program efficiently

     */

    @Override
    public String toString() {
        return
                String.format("Token < %s , %s, line %d >", type, lexeme, line);
    }
}

