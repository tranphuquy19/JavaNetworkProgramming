package tk.doraneko.commons;

import tk.doraneko.app.Classs;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * @author tranphuquy19@gmail.com
 * @since 24/08/2019
 */
public class JavaProcess {
    public static int exec(Class klass) throws IOException,
            InterruptedException {
        String javaHome = System.getProperty("java.home");
        String javaBin = javaHome +
                File.separator + "bin" +
                File.separator + "java";
        String classpath = System.getProperty("java.class.path");
        String className = klass.getName();

        ProcessBuilder builder = new ProcessBuilder(
                javaBin, "-cp", classpath, className);

        Process process = builder.inheritIO().start();
        process.waitFor();
        return process.exitValue();
    }

    public static void openCode(int classIndex) throws IOException{
        try {
            Desktop.getDesktop().browse(new URL(Classs.findByIndex(classIndex).getGitCode()).toURI());
        } catch (URISyntaxException e) {
        }
    }
}
