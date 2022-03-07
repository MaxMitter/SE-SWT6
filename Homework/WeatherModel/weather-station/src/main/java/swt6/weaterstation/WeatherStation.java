package swt6.weaterstation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import swt6.weathermodel.Measurement;
import swt6.weathermodel.Sensor;
import swt6.weathermodel.SensorListener;
import swt6.weathermodel.SensorProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

public class WeatherStation {

    private Sensor sensor;
    private List<Measurement> measurements;
    private static Logger logger = LoggerFactory.getLogger(WeatherStation.class);

    private Sensor fetchSensor(String unit, int interval) {
        ServiceLoader<SensorProvider> serviceLoader = ServiceLoader.load(SensorProvider.class);

        SensorProvider provider = null;

        for (var currentProvider : serviceLoader) {
            provider = currentProvider;
        }

        if (provider != null)
            return provider.createSensor(unit, interval);
        else
            return null;
    }

    public WeatherStation() {
        logger.info("Weather Station created, requesting Sensor.");
        this.sensor = fetchSensor("°C", 1000);
        measurements = new ArrayList<>();
        if (sensor != null) {
            sensor.addWeatherStation(new SensorListener() {
                @Override
                public void measurementTaken(Measurement event) {
                    measurements.add(event);
                }
            });

            sensor.start();
        }
    }

    public void shutdown() {
        this.sensor.stop();
    }

    public String getLatestMeasurement() {
        var measurement = measurements.get(measurements.toArray().length - 1);
        return measurement.getMeasurement() + measurement.getUnit();
    }

    public String getAverage(int noMeasurements) {
        double total = 0;
        var measurementArray = measurements.toArray();

        for (int i = measurementArray.length - 1; i > measurementArray.length - 1 - noMeasurements && i > 0; i--) {
            total += measurements.get(i).getMeasurement();
        }

        return Math.round(total / noMeasurements) + measurements.get(0).getUnit();
    }
}
