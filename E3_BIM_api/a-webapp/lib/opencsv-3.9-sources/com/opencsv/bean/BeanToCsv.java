package com.opencsv.bean;

/*
 Copyright 2007 Kyle Miller.

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */

import com.opencsv.CSVWriter;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Allows exporting content from Java beans to a new CSV spreadsheet file.
 *
 * @author Kali &lt;kali.tystrit@gmail.com&gt;
 * @author Andrew Rucker Jones
 * @param <T> Type of object that is being processed.
 * @deprecated Please use the much easier and more powerful {@link StatefulBeanToCsv}.
 */
@Deprecated
public class BeanToCsv<T> {

    /**
     * Default constructor.
     * It is, however, unnecessary to instantiate this class. This constructor
     * is preserved for backward compatibility.
     * @deprecated All methods in this class are static. Simply use them that way.
     */
    public BeanToCsv() {}

    /**
     * Writes all the objects, one at a time, to a created
     * {@link java.io.Writer Writer} using the passed in strategy.
     *
     * @param mapper  Mapping strategy for the bean.
     * @param writer  Writer object used to construct the CSVWriter.
     * @param objects List of objects to write.
     * @param <T> Type of object that is being processed.
     * @return False if there are no objects to process, true otherwise.
     */
    public static <T> boolean write(MappingStrategy<T> mapper, Writer writer,
                         List<? extends T> objects) {
        return write(mapper, new CSVWriter(writer), objects);
    }

    /**
     * Writes an single object allowing users to write large number of objects without worrying about
     * a large collection filling up memory.  However it is up to the user to determine if the header needs
     * to be written or not.
     *
     * @param mapper      Mapping Strategy for the bean
     * @param csv         CSVWriter
     * @param bean        Object to be written.
     * @param writeHeader If the header needs to be written or not.
     * @param <T> Type of object that is being processed.
     * @return False if no object is written (null object) true otherwise.
     */
    public static <T> boolean write(MappingStrategy<T> mapper, CSVWriter csv, T bean, boolean writeHeader) {
        if (bean == null) {
            return false;
        }

        try {
            if (writeHeader) {
                csv.writeNext(processHeader(mapper));
            }
            String[] line = processObject(findGetters(mapper), bean);
            csv.writeNext(line);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Error writing bean!", e);
        }
    }

    /**
     * Writes all the objects, one at a time, to the CSVWriter using the passed
     * in Strategy.
     * @param mapper Mapping strategy for the bean.
     * @param csv CSVWriter
     * @param objects List of objects to write.
     * @param <T> Type of object that is being processed.
     * @return False if there are no objects to process, true otherwise.
     */
    public static <T> boolean write(MappingStrategy<T> mapper, CSVWriter csv, List<? extends T> objects) {
        if (objects == null || objects.isEmpty()) {
            return false;
        }

        try {
            csv.writeNext(processHeader(mapper));
            List<Method> getters = findGetters(mapper);
            processAndWriteObjects(csv, objects, getters);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Error writing CSV!", e);
        }
    }

    /**
     * Processes a list of objects.
     * @param csv CSVWriter
     * @param objects List of objects to process
     * @param getters List of getter methods to retrieve the data from the objects.
     * @throws IntrospectionException Thrown if there is a failure in introspection.
     * @throws IllegalAccessException Thrown if there is a failure in introspection.
     * @throws InvocationTargetException Thrown if there is a failure in introspection.
     */
    private static <T> void processAndWriteObjects(CSVWriter csv, List<? extends T> objects, List<Method> getters) throws IntrospectionException, IllegalAccessException, InvocationTargetException {
        for (Object obj : objects) {
            String[] line = processObject(getters, obj);
            csv.writeNext(line);
        }
    }

    /**
     * Processes the header for the bean.
     * @param mapper MappingStrategy for the bean
     * @param <T> Type of object that is being processed.
     * @return String array with header values.
     * @throws IntrospectionException Thrown if there is a failure in introspection.
     */
    protected static <T> String[] processHeader(MappingStrategy<T> mapper) throws IntrospectionException {
        List<String> values = new ArrayList<String>();
        int i = 0;
        PropertyDescriptor prop = mapper.findDescriptor(i);
        while (prop != null) {
            values.add(prop.getName());
            prop = mapper.findDescriptor(++i);
        }
        return values.toArray(new String[values.size()]);
    }

    /**
     * Retrieve all the information out of an object.
     * @param getters List of methods to retrieve information.
     * @param bean Object to get the information from.
     * @param <T> Type of the bean to be written
     * @return String array containing the information from the object
     * @throws IntrospectionException Thrown by error in introspection.
     * @throws IllegalAccessException Thrown by error in introspection.
     * @throws InvocationTargetException Thrown by error in introspection.
     */
    protected static <T> String[] processObject(List<Method> getters, T bean) throws IntrospectionException,
            IllegalAccessException, InvocationTargetException {
        List<String> values = new ArrayList<String>(getters.size());
        // retrieve bean values
        for (Method getter : getters) {
            Object value = getter.invoke(bean, (Object[]) null);
            if (value == null) {
                values.add("null");
            } else {
                values.add(value.toString());
            }
        }
        return values.toArray(new String[values.size()]);
    }

    /**
     * Build getters list from provided mapper.
     * @param mapper MappingStrategy for the bean
     * @param <T> Type of object that is being processed.
     * @return List of methods for getting the data in the bean.
     * @throws IntrospectionException Thrown if there is a failure in introspection.
     */
    private static <T> List<Method> findGetters(MappingStrategy<T> mapper)
            throws IntrospectionException {
        int i = 0;
        PropertyDescriptor prop = mapper.findDescriptor(i);
        // build getters methods list
        List<Method> readers = new ArrayList<Method>();
        while (prop != null) {
            readers.add(prop.getReadMethod());
            prop = mapper.findDescriptor(++i);
        }
        return readers;
    }

}
