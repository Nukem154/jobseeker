package com.nukem.jobseeker.model.dto.robota;


import lombok.Data;

@Data
public class VariablesJson {
    private final PaginationJson pagination = new PaginationJson();
    private final FilterJson filter = new FilterJson();
}


