package com.koomineat.koomineat.domain.store.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Store {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private StoreCategory category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", nullable = false)
    private StoreLocation location;

    // 프로필 이미지
    @Column(nullable = false, columnDefinition = "varchar(255) default ''")
    private String image;

    // 배경화면 이미지
    @Column(nullable = false, columnDefinition = "varchar(255) default ''")
    private String backgroundImage;
}