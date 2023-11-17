package com.cricket.match.entity;

import lombok.Data;

import java.util.*;
import java.util.stream.IntStream;

@Data
public class Innings {

    private Integer id;
    private Queue<Player> playerQueue;
    private List<List<Ball>> overs;
    private Integer totalRun;


    public void getScoreCardForOver() {

        Map<Player, List<Integer>> performance = new HashMap<>();
        int extras = 0;
        int totalRun =0;
        for(List<Ball> ballList : overs) {
            for (Ball b : ballList) {
                if(Objects.isNull(b.getPlayer())){
                    extras += b.getRun();
                    totalRun += b.getRun();
                    continue;
                }
                if (performance.containsKey(b.getPlayer())) {
                    List<Integer> curr = performance.get(b.getPlayer());
                    switch (b.getStatus()) {
                        case SIX:
                            curr.set(2, curr.get(2) + 1);
                            curr.set(0, curr.get(0) + 6);
                            curr.set(3, curr.get(3)  + 1);
                            totalRun += 6;
                            break;
                        case FOUR:
                            curr.set(1, curr.get(1) + 1);
                            curr.set(0, curr.get(0) + 4);
                            curr.set(3, curr.get(3) + 1);
                            totalRun += 4;
                            break;
                        case RUN:
                            curr.set(0, curr.get(0) + b.getRun());
                            curr.set(3, curr.get(3)  +  1);
                            totalRun += b.getRun();
                    }
                }
                else{
                    totalRun += b.getRun();
                    performance.put(b.getPlayer() , Arrays.asList(b.getRun(),b.getStatus().equals(Ball.BallStatus.FOUR) ? 1 : 0,b.getStatus().equals(Ball.BallStatus.SIX) ? 1 : 0,1));
                }
            }
        }

        List<Player> playerSet = new ArrayList<> (performance.keySet());

        // printing scorecard
        System.out.println("*********************************");
        System.out.println("***********INNINGS "+id +"*************");
        for(int i=0; i< playerSet.size() ; i++){
            List<Integer> run = performance.get(playerSet.get(i));
            if(playerSet.get(i).getIsActive()==Boolean.TRUE){
                System.out.println(playerSet.get(i) + "*" + " " + run);
            }
            else {
                System.out.println(playerSet.get(i) + " " + run);
            }
        }
        System.out.println("extras: "+ extras);
        System.out.println("totalRun: "+ totalRun);

    }


    public void setTotalRun(){
        totalRun = overs.stream().flatMap(x->x.stream()).flatMapToInt(x-> IntStream.of(x.getRun())).sum();
    }

    public  void setOvers(List<List<String>> oversForTeam1, Innings innings) {

        List<List<Ball>> overList = new ArrayList<>();

        Player batsman = innings.getPlayerQueue().poll();
        Player runner = innings.getPlayerQueue().poll();
        batsman.setIsActive(true);
        runner.setIsActive(true);
        for(int i = 0; i< oversForTeam1.size() ; i++){
            List<Ball> ballList = new ArrayList<>();
            for(int j = 0; j< oversForTeam1.get(i).size(); j++) {
                Ball.BallStatus status;
                Integer run=0;
                Integer teamRun = 0;
                Boolean swapPlayer = false;
                Boolean batsmanChange = false;
                switch (oversForTeam1.get(i).get(j)) {
                    case "Wd" :
                        status = Ball.BallStatus.WIDE;
                        teamRun = 1;
                        break;
                    case "W":
                        status = Ball.BallStatus.WICKET;
                        batsmanChange = true;
                        break;
                    case "Nb":
                        status = Ball.BallStatus.NO_BALL;
                        teamRun = 1;
                        break;
                    case "6":
                        status = Ball.BallStatus.SIX;
                        run = 6;
                        batsman.setTotalSix(batsman.getTotalSix() + 1);
                        batsman.setRun(batsman.getRun() + run);
                        break;
                    case "4":
                        status = Ball.BallStatus.FOUR;
                        run = 4;
                        batsman.setTotalFour(batsman.getTotalFour() + 1);
                        batsman.setRun(batsman.getRun() + run);
                        break;
                    default:
                        status= Ball.BallStatus.RUN;
                        run = Integer.parseInt(oversForTeam1.get(i).get(j));
                        if(run%2!=0){
                            swapPlayer = true;
                        }
                        batsman.setRun(batsman.getRun() + run);
                        break;
                }
                Ball ball = Ball.builder().status(status).player(teamRun !=0 ? null : batsman).run(run !=0 ? run : teamRun).build();
                ballList.add(ball);

                if(batsmanChange){
                    batsman.setIsActive(false);
                    batsman = innings.getPlayerQueue().poll();
                    batsman.setIsActive(true);
                }

                if(Boolean.TRUE.equals(swapPlayer)){
                    Player temp = batsman;
                    batsman = runner;
                    runner = temp;
                }
            }
            overList.add(ballList);
            Player temp = batsman;
            batsman = runner;
            runner = temp;
        }
        overs = overList;
    }
}
