package com.prgrms.monthsub.module.payment.converter;

import com.prgrms.monthsub.common.s3.config.S3;
import com.prgrms.monthsub.module.payment.dto.PaymentForm.PaymentSeries;
import com.prgrms.monthsub.module.payment.dto.PaymentForm.Response;
import com.prgrms.monthsub.module.payment.dto.PaymentPost;
import com.prgrms.monthsub.module.payment.dto.PaymentPost.UserPoint;
import com.prgrms.monthsub.module.series.series.domain.ArticleUploadDate;
import com.prgrms.monthsub.module.series.series.domain.Series;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class PaymentConverter {
  private final S3 s3;

  public PaymentConverter(S3 s3) {this.s3 = s3;}

  public Response seriesToPaymentWindowResponse(
    Series series,
    List<ArticleUploadDate> uploadDateList
  ) {
    return new Response(
      PaymentSeries.builder()
        .email(series.getWriter()
          .getUser()
          .getEmail())
        .nickname(series.getWriter()
          .getUser()
          .getNickname())
        .title(series.getTitle())
        .thumbnail(this.s3.getDomain() + "/" + series.getThumbnailKey())
        .category(series.getCategory())
        .price(series.getPrice())
        .articleCount(series.getArticleCount())
        .startDate(series.getSubscribeStartDate())
        .endDate(series.getSubscribeEndDate())
        .date(uploadDateList.stream()
          .map(
            uploadDate -> {
              return uploadDate.getUploadDate()
                .toString()
                .toLowerCase();
            }
          )
          .toArray(String[]::new))
        .time(series.getUploadTime())
        .build()
    );
  }


  public PaymentPost.Response paymentResponse(
    Series series,
    List<ArticleUploadDate> uploadDateList,
    int point
  ) {
    return new PaymentPost.Response(
      PaymentPost.PaymentSeries.builder()
        .email(series.getWriter()
          .getUser()
          .getEmail())
        .nickname(series.getWriter()
          .getUser()
          .getNickname())
        .title(series.getTitle())
        .thumbnail(this.s3.getDomain() + "/" + series.getThumbnailKey())
        .category(series.getCategory())
        .price(series.getPrice())
        .articleCount(series.getArticleCount())
        .startDate(series.getSubscribeStartDate())
        .endDate(series.getSubscribeEndDate())
        .date(uploadDateList.stream()
          .map(
            uploadDate -> {
              return uploadDate.getUploadDate()
                .toString()
                .toLowerCase();
            }
          )
          .toArray(String[]::new))
        .time(series.getUploadTime())
        .build(),
      UserPoint.builder()
        .point(point)
        .build()
    );
  }

}
