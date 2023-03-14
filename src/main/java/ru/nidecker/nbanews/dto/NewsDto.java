package ru.nidecker.nbanews.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NewsDto {

    private String title;
    private String subtitle;
    private String contentAuthor;
    private String imageUrl;
}
