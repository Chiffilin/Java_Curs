package kurs.classes;


import kurs.classes.Configuration;
import kurs.classes.Process;

import java.util.ArrayList;
import java.util.List;

public class MemoryManager {

    private static List<MemoryBlock> memoryBlocks = new ArrayList<>();

    public static int findFreeMemoryBlock(int size) {
        int start = -1;
        for (MemoryBlock memoryBlock : getFreeMemoryBlocks()) {
            if (memoryBlock.size() >= size) {
                start = memoryBlock.start;
                break;
            }
        }

        return start;
    }

    private static List<MemoryBlock> getFreeMemoryBlocks() {
        memoryBlocks.sort(MemoryBlock.byStart);

        List<MemoryBlock> freeBlocks = new ArrayList<>();
        int current = 0;
        for (MemoryBlock allocatedBlock : memoryBlocks) {
            if (current < allocatedBlock.start) {
                MemoryBlock freeBlock = new MemoryBlock(current, allocatedBlock.start-1, null);
                freeBlocks.add(freeBlock);
            }

            current = allocatedBlock.end + 1;
        }

        if (current < Configuration.TOTAL_MEMORY) {
            MemoryBlock freeBlock = new MemoryBlock(current, Configuration.TOTAL_MEMORY-1, null);
            freeBlocks.add(freeBlock);
        }

        return freeBlocks;
    }

    public static boolean allocateMemory(Process process) {
        int start = findFreeMemoryBlock(process.getMemorySize());

        if (start != -1) {
            addMemoryBlock(new MemoryBlock(start, start + process.getMemorySize()-1, process));
            return true;
        } else
            return false;
    }

    public static void releaseMemory(Process process) {
        memoryBlocks.removeIf(mb -> mb.process == process);
    }

    public static void clearMemory() {
        memoryBlocks.clear();
    }

    public static void addMemoryBlock(MemoryBlock memoryBlock) {
        memoryBlocks.add(memoryBlock);
    }

    @Override
    public String toString() {
        return "MemoryManager{" + memoryBlocks + "}";
    }
}
