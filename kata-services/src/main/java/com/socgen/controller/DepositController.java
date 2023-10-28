package com.socgen.controller;

import com.socgen.dto.DepositDTO;
import com.socgen.service.DepositService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/deposit")
@CrossOrigin(origins = "*")
@Tag(name = "Deposit", description = "The Deposit Api")
public class DepositController {

    @Autowired
    DepositService depositService;

    @Operation(summary = "Get deposit by dates", description = "Search deposit between two dates")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content =
                    { @Content(mediaType = "application/json", schema =
                    @Schema(implementation = List.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    @GetMapping(produces = "application/json")
    public List<DepositDTO> findDepositByDateBetween(@RequestParam @DateTimeFormat(pattern = "ddMMyyyy") String begin,
                                                     @RequestParam @DateTimeFormat(pattern = "ddMMyyyy") String end) {
        return  depositService.findDepositByDateBetween(begin, end);
    }

    @Operation(summary = "Save deposit on database", description = "Save deposit on database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content =
                    { @Content(mediaType = "application/json", schema =
                    @Schema(implementation = DepositDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    @PostMapping(produces = "application/json", consumes = "application/json")
    public DepositDTO saveDeposit(@RequestBody DepositDTO deposit) {
       return depositService.saveDeposit(deposit);
    }
}
