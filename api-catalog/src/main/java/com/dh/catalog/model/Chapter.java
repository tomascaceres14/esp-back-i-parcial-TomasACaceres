package com.dh.catalog.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Chapter implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long chapterId;

    private String name;

    private Integer number;

    private String urlStream;

}
