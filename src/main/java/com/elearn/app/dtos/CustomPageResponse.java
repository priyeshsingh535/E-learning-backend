package com.elearn.app.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CustomPageResponse<T> {
    private int pageSize;
    private int pageNumber;

    private long totalElement;
    private int totalPages;
    private boolean isLast;
    private List<T> content;
}
