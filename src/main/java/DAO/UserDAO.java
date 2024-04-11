/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import entity.Customer;
import entity.UserAccount;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 *
 * @author Admin
 */
public class UserDAO {

    private final SessionFactory sessionFactory;
    UserAccount userAccount = null;

    public UserDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<UserAccount> GetAllData() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from UserAccount", UserAccount.class).list();
        }
    }

    public void AddUserAccount(UserAccount userAccount) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.save(userAccount);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public void updateUserAccount(UserAccount userAccount) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.update(userAccount);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public UserAccount byUserAccountid(int UserAccountid) {
        Session session = null;
        Transaction transaction = null;
        UserAccount userAccount = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            String sql = "FROM UserAccount WHERE id = :UserAccountid";
            Query query = session.createQuery(sql);
            query.setParameter("UserAccountid", UserAccountid);
            userAccount = (UserAccount) query.getSingleResult();
        } catch (Exception e) {

            if (transaction != null) {
                transaction.rollback();
                e.printStackTrace();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return userAccount;
    }
 public UserAccount getUserByUsername(String username) {
        try (Session session = sessionFactory.openSession()) {
            String sql = "FROM UserAccount WHERE username = :username";
            Query<UserAccount> query = session.createQuery(sql, UserAccount.class);
            query.setParameter("username", username);
            return query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void bydeleteUserAccountid(int UserAccountid) {
        Session session = null;
        Transaction transaction = null;

        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
//            String sql = "FROM UserAccount WHERE id = :UserAccountid";
//            Query query = session.createQuery(sql);
//            query.setParameter("UserAccountid", UserAccountid);
//            userAccount = (UserAccount) query.getSingleResult();
            UserAccount userAccount = session.get(UserAccount.class, UserAccountid);
            if (userAccount != null) {
                session.delete(userAccount);
            }
            transaction.commit();
        } catch (Exception e) {

            if (transaction != null) {
                transaction.rollback();
                e.printStackTrace();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }

    }
      public UserAccount getUserById(int userId) {
    UserDAO userDAOs = new UserDAO(sessionFactory); 
    return userDAOs.byUserAccountid(userId); 
}

    
}
