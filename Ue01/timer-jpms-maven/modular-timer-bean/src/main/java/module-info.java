module swt6.timer.bean {
    exports swt6.modular.beans;
    provides swt6.modular.beans.TimerProvider
            with swt6.modular.beans.impl.SimpleTimerProvider;
}