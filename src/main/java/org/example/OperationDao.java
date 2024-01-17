package org.example;

import jakarta.persistence.Enumerated;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;

import java.time.LocalDateTime;
import java.util.List;

import static org.example.Connection.getSession;

public class OperationDao implements Dao <Operation> {
    @Override
    public Operation save(Operation entity) {
        try {
            if (entity.getAmount() <= 0) {
                throw new InvalidDataException("Must be some amount");
            } else if (entity.getCategory() == null) {
                throw new InvalidDataException("Must be some Category");
            }
            try (Session session = getSession()) {
                session.beginTransaction();
                session.save(entity);
                session.getTransaction().commit();
                return entity;
            } catch (InvalidDataException exception) {
                exception.printStackTrace();
                System.out.println(exception.getMessage());
            }
        } finally {
        }
        return entity;
    }

    @Override
    public Operation update(Operation entity) {
        try (Session session = getSession()) {
            session.beginTransaction();
            session.update(entity);
            session.getTransaction().commit();
        }
        return entity;
    }

    @Override
    public void delete(long id) {
        try (Session session = getSession()) {
            session.beginTransaction();
            Operation operation = session.get(Operation.class,id);
            session.delete(operation);
            session.getTransaction().commit();
        }
    }
    @Override
    public Operation get(long id) {
        String sql = "SELECT * from operation where id = :id";
        try (Session session = getSession()) {
            session.beginTransaction();
            NativeQuery nativeQuery = session.createNativeQuery(sql).addEntity(Operation.class);
            nativeQuery.setParameter("id", id);
            Operation operation = (Operation) nativeQuery.getSingleResult();
            session.getTransaction().commit();
            return operation;
        }
    }

    @Override
    public List<Operation> getAll() {
        String sql = "SELECT * from operation";
        try (Session session = getSession()) {
            session.beginTransaction();
            NativeQuery nativeQuery = session.createNativeQuery(sql).addEntity(Operation.class);
            List<Operation> operations = nativeQuery.list();
            session.getTransaction().commit();
            return operations;
        }
    }
    public List<Operation> findOrders(long id,LocalDateTime firstDate,LocalDateTime lastDate) {
        try (Session session = getSession()) {
            session.beginTransaction();
            List<Operation> selectUsers = session.createQuery("select o from Operation o WHERE (account.id = :id) and o.createdAt between: firstDate and: lastDate", Operation.class)
                    .setParameter("id", id)
                    .setParameter("firstDate", firstDate)
                    .setParameter("lastDate", lastDate)
                    .list();
            session.getTransaction().commit();
            return selectUsers;
        }
    }
    public List<Operation> sortForAmount(long id) {
        try (Session session = getSession()) {
            session.beginTransaction();
            List<Operation> selectFromOperations =
                    session.createQuery("select MAX(amount)from Operation o WHERE (account.id = :id)", Operation.class)
                    .setParameter("id", id)
                    .list();
            session.getTransaction().commit();
            return selectFromOperations;
        }
    }
    public List<Operation> sortCategory (long id) {
        try (Session session = getSession()) {
            session.beginTransaction();
            List<Operation> selectFromOperations =
                    session.createQuery("select name from Operation  WHERE (account.id = :id) ", Operation.class)
                            .setParameter("id", id)
                            .list();
            session.getTransaction().commit();
            return selectFromOperations;
        }
    }
    public List<Operation> getSumMoneyForCategory(long id, Enum category) {
        try (Session session = getSession()) {
            session.beginTransaction();
            List<Operation> selectFromOperations =
                    session.createQuery("select sum(amount) from Operation where category = :category and account.id = :id", Operation.class)
                            .setParameter("id", id)
                            .setParameter("category",category)
                            .list();
            session.getTransaction().commit();
            return selectFromOperations;
        }
    }
}
