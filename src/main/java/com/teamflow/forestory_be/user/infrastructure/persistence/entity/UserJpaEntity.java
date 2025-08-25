package com.teamflow.forestory_be.user.infrastructure.persistence.entity;

import com.teamflow.forestory_be.support.common.domain.BaseTimeEntity;
import com.teamflow.forestory_be.user.domain.vo.UserStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Builder
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserJpaEntity extends BaseTimeEntity {
    @Id
    @Column(name = "user_id")
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "profile_image_url")
    private String profileImageUrl;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @Column(name = "introduction")
    private String introduction;

    @Column(name = "contact_url")
    private String contactUrl;
}
