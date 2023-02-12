package com.nukem.jobseeker.model.dto.robota;

import lombok.Data;

@Data
public class PaginationJson {
    private int count = 20;
    private int page;
}
