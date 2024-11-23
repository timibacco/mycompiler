package com.compilerTest.Lexer;

import com.compiler.Lexer;
import com.compiler.Token;
import com.compiler.TokenType;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class LexerTest {

    @Test
    public void testSimpleExpression() {
        Lexer lexer = new Lexer("int a = 5 + 3;");
        List<Token> tokens = lexer.scan();



        assertEquals(7, tokens.size());

        assertEquals(TokenType.INT, tokens.get(0).type());
        assertEquals(TokenType.IDENTIFIER, tokens.get(1).type());
        assertEquals(TokenType.ASSIGNMENT, tokens.get(2).type());
        assertEquals(TokenType.EOF, tokens.getLast().type());
    }
}
