package cn.snowflake.rose.utils.time;


public class TimeHelper {
    public long lastMs;
    private long previousTime;

    public TimeHelper() {
        this.lastMs = 0L;
        previousTime = -1L;
    }

    public boolean isDelayComplete(long delay) {
        if (System.currentTimeMillis() - this.lastMs > delay) {
            return true;
        }
        return false;
    }

    public long getCurrentMS() {
        return System.nanoTime() / 1000000L;
    }

    public void reset() {
        this.lastMs = System.currentTimeMillis();
    }

    public long getLastMs() {
        return this.lastMs;
    }

    public void setLastMs(int i) {
        this.lastMs = System.currentTimeMillis() + (long)i;
    }

    public boolean hasReached(long milliseconds) {
        return this.getCurrentMS() - this.lastMs >= milliseconds;
    }

    public boolean hasReached(float timeLeft) {
        return (float)(this.getCurrentMS() - this.lastMs) >= timeLeft;
    }
    private long prevMS = 0L;

    public boolean delay(double nextDelay) {
        return System.currentTimeMillis() - lastMs >= nextDelay;
    }

    public boolean delay(float milliSec) {
        return (float)getIncremental((double)(this.getTime() - this.prevMS), 50.0D) >= milliSec;
    }
    public static double getIncremental(double val, double inc) {
        double one = 1.0D / inc;
        return (double)Math.round(val * one) / one;
    }
    public boolean hasPassed(double milli) {
        return (double)(this.getCurrentMS() - this.lastMs) >= milli;
    }

    public boolean hasReached(double milliseconds) {
        if ((double)(this.getCurrentMS() - this.lastMs) >= milliseconds) {
            return true;
        }
        return false;
    }

    public boolean hasReach(long milliseconds) {
        if (getCurrentMS() - lastMs >= milliseconds) {
            reset();
            return true;
        }
        return false;
    }

    public boolean check(float milliseconds) {
        return getTime() >= milliseconds;
    }
    public long getTime() {
        return getCurrentTime() - previousTime;
    }

    public long getCurrentTime() {
        return System.currentTimeMillis();
    }
    //TODO ETB Reloaded 0.1
    public boolean sleep(final long time) {
        if (getCurrentMS() >= time) {
            reset();
            return true;
        }
        return false;
    }

}
