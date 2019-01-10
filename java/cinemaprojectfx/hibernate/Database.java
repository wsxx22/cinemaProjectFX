package cinemaprojectfx.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.criterion.CriteriaQuery;
import org.hibernate.criterion.Restrictions;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Root;
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

    // TODO: 08.01.2019 zmienic na nowego hibernate'a
    public boolean register(String username, String password, String email) {
        try {
            session = sessionFactory.openSession();

            var builder = session.getCriteriaBuilder();
            var query = builder.createQuery(User.class);
            var root = query.from(User.class);

            query.select(root).where(builder.and(
                    builder.equal(root.get("username"), username),
                    builder.equal(root.get("email"), email)
                    )
            );

            Optional<User> optionalUser = session.createQuery(query).uniqueResultOptional();

//            Optional<Object> optionalUser = Optional.ofNullable(session.createCriteria(User.class)
//                    .add(Restrictions.or(Restrictions.eq("username", username),
//                            Restrictions.eq("email", email))).uniqueResult());

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

            var builder = session.getCriteriaBuilder();
            var query = builder.createQuery(User.class);
            var root = query.from(User.class);

            query.select(root).where(builder.equal(root.get("email"), email));

            Optional<User> optionalUser = session.createQuery(query).uniqueResultOptional();

            if (optionalUser.isPresent())
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


    }


}
