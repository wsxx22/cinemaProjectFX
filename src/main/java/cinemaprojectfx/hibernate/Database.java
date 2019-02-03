package cinemaprojectfx.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

public class Database {

    private static Database instance;

    private Session session;
    private SessionFactory sessionFactory;

    private Database() {

        StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .configure("hibernate.cfg.xml").build();

        try {
            // klasa Metadata przechowuje konfiguracje
            var metadata = new MetadataSources(serviceRegistry).getMetadataBuilder().build();
            sessionFactory = metadata.buildSessionFactory();

        } catch (Exception e) {
            e.printStackTrace();
            StandardServiceRegistryBuilder.destroy(serviceRegistry);
        }
    }

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }

        return instance;
    }

    public <T> void updateEntity(T t) {
        try {
            session = sessionFactory.openSession();

            session.getTransaction().begin();

            session.update(t);

            session.getTransaction().commit();

        } catch ( Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public Optional<User> login(String username, String password) {
        try {
            session = sessionFactory.openSession();

            var builder = session.getCriteriaBuilder();
            var query = builder.createQuery(User.class);
            var root = query.from(User.class);

//            CriteriaBuilder builder = session.getCriteriaBuilder();
//            CriteriaQuery<User> query = builder.createQuery(User.class);
//            Root<User> root = query.from(User.class);

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

            var builder = session.getCriteriaBuilder();
            var query = builder.createQuery(User.class);
            var root = query.from(User.class);

            query.select(root).where(
                    builder.and(
                        builder.equal(root.get("username"), username),
                        builder.equal(root.get("email"), email)
                    )
            );

            Optional<User> optionalUser = session.createQuery(query).uniqueResultOptional();

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

    public List<Ticket> getTickets () {

        try {
            session = sessionFactory.openSession();
            var builder = session.getCriteriaBuilder();
            var query = builder.createQuery(Ticket.class);
            var root = query.from(Ticket.class);

            query.select(root);

            return session.createQuery(query).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Seance> getReportoire () {

        try {
            session = sessionFactory.openSession();
            var builder = session.getCriteriaBuilder();
            var query = builder.createQuery(Seance.class);
            var root = query.from(Seance.class);

            query.select(root);

            return session.createQuery(query).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
            close();
        }
        return false;
    }

    public <T> T getEntity(Class<T> type, int id) {

        try {
            session = sessionFactory.openSession();



            return session.get(type, id);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }

        return null;
    }

    public List<TicketType> getTicketTypes () {

        try {
            session = sessionFactory.openSession();

            var builder = session.getCriteriaBuilder();
            var query = builder.createQuery(TicketType.class);
            var root = query.from(TicketType.class);

            query.select(root);

            return session.createQuery(query).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void close () {
        if (session != null)
            session.close();
    }

    public Optional<Room> getRoom (int id) {

        try {
            session = sessionFactory.openSession();

            var builder = session.getCriteriaBuilder();
            CriteriaQuery <Room> query = builder.createQuery(Room.class);
            Root<Room> root = query.from(Room.class);

            query.select(root).where(
                    builder.equal(root.get("id"), id)
            );

            return session.createQuery(query).uniqueResultOptional();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }











}
