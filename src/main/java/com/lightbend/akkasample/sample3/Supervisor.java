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

import akka.actor.*;
import akka.japi.pf.DeciderBuilder;
import akka.japi.pf.ReceiveBuilder;
import scala.concurrent.duration.Duration;
import static akka.actor.SupervisorStrategy.*;
import java.util.concurrent.TimeUnit;

public class Supervisor extends AbstractLoggingActor {

    final ActorRef child = getContext().actorOf(NonTrustWorthyChild.props(), "child");
    private int counter = 0;

    {

        receive(ReceiveBuilder
                .match(NonTrustWorthyChild.Command.class, this::forward)
                .build()
        );

    }

    private void forward(NonTrustWorthyChild.Command command) {
        counter++;
        log().info("forward message to child " + counter);
        child.forward(command, getContext());
    }

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return new OneForOneStrategy(
                10,
                Duration.create(10, TimeUnit.SECONDS),
                DeciderBuilder
                        .match(RuntimeException.class, ex -> escalate())
                        .build()
        );
    }

    public static Props props() {
        return Props.create(Supervisor.class);
    }
}
