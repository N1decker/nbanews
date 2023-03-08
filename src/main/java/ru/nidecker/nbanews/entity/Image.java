package ru.nidecker.nbanews.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Image {

    @Id
    @SequenceGenerator(name = "image_seq", sequenceName = "image_id_seq", initialValue = 2, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "image_id_seq")
    private Long id;

    private String avatar;

    public Image(String avatar) {
        this.avatar = avatar;
    }
}
