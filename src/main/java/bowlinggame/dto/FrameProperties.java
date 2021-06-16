package bowlinggame.dto;

public class FrameProperties {

    private boolean strike;

    private boolean spare;

    private boolean display;

    public FrameProperties(boolean strike, boolean spare, boolean display) {
        this.strike = strike;
        this.spare = spare;
        this.display = display;
    }

    public boolean isStrike() {
        return strike;
    }

    public void setStrike(boolean strike) {
        this.strike = strike;
    }

    public boolean isSpare() {
        return spare;
    }

    public void setSpare(boolean spare) {
        this.spare = spare;
    }

    public boolean isDisplay() {
        return display;
    }

    public void setDisplay(boolean display) {
        this.display = display;
    }

    @Override
    public String toString() {
        return "FrameProperties{" +
                "strike=" + strike +
                ", spare=" + spare +
                '}';
    }
}