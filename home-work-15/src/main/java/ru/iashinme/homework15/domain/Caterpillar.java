package ru.iashinme.homework15.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class Caterpillar {
    private String name;

    public Caterpillar(String name) {
        this.name = name;
    }
}
