package cinemaprojectfx.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.hibernate.service.ServiceRegistry;

import java.util.Map;
import java.util.Optional;

public class Database {

    public static Database instance;

    private Session session;
    private SessionFactory sessionFactory;

    private Database() {
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
    }

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }

        return instance;
    }

    public boolean login(String username, String password) {
        try {
            session = sessionFactory.openSession();

            return Optional.ofNullable(session.createCriteria(User.class).add(Restrictions.allEq(Map.ofEntries(
                    Map.entry("username", username),
                    Map.entry("password", password)
            ))).uniqueResult()).isPresent();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return false;
    }
}
