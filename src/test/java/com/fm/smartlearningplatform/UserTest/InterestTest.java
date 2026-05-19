package com.fm.smartlearningplatform.UserTest;

import com.fm.smartlearningplatform.dto.user.interest.request.CreateInterestRequest;
import com.fm.smartlearningplatform.dto.user.interest.request.UpdateInterestRequest;
import com.fm.smartlearningplatform.dto.user.interest.response.InterestResponse;
import com.fm.smartlearningplatform.exception.DuplicateResourceException;
import com.fm.smartlearningplatform.exception.ResourceNotFoundException;
import com.fm.smartlearningplatform.mapper.user.InterestMapper;
import com.fm.smartlearningplatform.model.user.Interest;
import com.fm.smartlearningplatform.repository.user.InterestRepository;
import com.fm.smartlearningplatform.service.user.InterestService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InterestServiceTest {

    @Mock
    private InterestRepository interestRepository;

    @Mock
    private InterestMapper interestMapper;

    @InjectMocks
    private InterestService interestService;

    // ─── Create ────────────────────────────────────────────────

    @Test
    void createInterest_Success() {
        CreateInterestRequest request = new CreateInterestRequest("Java");
        Interest interest = Interest.builder().id(1L).name("Java").build();
        InterestResponse response = new InterestResponse(1L, "Java");

        when(interestRepository.existsByNameAndDeletedAtIsNull("Java")).thenReturn(false);
        when(interestMapper.toEntity(request)).thenReturn(interest);
        when(interestRepository.save(interest)).thenReturn(interest);
        when(interestMapper.toResponse(interest)).thenReturn(response);

        InterestResponse result = interestService.createInterest(request);

        assertThat(result.getName()).isEqualTo("Java");
    }

    @Test
    void createInterest_ThrowsDuplicate() {
        CreateInterestRequest request = new CreateInterestRequest("Java");

        when(interestRepository.existsByNameAndDeletedAtIsNull("Java")).thenReturn(true);

        assertThrows(DuplicateResourceException.class,
                () -> interestService.createInterest(request));
    }

    // ─── Update ────────────────────────────────────────────────

    @Test
    void updateInterest_Success() {
        UpdateInterestRequest request = new UpdateInterestRequest("Python");
        Interest interest = Interest.builder().id(1L).name("Java").build();
        InterestResponse response = new InterestResponse(1L, "Python");

        when(interestRepository.findByIdAndDeletedAtIsNull(1L)).thenReturn(Optional.of(interest));
        when(interestRepository.existsByIdNotAndNameAndDeletedAtIsNull(1L, "Python")).thenReturn(false);
        when(interestRepository.save(interest)).thenReturn(interest);
        when(interestMapper.toResponse(interest)).thenReturn(response);

        InterestResponse result = interestService.updateInterest(1L, request);

        assertThat(result.getName()).isEqualTo("Python");
    }

    @Test
    void updateInterest_ThrowsNotFound() {
        UpdateInterestRequest request = new UpdateInterestRequest("Python");

        when(interestRepository.findByIdAndDeletedAtIsNull(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> interestService.updateInterest(1L, request));
    }

    @Test
    void updateInterest_ThrowsDuplicate() {
        UpdateInterestRequest request = new UpdateInterestRequest("Python");
        Interest interest = Interest.builder().id(1L).name("Java").build();

        when(interestRepository.findByIdAndDeletedAtIsNull(1L)).thenReturn(Optional.of(interest));
        when(interestRepository.existsByIdNotAndNameAndDeletedAtIsNull(1L, "Python")).thenReturn(true);

        assertThrows(DuplicateResourceException.class,
                () -> interestService.updateInterest(1L, request));
    }

    // ─── Find ────────────────────────────────────────────────

    @Test
    void findByIdAndDeletedAtIsNull_Success() {
        Interest interest = Interest.builder().id(1L).name("Java").build();
        InterestResponse response = new InterestResponse(1L, "Java");

        when(interestRepository.findByIdAndDeletedAtIsNull(1L)).thenReturn(Optional.of(interest));
        when(interestMapper.toResponse(interest)).thenReturn(response);

        InterestResponse result = interestService.findByIdAndDeletedAtIsNull(1L);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Java");
    }

    @Test
    void findByIdAndDeletedAtIsNull_ThrowsNotFound() {
        when(interestRepository.findByIdAndDeletedAtIsNull(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> interestService.findByIdAndDeletedAtIsNull(1L));
    }

    @Test
    void findAllActive_Success() {
        Interest interest = Interest.builder().id(1L).name("Java").build();
        InterestResponse response = new InterestResponse(1L, "Java");

        when(interestRepository.findByDeletedAtIsNull()).thenReturn(List.of(interest));
        when(interestMapper.toResponse(interest)).thenReturn(response);

        List<InterestResponse> result = interestService.findAllActive();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Java");
    }

    // ─── Delete ────────────────────────────────────────────────

    @Test
    void deleteById_Success() {
        Interest interest = Interest.builder().id(1L).name("Java").build();

        when(interestRepository.findByIdAndDeletedAtIsNull(1L)).thenReturn(Optional.of(interest));

        interestService.deleteById(1L);

        assertThat(interest.getDeletedAt()).isNotNull();
        verify(interestRepository).save(interest);
    }

    @Test
    void deleteById_ThrowsNotFound() {
        when(interestRepository.findByIdAndDeletedAtIsNull(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> interestService.deleteById(1L));
    }
}