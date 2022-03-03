package swt6.modular.client;

import swt6.modular.beans.*;

import java.util.ServiceLoader;

public class TimerClient {
    public static void main(String[] args) {
        //testTimer();
        
        testTimerProvider();
    }

    private static Timer getBestTimer(int interval, int noTicks) {
        ServiceLoader<TimerProvider> serviceLoader = ServiceLoader.load(TimerProvider.class);

        double minResolution = Double.MAX_VALUE;
        TimerProvider minProvider = null;

        for (TimerProvider provider : serviceLoader) {
            if (provider.timerResolution() < minResolution) {
                minProvider = provider;
                minResolution = provider.timerResolution();
            }
        }

        return minProvider == null ? null : minProvider.createTimer(interval, noTicks);
    }

    private static void testTimerProvider() {
        Timer timer = getBestTimer(100, 10);
        timer.addTimerListener(new TimerListener() {
            @Override
            public void expired(TimerEvent event) {
                System.out.printf("%d/%d%n", event.getTickCount(), event.getNoTicks());
            }
        });

        timer.start();
        sleep(500);
        timer.stop();
    }

    private static void testTimer() {
        Timer timer = TimerFactory.createTimer(100, 10);
        timer.addTimerListener(new TimerListener() {
            @Override
            public void expired(TimerEvent event) {
                System.out.printf("%d/%d%n", event.getTickCount(), event.getNoTicks());
            }
        });

        timer.start();
        sleep(500);
        timer.stop();
    }

    private static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
