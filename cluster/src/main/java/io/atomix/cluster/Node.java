/*
 * Copyright 2014-present Open Networking Foundation
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
package io.atomix.cluster;

import io.atomix.utils.config.Configured;
import io.atomix.utils.net.Address;

import java.util.Objects;

import static com.google.common.base.MoreObjects.toStringHelper;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Represents a node.
 */
public class Node implements Configured<NodeConfig> {

  /**
   * Returns a new member builder with no ID.
   *
   * @return the member builder
   */
  public static NodeBuilder builder() {
    return new NodeBuilder(new NodeConfig());
  }

  private final NodeId id;
  private final String hostname;
  private final int membershipPort;
  private transient volatile Address membershipAddress;

  public Node(NodeConfig config) {
    this.id = config.getId();
    this.hostname = config.getHostname();
    this.membershipPort = config.getMembershipPort();
  }

  protected Node(NodeId id, String hostname, int membershipPort) {
    checkArgument(membershipPort > 0, "membershipPort must be positive");
    this.id = checkNotNull(id, "id cannot be null");
    this.hostname = checkNotNull(hostname, "hostname cannot be null");
    this.membershipPort = membershipPort;
  }

  /**
   * Returns the instance identifier.
   *
   * @return instance identifier
   */
  public NodeId id() {
    return id;
  }

  /**
   * Returns the node hostname.
   *
   * @return the node's hostname
   */
  public String hostname() {
    return hostname;
  }

  /**
   * Returns the node's membership port.
   *
   * @return the node's membership port
   */
  public int membershipPort() {
    return membershipPort;
  }

  /**
   * Returns the address through which the node participates in the membership protocol.
   *
   * @return the address through which the node participates in the membership protocol
   */
  public Address membershipAddress() {
    if (membershipAddress == null) {
      synchronized (this) {
        if (membershipAddress == null) {
          membershipAddress = Address.from(hostname, membershipPort);
        }
      }
    }
    return membershipAddress;
  }

  /**
   * Returns the node address.
   *
   * @return the node address
   */
  @Deprecated
  public Address address() {
    return membershipAddress();
  }

  @Override
  public NodeConfig config() {
    return new NodeConfig()
        .setId(id)
        .setHostname(hostname)
        .setMembershipPort(membershipPort);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, hostname, membershipPort);
  }

  @Override
  public boolean equals(Object object) {
    if (object instanceof Node) {
      Node member = (Node) object;
      return member.id().equals(id())
          && member.address().equals(address());
    }
    return false;
  }

  @Override
  public String toString() {
    return toStringHelper(Node.class)
        .add("id", id)
        .add("hostname", hostname)
        .add("membershipPort", membershipPort)
        .omitNullValues()
        .toString();
  }
}
