package net.jdft.function.sequence;

public abstract class Sequence {    
    /**
     * 일련번호를 구한다.
     * @return
     */
    public synchronized final String nextValue(int minLength, int maxLength) {
    	if(minLength <= 0) {
    		throw new IllegalArgumentException("Minimum length has greater than 0.");
    	}
    	if(maxLength <= 0) {
    		throw new IllegalArgumentException("Maximum length has greater than 0.");
    	}
    	if(minLength > maxLength) {
    		throw new IllegalArgumentException("Maximum length has greater than minimum length.");
    	}
    	
        
        return resize(minLength, maxLength, generate());
    }

    private String resize(int minLen, int maxLen, String v) {
        int vLen = v.length();

        if (vLen < minLen) {
            StringBuffer buf = new StringBuffer(minLen);

            for (int i = minLen - vLen; i > 0; i--) {
                buf.append('0');
            }

            buf.append(v);
            return buf.toString();
        }

        if (vLen > maxLen) {
            return v.substring(vLen - maxLen);
        }

        return v;
    }

    protected abstract String generate();
}
