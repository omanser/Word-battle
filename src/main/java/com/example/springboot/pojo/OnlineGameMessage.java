package com.example.springboot.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OnlineGameMessage {
    private ArrayList<Words> words;
    private Boolean isEnd;
    private Integer responseNum;
    private Integer wordIndex;
}
