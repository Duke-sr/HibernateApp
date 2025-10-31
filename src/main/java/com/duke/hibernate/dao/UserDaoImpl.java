package com.duke.hibernate.dao;

import com.duke.hibernate.entity.UserEntity;
import com.duke.hibernate.util.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserDaoImpl implements UserDao {

    @Override
    public void create(UserEntity userEntity) {
        Transaction transaction = null;
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(userEntity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            log.info("Пользователь не добавлен: {}", e.getMessage());
        }
    }

    @Override
    public UserEntity get(Long id) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            return session.find(UserEntity.class, id);
        } catch (Exception e) {
            log.info("Пользователь не найден: {}", e.getMessage());
            return null;
        }
    }

    @Override
    public void update(UserEntity userEntity) {
        Transaction transaction = null;
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            UserEntity persistentUserEntity = session.find(UserEntity.class, userEntity.getId());
            if (persistentUserEntity != null) {
                session.merge(userEntity);
                transaction.commit();
            } else {
                log.info("Пользователь с ID {} не найден", userEntity.getId());
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            log.info("Пользователь не обновлен{}", e.getMessage());
        }
    }

    @Override
    public void delete(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            UserEntity persistentUserEntity = session.find(UserEntity.class, id);
            if (persistentUserEntity != null) {
                session.remove(persistentUserEntity);
            } else {
                log.info("Пользователь с ID {} не найден", id);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            log.info("Пользователь не удален{}", e.getMessage());
        }
    }
}