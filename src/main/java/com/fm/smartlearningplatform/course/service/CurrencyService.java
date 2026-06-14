package com.fm.smartlearningplatform.course.service;

import com.fm.smartlearningplatform.course.dto.currency.request.CreateCurrencyRequest;
import com.fm.smartlearningplatform.course.dto.currency.request.UpdateCurrencyRequest;
import com.fm.smartlearningplatform.course.dto.currency.response.CurrencyResponse;
import com.fm.smartlearningplatform.course.mapper.CurrencyMapper;
import com.fm.smartlearningplatform.course.model.Currency;
import com.fm.smartlearningplatform.course.repository.CurrencyRepository;
import com.fm.smartlearningplatform.exceptionhandler.exception.DuplicateResourceException;
import com.fm.smartlearningplatform.exceptionhandler.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CurrencyService {

    private final CurrencyRepository currencyRepository;
    private final CurrencyMapper currencyMapper;

    // ─── Create ───────────────────────────────────────────────

    @Transactional
    public CurrencyResponse create(
            CreateCurrencyRequest request
    ) {

        validateCurrencyNameNotExist(request.name());
        validateCurrencyCodeNotExist(request.code());

        Currency currency = currencyMapper.toEntity(request);

        return currencyMapper.toResponse(
                currencyRepository.save(currency)
        );
    }

    // ─── Find ─────────────────────────────────────────────────

    public CurrencyResponse findById(Long currencyId) {

        return currencyMapper.toResponse(
                getCurrency(currencyId)
        );
    }

    public Page<CurrencyResponse> findAll(
            String keyword,
            Pageable pageable
    ) {

        Page<Currency> currencies;

        if (keyword == null || keyword.isBlank()) {

            currencies = currencyRepository
                    .findAll(pageable);

        } else {

            keyword = keyword.trim();

            currencies = currencyRepository
                    .findByNameContainingIgnoreCaseOrCodeContainingIgnoreCase(
                            keyword,
                            keyword,
                            pageable
                    );
        }

        return currencies.map(currencyMapper::toResponse);
    }

    // ─── Update ───────────────────────────────────────────────

    @Transactional
    public CurrencyResponse update(
            Long currencyId,
            UpdateCurrencyRequest request
    ) {

        Currency currency = getCurrency(currencyId);

        if (
                request.name() != null
                        && currencyRepository
                        .existsByIdNotAndName(
                                currencyId,
                                request.name()
                        )
        ) {

            throw new DuplicateResourceException(
                    "Currency name already exists."
            );
        }

        if (
                request.code() != null
                        && currencyRepository
                        .existsByIdNotAndCode(
                                currencyId,
                                request.code()
                        )
        ) {

            throw new DuplicateResourceException(
                    "Currency code already exists."
            );
        }

        currencyMapper.update(request, currency);

        return currencyMapper.toResponse(
                currencyRepository.save(currency)
        );
    }

    // ─── Delete ───────────────────────────────────────────────

    @Transactional
    public void delete(Long currencyId) {

        Currency currency = getCurrency(currencyId);

        currencyRepository.delete(currency);
    }

    // ─── Exists ───────────────────────────────────────────────

    public boolean existsById(Long currencyId) {

        return currencyRepository.existsById(currencyId);
    }

    // ─── Helper ───────────────────────────────────────────────

    private Currency getCurrency(Long currencyId) {

        return currencyRepository.findById(currencyId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Currency not found."
                        )
                );
    }

    private void validateCurrencyNameNotExist(
            String name
    ) {

        if (currencyRepository.existsByName(name)) {

            throw new DuplicateResourceException(
                    "Currency name already exists."
            );
        }
    }

    private void validateCurrencyCodeNotExist(
            String code
    ) {

        if (currencyRepository.existsByCode(code)) {

            throw new DuplicateResourceException(
                    "Currency code already exists."
            );
        }
    }
}