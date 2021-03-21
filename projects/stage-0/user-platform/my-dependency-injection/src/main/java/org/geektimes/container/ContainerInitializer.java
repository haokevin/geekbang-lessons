package org.geektimes.container;

import org.geektimes.filter.CharsetEncodingFilter;

import javax.servlet.*;
import java.util.*;

/**
 * @see ServletContainerInitializer
 */
public class ContainerInitializer implements ServletContainerInitializer {
    @Override
    public void onStartup(Set<Class<?>> c, ServletContext ctx) throws ServletException {
        //增加Listener
        for (ServletContextListener servletContextListener : ServiceLoader.load(ServletContextListener.class, this.getClass().getClassLoader())) {
            ctx.addListener(servletContextListener);
        }

        //增加filter
        for (Filter filter : ServiceLoader.load(Filter.class, this.getClass().getClassLoader())) {
            ctx.addFilter(filter.getClass().getSimpleName(), filter);
        }

        //设置servletContext 参数
        ctx.setInitParameter("application.name","user-web");
    }

}
