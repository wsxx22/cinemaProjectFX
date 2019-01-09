package cinemaprojectfx.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.criterion.Restrictions;

import java.util.Map;
import java.util.Optional;

public class Database {

    public static Database instance;

    private Session session;
    private SessionFactory sessionFactory;

    private Database() {
        var serviceRegistry = new StandardServiceRegistryBuilder()
                .configure("hibernate.cfg.xml").build();

        try {
            // klasa Metadata przechowuje konfiguracje
            var metadata = new MetadataSources(serviceRegistry).getMetadataBuilder().build();
            sessionFactory = metadata.buildSessionFactory();

        } catch (Exception e) {
            StandardServiceRegistryBuilder.destroy(serviceRegistry);
        }
    }

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }

        return instance;
    }

    public Optional<User> login(String username, String password) {
        try {
            session = sessionFactory.openSession();

            var builder = session.getCriteriaBuilder();
            var query = builder.createQuery(User.class);
            var root = query.from(User.class);

            query.select(root).where(
                    builder.and(
                            builder.equal(root.get("username"), username),
                            builder.equal(root.get("password"), password)
                    )
            );

            return session.createQuery(query).uniqueResultOptional();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return Optional.empty();
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

        User user = session.get(User.class,4);
        user.setEmail(email);
        session.update(user);
        session.close();


//        session.save(user);
//        session.evict(user);
//        session.update(user);
//        session.close();

    }


}
