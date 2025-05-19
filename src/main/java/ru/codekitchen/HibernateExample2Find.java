package ru.codekitchen;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.codekitchen.config.HibernateConfig;
import ru.codekitchen.entity.Address;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class HibernateExample2Find {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(HibernateConfig.class);

        EntityManagerFactory entityManagerFactory = context.getBean(EntityManagerFactory.class);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();

            // Пример извлечения данных о родительской сущности (Student) из зависимой сущности (Address)
            // Это возможно благодаря unidirectional (однонаправленной) связи между сущностями
            Address address = entityManager.find(Address.class, 1);
            System.out.println(address);
            System.out.println(address.getStudent());

            entityManager.getTransaction().commit();
        } catch (Exception exception) {
            System.out.println("Exception caught, executing rollback...");
            entityManager.getTransaction().rollback();
        } finally {
            entityManager.close();
        }

        context.close();
    }
}
