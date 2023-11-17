package com.cricket.match.entity;

import lombok.Data;

@Data
public class Team {
    private TeamName teamName;

    public enum TeamName{
        TEAM_1 , TEAM_2;
    }
}
