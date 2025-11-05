package com.duke.hibernate.util;

import com.duke.hibernate.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.Properties;

@Slf4j
public class HibernateSessionFactoryUtil {

    private static SessionFactory sessionFactory;

    private HibernateSessionFactoryUtil() {
    }

    public static synchronized void initSessionFactory(Properties overrideProperties) {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            return;
        }

        try {
            Configuration configuration = new Configuration()
                    .configure()
                    .addAnnotatedClass(UserEntity.class);

            if (overrideProperties != null && !overrideProperties.isEmpty()) {
                configuration
                        .getProperties()
                        .putAll(overrideProperties);
            }

            var registry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties())
                    .build();

            sessionFactory = configuration.buildSessionFactory(registry);

        } catch (Exception e) {
            log.error("Ошибка при создании SessionFactory", e);
            throw new ExceptionInInitializerError(e);
        }
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            initSessionFactory(null);
        }
        return sessionFactory;
    }

    public static void shutdown() {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            sessionFactory.close();
            sessionFactory = null;
        }
    }
}