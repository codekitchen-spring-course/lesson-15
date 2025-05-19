package ru.codekitchen;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.codekitchen.config.HibernateConfig;
import ru.codekitchen.entity.Address;
import ru.codekitchen.entity.Student;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class HibernateExample6CorrectRemove {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(HibernateConfig.class);

        EntityManagerFactory entityManagerFactory = context.getBean(EntityManagerFactory.class);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();

            // Пример правильного удаления записи студента, когда мы сначала разрываем связь между студентом и адресом
            Student student = entityManager.find(Student.class, 13);
            Address address = student.getAddress();
            address.setStudent(null);
            entityManager.merge(address);
            entityManager.remove(student);

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
