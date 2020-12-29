package org.firstinspires.ftc.teamcode;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DelayCommand {

    ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
    //To run a method as a runnable event, do this:
    /*
    Runnable targetMethod = new Runnable() {
    public void run() {
        myObject.objectMethod();
    }};
    */
    //As a novice programmer, I'm not sure about what this really does and how safe it is.

    //Delay is in milliseconds. TimeUnit can be changed.
    //In event, input your method converted to a Runnable.

    public void delay(Runnable event, int delay){
        executorService.schedule(event, delay, TimeUnit.MILLISECONDS);
    }
}
