package kurs.classes;

import kurs.classes.Configuration;
import kurs.classes.Utils;
import kurs.classes.ClockGenerator;

public class Process {
    private int id;
    private String name;
    private int priority;
    private Status status;
    private int ticksNeeded;
    private int memorySize;
    private final int startTick;
    private int ticksExecuted;

    public Process(int id, String name) {
        this.id = id;
        this.name = name;
        this.memorySize = Utils.getRandInt(Configuration.MIN_PROCESS_MEMORY_SIZE, Configuration.MAX_PROCESS_MEMORY_SIZE);
        this.priority= Utils.getRandInt(Configuration.MAX_PRIORITY);
        this.startTick = ClockGenerator.getTick();
        this.ticksNeeded = Utils.getRandInt(Configuration.MIN_PROCESS_DURATION, Configuration.MAX_PROCESS_DURATION);
        this.ticksExecuted = 0;
        this.status = Status.ready;
    }

    public Process(int id) {
        this(id, "proc-"+id);
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int incExecutedTicks() {
        ticksExecuted++;
//        System.out.println("Increment ticks for PID " + id + ": " + ticksExecuted);
        return ticksExecuted;
    }

    public void start() {
        setStatus(Status.running);
    }

    public boolean isCompleted(){
        return ticksExecuted == ticksNeeded;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPriority() {
        return priority;
    }

    public int getTicksNeeded() {
        return ticksNeeded;
    }

    public int getMemorySize() {
        return memorySize;
    }

    public int getStartTick() {
        return startTick;
    }

    public int getTicksExecuted() {
        return ticksExecuted;
    }

    public Status getStatus(){return status;}


    @Override
    public String toString() {
        return "Process" +
                "\tid=" + id +
                "\tname=" + name +
                "\tpriority=" + priority +
                "\tstate=" + status +
                "\ttick=" + ticksNeeded +
                "\tmemory=" + memorySize +
                "\tstartTick=" + startTick +
                "\tticksExecuted=" + ticksExecuted +'\n';
    }

}
