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
package org.exoplatform.social.xmlprocessor.core;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import org.exoplatform.social.xmlprocessor.api.Filter;
import org.exoplatform.social.xmlprocessor.core.model.Attributes;
import org.exoplatform.social.xmlprocessor.core.model.Node;
import org.exoplatform.social.xmlprocessor.core.model.XMLTagFilterPolicy;


/**
 * this Filter travel through DOM tree and find if any TAG not satisfied the rules
 * spectify by whiteList. With wrong TAG it change it to content Type.
 *
 * @author Ly Minh Phuong - http://phuonglm.net
 *
 */
public class DOMXMLTagFilter implements Filter {
  private LinkedHashMap<String, Attributes> whiteList = new LinkedHashMap<String, Attributes>();
  /**
   * Gets the policy List.
   *
   * @return the whiteList
   */
  public LinkedHashMap<String, Attributes> getWhiteList() {
    return whiteList;
  }
  
  /**
   * Sets whitelist policy to DOMXMLagFilter.
   *
   * @param whiteList
   */
  public void setWhiteList(LinkedHashMap<String, Attributes> whiteList) {
    this.whiteList = whiteList;
  }
  
  /**
   * Contructor, the policy must be set from constructor.
   *
   * @param policy
   */
  public DOMXMLTagFilter(XMLTagFilterPolicy policy) {
    whiteList = policy.getWhiteList();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Object doFilter(Object input) {
    if (input instanceof Node) {
      nodeFilter((Node) input);
    }
    return input;
  }

  private Node nodeFilter(Node currentNode){
    LinkedList<Node> currentChildNode = currentNode.getChildNodes();
    if(!currentNode.getTitle().equals("")){
      String tag = currentNode.getTitle();
      if(whiteList.containsKey(tag)){
        
        Attributes currentAttributes = currentNode.getAttributes();
        Attributes validedAttrubutes = new Attributes();
        
        for (Iterator<String> iterator = currentAttributes.getKeyIterator(); iterator.hasNext();) {
            String key = iterator.next();
            if(whiteList.get(tag).hasKey(key)){
              validedAttrubutes.put(key, currentAttributes.get(key)); 
            }
        }
        currentNode.setAttributes(validedAttrubutes);        
      } else {
        currentNode.convertToContent();
      }      
    }
    for(int i = 0; i < currentChildNode.size(); i++){
      nodeFilter(currentChildNode.get(i));
    }
    return currentNode;
  }
}
