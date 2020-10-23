package at.technikumwien;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class MainApp {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NewsPU");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        NewsRepository newsRepository = new NewsRepository(em);

        // --------------------------------------------------------------------------
        News news = new News(
                "Hello Mars!",
                "Am Planeten Mars wurde Leben entdeckt.",
                LocalDate.now(),
                true,
                new Category(1L,"Wissenschaft"),
                List.of(new Author(Sex.MALE, "Maxi", "Musterkind"))
        );
        news = newsRepository.save(news);
        System.out.println(news);

        // --------------------------------------------------------------------------
        Optional<News> optNews = newsRepository.findById(1);
        optNews.ifPresentOrElse(
                System.out::println,        //alternative with lambda: optNews.ifPresent(news -> System.out.println(news))
                () -> System.out.println("No News found")
        );

        // --------------------------------------------------------------------------
        newsRepository.deleteById(news.getId());

        // --------------------------------------------------------------------------
        newsRepository.findAll()
                .forEach(System.out::println);  //alternative: news-> System.out.println(news)

        // --------------------------------------------------------------------------
        newsRepository.findAllByCategoryId(1)
                .forEach(System.out::println);  //alternative: news-> System.out.println(news)

        // --------------------------------------------------------------------------
        em.getTransaction().commit();   // needed so we get the new category
        em.close();
    }
}
