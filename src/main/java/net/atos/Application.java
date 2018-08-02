package net.atos;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.servlet.CamelHttpTransportServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

/**
 * A spring-boot application that includes a Camel route builder to setup the Camel routes
 */
@SpringBootApplication
public class Application extends RouteBuilder {

    // must have a main method spring-boot can run
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void configure() throws Exception {

        rest("/ping").description("User REST service")
        	.consumes(MediaType.APPLICATION_JSON_VALUE)
        	.produces(MediaType.TEXT_PLAIN_VALUE)

	        .get().id("ping").description("heartbeat response")
	        	.to("direct:xslt");
    	
        from("direct:xslt").id("foo")
          .setBody()
             .constant("<foo>bar</foo>")
          .to("xslt:wrapInSoap.xsl")
          .to("mock:xslt");
    }

}
