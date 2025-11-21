package com.koomineat.koomineat.domain.auth.dto.response;

import com.koomineat.koomineat.domain.auth.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponse {
    private Long id;

    private String name;
    public static UserResponse from(User u) {
        return UserResponse.builder()
                .id(u.getId())
                .name(u.getName())
                .build();
    }
}
