package ru.nidecker.nbanews.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class LikeDislike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn
    @JsonIgnoreProperties(value = {"title", "image", "source", "sourceLogo"})
    private News news;

    @NotNull
    @ManyToOne
    @JoinColumn
    @JsonIgnoreProperties(value = {"password", "roles"})
    @Cascade(org.hibernate.annotations.CascadeType.MERGE)
    private User user;

    private int likeType = 0;

    public LikeDislike(News news, User user) {
        this.news = news;
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        LikeDislike that = (LikeDislike) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
