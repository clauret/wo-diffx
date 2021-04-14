/*
 * Copyright 2010-2021 Allette Systems (Australia)
 *    http://www.allette.com.au
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.pageseeder.diffx.event;

public interface Namespaceable {

  /**
   * Returns the local name of the attribute.
   *
   * <p>This method should never return <code>null</code>.
   *
   * @return The local name of the attribute.
   */
  String getName();

  /**
   * Returns the namespace URI the attribute belongs to.
   *
   * <p>This method should return <code>null</code> if the implementation
   * is not namespace aware or if the attribute is not bound to any namespace.
   *
   * @return The namespace URI the attribute belongs to or <code>null</code>.
   */
  String getURI();

}
