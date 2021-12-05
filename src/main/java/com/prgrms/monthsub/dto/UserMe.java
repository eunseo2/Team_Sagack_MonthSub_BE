package com.prgrms.monthsub.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class UserMe {

    @Schema(name = "UserMe.Response")
    public record Response(
        Long userId,
        String token,
        String nicName,
        String profileImage,
        String profileIntroduce,
        String group
    ) {}

}
