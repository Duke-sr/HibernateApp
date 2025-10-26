package com.duke.hibernate.dao;

import com.duke.hibernate.entity.User;
import com.duke.hibernate.util.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class UserDaoImpl implements UserDao {

    @Override
    public void create(User user) {
        Transaction tx = null;
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(user);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            log.info("Пользователь не добавлен: {}", e.getMessage());
        }
    }

    @Override
    public User get(Long id) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            return session.find(User.class, id);
        } catch (Exception e) {
            log.info("Пользователь не найден: {}", e.getMessage());
            return null;
        }
    }

    @Override
    public void update(User user) {
        Transaction tx = null;
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            User persistentUser = session.find(User.class, user.getId());
            if (persistentUser != null) {
                session.merge(user);
                tx.commit();
            } else {
                log.info("Пользователь с ID {} не найден", user.getId());
            }
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            log.info("Пользователь не обновлен{}", e.getMessage());
        }
    }

    @Override
    public void delete(Long id) {
        Transaction tx = null;
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            User persistentUser = session.find(User.class, id);
            if (persistentUser != null) {
                session.remove(persistentUser);
            } else {
                log.info("Пользователь с ID {} не найден", id);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            log.info("Пользователь не удален{}", e.getMessage());
        }
    }
}