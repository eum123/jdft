package net.jdft.function.sequence;

public class IntSequenceGenerator extends Sequence {
	private int seq = (int) System.currentTimeMillis();

    public IntSequenceGenerator() {
    	 if (seq < 0) {
             seq += Integer.MAX_VALUE;
         }
    }

    protected String generate() {
        String v = Integer.toString(seq++);

        if (seq < 0) {
            seq = 0;
        }

        return v;
    }
}
