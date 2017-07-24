/*
 * Copyright 2016 Andrew Rucker Jones.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.opencsv.bean.customconverter;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

/**
 * This is the same as {@link com.opencsv.bean.customconverter.ConvertGermanToBoolean},
 * except that it throws {@link com.opencsv.exceptions.CsvRequiredFieldEmptyException}
 * if the input is empty.
 *
 * @param <T> Type of the bean to be manipulated
 * 
 * @author Andrew Rucker Jones
 * @since 3.8
 */
public class ConvertGermanToBooleanRequired<T> extends ConvertGermanToBoolean<T> {

    /**
     * Silence code style checker by adding a useless constructor.
     */
    public ConvertGermanToBooleanRequired() {
    }

    /**
     * @throws CsvRequiredFieldEmptyException If the input is empty
     */
    // The rest of the JavaDoc is automatically inherited from the base class.
    @Override
    protected Object convert(String value)
            throws CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        Object o = super.convert(value);
        if (o == null) {
            throw new CsvRequiredFieldEmptyException();
        }
        return o;
    }
    
    /**
     * @throws CsvRequiredFieldEmptyException If value is null, because this
     *   converter requires a value
     */
    // The rest of the JavaDoc is automatically inherited from the base class.
    @Override
    protected String convertToWrite(Object value)
            throws CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        if(value == null) {
            throw new CsvRequiredFieldEmptyException();
        }
        return super.convertToWrite(value);
    }
}
