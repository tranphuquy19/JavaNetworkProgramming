package tk.doraneko.commons;

import java.io.File;
import java.io.IOException;

/**
 * Created by @tranphuquy19 on 24/08/2019
 * Email:       tranphuquy19@gmail.com
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
}
