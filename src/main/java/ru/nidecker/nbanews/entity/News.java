package ru.nidecker.nbanews.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @NotBlank
    private String title;
    private String image;
//    @NotBlank
    private String source;
//    @NotNull
    private String sourceLogo;
//    @NotNull
    private LocalDate newsDate = LocalDate.now();

    private LocalTime newsTime = LocalTime.now().truncatedTo(TimeUnit.MINUTES.toChronoUnit());

    private String editor;
//    @NotBlank
//    @ManyToOne
//    @JsonBackReference
//    private User editor;

    @OneToMany(orphanRemoval = true, mappedBy = "news", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Comment> comments;

    @OneToMany(orphanRemoval = true, mappedBy = "news", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JsonBackReference
    private List<LikeDislike> likeDislikes;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        News news = (News) o;
        return id != null && Objects.equals(id, news.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
