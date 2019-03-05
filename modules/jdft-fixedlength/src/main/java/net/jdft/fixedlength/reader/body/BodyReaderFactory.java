package net.jdft.fixedlength.reader.body;

import net.jdft.config.body.BodyConfig;
import net.jdft.constants.DataFormatConstants;
import net.jdft.converter.reader.body.BodyReader;
import net.jdft.delimiter.reader.body.DelimiterBodyReader;
import net.jdft.xml.reader.body.XmlBodyReader;

import org.jdom.IllegalDataException;

public class BodyReaderFactory {
	public static BodyReader newInstance(BodyConfig config) {
		if (config.getFormat().equals(DataFormatConstants.FIXED_LENGTH)) {
			return new FixedLengthBodyReader();
		} else if(config.getFormat().equals(DataFormatConstants.XML)) {
			return new XmlBodyReader();
		} else if(config.getFormat().equals(DataFormatConstants.DELIMITER)) {
			return new DelimiterBodyReader();
		} else {
			throw new IllegalDataException("not supported format : "
					+ config.getFormat());
		}
	}
}
