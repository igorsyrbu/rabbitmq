package com.syrbu.rabbitdemo.message;

import lombok.Data;

import java.io.Serializable;

@Data
public class SimpleMessage implements Serializable {
    private String id;
    private String text;
    private String receivedAt;
}
