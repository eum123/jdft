package net.jdft;

import java.io.InputStream;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import net.jdft.config.FormatConversionConfig;
import net.jdft.converter.ConverterRegistry;
import net.jdft.executor.TransformExecutor;
import net.jdft.executor.TransformPoolFactory;
import net.jdft.util.StopWatch;

import org.apache.commons.pool.impl.GenericKeyedObjectPool;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.commons.pool.impl.GenericObjectPool.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author manjin
 *
 */
public class FormatTransformation implements FormatTransformationMBean {
	private static final transient Logger log = LoggerFactory.getLogger(FormatTransformation.class);
	
	private String path = null;
	private InputStream configInputStream = null;
	private GenericObjectPool<TransformExecutor> pool = new GenericObjectPool<TransformExecutor>();
	
	private AtomicBoolean isReload = new AtomicBoolean(false);
	
	private final Lock lock = new ReentrantLock();
	private Condition condition = lock.newCondition();
	
	private FormatConversionConfig formatConversionConfig = null;
	
	private ConverterRegistry converterRegister = new ConverterRegistry();
	
	private StopWatch watch = new StopWatch();
	
	private String state = "stop";
	
	/** Load 완료 시간 */
	private long loadTimestamp = 0;
	/** load 처리 시간 */
	private long processingTime = 0;
	
	public ConverterRegistry getConverterRegister() {
		return converterRegister;
	}

	public void start() throws Exception {
		
		state = "starting";
		
		converterRegister.load();
		
		load();
		
		setPool();
		
		state = "start";
	}
	
	private void setPool() {
		Config poolConfig = new Config();
		poolConfig.maxActive = formatConversionConfig.getPoolConfig().getMaxActive();
		poolConfig.maxIdle = formatConversionConfig.getPoolConfig().getMaxIdle();
		poolConfig.maxWait = formatConversionConfig.getPoolConfig().getMaxWait();
		poolConfig.whenExhaustedAction = formatConversionConfig.getPoolConfig().getWhenExhaustedAction();

		pool = new GenericObjectPool<TransformExecutor>(
				new TransformPoolFactory(this), poolConfig);
	}
	
	public void load() throws Exception {
		
		isReload.set(true);
		lock.lock();
		
		try {
			watch.start();
			
			if(path != null) {
				formatConversionConfig = FormatConversionConfig.create(path);
			} else if(configInputStream != null) {
				formatConversionConfig = FormatConversionConfig.create(configInputStream);
			} else {
				throw new IllegalArgumentException("JDFT : There is No path or inputStream Data. ");
			}
			
			processingTime = watch.stop();
			
			log.info("load complete : {}ms", processingTime);
			
			loadTimestamp = System.currentTimeMillis();
		} finally {
			isReload.set(false);
			condition.signalAll();
			lock.unlock();
		}
	}
	

	public TransformExecutor getExecutor() throws Exception {
		lock.lock();

		try {
			if (isReload.get()) {
				condition.await();
			}
			return pool.borrowObject();
		
		} finally {
			lock.unlock();
		}
	}
	
	public void release(TransformExecutor executor) throws Exception {
		if(executor != null) {
			pool.returnObject(executor);
		}
	}
	
	public void close() throws Exception {
		state = "stopping";
		pool.close();
		
		state = "stop";
	}
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	public void setPath(InputStream configInputStream) {
		this.configInputStream = configInputStream;
	}

	public int getMaxActive() {
		return pool.getMaxActive();
	}

	public int getMaxIdle() {
		return pool.getMaxIdle();
	}

	public long getMaxWait() {
		return pool.getMaxWait();
	}

	public boolean isReload() {
		return isReload.get();
	}

	public int getNumActive() {
		return pool.getNumActive();
	}
	
	public int getNumIdle() {
		return pool.getNumIdle();
	}

	public Lock getLock() {
		return lock;
	}

	public Condition getCondition() {
		return condition;
	}

	public FormatConversionConfig getFormatConversionConfig() {
		return formatConversionConfig;
	}
	
	public List<String> getLoadedResources() {
		return converterRegister.getLoadedResources();
	}

	public long getLoadTimestamp() {
		return loadTimestamp;
	}

	public long getProcessingTime() {
		return processingTime;
	}

	public String getState() {
		return state;
	}
}
