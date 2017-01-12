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
package com.lightbend.akkasample.sample3;

import akka.actor.AbstractLoggingActor;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;

public class NonTrustWorthyChild extends AbstractLoggingActor {

    static class Command {
    }

    private long messages = 0L;

    {
        receive(ReceiveBuilder
                .match(Command.class, this::onCommand)
                .build()
        );
    }

    private void onCommand(Command c) {
        messages++;
        if (messages % 4 == 0) {
            throw new RuntimeException("Oh no, I got four commands, I can't handle any more");
        } else {
            log().info("Got a command " + messages);
        }
    }

    public static Props props() {
        return Props.create(NonTrustWorthyChild.class);
    }

}
