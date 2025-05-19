package ru.codekitchen;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.codekitchen.config.HibernateConfig;
import ru.codekitchen.entity.Address;
import ru.codekitchen.entity.Student;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class HibernateExample1Persist {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(HibernateConfig.class);

        EntityManagerFactory entityManagerFactory = context.getBean(EntityManagerFactory.class);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();

            // Пример сохранений двух новых записей с указанием связи через зависимую сущность (Address)
            Student student = new Student(20, "Sergeev", "Anton");
            entityManager.persist(student);
            Address address = new Address("Central street", 92, 118);
            address.setStudent(student);
            entityManager.persist(address);

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
