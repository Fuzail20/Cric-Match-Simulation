package com.cricket.match.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Ball {
    private Player player;
    private Integer run;
    private BallStatus status;

    public enum BallStatus{
        WIDE , NO_BALL , WICKET , FOUR , SIX , RUN;
    }
}
