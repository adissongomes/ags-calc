package br.com.weblaje;

import br.com.weblaje.table.*;
import org.apache.catalina.startup.Tomcat;

import java.io.File;
import java.io.IOException;

public class Startup {
    private static final int PORT = 8080;

    public static void main(String[] args) throws Exception {
        // tables
        KMDValues.getInstance();
        MarcusValues.getInstance();
        AreaAcoValues.getInstance();
        ASMinValues.getInstance();
        FlechaValues.getInstance();

        // server
        String appBase = ".";
        Tomcat tomcat = new Tomcat();
        tomcat.setBaseDir(createTempDir());
        tomcat.setPort(Integer.valueOf(System.getProperty("server.port", "8080")));
        tomcat.addWebapp("", new File("src/main/webapp").getAbsolutePath());
        tomcat.start();
        tomcat.getServer().await();
    }

    // based on AbstractEmbeddedServletContainerFactory
    private static String createTempDir() {
        try {
            File tempDir = File.createTempFile("tomcat.", "." + PORT);
            tempDir.delete();
            tempDir.mkdir();
            tempDir.deleteOnExit();
            return tempDir.getAbsolutePath();
        } catch (IOException ex) {
            throw new RuntimeException(
                    "Unable to create tempDir. java.io.tmpdir is set to " + System.getProperty("java.io.tmpdir"),
                    ex
            );
        }
    }
}
