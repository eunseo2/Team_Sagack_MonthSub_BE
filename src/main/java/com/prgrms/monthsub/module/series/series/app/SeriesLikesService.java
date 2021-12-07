package com.prgrms.monthsub.module.series.series.app;

import com.prgrms.monthsub.module.series.series.domain.Series;
import com.prgrms.monthsub.module.series.series.domain.SeriesLikes;
import com.prgrms.monthsub.module.series.series.domain.SeriesLikes.LikesStatus;
import com.prgrms.monthsub.module.series.series.dto.SeriesLikesEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class SeriesLikesService {

    private final SeriesLikesRepository seriesLikesRepository;

    private final SeriesService seriesService;

    public SeriesLikesService(SeriesLikesRepository seriesLikesRepository,
                              SeriesService seriesService, SeriesService seriesService1) {
        this.seriesLikesRepository = seriesLikesRepository;
        this.seriesService = seriesService1;
    }

    @Transactional
    public SeriesLikesEvent.Response likesEvent(Long userId, Long seriesId) {
        Optional<SeriesLikes> seriesLikes = seriesLikesRepository.findSeriesLikesByUserIdAndSeriesId(
                userId, seriesId);
        if (seriesLikes.isPresent()) {
            LikesStatus changeStatus = seriesLikes.get().changeLikeStatus();
            seriesLikes.get().getSeries().changeLikesCount(changeStatus);
            return new SeriesLikesEvent.Response(
                    seriesLikesRepository.save(seriesLikes.get()).getId(),
                    String.valueOf(changeStatus)
            );
        }
        Series seriesEntity = seriesService.getSeriesById(seriesId);
        seriesEntity.changeLikesCount(LikesStatus.Like);
        SeriesLikes seriesLikesEntity = SeriesLikes.builder().userId(userId).series(seriesEntity)
                .likesStatus(LikesStatus.Like).build();
        return new SeriesLikesEvent.Response(
                seriesLikesRepository.save(seriesLikesEntity).getId(),
                String.valueOf(LikesStatus.Like)
        );
    }

    @Transactional
    public SeriesLikesEvent.Response cancelSeriesLike(Long userId, Long seriesId) {
        Optional<SeriesLikes> seriesLikes = seriesLikesRepository.findSeriesLikesByUserIdAndSeriesId(
                userId, seriesId);
        LikesStatus changeStatus = seriesLikes.get().changeLikeStatus();
        seriesLikes.get().getSeries().changeLikesCount(changeStatus);
        return new SeriesLikesEvent.Response(
                seriesLikesRepository.save(seriesLikes.get()).getId(),
                String.valueOf(changeStatus));
    }
}