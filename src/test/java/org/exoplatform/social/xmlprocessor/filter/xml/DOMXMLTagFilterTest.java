/*
 * Copyright (C) 2003-2011 eXo Platform SAS.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.exoplatform.social.xmlprocessor.filter.xml;

import org.exoplatform.social.xmlprocessor.Filter;
import org.exoplatform.social.xmlprocessor.filter.xml.DOMXMLTagFilter;
import org.exoplatform.social.xmlprocessor.filter.xml.XMLTagFilterPolicy;
import org.exoplatform.social.xmlprocessor.filter.xml.model.Node;
import org.exoplatform.social.xmlprocessor.filter.xml.util.DOMParser;
import org.exoplatform.social.xmlprocessor.filter.xml.util.Tokenizer;

import junit.framework.TestCase;

public class DOMXMLTagFilterTest extends TestCase {

  public void testDOMXMLTagFilter() {
    XMLTagFilterPolicy tagFilterPolicyBasicText = new XMLTagFilterPolicy();
    tagFilterPolicyBasicText.addAllowTags("b", "i", "a", "br");
    tagFilterPolicyBasicText.addAllowAttributes("a", "href");
    Node rootNode;
    assertEquals(
        "",
        new DOMXMLTagFilter(tagFilterPolicyBasicText).doFilter(
            DOMParser.createDOMTree(new Node(), Tokenizer.tokenize("")))
            .toString());
    assertEquals(
        "hello 1",
        new DOMXMLTagFilter(tagFilterPolicyBasicText).doFilter(
            DOMParser.createDOMTree(new Node(), Tokenizer.tokenize("hello 1")))
            .toString());
    
    rootNode = new Node();
    assertEquals(
        "<c><a href=\"http://\">hello2</a></c>",
          new DOMXMLTagFilter(tagFilterPolicyBasicText).doFilter(
              DOMParser.createDOMTree(rootNode,
                  Tokenizer.tokenize("<c><a HREF=\"http://\" id=\"hello\">hello2</a></c>")))
                    .toString());
    assertEquals(3,rootNode.getChildNodes().size());
    assertEquals("<c>",rootNode.getChildNodes().get(0).getContent());
    assertEquals("</c>",rootNode.getChildNodes().get(2).getContent());
    assertEquals("a",rootNode.getChildNodes().get(1).getTitle());
    assertEquals("hello2",rootNode.getChildNodes().get(1).getChildNodes().get(0).toString());
    
    rootNode = new Node();
    assertEquals(
        "<c><i>hello world</i><a href=\"http://\">hello2</a></c>",
          new DOMXMLTagFilter(tagFilterPolicyBasicText).doFilter(
              DOMParser.createDOMTree(rootNode,
                  Tokenizer.tokenize("<c><i>hello world</i><a HREF=\"http://\" id=\"hello\">hello2</a></c>")))
                    .toString());
    assertEquals(4,rootNode.getChildNodes().size());
    assertEquals("<c>",rootNode.getChildNodes().get(0).getContent());
    assertEquals("</c>",rootNode.getChildNodes().get(3).getContent());
    assertEquals("i",rootNode.getChildNodes().get(1).getTitle());
    assertEquals("hello world",rootNode.getChildNodes().get(1).getChildNodes().get(0).toString());
    assertEquals("a",rootNode.getChildNodes().get(2).getTitle());
    assertEquals("hello2",rootNode.getChildNodes().get(2).getChildNodes().get(0).toString());
  }
}
