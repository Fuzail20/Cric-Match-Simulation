package com.cricket.match.entity;

import lombok.Builder;
import lombok.Data;

import java.util.Objects;

@Data
@Builder
public class Player {
    private String name;
    @Builder.Default
    private Integer run  =0 ;
    @Builder.Default
    private Integer totalFour =0;
    @Builder.Default
    private Integer totalSix=0;
    @Builder.Default
    private Boolean isActive = Boolean.FALSE;
    private Team.TeamName teamName;
    @Override
    public String toString() {
        if(Objects.isNull(name)){
            return "Extras";
        }
        return this.name;
    }
}
