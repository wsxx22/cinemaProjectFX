package cinemaprojectfx.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.service.ServiceRegistry;

import java.sql.SQLIntegrityConstraintViolationException;
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

    public boolean registerNewClient (String username, String password, String email) {

        session = sessionFactory.openSession();

        if (!Optional.ofNullable(session.createCriteria(User.class).add(Restrictions.allEq(Map.ofEntries(
                Map.entry("username", username),
                Map.entry("email", email)
        ))).uniqueResult()).isPresent()) {
            session.beginTransaction();

            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setEmail(email);

            session.save(user);
            session.getTransaction().commit();
            session.close();
            return true;
        } else {
            session.close();
            return false;
        }

    }
}
