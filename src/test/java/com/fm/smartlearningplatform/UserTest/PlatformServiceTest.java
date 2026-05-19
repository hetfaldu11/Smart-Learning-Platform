package com.fm.smartlearningplatform.UserTest;

import com.fm.smartlearningplatform.dto.user.platform.request.CreatePlatformRequest;
import com.fm.smartlearningplatform.dto.user.platform.request.UpdatePlatformRequest;
import com.fm.smartlearningplatform.dto.user.platform.response.PlatformResponse;
import com.fm.smartlearningplatform.exception.DuplicateResourceException;
import com.fm.smartlearningplatform.exception.ResourceNotFoundException;
import com.fm.smartlearningplatform.mapper.user.PlatformMapper;
import com.fm.smartlearningplatform.model.user.Platform;
import com.fm.smartlearningplatform.repository.user.PlatformRepository;
import com.fm.smartlearningplatform.service.user.PlatformService;
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
public class PlatformServiceTest {

    @Mock
    private PlatformRepository platformRepository;

    @Mock
    private PlatformMapper platformMapper;

    @InjectMocks
    private PlatformService platformService;

    // ─── Create ────────────────────────────────────────────────

    @Test
    void createPlatform_Success() {
        CreatePlatformRequest request = new CreatePlatformRequest("Github");
        Platform platform = Platform.builder().id(1L).name("Github").build();
        PlatformResponse response = new PlatformResponse(1L, "Github");

        when(platformRepository.existsByNameAndDeletedAtIsNull("Github")).thenReturn(false);
        when(platformMapper.toEntity(request)).thenReturn(platform);
        when(platformRepository.save(platform)).thenReturn(platform);
        when(platformMapper.toResponse(platform)).thenReturn(response);

        PlatformResponse result = platformService.createPlatform(request);

        assertThat(result.getName()).isEqualTo("Github");
    }

    @Test
    void createPlatform_ThrowsDuplicate() {
        CreatePlatformRequest request = new CreatePlatformRequest("Github");

        when(platformRepository.existsByNameAndDeletedAtIsNull("Github")).thenReturn(true);

        assertThrows(DuplicateResourceException.class,
                () -> platformService.createPlatform(request));
    }

    // ─── Update ────────────────────────────────────────────────

    @Test
    void updatePlatform_Success() {
        UpdatePlatformRequest request = new UpdatePlatformRequest("Linkedin");
        Platform platform = Platform.builder().id(1L).name("Github").build();
        PlatformResponse response = new PlatformResponse(1L, "Linkedin");

        when(platformRepository.findByIdAndDeletedAtIsNull(1L)).thenReturn(Optional.of(platform));
        when(platformRepository.existsByIdNotAndNameAndDeletedAtIsNull(1L, "Linkedin")).thenReturn(false);
        when(platformRepository.save(platform)).thenReturn(platform);
        when(platformMapper.toResponse(platform)).thenReturn(response);

        PlatformResponse result = platformService.updatePlatform(1L, request);

        assertThat(result.getName()).isEqualTo("Linkedin");
    }

    @Test
    void updatePlatform_ThrowsNotFound() {
        UpdatePlatformRequest request = new UpdatePlatformRequest("Linkedin");

        when(platformRepository.findByIdAndDeletedAtIsNull(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> platformService.updatePlatform(1L, request));
    }

    @Test
    void updatePlatform_ThrowsDuplicate() {
        UpdatePlatformRequest request = new UpdatePlatformRequest("Linkedin");
        Platform platform = Platform.builder().id(1L).name("Github").build();

        when(platformRepository.findByIdAndDeletedAtIsNull(1L)).thenReturn(Optional.of(platform));
        when(platformRepository.existsByIdNotAndNameAndDeletedAtIsNull(1L, "Linkedin")).thenReturn(true);

        assertThrows(DuplicateResourceException.class,
                () -> platformService.updatePlatform(1L, request));
    }

    // ─── Find ────────────────────────────────────────────────

    @Test
    void findByIdAndDeletedAtIsNull_Success() {
        Platform platform = Platform.builder().id(1L).name("Github").build();
        PlatformResponse response = new PlatformResponse(1L, "Github");

        when(platformRepository.findByIdAndDeletedAtIsNull(1L)).thenReturn(Optional.of(platform));
        when(platformMapper.toResponse(platform)).thenReturn(response);

        PlatformResponse result = platformService.findByIdAndDeletedAtIsNull(1L);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Github");
    }

    @Test
    void findByIdAndDeletedAtIsNull_ThrowsNotFound() {
        when(platformRepository.findByIdAndDeletedAtIsNull(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> platformService.findByIdAndDeletedAtIsNull(1L));
    }

    @Test
    void findAllActive_Success() {
        Platform platform = Platform.builder().id(1L).name("Github").build();
        PlatformResponse response = new PlatformResponse(1L, "Github");

        when(platformRepository.findByDeletedAtIsNull()).thenReturn(List.of(platform));
        when(platformMapper.toResponse(platform)).thenReturn(response);

        List<PlatformResponse> result = platformService.findAllActive();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Github");
    }

    // ─── Delete ────────────────────────────────────────────────

    @Test
    void deleteById_Success() {
        Platform platform = Platform.builder().id(1L).name("Github").build();

        when(platformRepository.findByIdAndDeletedAtIsNull(1L)).thenReturn(Optional.of(platform));

        platformService.deleteById(1L);

        assertThat(platform.getDeletedAt()).isNotNull();
        verify(platformRepository).save(platform);
    }

    @Test
    void deleteById_ThrowsNotFound() {
        when(platformRepository.findByIdAndDeletedAtIsNull(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> platformService.deleteById(1L));
    }
}