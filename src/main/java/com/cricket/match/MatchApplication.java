package com.cricket.match;

import com.cricket.match.entity.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.*;

@SpringBootApplication
public class MatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(MatchApplication.class, args);

		List<String> battingOrderForTeam1 = Arrays.asList("P1","P2","P3","P4","P5");
		List<List<String>> oversForTeam1 = Arrays.asList(
				Arrays.asList("1","6","1","4","1","5"),
				Arrays.asList("1","6","1","4","1","5"),
				Arrays.asList("W","4","Wd","Wd","W","1","6")
		);

		List<String> battingOrderForTeam2 = Arrays.asList("P6","P7","P8","P9","P10");
		List<List<String>> oversForTeam2 = Arrays.asList(
				Arrays.asList("3","4","1","1","2","2"),
				Arrays.asList("2","1","4","1","3","2"),
				Arrays.asList("W","4","Wd","Wd","W","1","6")
		);

		Team team1 = new Team();
		team1.setTeamName(Team.TeamName.TEAM_1);

		List<Player> playerListTeam1 = new ArrayList<>();

		for(int i=0; i<battingOrderForTeam1.size() ; i++){
			Player player = Player.builder()
					.name(battingOrderForTeam1.get(i))
					.teamName(Team.TeamName.TEAM_1)
					.build();
			playerListTeam1.add(player);
		}

		Innings innings1 = new Innings();
		innings1.setId(1);
		innings1.setPlayerQueue(new LinkedList<>(playerListTeam1));
		innings1.setOvers(oversForTeam1, innings1);
		innings1.setTotalRun();
		innings1.getScoreCardForOver();



		Team team2 = new Team();
		team2.setTeamName(Team.TeamName.TEAM_2);
		List<Player> playerListTeam2 = new ArrayList<>();

		for(int i=0; i<battingOrderForTeam2.size() ; i++){
			Player player = Player.builder()
					.name(battingOrderForTeam2.get(i))
					.teamName(Team.TeamName.TEAM_2)
					.build();
			playerListTeam2.add(player);
		}

		System.out.println("*********************************");
		System.out.println("*********************************");

		Innings innings2 = new Innings();
		innings2.setId(2);
		innings2.setPlayerQueue(new LinkedList<>(playerListTeam2));
		innings2.setOvers(oversForTeam2, innings2);
		innings2.setTotalRun();
		innings2.getScoreCardForOver();



		System.out.println("*********************************");
		System.out.println("*********************************");
		if(innings1.getTotalRun() > innings2.getTotalRun()){
			System.out.println("Team 1 wins");
		}
		else{
			System.out.println("Team 2 wins");
		}

	}


}
