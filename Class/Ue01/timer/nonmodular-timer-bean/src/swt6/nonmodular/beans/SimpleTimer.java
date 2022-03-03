package swt6.nonmodular.beans;

import java.util.ArrayList;
import java.util.List;

public class SimpleTimer {

    private int tickInterval = 1000;
    private int noTicks = 1;
    private Thread tickerThread = null;
    private List<TimerListener> listeners = new ArrayList<>();
    private boolean stopTimer = false;

    public SimpleTimer(int tickInterval, int noTicks) {
        this.tickInterval = tickInterval;
        this.noTicks = noTicks;
    }

    public int getTickInterval() {
        return tickInterval;
    }

    public void setTickInterval(int i) {
        this.tickInterval = i;
    }

    public int getNoTicks() {
        return noTicks;
    }

    public void setNoTicks(int n) {
        this.noTicks = n;
    }

    public void start() {
        if (isRunning()) throw new IllegalStateException("Timer already running");

        int interval = getTickInterval();
        int noTicks = getNoTicks();

        tickerThread = new Thread(() -> {
            int tickCount = 0;
            while (tickCount < noTicks && !stopTimer) {
                try {
                    Thread.sleep(interval);
                } catch (InterruptedException e) {
                }

                if (!stopTimer) {
                    tickCount++;
                    fireTickEvent(new TimerEvent(SimpleTimer.this, tickCount, noTicks));
                }
            }

            stopTimer = false;
            tickerThread = null;
        });

        tickerThread.start();
    }

    private void fireTickEvent(TimerEvent timerEvent) {
        listeners.forEach(listener -> listener.expired(timerEvent));
    }

    private boolean isRunning() {
        return tickerThread != null;
    }

    public void stop() {
        stopTimer = true;
    }

    public void addTimerListener(TimerListener listener) {
        listeners.add(listener);
    }

    public void removeTimerListener(TimerListener listener) {
        listeners.remove(listener);
    }
}
