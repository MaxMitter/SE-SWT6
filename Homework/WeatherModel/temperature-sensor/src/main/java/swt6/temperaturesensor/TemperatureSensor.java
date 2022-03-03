package swt6.temperaturesensor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import swt6.weathermodel.Measurement;
import swt6.weathermodel.Sensor;
import swt6.weathermodel.SensorListener;

import java.util.*;

public class TemperatureSensor implements Sensor {

    private String unit;
    private long measurementInterval;

    private Timer measurementTimer = new Timer();
    private List<SensorListener> listeners = new ArrayList<>();
    private boolean isRunning;
    private Random random = new Random();
    private static Logger logger = LoggerFactory.getLogger(TemperatureSensor.class);

    public TemperatureSensor(String unit, long interval) {
        this.unit = unit;
        this.measurementInterval = interval;
        isRunning = false;
        logger.info("created Sensor");
    }

    private void generateMeasurement() {
        double value = random.nextDouble(0, 100);
        fireEvent(new Measurement(this, value, unit));
    }

    private void fireEvent(Measurement measurement) {
        listeners.forEach(listener -> listener.measurementTaken(measurement));
    }

    @Override
    public void start() {
        if (isRunning()) {
            logger.error("Sensor cannot be started - Sensor is already running");
            throw new IllegalStateException("Sensor is already running");
        }

        this.measurementTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                generateMeasurement();
            }
        }, 0, measurementInterval);
        isRunning = true;
    }

    @Override
    public void stop() {
        if (isRunning) {
            measurementTimer.cancel();
            isRunning = false;
            logger.info("sensor disabled");
        }
    }

    @Override
    public boolean isRunning() {
        return isRunning;
    }

    @Override
    public void addWeatherStation(SensorListener listener) {
        listeners.add(listener);

    }

    @Override
    public void removeWeatherStation(SensorListener listener) {
        listeners.remove(listener);
    }
}
