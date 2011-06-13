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
package org.exoplatform.social.xmlprocessor.impl;

import java.util.Iterator;
import java.util.LinkedList;

import org.exoplatform.social.xmlprocessor.api.Filter;
import org.exoplatform.social.xmlprocessor.api.Processor;

/**
 * The Processor is an Object which contain many Filter clustered as a queue
 * each input should run though many filter using Processor.
 * 
 * @author Ly Minh Phuong - http://phuonglm.net
 * 
 */
public class ProcessorImpl implements Processor {
  LinkedList<Filter> filters;

  /* (non-Javadoc)
   * @see org.exoplatform.social.xmlprocessor.Processor#addFilter(org.exoplatform.social.xmlprocessor.api.Filter)
   */
  public void addFilter(Filter filter) {
    filters.add(filter);
  }

  /* (non-Javadoc)
   * @see org.exoplatform.social.xmlprocessor.Processor#removeFilter(java.lang.Class)
   */
  public void removeFilter(Class className) {
    LinkedList<Filter> filtersClone = (LinkedList<Filter>) filters.clone();

    for (Iterator<Filter> filterIterator = filtersClone.iterator(); filterIterator.hasNext();) {
      Filter filter = filterIterator.next();
      if (filter.getClass().equals(className)) {
        filters.remove(filter);
      }
    }
  }

  public ProcessorImpl() {
    filters = new LinkedList<Filter>();
  }

  /* (non-Javadoc)
   * @see org.exoplatform.social.xmlprocessor.Processor#process(java.lang.Object)
   */
  public Object process(Object input) {
    for (Iterator<Filter> filterIterator = filters.iterator();
            filterIterator.hasNext();) {
      Filter filter = filterIterator.next();
      input = filter.doFilter(input);
    }
    return input;
  }
}
