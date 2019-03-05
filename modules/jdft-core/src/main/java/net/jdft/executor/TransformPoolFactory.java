package net.jdft.executor;

import net.jdft.FormatTransformation;

import org.apache.commons.pool.BasePoolableObjectFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransformPoolFactory extends
		BasePoolableObjectFactory<TransformExecutor> {

	private static final Logger log = LoggerFactory
			.getLogger(TransformPoolFactory.class);

	private FormatTransformation conversion = null;

	public TransformPoolFactory(FormatTransformation conversion) {
		this.conversion = conversion;
	}

	@Override
	public TransformExecutor makeObject() throws Exception {

		log.debug("CREATE ConversionExecutor");

		return new TransformExecutor(conversion);
	}
}
