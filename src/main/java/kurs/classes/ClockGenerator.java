package kurs.classes;

import kurs.interfaces.ClockListener;

import java.util.ArrayList;
import java.util.TimerTask;

public class ClockGenerator extends TimerTask {
    ArrayList<ClockListener> listenersList = new ArrayList<>();

    private static boolean active = false;
    private static int tick;
    public static int getTick() {
        return tick;
    }
    public static void nextTick(){
        tick++;
    }
    public static void stop(){active = false; tick = 0;}

    @Override
    public void run() {
        active = true;
        while (active)
        {
            try {
                Thread.sleep(1000L * Configuration.TICK_DURATION);
            } catch (InterruptedException e) {
                stop();
                return;
            }
            nextTick();

            for(ClockListener listener : listenersList)
                listener.onTick();

        }
    }

    public void addListener(ClockListener listener)
    {
        listenersList.add(listener);
    }
}
