package bowlinggame.dto;

import java.util.ArrayList;
import java.util.List;

public class Frame {

    private int frameNumber;

    private List<String> frameRolls;

    private List<Integer> frameScores;

    private FrameProperties frameProperties;

    public Frame(int index) {
        this.frameNumber = index;
        this.frameRolls = new ArrayList<>();
        this.frameScores = new ArrayList<>();
        this.frameProperties = new FrameProperties(false, false, false);
    }

    public Frame(int frameNumber, List<String> frameRolls, List<Integer> frameScores, FrameProperties frameProperties) {
        this.frameNumber = frameNumber;
        this.frameRolls = frameRolls;
        this.frameScores = frameScores;
        this.frameProperties = frameProperties;
    }

    public int getFrameNumber() {
        return frameNumber;
    }

    public void setFrameNumber(int frameNumber) {
        this.frameNumber = frameNumber;
    }

    public List<String> getFrameRolls() {
        return frameRolls;
    }

    public void setFrameRolls(List<String> frameRolls) {
        this.frameRolls = frameRolls;
    }

    public List<Integer> getFrameScores() {
        return frameScores;
    }

    public void setFrameScores(List<Integer> frameScores) {
        this.frameScores = frameScores;
    }

    public FrameProperties getFrameProperties() {
        return frameProperties;
    }

    public void setFrameProperties(FrameProperties frameProperties) {
        this.frameProperties = frameProperties;
    }

    @Override
    public String toString() {
        return "Frame{" +
                "frameNumber=" + frameNumber +
                ", frameRolls=" + frameRolls +
                ", frameScores=" + frameScores +
                ", frameProperties=" + frameProperties +
                '}';
    }
}