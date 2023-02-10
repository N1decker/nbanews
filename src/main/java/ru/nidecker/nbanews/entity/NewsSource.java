package ru.nidecker.nbanews.entity;


import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GeneratorType;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewsSource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    private String image;

    public NewsSource(String name, String image) {
        this.name = name;
        this.image = image;
    }

    public enum SourceName {
        ESPN, ANDSCAPE
    }
}
