package ru.codekitchen;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.codekitchen.config.HibernateConfig;
import ru.codekitchen.entity.Student;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class HibernateExample3Find {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(HibernateConfig.class);

        EntityManagerFactory entityManagerFactory = context.getBean(EntityManagerFactory.class);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();

            // Пример извлечения данных о зависимой сущности (Address) из родительской сущности (Student)
            // Это возможно благодаря bidirectional (двунаправленной) связи между сущностями
            Student student = entityManager.find(Student.class, 11);
            System.out.println(student);
            System.out.println(student.getAddress());

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
