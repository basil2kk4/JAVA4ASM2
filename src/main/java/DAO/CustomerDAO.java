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
public class CustomerDAO {

    private final SessionFactory sessionFactory;

    public CustomerDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Customer> GetdataCustomer() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Customer", Customer.class).list();
        }

    }

    public void AddCustomer(Customer customer) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.save(customer);
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
 public Customer getUserByEmail(String Email) {
        try (Session session = sessionFactory.openSession()) {
            String sql = "FROM Customer WHERE email = :Email";
            Query<Customer> query = session.createQuery(sql, Customer.class);
            query.setParameter("Emails", Email);
            return query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public void UpdateCustomer(Customer customer) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.update(customer);
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

    public void DeleteCustomer(Customer customerid) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.delete(customerid);

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

    public Customer ByCustomerId(int customerid) {
        Session session = null;
        Transaction transaction = null;
        Customer customer = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            String sql = "From Customer where id = :customerid";
            Query query = session.createQuery(sql);
            query.setParameter("customerid", customerid);
            customer = (Customer) query.getSingleResult();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return customer;
    }
}
