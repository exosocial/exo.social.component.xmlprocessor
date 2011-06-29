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
package org.exoplatform.social.xmlprocessor.api.impl;

import org.exoplatform.social.xmlprocessor.api.Filter;
import org.exoplatform.social.xmlprocessor.api.Processor;
import org.exoplatform.social.xmlprocessor.core.DOMContentEscapeFilter;
import org.exoplatform.social.xmlprocessor.core.DOMLineBreakerFilter;
import org.exoplatform.social.xmlprocessor.core.DOMXMLTagFilter;
import org.exoplatform.social.xmlprocessor.core.LineBreakerFilter;
import org.exoplatform.social.xmlprocessor.core.XMLBalancer;
import org.exoplatform.social.xmlprocessor.core.XMLTagFilter;
import org.exoplatform.social.xmlprocessor.core.model.XMLTagFilterPolicy;
import org.exoplatform.social.xmlprocessor.core.util.DOMParser;
import org.exoplatform.social.xmlprocessor.core.util.Tokenizer;

import junit.framework.TestCase;

/**
 * Unit Test for {@link ProcessorImpl}.
 *
 * @author Ly Minh Phuong - http://phuonglm.net
 */
public class ProcessorTest extends TestCase {
    public void testXMLBalancer() {
      Processor processor = new ProcessorImpl();
      LineBreakerFilter breakLineFilter = new LineBreakerFilter();
      XMLBalancer xmlBalancer = new XMLBalancer();
      
      processor.addFilter(breakLineFilter);
      processor.addFilter(xmlBalancer);
      
      assertEquals(null, 
                    processor.process(null));
      assertEquals("", processor.process(""));
      assertEquals("hello 1", processor.process("hello 1"));
      assertEquals("hello 1<br /> hello2", 
                      processor.process("hello 1\n hello2"));
      assertEquals("hello 1&lt;&gt; hello2", 
                      processor.process("hello 1<> hello2"));
      assertEquals("<a>hello 1</a>", processor.process("<a>hello 1"));
      assertEquals("hello 1&lt;/a&gt;", processor.process("hello 1</a>"));
      assertEquals("<a>Hello 2<a><b /></a></a>", processor.process("<a<b>Hello 2<a><b>"));
        
    }
    
    public void testXMLFilter() {
      Processor processor = new ProcessorImpl();
      XMLTagFilterPolicy tagFilterPolicy = new XMLTagFilterPolicy();
      tagFilterPolicy.addAllowTags("div","p","b","br","a");
      XMLTagFilter xmlFilter = new XMLTagFilter(tagFilterPolicy);
      processor.addFilter(xmlFilter);
      
      assertEquals(null,processor.process(null));
      
      assertEquals("hello 1", processor.process("hello 1"));
      assertEquals("hello 1\n hello2", processor.process("hello 1\n hello2"));
      assertEquals("<a>hello 1", processor.process("<a>hello 1"));
      assertEquals("hello 1</a>", processor.process("hello 1</a>"));
      assertEquals("<a>Hello 2<a><b>", processor.process("<a<b>Hello 2<a><b>"));
      assertEquals("<a>Hello 2&lt;i&gt;<b>", processor.process("<a<b>Hello 2<i><b>"));
      assertEquals("<a>Hello 2<b /><b>", processor.process("<a<b>Hello 2<b /><b>"));
    }
    
    public void testXMLFilterWithTagAndAttributes() {
      Processor processor = new ProcessorImpl();
      XMLTagFilterPolicy tagFilterPolicy = new XMLTagFilterPolicy();
      tagFilterPolicy.addAllowTags("div","p","b","br","a");
      XMLTagFilter xmlFilter = new XMLTagFilter(tagFilterPolicy);
      processor.addFilter(xmlFilter);
      
      assertEquals(null,processor.process(null));      
      assertEquals("hello 1", processor.process("hello 1"));
      assertEquals("hello 1\n hello2", processor.process("hello 1\n hello2"));
      assertEquals("<a>hello 1", processor.process("<a>hello 1"));
      assertEquals("hello 1</a>", processor.process("hello 1</a>"));
      assertEquals("<a>Hello 2<a><b>", processor.process("<a<b>Hello 2<a><b>"));
      assertEquals("<a>Hello 2&lt;i&gt;<b>", processor.process("<a<b>Hello 2<i><b>"));
      assertEquals("<a>Hello 2<b /><b>", processor.process("<a<b>Hello 2<b /><b>"));
    }
    
    public void testXMLDOMFilterAndEscapeWithTagAndAttributes() {
      Processor processor = new ProcessorImpl();
      XMLTagFilterPolicy tagFilterPolicy = new XMLTagFilterPolicy();
      tagFilterPolicy.addAllowTags("div","p","b","br","a");
      tagFilterPolicy.addAllowAttributes("a","href");

      
      Filter domxmlTagFilter = new DOMXMLTagFilter(tagFilterPolicy);
      Filter domContentEscapeFilter = new DOMContentEscapeFilter();
      Filter domLineBreakerFilter = new DOMLineBreakerFilter();
      
      processor.addFilter(domLineBreakerFilter);
      processor.addFilter(domxmlTagFilter);
      processor.addFilter(domContentEscapeFilter);
            
      assertEquals("hello 1", processor.process(DOMParser.createDOMTree( Tokenizer.tokenize("hello 1"))).toString());
      assertEquals("hello 1<br /> hello2", processor.process(DOMParser.createDOMTree( Tokenizer.tokenize("hello 1\n hello2"))).toString());      
      assertEquals("&lt;a&gt;hello 1", processor.process(DOMParser.createDOMTree( Tokenizer.tokenize("<a>hello 1"))).toString());      
      assertEquals("hello 1&lt;/a&gt;", processor.process(DOMParser.createDOMTree( Tokenizer.tokenize("hello 1</a>"))).toString());      
      assertEquals("&lt;a&lt;b&gt;Hello 2&lt;a&gt;&lt;b&gt;", processor.process(DOMParser.createDOMTree( Tokenizer.tokenize("<a<b>Hello 2<a><b>"))).toString());      
      assertEquals("<a>Hello 2</a>", processor.process(DOMParser.createDOMTree( Tokenizer.tokenize("<a>Hello 2</a>"))).toString());      
      assertEquals("<a>Hello 2<b /></a>", processor.process(DOMParser.createDOMTree( Tokenizer.tokenize("<a>Hello 2<b /></a>"))).toString());
      assertEquals("<a href=\"abc\">Hello 2<b /></a>", processor.process(DOMParser.createDOMTree( Tokenizer.tokenize("<a href='abc' id='def'>Hello 2<b /></a>"))).toString());      
    }

}
