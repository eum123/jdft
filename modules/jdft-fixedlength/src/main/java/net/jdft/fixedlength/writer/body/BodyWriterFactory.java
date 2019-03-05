package net.jdft.fixedlength.writer.body;

import net.jdft.config.body.BodyConfig;
import net.jdft.constants.DataFormatConstants;
import net.jdft.converter.writer.body.BodyWriter;
import net.jdft.delimiter.writer.body.DelimiterBodyWriter;
import net.jdft.xml.writer.body.XmlBodyWriter;

import org.jdom.IllegalDataException;

public class BodyWriterFactory {
	public static BodyWriter newInstance(BodyConfig config) {
		if (config.getFormat().equals(DataFormatConstants.FIXED_LENGTH)) {
			return new FixedLengthBodyWriter();
		} else if(config.getFormat().equals(DataFormatConstants.XML)) {
			return new XmlBodyWriter();
		} else if(config.getFormat().equals(DataFormatConstants.DELIMITER)) {
			return new DelimiterBodyWriter();
		} else {
			throw new IllegalDataException("not supported format : "
					+ config.getFormat());
		}
	}
}
