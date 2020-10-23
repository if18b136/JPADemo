package at.technikumwien;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

//@ToString
//@EqualsAndHashCode
//@Getter
//@Setter
@Data   //substitutes the above annotations
@NoArgsConstructor
@AllArgsConstructor

@Entity // now gets classified as sql table
@Table(name = "t_news")
@NamedQuery(
        name = "News.selectAll",
        query = "SELECT n FROM News n"
)
@NamedQuery(
        name = "News.selectAllByCategoryId",
        query = "SELECT n FROM News n WHERE n.category.id = :categoryid"    // colon shows the compiler that categoryid is a parameter
)
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //generates an ID automatically
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, length = 1000)
    private String text;

    @Column(nullable = false)
    private LocalDate publicationDate;

    @Column(nullable = false)
    private boolean topNews;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "categoryid")    //prevent a middle table and write it into column instead
    private Category category;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(     //define the middle table
            name = "t_news_author",
            joinColumns = @JoinColumn(name = "newsid"),
            inverseJoinColumns = @JoinColumn(name = "authorid")
    )
    private List<Author> authors;

    public News(String title, String text, LocalDate publicationDate, boolean topNews, Category category, List<Author> authors) {
        this(null,title,text,publicationDate,topNews,category,authors);
    }
}
