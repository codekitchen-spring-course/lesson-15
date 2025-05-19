package ru.codekitchen;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.codekitchen.config.HibernateConfig;
import ru.codekitchen.entity.Student;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class HibernateExample5NotCorrectRemove {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(HibernateConfig.class);

        EntityManagerFactory entityManagerFactory = context.getBean(EntityManagerFactory.class);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();

            // Пример НЕправильного удаления записи студента, когда нарушается constraint по внешнему ключу
            // Так как запись адреса будет ссылаться на несуществующего студента, то при удалении случится ОШИБКА
            // ВАЖНО: ошибка случится, если НЕ настроен каскад на удаление!
            Student student = entityManager.find(Student.class, 13);
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
