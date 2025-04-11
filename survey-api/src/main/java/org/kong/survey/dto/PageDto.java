package org.kong.survey.dto;

import lombok.*;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageDto<T> {
    private List<T> content;
    private int totalPages;
    private long totalElements;

    public PageDto(Page<T> page) {
        this.content = page.getContent();
        this.totalPages = page.getTotalPages();
        this.totalElements = page.getTotalElements();
    }
}
