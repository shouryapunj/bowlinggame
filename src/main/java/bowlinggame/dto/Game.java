package bowlinggame.dto;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;

@Component
public class Game {

    private Frame[] frames;

    private int totalScore;

    private Queue<Frame> framesToBeProcessed;

    public Game() {
        this.frames = new Frame[] {new Frame(1), new Frame(2), new Frame(3), new Frame(4), new Frame(5),
                new Frame(6), new Frame(7), new Frame(8), new Frame(9), new Frame(10),
                new Frame(11)};
        this.totalScore = 0;
        this.framesToBeProcessed = new PriorityQueue<>((a,b) -> (a.getFrameNumber() - b.getFrameNumber()));
    }

    public Game(Frame[] frames, int totalScore, Queue<Frame> framesToBeProcessed) {
        this.frames = frames;
        this.totalScore = totalScore;
        this.framesToBeProcessed = framesToBeProcessed;
    }

    public Frame[] getFrames() {
        return frames;
    }

    public void setFrames(Frame[] frames) {
        this.frames = frames;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public Queue<Frame> getFramesToBeProcessed() {
        return framesToBeProcessed;
    }

    public void setFramesToBeProcessed(Queue<Frame> framesToBeProcessed) {
        this.framesToBeProcessed = framesToBeProcessed;
    }

    @Override
    public String toString() {
        return "Game{" +
                "frames=" + Arrays.toString(frames) +
                ", totalScore=" + totalScore +
                ", framesToBeProcessed=" + framesToBeProcessed +
                '}';
    }
}
