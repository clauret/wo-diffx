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
package org.pageseeder.diffx.core;

import org.junit.Assert;
import org.junit.Test;
import org.pageseeder.diffx.DiffXException;
import org.pageseeder.diffx.action.Action;
import org.pageseeder.diffx.action.Actions;
import org.pageseeder.diffx.config.DiffXConfig;
import org.pageseeder.diffx.config.TextGranularity;
import org.pageseeder.diffx.event.DiffXEvent;
import org.pageseeder.diffx.test.Events;

import java.io.IOException;
import java.util.List;

/**
 * Test case for progressive XML processor.
 *
 * @author Christophe Lauret
 * @version 0.9.0
 */
public final class ProgressiveXMLProcessorTest extends BaseProcessorLevel1Test {

  @Override
  public DiffProcessor getDiffProcessor() {
    return new ProgressiveXMLProcessor();
  }

  /**
   * @throws IOException    Should an I/O exception occur.
   * @throws DiffXException Should an error occur while parsing XML.
   */
  @Test
  public final void testSticky() throws IOException, DiffXException {
    String xml1 = "<a>a white cat</a>";
    String xml2 = "<a>a black hat</a>";
    String expA = "<a>a+( white cat)-( black hat)</a>";
    String expB = "<a>a-( black hat)+( white cat)</a>";
    assertDiffXMLProgOK2(xml1, xml2, expA, expB);
  }

  /**
   * @throws IOException    Should an I/O exception occur.
   * @throws DiffXException Should an error occur while parsing XML.
   */
  @Test
  public final void testList() throws IOException, DiffXException {
    String xml1 = "<ul><li>blue</li><li>red</li><li>green</li></ul>";
    String xml2 = "<ul><li>black</li><li>red</li><li>green</li></ul>";
    String expA = "<ul><li>+(blue)-(black)</li><li>red</li><li>green</li></ul>";
    String expB = "<ul><li>-(black)+(blue)</li><li>red</li><li>green</li></ul>";
    assertDiffXMLProgOK2(xml1, xml2, expA, expB);
  }

  /**
   * Asserts that the Diff-X operation for XML meets expectations.
   *
   * @param xml1 The first XML to compare with diffx.
   * @param xml2 The first XML to compare with diffx.
   * @param exp  The expected result as formatted by the TestFormatter.
   * @throws IOException    Should an I/O exception occur.
   * @throws DiffXException Should an error occur while parsing XML.
   */
  public final void assertDiffXMLProgOK2(String xml1, String xml2, String ...exp)
      throws IOException, DiffXException {
    // Record XML
    DiffXConfig config = new DiffXConfig();
    config.setGranularity(TextGranularity.TEXT);
    List<? extends DiffXEvent> seq1 = Events.recordXMLEvents(xml1, config);
    List<? extends DiffXEvent> seq2 = Events.recordXMLEvents(xml2, config);

    // Process as list of actions
    List<Action> actions = diffToActions(seq1, seq2);
    try {
      assertDiffIsCorrect(seq1, seq2, actions);
      assertDiffIsWellFormedXML(actions);
      assertMatchTestOutput(actions, exp);
    } catch (AssertionError ex) {
      printXMLErrorDetails(xml1, xml2, exp, toXML(actions), actions);
      throw ex;
    }
  }

}
