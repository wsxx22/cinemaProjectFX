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

    public Optional<User> login(String username, String password) {
        Optional<User> optionalUser = Optional.empty();

        try {
            session = sessionFactory.openSession();

            User user = (User) session.createCriteria(User.class).add(Restrictions.allEq(Map.ofEntries(
                    Map.entry("username", username),
                    Map.entry("password", password)
            ))).uniqueResult();

            optionalUser = Optional.ofNullable(user);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return optionalUser;
    }

    public boolean register(String username, String password, String email) {
        try {
            session = sessionFactory.openSession();

            Optional<Object> optionalUser = Optional.ofNullable(session.createCriteria(User.class)
                    .add(Restrictions.or(Restrictions.eq("username", username),
                            Restrictions.eq("email", email))).uniqueResult());


            if (!optionalUser.isPresent()) {
                session.beginTransaction();

                User user = new User(username, password, email);
                session.save(user);

                session.getTransaction().commit();
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return false;
    }

    public boolean isExistEmail (String email) {

        try {
            session = sessionFactory.openSession();

            User user =  (User) session.createCriteria(User.class).add(Restrictions.eq(
                    "email", email)).uniqueResult();

            if (user != null)
                return true;

        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null)
                session.close();
        }
        return false;
    }

    public void changeClientEmail (String email) {

        session = sessionFactory.openSession();
        session.beginTransaction();

        User user = (User) session.get(User.class,4);
        user.setEmail(email);
        session.update(user);
        session.close();


//        session.save(user);
//        session.evict(user);
//        session.update(user);
//        session.close();

    }


}
