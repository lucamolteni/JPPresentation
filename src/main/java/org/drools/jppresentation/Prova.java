package org.drools.jppresentation;

import java.io.InputStream;
import java.util.List;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.type.ClassOrInterfaceType;

import static com.github.javaparser.ast.Modifier.publicModifier;
import static com.github.javaparser.ast.NodeList.nodeList;

public class Prova {

    ExampleJShell exampleJShell = new ExampleJShell();

    public static void main(String[] args) {
        new Prova().run();
    }

    private void run() {
//        exampleJShell.eval(generateGreetingUsingJP(true));
//        exampleJShell.eval("greeting(\"Luca\")");
//
//        exampleJShell.eval(generateSumMethodJP("double"));
//        exampleJShell.eval("sum(new double[] {1, 2, 3})");


        exampleJShell.eval(templateFoo("Luca"));
        exampleJShell.eval("new Foo().bar()");

    }

    public String templateFoo(String name) {
        InputStream resourceAsStream = this.getClass().getResourceAsStream("/Foo.java");
        CompilationUnit parse = StaticJavaParser.parse(resourceAsStream);

        List<StringLiteralExpr> nodes = parse.findAll(StringLiteralExpr.class, sl -> sl.toString().equals("\"_____whatever\""));

        nodes.forEach(n -> n.replace(new StringLiteralExpr(name)));

        return parse.toString();
    }

    public String generateSumMethodJP(String numType) {
        String template = "   public NUM_TYPE sum(NUM_TYPE[] ints) {\n" +
                "        NUM_TYPE accumulator = 0;\n" +
                "        for(NUM_TYPE i : ints) {\n" +
                "            accumulator += i;\n" +
                "        }\n" +
                "        return accumulator;\n" +
                "    }";

        BodyDeclaration<?> bodyDeclaration = StaticJavaParser.parseBodyDeclaration(template);

        bodyDeclaration.findAll(ClassOrInterfaceType.class, t -> t.getName().asString().equals("NUM_TYPE"))
                .forEach(t -> t.replace(new ClassOrInterfaceType(null, numType)));

        return bodyDeclaration.toString();

    }

    public String generateGreeting(boolean formal) {
        return "\n" +
                "    public String greeting(String name) {\n" +
                "        return " + (formal ? "\"Good morning \"" : "\"Hello \"") + " + name;\n" +
                "    }";
    }

    public String greeting(String name) {
        return "Hello " + name;
    }

    public int sum(int[] ints) {
        int accumulator = 0;
        for(int i : ints) {
            accumulator += i;
        }
        return accumulator;
    }

    public String generateSumMethod(String numType) {
        return "    public "+numType+" sum("+numType+"[] ints) {\n" +
                "        "+numType+" accumulator = 0;\n" +
                "        for("+numType+" i : ints) {\n" +
                "            accumulator += i;\n" +
                "        }\n" +
                "        return accumulator;\n" +
                "    }";
    }

    public String generateGreetingUsingJP(boolean formal) {

        ClassOrInterfaceType returnedType = new ClassOrInterfaceType(null, "String");
        MethodDeclaration greeting = new MethodDeclaration(nodeList(publicModifier()), returnedType, "greeting")
                .setParameters(nodeList(new Parameter(returnedType, "name")));

        String hello_ = formal ? "Good morning " : "Hello ";
        Expression expr = new BinaryExpr(new StringLiteralExpr(hello_), new NameExpr("name"), BinaryExpr.Operator.PLUS);
        Statement statement = new ReturnStmt(expr);

        greeting.setBody(new BlockStmt(nodeList(statement)));

        return greeting.toString();
    }
}
