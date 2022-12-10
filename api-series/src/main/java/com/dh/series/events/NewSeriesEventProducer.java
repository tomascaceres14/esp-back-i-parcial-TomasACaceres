package com.dh.series.events;

import com.dh.series.config.RabbitMQConfig;
import com.dh.series.model.Chapter;
import com.dh.series.model.Season;
import com.dh.series.model.Serie;
import com.dh.series.model.dto.ChapterDTO;
import com.dh.series.model.dto.SeasonDTO;
import com.dh.series.model.dto.SerieDTO;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class NewSeriesEventProducer {

    private final RabbitTemplate rabbitTemplate;

    public NewSeriesEventProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void execute(Serie serie) {
        SerieDTO serieDTO = new SerieDTO();
        BeanUtils.copyProperties(serie, serieDTO);

        for (Season s :
                serie.getSeasons()) {
            SeasonDTO sDTO = new SeasonDTO();
            BeanUtils.copyProperties(s, sDTO);

            for (ChapterDTO c :
                    sDTO.getChapters()) {
                ChapterDTO cDTO = new ChapterDTO();
                BeanUtils.copyProperties(c, cDTO);
                sDTO.getChapters().add(cDTO);
            }
            serieDTO.getSeasons().add(sDTO);
        }

        if (serieDTO.getSeasons().get(0).getChapters().get(0) != null && serie.getSeasons().get(0).getChapters().get(0) != null) {
            //BeanUtils.copyProperties(serie.getSeasons(), serieDTO.getSeasons());
            rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.TOPIC_NEW_PLAYLIST, serieDTO);
        }


    }

}