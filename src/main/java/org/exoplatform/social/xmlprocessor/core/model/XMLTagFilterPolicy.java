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
package org.exoplatform.social.xmlprocessor.core.model;

import java.util.LinkedHashMap;

/**
 * Contains LinkedHashMap<Tag, Attributes> which can be use for whiteList or blackList.
 *
 * @author Ly Minh Phuong - http://phuonglm.net
 *
 */
public class XMLTagFilterPolicy {
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
   * @param whiteList the white list
   */
  public void setWhiteList(LinkedHashMap<String, Attributes> whiteList) {
    this.whiteList = whiteList;
  }
  
  /**
   * Adds TagStrings to whiteList.
   *
   * @param tagStrings
   */
  public void addAllowTags(String... tagStrings){
    for (String tagString : tagStrings){
      String nomarlTag = tagString.toLowerCase();
      if(!whiteList.containsKey(nomarlTag)){
        whiteList.put(nomarlTag, new Attributes());
      }
    }
  }
  /**
   * Adds Tag and Attrubutes to whitelist. If tag Not in.
   *
   * @param tag which tag of this attribute list
   * @param attributes attribute List which add to AlowTag
   */
  public void addAllowAttributes(String tag, String... attributes){
    String nomarlTag = tag.toLowerCase();
    Attributes attributesToAdd;
    if(whiteList.containsKey(nomarlTag)){      
      attributesToAdd = whiteList.get(tag);
    } else {
      attributesToAdd = new Attributes();
      whiteList.put(nomarlTag, attributesToAdd);
    }    
    for(String attribute: attributes){
      attributesToAdd.put(attribute.toLowerCase(), null);
    }
  }
  
  /**
   * Removes Allowed Attribute in this whiteList.
   *
   * @param tag
   * @param attributes
   */
  public void removeAllowedAttribute(String tag, String... attributes){
    if(whiteList.containsKey(tag)){
      Attributes attributesHashMap = whiteList.get(tag);
      for(String attribute: attributes){
        if(attributesHashMap.hasKey(attribute)){
          attributesHashMap.remove(attribute);
        }
      }
    }
  }
}
