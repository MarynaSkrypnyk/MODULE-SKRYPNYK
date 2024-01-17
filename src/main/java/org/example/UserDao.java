package org.example;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.TypedQuery;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;

import java.time.LocalDateTime;
import java.util.*;

import static org.example.Connection.getSession;
 public class UserDao implements Dao<User> {
     @Override
     public User save(User entity) {
         try (Session session = getSession()) {
             session.beginTransaction();
             session.save(entity);
             session.getTransaction().commit();
         }
         return entity;
     }

     @Override
     public User update(User entity) {
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
             User nativeQuery = session.byId(User.class).load(id);
             session.delete(nativeQuery);

             session.getTransaction().commit();
         }
     }

     @Override
     public User get(long id) {
         String sql = "SELECT * from users where id = :id";
         try (Session session = getSession()) {
             session.beginTransaction();
             NativeQuery nativeQuery = session.createNativeQuery(sql).addEntity(User.class);
             nativeQuery.setParameter("id", id);
             User users = (User) nativeQuery.getSingleResult();
             session.getTransaction().commit();
             return users;
         }
     }

     @Override
     public List<User> getAll() {
         String sql = "SELECT * from users";
         try (Session session = getSession()) {
             session.beginTransaction();
             NativeQuery nativeQuery = session.createNativeQuery(sql).addEntity(User.class);
             List<User> users = nativeQuery.list();
             session.getTransaction().commit();
             return users;
         }
     }

     public List<User> getAccount(long id) {
         String sql = "SELECT accounts from User  where id = :id";
         try (Session session = getSession()) {
             session.beginTransaction();
             EntityGraph<Account> graph = session.createEntityGraph(Account.class);
             graph.addAttributeNodes("user");
             graph.addAttributeNodes("operations");
             TypedQuery<User> a = session.createQuery(sql, User.class);
             a.setHint("javax.persistence.fetchgraph", graph);
             a.setParameter("id", id);
             List<User> users = a.getResultList();
             session.getTransaction().commit();
             return users;
         }
     }
 }
