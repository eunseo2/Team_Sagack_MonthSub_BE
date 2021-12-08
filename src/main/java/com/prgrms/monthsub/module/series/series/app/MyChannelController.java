package com.prgrms.monthsub.module.series.series.app;

import com.prgrms.monthsub.common.exception.model.ApiResponse;
import com.prgrms.monthsub.common.jwt.JwtAuthentication;
import com.prgrms.monthsub.module.series.series.dto.MyChannel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/channel")
@Tag(name = "MyChannel")
public class MyChannelController {

  private final MyChannelAssemble myChannelService;

  public MyChannelController(MyChannelAssemble myChannelService) {
    this.myChannelService = myChannelService;
  }

  @GetMapping("/me")
  @Operation(summary = "내 채널 조회")
  @Tag(name = "[화면]-내 채널")
  public ApiResponse<MyChannel.Response> getSeriesList(
    @AuthenticationPrincipal JwtAuthentication authentication
  ) {
    return ApiResponse.ok(
      HttpMethod.GET, this.myChannelService.getMyChannel(authentication.userId));
  }

}
