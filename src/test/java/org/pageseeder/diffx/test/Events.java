/*
 * Copyright 2010-2015 Allette Systems (Australia)
 * http://www.allette.com.au
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
package org.pageseeder.diffx.test;

import org.pageseeder.diffx.event.*;
import org.pageseeder.diffx.event.impl.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for events and testing.
 *
 * @author Christophe Lauret
 * @version 0.9.0
 */
public final class Events {

  /**
   * Prevents creation of instances.
   */
  private Events() {
  }

  /**
   * Returns a simple representation for each event in order to recognise them depending on
   * their class.
   *
   * <p>This method will return <code>null</code> if it does not know how to format it.
   *
   * @param e The event to format
   * @return Its 'abstract' representation or <code>null</code>.
   */
  public static String toAbstractString(DiffXEvent e) {
    // TODO: handle unknown event implementations nicely.
    // an element to open
    if (e instanceof OpenElementEvent) return '<' + ((OpenElementEvent) e).getName() + '>';
    // an element to close
    if (e instanceof CloseElementEvent) return "</" + ((CloseElementEvent) e).getName() + '>';
    // an element
    if (e instanceof ElementEvent) return '<' + ((ElementEvent) e).getName() + "/>";
    // an attribute
    if (e instanceof AttributeEvent)
      return "@{" + ((AttributeEvent) e).getName() + '=' + ((AttributeEvent) e).getValue() + '}';
    // a word
    if (e instanceof WordEvent) return "$w{" + ((CharactersEventBase) e).getCharacters() + '}';
    // a white space event
    if (e instanceof SpaceEvent) return "$s{" + ((CharactersEventBase) e).getCharacters() + '}';
    // a single character
    if (e instanceof CharEvent) return "$c{" + ((CharactersEventBase) e).getCharacters() + '}';
    // an ignorable space event
    if (e instanceof IgnorableSpaceEvent) return "$i{" + ((IgnorableSpaceEvent) e).getCharacters() + '}';
    // a single line
    if (e instanceof LineEvent) return "$L" + ((LineEvent) e).getLineNumber();
    return null;
  }

  public static TextEvent toTextEvent(String text) {
    if (text.matches("\\s+")) {
      return new IgnorableSpaceEvent(text);
    }
    return new WordEvent(text);
  }

  public static List<TextEvent> toTextEvents(String... words) {
    List<TextEvent> events = new ArrayList<>();
    for (String word : words) {
      events.add(toTextEvent(word));
    }
    return events;
  }
}