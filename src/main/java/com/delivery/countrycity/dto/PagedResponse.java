package com.delivery.countrycity.dto;

import java.util.List;

public record PagedResponse<T>(
        List<T> content,
        int page,
        int size,
        long totalItems,
        int totalPages,
        boolean first,
        boolean last
) {
}
