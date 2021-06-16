package bowlinggame.services;

import bowlinggame.constants.BowlingConstants;
import bowlinggame.dto.Frame;
import bowlinggame.dto.FrameProperties;
import bowlinggame.dto.Game;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Queue;
import java.util.Scanner;

@Service
public class GameService {

    private int frameBeingProcessed;

    public GameService() {
        this.frameBeingProcessed = 0;
    }

    /**
     * Checks whether input is valid or not
     * @param input
     * @return true/false
     */
    public boolean isValidInput(String input) {
        if (input.toUpperCase().equals(BowlingConstants.STRIKE)
                ||  input.toUpperCase().equals(BowlingConstants.SPARE)
                ||  input.toUpperCase().equals(BowlingConstants.MISS)
                ||  input.matches("[1-9]")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Starts a new game
     * @param game
     */
    public void startANewGame(Game game) {
        Scanner scanner = new Scanner(System.in);
        this.frameBeingProcessed = 0;
        String input = "";
        boolean isValid = false;
        while (true) {
            System.out.print("Enter input : ");
            input = scanner.next();
            System.out.println("");
            isValid =  isValidInput(input);
            if (!isValid) {
                System.out.println("Invalid Input! Please enter again (strike/miss/spare/[1-9]) !");
                continue;
            }
            boolean isUpdated = updateGame(game, input);
            if (isUpdated) {
                printScorecard(game);
            }

            if (getFrameBeingProcessed() == 11) {
                System.out.println("--Game Ends--\n");
                break;
            }
        }

    }

    /**
     * Updates the game state
     * @param bowlingGame
     * @param input
     * @return true/false
     */
    public boolean updateGame(Game bowlingGame, String input) {
        Queue<Frame> frameQueue = bowlingGame.getFramesToBeProcessed();

        Frame[] frames = bowlingGame.getFrames();
        Frame frame = frames[this.frameBeingProcessed];
        FrameProperties frameProperties = frame.getFrameProperties();
        List<String> frameRolls = frame.getFrameRolls();
        List<Integer> frameScores = frame.getFrameScores();

        if (input.toUpperCase().equals(BowlingConstants.STRIKE)) {
            frameRolls.add("X");
            frameScores.add(10);
            frameProperties.setStrike(true);
            if (frame.getFrameNumber() != 11) {
                frameQueue.add(frame);
            }
        } else if (input.toUpperCase().equals(BowlingConstants.SPARE)) {
            if (frameRolls.size() == 0) {
                System.out.println("Invalid input! First roll in a frame cannot be spare!");
                return false;
            }
            frameRolls.add("/");
            frameScores.add(10-frameScores.get(0));
            frameProperties.setSpare(true);
            if (frame.getFrameNumber() != 11) {
                frameQueue.add(frame);
            }
        } else if (input.toUpperCase().equals(BowlingConstants.MISS)) {
            frameRolls.add("-");
            frameScores.add(0);
            if (frameScores.size() == 2 && !frameRolls.get(1).equals("/")) {
                frameProperties.setDisplay(true);
            }
        } else {
            frameRolls.add(input);
            frameScores.add(Integer.parseInt(input));

            if (frameRolls.size() > 1 && !frameRolls.get(0).equals("X") && !frameRolls.get(1).equals("/")) {
                frameProperties.setDisplay(true);
            }
        }

        if (frameQueue.size() > 0) {
            updateGameFromQueue(bowlingGame, frameQueue);
        }

        if (this.frameBeingProcessed == 10) {
            if (frames[this.frameBeingProcessed-1].getFrameRolls().get(0).equals("X") && frameRolls.size() == 2) {
                this.frameBeingProcessed++;
            } else if (frames[this.frameBeingProcessed-1].getFrameRolls().size() > 1 && frames[this.frameBeingProcessed-1].getFrameRolls().get(1).equals("/") && frameRolls.size() == 1) {
                this.frameBeingProcessed++;
            } else if (frames[this.frameBeingProcessed-1].getFrameRolls().size() > 1) {
                this.frameBeingProcessed++;
            }
        } else if ( frameRolls.get(0).equals("X") || frameRolls.size() == 2) {
            if (frames[9].getFrameRolls().size() == 2
                    && !frames[9].getFrameRolls().get(0).equals("X")
                    && !frames[9].getFrameRolls().get(1).equals("/")) {
                this.frameBeingProcessed = 11;
            } else {
                this.frameBeingProcessed++;
            }
        }

        return true;
    }

    /**
     * Updates game state for previous frames
     * @param bowlingGame
     * @param frameQueue
     */
    public void updateGameFromQueue(Game bowlingGame, Queue<Frame> frameQueue) {
        int size = frameQueue.size();
        while (size-- > 0) {
            Frame f = frameQueue.poll();
            if (f.getFrameProperties().isStrike()) {
                int fNumber = f.getFrameNumber();
                Frame[] frameList = bowlingGame.getFrames();
                Frame s = frameList[fNumber+1-1];
                if (s.getFrameScores().size() >= 2) {
                    int a = s.getFrameScores().get(0);
                    int b = s.getFrameScores().get(1);
                    List<Integer> fList = f.getFrameScores();
                    fList.add(a);
                    fList.add(b);
                    f.setFrameScores(fList);
                    f.getFrameProperties().setDisplay(true);
                    frameList[fNumber-1] = f;
                    bowlingGame.setFrames(frameList);
                } else if (s.getFrameScores().size() == 1 && fNumber+2-1 <= 10) {
                    int a = s.getFrameScores().get(0);
                    Frame t = frameList[fNumber+2-1];
                    if (t.getFrameScores().size() == 0) {
                        frameQueue.add(f);
                        continue;
                    }
                    int b = t.getFrameScores().get(0);
                    List<Integer> fList = f.getFrameScores();
                    fList.add(a);
                    fList.add(b);
                    f.setFrameScores(fList);
                    f.getFrameProperties().setDisplay(true);
                    Frame[] frames = bowlingGame.getFrames();
                    frames[fNumber-1] = f;
                    bowlingGame.setFrames(frames);
                } else {
                    frameQueue.add(f);
                    continue;
                }
            } else if (f.getFrameProperties().isSpare()) {
                int fNumber = f.getFrameNumber();
                Frame[] frameList = bowlingGame.getFrames();
                Frame s = frameList[fNumber+1-1];
                if (s.getFrameScores().size() == 0) {
                    frameQueue.add(f);
                    continue;
                }
                int a = s.getFrameScores().get(0);
                List<Integer> fList = f.getFrameScores();
                fList.add(a);
                f.setFrameScores(fList);
                f.getFrameProperties().setDisplay(true);
                frameList[fNumber-1] = f;
                bowlingGame.setFrames(frameList);
            }
        }
    }

    public int getFrameBeingProcessed() {
        return this.frameBeingProcessed;
    }

    /**
     * prints scoreboard
     * @param bowlingGame
     */
    public void printScorecard(Game bowlingGame) {
        System.out.println(" ");
        System.out.println("-----------------------------------------------------------------------------------------------");
        System.out.println("1\t\t2\t\t3\t\t4\t\t5\t\t6\t\t7\t\t8\t\t9\t\t10");
        Frame[] frames = bowlingGame.getFrames();
        int sum = 0;
        for (int i = 0; i<= 9; i++) {
            Frame f = frames[i];
            List<String> l = f.getFrameRolls();
            if (i == 9 && l.size() > 0) {
                System.out.print("[");
                for (String str : l) {
                    System.out.print(str + ", ");
                }
                if (frames.length == 11 && frames[10].getFrameRolls().size() > 0) {
                    for (String str : frames[10].getFrameRolls()) {
                        System.out.print(str + ", ");
                    }
                    System.out.println("\b]\t\t");
                } else{
                    System.out.println("\t\t");
                }

            } else {
                System.out.print(f.getFrameRolls() + "\t\t");
            }

        }
        System.out.println("");

        for (int i = 0; i <= 9; i++) {
            Frame f = frames[i];
            if (!f.getFrameProperties().isDisplay()) {
                System.out.print("");
                continue;
            }

            for (int s : f.getFrameScores()) {
                sum += s;
            }
            System.out.print(sum + "\t\t");
        }
        bowlingGame.setTotalScore(sum);
        System.out.println("\n-----------------------------------------------------------------------------------------------");
    }
}