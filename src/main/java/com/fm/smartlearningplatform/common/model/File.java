package com.fm.smartlearningplatform.common.model;

import com.fm.smartlearningplatform.common.enums.FileStatus;
import com.fm.smartlearningplatform.common.enums.FileType;
import com.fm.smartlearningplatform.common.enums.StorageProvider;
import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Entity
@Table(name = "files")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class File extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @Column(name = "public_id",nullable = false, unique = true)
    private String publicId;

    @Column(name = "url", nullable = false, length = 1000)
    private String url;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "content_type", nullable = false)
    private String contentType;

    @Column(name = "size", nullable = false)
    private Long size;

    @Enumerated(EnumType.STRING)
    @Column(name = "type",nullable = false)
    private FileType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "provider",nullable = false)
    private StorageProvider provider;

    @Column(name = "hash",length = 64)
    private String hash; // to me karyu te ama convert karvu pdse ne ? kayu batav mara,a

    @Enumerated(EnumType.STRING)
    @Column(name = "status",nullable = false)
    private FileStatus status;

    @Column(name = "deleted_at")
    private Instant deletedAt;
}