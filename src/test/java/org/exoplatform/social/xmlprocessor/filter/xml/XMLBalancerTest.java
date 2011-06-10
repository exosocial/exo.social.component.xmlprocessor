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
import org.exoplatform.social.xmlprocessor.filter.xml.XMLBalancer;
import org.exoplatform.social.xmlprocessor.filter.xml.model.Node;
import org.exoplatform.social.xmlprocessor.filter.xml.util.Tokenizer;

import junit.framework.TestCase;

public class XMLBalancerTest extends TestCase {
  
     public void testXMLBalancerFilter() {
      Filter balancer = new XMLBalancer();
      
      assertEquals("hello 1", 
          balancer.doFilter("hello 1"));
      
      assertEquals("<a href=\"http://\">hello2</a>", 
          balancer.doFilter("<a href=\"http://\">hello2</a>"));
      
      assertEquals("<b><i> hello 3</i></b> hello 4&lt;/i&gt;", 
          balancer.doFilter("<b><i> hello 3</b> hello 4</i>"));
      
      assertEquals("<b> hello 5 <br /></b>", 
          balancer.doFilter("<b> hello 5 <br   /></b>"));
      
      assertEquals("<b> hello 6 <br /><b /></b>", 
          balancer.doFilter("<b> hello 6 <br /><b>"));
      
      assertEquals("3 &lt; 5 &gt;", 
          balancer.doFilter("3 < 5 >"));
    }
}
