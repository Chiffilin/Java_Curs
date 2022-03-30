package kurs.classes;

import kurs.classes.Process;

import java.util.Comparator;

public class MemoryBlock {
    int start;
    int end;
    Process process;

    public static Comparator<MemoryBlock> byStart = Comparator.comparingInt(o -> o.start);

    public MemoryBlock(int start, int end, Process process) {
        this.start=start;
        this.end=end;
        this.process=process;
    }

    public int size() {
        return this.end - this.start + 1;
    }

    @Override
    public String toString() {
        return "MemoryBlock{" +"start=" + start +", end=" + end +", process="+process+"}";
    }
}
