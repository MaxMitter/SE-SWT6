package swt6.weathermodel;

import java.time.LocalDateTime;
import java.util.EventObject;

public class Measurement extends EventObject {
    private double measurement;
    private String unit;
    private LocalDateTime timeStamp;

    public Measurement(Object source, double measurement, String unit) {
        super(source);
        this.measurement = measurement;
        this.unit = unit;
        this.timeStamp = LocalDateTime.now();
    }

    public double getMeasurement() {
        return measurement;
    }

    public String getUnit() {
        return unit;
    }
}
