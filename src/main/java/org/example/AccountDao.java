package org.example;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.TypedQuery;
import org.hibernate.Session;
import org.hibernate.graph.RootGraph;
import org.hibernate.query.NativeQuery;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.example.Connection.getSession;
public class AccountDao implements Dao <Account> {
    @Override
    public Account save(Account entity) {
        try (Session session = getSession()) {
            session.beginTransaction();
            session.save(entity);
            session.getTransaction().commit();
        }
        return entity;
    }
    @Override
    public Account update(Account entity) {
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
            Account nativeQuery = session.byId(Account.class).load(id);
            session.delete(nativeQuery);
            session.getTransaction().commit();
        }
    }
    @Override
    public Account get(long id) {
        String sql = "SELECT * from account where id = :id";
        try (Session session = getSession()) {
            session.beginTransaction();
            NativeQuery nativeQuery = session.createNativeQuery(sql).addEntity(Account.class);
            nativeQuery.setParameter("id",id);
            Account account = (Account) nativeQuery.getSingleResult();
            session.getTransaction().commit();
            return account;
        }
    }
    @Override
    public List<Account> getAll() {
        String sql = "SELECT * from Account ";
        try (Session session = getSession()) {
            session.beginTransaction();
            NativeQuery nativeQuery = session.createNativeQuery(sql).addEntity(Account.class);
            List<Account> accounts = nativeQuery.list();
            session.getTransaction().commit();
            return accounts;
            }
    }
    public List<Account> getOperation(long id) {
        String sql = "SELECT operations from Account where id = :id";
        try (Session session = getSession()) {
            session.beginTransaction();
            EntityGraph<Account> graph = session.createEntityGraph(Account.class);
            graph.addAttributeNodes("operations");
            TypedQuery<Account> a = session.createQuery(sql, Account.class);
            a.setHint("javax.persistence.fetchgraph", graph);
            a.setParameter("id",id);
            List<Account> accounts = a.getResultList();
            session.getTransaction().commit();
            return accounts;
        }
    }
    public List<Account> getSumBalance(long id) {
        try (Session session = getSession()) {
            session.beginTransaction();
            List<Account> selectFromOperations =
                    session.createQuery("select sum(balance) from Account where user.id = :id", Account.class)
                    .setParameter("id", id)
                    .list();
            session.getTransaction().commit();
            return selectFromOperations;
        }
    }
    public List<Account> getBalance(long id) {
        try (Session session = getSession()) {
            session.beginTransaction();
            List<Account> selectFromOperations =
                    session.createQuery("select balance from Account where user.id = :id", Account.class)
                            .setParameter("id", id)
                            .list();
            session.getTransaction().commit();
            return selectFromOperations;
        }
    }
}
