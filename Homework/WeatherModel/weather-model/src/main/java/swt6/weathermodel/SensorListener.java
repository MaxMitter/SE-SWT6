package swt6.weathermodel;

import java.util.EventListener;

public interface SensorListener extends EventListener {
    void measurementTaken(Measurement event);
}
