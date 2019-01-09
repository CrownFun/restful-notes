package pl.filewicz.grzegorz.restful.notes.dao;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataBuilder;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class DefaultNoteHibernateUtil implements NoteHibernateUtil {
    // Create the SessionFactory when you start the application.
    private static final SessionFactory SESSION_FACTORY;

    // Initialize the SessionFactory instance.
    static {
        // Builder configured using the application resource named hibernate.cfg.xml
        StandardServiceRegistryBuilder serviceRegistryBuilder =
                new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml");
        // Build a ServiceRegistry
        StandardServiceRegistry standardRegistry = serviceRegistryBuilder.build();
        // MetadataSource created from ServiceRegistry
        MetadataBuilder metadataBuilder =
                new MetadataSources(standardRegistry).getMetadataBuilder();
        // ORM model as determined from all provided mapping sources
        Metadata metaData = metadataBuilder.build();
        // Building Hibernate SessionFactory
        SESSION_FACTORY = metaData.getSessionFactoryBuilder().build();
    }

    public static SessionFactory getSessionFactory() {
        return SESSION_FACTORY;
    }
}
