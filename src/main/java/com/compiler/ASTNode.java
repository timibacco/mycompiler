package com.compiler;

import java.util.List;

public abstract class ASTNode {

    /** <p> ********************************************************* </p>
       THIS CLASS WILL REPRESENT THE "ABSTRACT SYNTAX TREE".
     <p> ********************************************************* </p>

    <> <p>This class represents the hierarchical syntactic structure of the source program. </p>
    * >> Where each operator is Node, and takes its operands/arguments as its children at that Node.

         int a = 5 + 3;

                 =
               /   \
             <a>    \
                     +
                    /  \
                   /    \
                 <5>    <3>
      * <p> ************************************************************************* </p>
      * */

    public static class Program extends ASTNode{
        final List<ClassDeclaration> classes;

        public Program(List<ClassDeclaration> classes){
            this.classes = classes;
        }
    }



    public static class ClassDeclaration extends ASTNode{

        final String name;

        final List<MethodDeclaration> declaredMethods;

        public ClassDeclaration(String name, List<MethodDeclaration> declaredMethods) {
            this.name = name;
            this.declaredMethods = declaredMethods;
        }
    }



    public static class MethodDeclaration extends ASTNode{

        /**  */

        final String methodName;

        final Object returnType;

        final List<Statement> statements;


        public MethodDeclaration(String methodName, Object returnType, List<Statement> statements) {
            this.methodName = methodName;
            this.returnType = returnType;
            this.statements = statements;
        }
    }


    public static abstract class Statement {}



    public static class ExpressionStatement extends Statement {
        final Expression expression;

        public ExpressionStatement(Expression expression) {
            this.expression = expression;
        }

    }


    public static abstract class Expression extends ASTNode {

        /* This class represents an expression. Comprising operators, literal expression (such as integers) e.t.c */
    }



    public static class BinaryExpression extends Expression {

    /**  This class represents a binary expression. Consisting of an operator with expressions on both sides of the operator. */

        final Expression left;
        final String operator;
        final Expression right;

        public BinaryExpression(Expression left, String operator, Expression right) {
            this.left = left;
            this.operator = operator;
            this.right = right;
        }


        public Expression getLeft(){

            return left;
        }

        public String getOperator(){

            return operator;
        }

        public Expression getRight() {
            return right;
        }

        @Override
        public String toString() {
            return "(" + left + " " + operator + " " + right + ")";
        }
    }


    public static class IntegerLiteral extends Expression {

        /** This class represents an Integer literal. A binary expression could be made up just two integer literal. **/
        private final int value;

        public IntegerLiteral(int value) {
            this.value = value;
        }

        @Override
        public String toString(){
            return Integer.toString(value);
        }
    }




    public static class Identifier extends Expression {

    /** This class represents a variable lexeme in an expression. */

        private final String name;
//        private final String type;

        public Identifier(String name) {
            this.name = name;
//            this.type = type;
        }

        public String getName(){
            return this.name;
        }



        @Override
        public String toString(){
            return name;
        }
    }

    public static class VariableDeclaration extends Expression{

        final String name;

        final String type;
        final Expression initializer ;


        public VariableDeclaration(String name, String type, Expression initializer) {
            this.name = name;
            this.type = type;
            this.initializer = initializer;
        }

        public Expression getInitializer() {
            return initializer;
        }

        public String getType() {
            return type;
        }

        public String getName() {
            return name;
        }
    }





}
