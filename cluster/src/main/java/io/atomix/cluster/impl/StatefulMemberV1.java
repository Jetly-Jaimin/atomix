/*
 * Copyright 2018-present Open Networking Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.atomix.cluster.impl;

import io.atomix.cluster.MemberId;
import io.atomix.utils.Version;
import io.atomix.utils.net.Address;

import java.util.Properties;

/**
 * Version 1 stateful member.
 */
public class StatefulMemberV1 {
  final MemberId id;
  final Address address;
  final String zone;
  final String rack;
  final String host;
  final Properties properties;
  final Version version;
  final boolean active;
  final boolean reachable;

  StatefulMemberV1(MemberId id, Address address, String zone, String rack, String host, Properties properties, Version version, boolean active, boolean reachable) {
    this.id = id;
    this.address = address;
    this.zone = zone;
    this.rack = rack;
    this.host = host;
    this.properties = properties;
    this.version = version;
    this.active = active;
    this.reachable = reachable;
  }
}
