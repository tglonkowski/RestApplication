package com.tglonkowski.homework3.model;

import lombok.*;
import lombok.experimental.FieldDefaults;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Car {

    Long id;
    String mark;
    String model;
    Color color;
}
