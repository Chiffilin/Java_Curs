package kurs.classes;

import kurs.interfaces.ClockListener;
import kurs.Main;

import java.util.ArrayList;

public class VirtualMachine implements ClockListener {
    private ArrayList<Process> activeProcesses;
    private ArrayList<Process> terminatedProcesses;
    private CpuQueue cpuQueue;
    private int PID = 0;

    CPU cpu;
    MemoryManager memoryManager;
    ClockGenerator clockGenerator;

    public VirtualMachine() {
        cpuQueue = new CpuQueue();
        activeProcesses = new ArrayList<>();
        terminatedProcesses = new ArrayList<>();

        this.cpu = new CPU(Configuration.CPU_CORES, this);
        this.memoryManager = new MemoryManager();
        this.clockGenerator = new ClockGenerator();

        this.clockGenerator.addListener(cpu);
        this.clockGenerator.addListener(this);
    }

    public void start() {
        this.clockGenerator.run();

        //after stopping generator
        MemoryManager.clearMemory();
    }

    public void createProcess(String name) {
        PID++;
        Process process = new Process(PID, name);
        addProcess(process);
    }

    public void createRandomProcess() {
        PID++;
        Process process = new Process(PID);
        addProcess(process);
    }

    public void addProcess(Process process) {
        if (cpuQueue.addProcess(process)) {
            activeProcesses.add(process);
        }
    }

    private void addRandomProcesses() {
        if (Utils.getRandBool()) {
            for (int i = 0; i <Configuration.MAX_RANDOM_PROCESSES; i++) {
                createRandomProcess();
            }
        }
    }

    @Override
    public String toString() {
        return "VirtualMachine{\n" + cpu + '\n' + memoryManager + '\n' + cpuQueue + "\nDone:" + terminatedProcesses + "\n}";
    }

    public CpuQueue getCpuQueue() {
        return cpuQueue;
    }

    public void terminateProcess(Process process) {
        process.setStatus(Status.terminated);
        activeProcesses.remove(process);
        terminatedProcesses.add(process);
    }

    public void updateTable() {
        Main.controller.updateTable(activeProcesses, terminatedProcesses);
    }

    @Override
    public void onTick() {
        addRandomProcesses();
        updateTable();
    }
}
