package com.fm.smartlearningplatform.course.controller;

import com.fm.smartlearningplatform.course.dto.currency.request.CreateCurrencyRequest;
import com.fm.smartlearningplatform.course.dto.currency.request.UpdateCurrencyRequest;
import com.fm.smartlearningplatform.course.dto.currency.response.CurrencyResponse;
import com.fm.smartlearningplatform.course.service.CurrencyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/currency")
@RequiredArgsConstructor
public class CurrencyController {

    private final CurrencyService currencyService;

    // ─── Create ───────────────────────────────────────────────

    @PostMapping
    public ResponseEntity<CurrencyResponse> createCurrency(
            @Valid
            @RequestBody
            CreateCurrencyRequest request
    ) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        currencyService.create(request)
                );
    }

    // ─── Find ─────────────────────────────────────────────────

    @GetMapping("/{id}")
    public ResponseEntity<CurrencyResponse>
    getCurrencyById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                currencyService.findById(id)
        );
    }

    @GetMapping
    public ResponseEntity<Page<CurrencyResponse>>
    getCurrencies(
            @RequestParam(
                    value = "q",
                    required = false
            )
            String keyword,

            @PageableDefault(
                    size = 10,
                    sort = "name"
            )
            Pageable pageable
    ) {

        return ResponseEntity.ok(
                currencyService.findAll(
                        keyword,
                        pageable
                )
        );
    }

    // ─── Update ───────────────────────────────────────────────

    @PutMapping("/{id}")
    public ResponseEntity<CurrencyResponse>
    updateCurrencyById(
            @PathVariable Long id,

            @Valid
            @RequestBody
            UpdateCurrencyRequest request
    ) {

        return ResponseEntity.ok(
                currencyService.update(
                        id,
                        request
                )
        );
    }

    // ─── Delete ───────────────────────────────────────────────

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>
    deleteCurrencyById(
            @PathVariable Long id
    ) {

        currencyService.delete(id);

        return ResponseEntity.noContent().build();
    }
}