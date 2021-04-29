package com.example.springbootsecurity.config;

import com.example.springbootsecurity.SpringBootSecurityApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.OrderComparator;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.SecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;

import javax.servlet.Filter;
import java.lang.reflect.Field;
import java.util.List;

@Configuration(proxyBeanMethods = false)
public class MyWebSecurityConfiguration extends WebSecurityConfiguration {


    @Override
    @Autowired(required = false)
    public void setFilterChainProxySecurityConfigurer(
            ObjectPostProcessor<Object> objectPostProcessor,
            @Value("#{@autowiredWebSecurityConfigurersIgnoreParents.getWebSecurityConfigurers()}") List<SecurityConfigurer<Filter, WebSecurity>> webSecurityConfigurers)
            throws Exception {
        super.setFilterChainProxySecurityConfigurer(objectPostProcessor, webSecurityConfigurers);
        webSecurityConfigurers.sort(MyAnnotationAwareOrderComparator.INSTANCE);
        Field field = super.getClass().getDeclaredField("webSecurityConfigurers");
    }

    private static class MyAnnotationAwareOrderComparator extends OrderComparator {
        private static final MyAnnotationAwareOrderComparator INSTANCE = new MyAnnotationAwareOrderComparator();

        @Override
        protected int getOrder(Object obj) {
            return lookupOrder(obj);
        }

        private static int lookupOrder(Object obj) {
            if (obj instanceof SpringBootSecurityApplication.FormLoginWebSecurityConfigurerAdapter) {
                return 1;
            }

            if (obj instanceof Ordered) {
                return ((Ordered) obj).getOrder() + 1;
            }
            if (obj != null) {
                Class<?> clazz = (obj instanceof Class ? (Class<?>) obj : obj.getClass());
                Order order = AnnotationUtils.findAnnotation(clazz, Order.class);
                if (order != null) {
                    return order.value() + 1;
                }
            }
            return Ordered.LOWEST_PRECEDENCE;
        }
    }

}
