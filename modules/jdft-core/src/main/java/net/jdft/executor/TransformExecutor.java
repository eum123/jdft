package net.jdft.executor;

import java.util.Map;

import net.jdft.FormatTransformation;
import net.jdft.config.ConversionConfig;
import net.jdft.converter.reader.Reader;
import net.jdft.converter.writer.Writer;
import net.jdft.util.StopWatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransformExecutor {
	private static final Logger log = LoggerFactory.getLogger(TransformExecutor.class);

	private FormatTransformation conversion = null;

	private StopWatch stopWatch = new StopWatch();

	public TransformExecutor(FormatTransformation conversion) {
		this.conversion = conversion;
	}

	/**
	 * map을 설정에 맞는 형식으로 변환한다.
	 * @param id
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public Object write(String id, Map data) throws Exception {
		conversion.getLock().lock();

		try {
			if (conversion.isReload()) {
				conversion.getCondition().await();
			}

			stopWatch.start();

			return write1(id, data);

		} finally {
			conversion.getLock().unlock();
			log.debug("ConversionExecutoer(id:" + id + ") write execute time : " + stopWatch.split()
					+ " ms");
		}
	}

	private Object write1(String id, Map data) throws Exception {
		ConversionConfig config = conversion.getFormatConversionConfig().getGroupConfig()
				.getConversionConfig(id);
		String writeFormat = config.getToConfig().getFormat();

		if (conversion.getConverterRegister().getConverter(writeFormat) == null) {
			throw new Exception("converter(" + id + ")에 해당하는 writer(" + writeFormat + ")가 없음");
		}

		Writer writer = conversion.getConverterRegister().getConverter(writeFormat).getWriter();
		return writer.write(data, config.getToConfig());
	}

	/**
	 * 입력한 데이터를 map으로 변환한다.
	 * 
	 * @param id
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public Map read(String id, Object data) throws Exception {
		conversion.getLock().lock();

		try {
			if (conversion.isReload()) {
				conversion.getCondition().await();
			}

			stopWatch.start();

			return read1(id, data);

		} finally {
			conversion.getLock().unlock();
			log.debug("ConversionExecutoer(id:" + id + ") read execute time : " + stopWatch.split()
					+ " ms");
		}
	}

	private Map read1(String id, Object data) throws Exception {
		ConversionConfig config = conversion.getFormatConversionConfig().getGroupConfig()
				.getConversionConfig(id);

		String readFormat = config.getFromConfig().getFormat();

		if (conversion.getConverterRegister().getConverter(readFormat) == null) {
			throw new Exception("converter(" + id + ")에 해당하는 reader(" + readFormat + ")가 없음");
		}

		Reader reader = conversion.getConverterRegister().getConverter(readFormat).getReader();
		return reader.read(data, config.getFromConfig());
	}

	/**
	 * 입력한 데이터를 설정에 맞는 format으로 변환한다.
	 * 
	 * @param id
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public Object convert(String id, Object data) throws Exception {
		conversion.getLock().lock();

		try {
			if (conversion.isReload()) {
				conversion.getCondition().await();
			}

			stopWatch.start();

			return convert1(id, data);

		} finally {
			conversion.getLock().unlock();
			log.debug("ConversionExecutoer(id:" + id + ") convert execute time : "
					+ stopWatch.stop() + " ms");
		}
	}

	private Object convert1(String id, Object data) throws Exception {
		
		Map map = read1(id, data);
		
		return write(id, map);
	}
}
