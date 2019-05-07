package org.drools.jppresentation;

import java.io.IOException;
import java.nio.charset.Charset;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.type.ClassOrInterfaceType;

import static com.github.javaparser.ast.Modifier.publicModifier;
import static com.github.javaparser.ast.NodeList.nodeList;

public class Example {

    ExampleJShell exampleJShell = new ExampleJShell();

    public static void main(String[] args) throws Exception {
        System.out.println();
        new Example().run();
        System.out.println();
    }

    private void run() throws Exception {

        exampleJShell.eval(generateSumMethodJP("long"));
        exampleJShell.eval("new Foo().sum(new long[] { 2,3, 4})");
    }

    public int sum(int[] numbers) {
        int acc = 0;
        for (int i : numbers) {
            acc += i;
        }
        return acc;
    }

    public String generateSumMethod(String numType) {
        return "public " + numType + " sum(" + numType + "[] numbers) {\n" +
                "        " + numType + " acc = 0;\n" +
                "        for(" + numType + " i : numbers) {\n" +
                "            acc += i;\n" +
                "        }\n" +
                "        return acc;\n" +
                "    }\n";
    }

    public String generateSumMethodJP(String numType) throws IOException {

        CompilationUnit bodyDeclaration =
                StaticJavaParser.parseResource(this.getClass().getClassLoader(), "Foo.java", Charset.defaultCharset());

        bodyDeclaration.findAll(ClassOrInterfaceType.class)
                .forEach(t -> t.replace(new ClassOrInterfaceType(null, numType)));

        return bodyDeclaration.toString();

    }
}
