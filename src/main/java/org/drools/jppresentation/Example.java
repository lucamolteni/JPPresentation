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

    }

}
