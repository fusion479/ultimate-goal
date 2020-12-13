package org.firstinspires.ftc.teamcode.opMode;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DelayCommand {

    ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
    public void delay(Runnable event, int delay){
        executorService.schedule(event, delay, TimeUnit.SECONDS);
    }
}
