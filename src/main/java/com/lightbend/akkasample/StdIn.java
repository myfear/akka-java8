/*
 * Copyright (C) 2009-2017 Lightbend Inc. (https://www.lightbend.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this project except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0.
 * 
 * Unless required by applicable law or agreed to in writing, 
 * software distributed under the License is distributed on an 
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific 
 * language governing permissions and limitations under the License.
 */
package com.lightbend.akkasample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class StdIn {

    public static String readLine() {
        // written to make it work in intellij as System.console() is null
        // when run inside the IDE
        try (BufferedReader in = new BufferedReader(new InputStreamReader(System.in))) {
            return in.readLine();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
