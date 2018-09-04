package com.xiangji.warcraft3;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.zip.DataFormatException;

public class RepKing {
    public static void main(String[] args) throws IOException, W3GException, DataFormatException {
        Replay replay = new Replay(new File(args[0]));
        //Replay replay = new Replay(new File("/Users/xiangji/git/WarcraftRepKing/replays/01_08_38_946.nwg"));

        Header header = replay.getHeader();
        System.out.println("版本：1." + header.getVersionNumber() + "." + header.getBuildNumber());
        long duration = header.getDuration();
        System.out.println("时长：" + convertMillisecondToString(duration));

        UncompressedData uncompressedData = replay.getUncompressedData();
        System.out.println("游戏名称：" + uncompressedData.getGameName());
        System.out.println("游戏创建者：" + uncompressedData.getCreaterName());
        System.out.println("游戏地图：" + uncompressedData.getMap());

        List<Player> list = uncompressedData.getPlayerList();
        for(Player player : list) {
            System.out.println("---玩家" + player.getPlayerId() + "---");
            System.out.println("玩家名称：" + player.getPlayerName());
            if(player.isHost()) {
                System.out.println("是否主机：主机");
            } else {
                System.out.println("是否主机：否");
            }
            System.out.println("游戏时间：" + convertMillisecondToString(player.getPlayTime()));
            System.out.println("操作次数：" + player.getAction());
            System.out.println("APM：" + player.getAction() * 60000 / player.getPlayTime());
            if(!player.isObserverOrReferee()) {
                System.out.println("玩家队伍：" + (player.getTeamNumber() + 1));
                switch(player.getRace()) {
                    case HUMAN:
                        System.out.println("玩家种族：人族");
                        break;
                    case ORC:
                        System.out.println("玩家种族：兽族");
                        break;
                    case NIGHT_ELF:
                        System.out.println("玩家种族：暗夜精灵");
                        break;
                    case UNDEAD:
                        System.out.println("玩家种族：不死族");
                        break;
                    case RANDOM:
                        System.out.println("玩家种族：随机");
                        break;
                }
                switch(player.getColor()) {
                    case RED:
                        System.out.println("玩家颜色：红");
                        break;
                    case BLUE:
                        System.out.println("玩家颜色：蓝");
                        break;
                    case CYAN:
                        System.out.println("玩家颜色：青");
                        break;
                    case PURPLE:
                        System.out.println("玩家颜色：紫");
                        break;
                    case YELLOW:
                        System.out.println("玩家颜色：黄");
                        break;
                    case ORANGE:
                        System.out.println("玩家颜色：橘");
                        break;
                    case GREEN:
                        System.out.println("玩家颜色：绿");
                        break;
                    case PINK:
                        System.out.println("玩家颜色：粉");
                        break;
                    case GRAY:
                        System.out.println("玩家颜色：灰");
                        break;
                    case LIGHT_BLUE:
                        System.out.println("玩家颜色：浅蓝");
                        break;
                    case DARK_GREEN:
                        System.out.println("玩家颜色：深绿");
                        break;
                    case BROWN:
                        System.out.println("玩家颜色：棕");
                        break;
                }
                System.out.println("障碍（血量）：" + player.getHandicap() + "%");
                if(player.isComputer()) {
                    System.out.println("是否电脑玩家：电脑玩家");
                    switch (player.getAiStrength())
                    {
                        case EASY:
                            System.out.println("电脑难度：简单的");
                            break;
                        case NORMAL:
                            System.out.println("电脑难度：中等难度的");
                            break;
                        case INSANE:
                            System.out.println("电脑难度：令人发狂的");
                            break;
                    }
                } else {
                    System.out.println("是否电脑玩家：否");
                }
            } else {
                System.out.println("玩家队伍：裁判或观看者");
            }

        }

        List<ChatMessage> chatList = uncompressedData.getReplayData().getChatList();
        for(ChatMessage chatMessage : chatList) {
            String chatString = "[" + convertMillisecondToString(chatMessage.getTime()) + "]";
            chatString += chatMessage.getFrom().getPlayerName() + " 对 ";
            switch ((int)chatMessage.getMode()) {
                case 0:
                    chatString += "所有人";
                    break;
                case 1:
                    chatString += "队伍";
                    break;
                case 2:
                    chatString += "裁判或观看者";
                    break;
                default:
                    chatString += chatMessage.getTo().getPlayerName();
            }
            chatString += " 说：" + chatMessage.getMessage();
            System.out.println(chatString);
        }

    }

    private static String convertMillisecondToString(long millisecond) {
        long second = (millisecond / 1000) % 60;
        long minite = (millisecond / 1000) / 60;
        if (second < 10) {
            return minite + ":0" + second;
        } else {
            return minite + ":" + second;
        }
    }


}
