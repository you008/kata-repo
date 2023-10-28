package com.socgen.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DepositDTO {

    private Long id;

    private Long amount;

    private Date date;

    private String description;

    private Long balance = 0L;

}
