package org.example.frontcontroller;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;


public class FrontControllerWebServer {

    private static final Logger log = LoggerFactory.getLogger(FrontControllerWebServer.class);
    public static void main(String[] args) throws LifecycleException {

        String webappDirLocation = "webapps/";
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);
        tomcat.addWebapp("/", new File(webappDirLocation).getAbsolutePath());
        log.info("configuration app with basedir : {} ", new File("./" + webappDirLocation).getAbsolutePath());

        tomcat.start();
        tomcat.getServer().await();


    }
}