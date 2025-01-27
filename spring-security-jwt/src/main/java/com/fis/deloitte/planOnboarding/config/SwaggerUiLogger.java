//package com.fis.deloitte.planOnboarding.config;
//
//import jakarta.annotation.PostConstruct;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//@Component
//public class SwaggerUiLogger {
//
//    @Value("${server.port:8084}")
//    private String serverPort;
//
//    @Value("${server.servlet.context-path:/}")
//    private String contextPath;
//
//    @Value("${springdoc.swagger-ui.path:/swagger-ui}")
//    private String swaggerPath;
//
//    @PostConstruct
//    public void logSwaggerUiUrl() {
//        String basePath=contextPath.equals("/") ? "" : contextPath;
//        System.out.println("--------------------");
//        System.out.println("Swagger ui is available at: http://localhost:"+serverPort+basePath+swaggerPath+"/index.html");
//        System.out.println("--------------------");
//    }
//}
