package mvc.config;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

/**
 * Created by Mark on 01/04/15.
 */
public class AppInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext container) {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(WebConfig.class, DatabaseConfig.class);
        container.addListener(new ContextLoaderListener(context));
        ServletRegistration.Dynamic registration = container.addServlet("DispatcherServlet", new DispatcherServlet(context));
        registration.setLoadOnStartup(1);
        registration.addMapping("/");

        FilterRegistration.Dynamic corsFilter = container.addFilter("corsFilter", CORSFilter.class);
        corsFilter.addMappingForUrlPatterns(null, false, "/*");
    }
}