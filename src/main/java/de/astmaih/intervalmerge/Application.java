package de.astmaih.intervalmerge;

import javax.faces.webapp.FacesServlet;
import javax.servlet.ServletContext;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

/**
 * Application class for starting the Spring boot Application
 * including the registration for JSF
 *
 */
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	ServletRegistrationBean<FacesServlet> jsfServletRegistration(ServletContext servletContext) {
		// spring boot only works if this is set
		// FacesServlet registration
		ServletRegistrationBean<FacesServlet> srb = new ServletRegistrationBean<>(new FacesServlet(), "*.xhtml");
		srb.setLoadOnStartup(1);
		return srb;
	}
}
