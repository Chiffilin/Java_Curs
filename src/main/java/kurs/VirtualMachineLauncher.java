package kurs;

import kurs.classes.VirtualMachine;

public class VirtualMachineLauncher implements Runnable {
    public static VirtualMachine vm;

    @Override
    public void run() {
        vm = new VirtualMachine();
        vm.start();
    }
}
