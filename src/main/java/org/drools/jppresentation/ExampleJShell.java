package org.drools.jppresentation;

import java.util.List;

import jdk.jshell.JShell;
import jdk.jshell.Snippet.Status;
import jdk.jshell.SnippetEvent;

class ExampleJShell {

    private JShell js = JShell.create();

    public void eval(String input) {
        List<SnippetEvent> events = js.eval(input);
        for (SnippetEvent e : events) {
            StringBuilder sb = new StringBuilder();
            if (e.causeSnippet() == null) {
                //  We have a snippet creation event
                switch (e.status()) {
                    case VALID:
                        sb.append("Successful ");
                        break;
                    case RECOVERABLE_DEFINED:
                        sb.append("With unresolved references ");
                        break;
                    case RECOVERABLE_NOT_DEFINED:
                        sb.append("Possibly reparable, failed  ");
                        break;
                    case REJECTED:
                        sb.append("Failed ");
                        break;
                }
                if (e.previousStatus() == Status.NONEXISTENT) {
                    sb.append("addition");
                } else {
                    sb.append("modification");
                }
                sb.append(" of ");
                sb.append(e.snippet().source());
                System.out.println(sb);
                if (e.value() != null) {
                    System.out.printf("Value is: %s\n", e.value());
                }
                System.out.flush();
            }
        }
    }
}
