/*
 
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
package org.exoplatform.social.xmlprocessor.filter.xml.model;

import java.util.*;

/**
 * The attributes of an Node.
 * <p/>
 * Attributes are treated as a map: there can be only one value associated with
 * an attribute key.
 * <p/>
 * Attribute key and value comparisons are done case insensitively, and keys are
 * normalised to lower-case.
 * 
 * @author Ly Minh Phuong - http://phuonglm.net
 */
public class Attributes {
  private LinkedHashMap<String, String> attributes = new LinkedHashMap<String, String>(
      2);

  // linked hash map to preserve insertion order.

  /**
   * Get an attribute value by key.
   * 
   * @param key
   *            the attribute key
   * @return the attribute value if set; or empty string if not set.
   * @see #hasKey(String)
   */
  public String get(String key) {
    String attr = attributes.get(key.toLowerCase());
    return attr != null ? attr : "";
  }

  /**
   * Set a new attribute, or replace an existing one by key.
   * 
   * @param key
   *            attribute key
   * @param value
   *            attribute value
   */
  public void put(String key, String value) {
    attributes.put(key.toLowerCase(), value);
  }

  /**
   * Remove an attribute by key.
   * 
   * @param key
   *            attribute key to remove
   */
  public void remove(String key) {
    attributes.remove(key.toLowerCase());
  }

  /**
   * Tests if these attributes contain an attribute with this key.
   * 
   * @param key
   *            key to check for
   * @return true if key exists, false otherwise
   */
  public boolean hasKey(String key) {
    return attributes.containsKey(key.toLowerCase());
  }

  /**
   * Get the number of attributes in this set.
   * 
   * @return size
   */
  public int size() {
    return attributes.size();
  }

  /**
   * get the KeySet iterator of attributes
   * 
   * @param             
   * @return Iterator<String>
   */
  public Iterator<String> getKeyIterator() {
    return attributes.keySet().iterator();
  }

  /**
   * Get the XML representation of these attributes.
   * 
   * @return XML
   */
  public String xml() {
    StringBuilder attributesString = new StringBuilder();
    for (String key : attributes.keySet()) {
      String val = attributes.get(key);
      attributesString.append(" " + key + "=" + "\"" + val + "\"");
    }
    return attributesString.toString();
  }
  
  /**
   * Clear all attrib of the of the 
   * 
   * @return XML
   */
  public void clear() {
    attributes.clear();
  }

  public String toString() {
    return xml();
  }

}
