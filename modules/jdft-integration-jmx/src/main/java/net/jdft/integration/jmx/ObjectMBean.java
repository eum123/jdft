/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.jdft.integration.jmx;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.management.Attribute;
import javax.management.AttributeChangeNotification;
import javax.management.AttributeList;
import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.InvalidAttributeValueException;
import javax.management.ListenerNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanInfo;
import javax.management.MBeanNotificationInfo;
import javax.management.MBeanRegistration;
import javax.management.MBeanServer;
import javax.management.Notification;
import javax.management.NotificationFilter;
import javax.management.NotificationListener;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.management.RuntimeOperationsException;
import javax.management.modelmbean.InvalidTargetObjectTypeException;
import javax.management.modelmbean.ModelMBean;
import javax.management.modelmbean.ModelMBeanInfo;

/**
 * A {@link ModelMBean} wrapper implementation for a POJO.
 * 
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 * 
 * @param <T>
 *            the type of the managed object
 */
public class ObjectMBean<T> implements ModelMBean, MBeanRegistration {
	private static final Map<ObjectName, Object> sources = new ConcurrentHashMap<ObjectName, Object>();
	private final T source;
	private volatile MBeanServer server;

	private volatile ObjectName name;
	
	public ObjectMBean(T source) {
		this.source = source;
	}

	public Object getAttribute(String arg0) throws AttributeNotFoundException,
			MBeanException, ReflectionException {
		// TODO Auto-generated method stub
		return null;
	}

	public AttributeList getAttributes(String[] arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public MBeanInfo getMBeanInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object invoke(String arg0, Object[] arg1, String[] arg2)
			throws MBeanException, ReflectionException {
		// TODO Auto-generated method stub
		return null;
	}

	public void setAttribute(Attribute arg0) throws AttributeNotFoundException,
			InvalidAttributeValueException, MBeanException, ReflectionException {
		// TODO Auto-generated method stub

	}

	public AttributeList setAttributes(AttributeList arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public void load() throws MBeanException, RuntimeOperationsException,
			InstanceNotFoundException {
		// TODO Auto-generated method stub

	}

	public void store() throws MBeanException, RuntimeOperationsException,
			InstanceNotFoundException {
		// TODO Auto-generated method stub

	}

	public void addAttributeChangeNotificationListener(
			NotificationListener arg0, String arg1, Object arg2)
			throws MBeanException, RuntimeOperationsException,
			IllegalArgumentException {
		// TODO Auto-generated method stub

	}

	public void removeAttributeChangeNotificationListener(
			NotificationListener arg0, String arg1) throws MBeanException,
			RuntimeOperationsException, ListenerNotFoundException {
		// TODO Auto-generated method stub

	}

	public void sendAttributeChangeNotification(AttributeChangeNotification arg0)
			throws MBeanException, RuntimeOperationsException {
		// TODO Auto-generated method stub

	}

	public void sendAttributeChangeNotification(Attribute arg0, Attribute arg1)
			throws MBeanException, RuntimeOperationsException {
		// TODO Auto-generated method stub

	}

	public void sendNotification(Notification arg0) throws MBeanException,
			RuntimeOperationsException {
		// TODO Auto-generated method stub

	}

	public void sendNotification(String arg0) throws MBeanException,
			RuntimeOperationsException {
		// TODO Auto-generated method stub

	}

	public void addNotificationListener(NotificationListener arg0,
			NotificationFilter arg1, Object arg2)
			throws IllegalArgumentException {
		// TODO Auto-generated method stub

	}

	public MBeanNotificationInfo[] getNotificationInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	public void removeNotificationListener(NotificationListener arg0)
			throws ListenerNotFoundException {
		// TODO Auto-generated method stub

	}

	public final ObjectName preRegister(MBeanServer server, ObjectName name)
			throws Exception {
		this.server = server;
		this.name = name;
		return name;
	}

	public final void postRegister(Boolean registrationDone) {
		if (registrationDone) {
			sources.put(name, source);
		}
	}

	public final void preDeregister() throws Exception {
		// Do nothing
	}

	public final void postDeregister() {
		sources.remove(name);
		this.server = null;
		this.name = null;
	}

	public void setManagedResource(Object arg0, String arg1)
			throws MBeanException, RuntimeOperationsException,
			InstanceNotFoundException, InvalidTargetObjectTypeException {
		// TODO Auto-generated method stub

	}

	public void setModelMBeanInfo(ModelMBeanInfo arg0) throws MBeanException,
			RuntimeOperationsException {
		// TODO Auto-generated method stub

	}

}