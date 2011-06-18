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

import java.util.LinkedList;

/**
 * Model of XML node tree
 * 
 * @author Ly Minh Phuong - http://phuonglm.net
 * 
 */
public class Node {
  private Node parentNode = null;
  private String title = "";
  private Attributes attributes = new Attributes();
  private String content = "";
  private LinkedList<Node> childNodes = new LinkedList<Node>();

  /**
   * get Parent Node of current Node. If current Node is root, parent Node ==
   * null;
   * 
   * @return parent node
   */
  public Node getParentNode() {
    return parentNode;
  }
  
  /**
   * set the parent Node of currentNode
   * @param parentNode
   */
  public void setParentNode(Node parentNode) {
    this.parentNode = parentNode;
  }
  /**
   * Get title of Node, node's title ak the tag name.
   * @return
   */
  public String getTitle() {
    return title;
  }

  /**
   * set title of Node, node's title ak the tag name.
   * @param nodeTitle
   */
  public void setTitle(String nodeTitle) {
    this.title = nodeTitle;
  }
  /**
   * return Attributes of Node
   * @return
   */
  public Attributes getAttributes() {
    return attributes;
  }
  /**
   * set Attributes of Node
   * @param attributes
   */
  public void setAttributes(Attributes attributes) {
    this.attributes = attributes;
  }
  /**
   * get the Content of Node, if Node have Content it mean that is text Node.
   * @return
   */
  public String getContent() {
    return content;
  }
  /**
   * set the Content of Node, if Node have Content it mean that is text Node.
   * @param content
   */
  public void setContent(String content) {
    this.content = content;
  }
  /**
   * get the list child node of current Node
   * @return
   */
  public LinkedList<Node> getChildNodes() {
    return childNodes;
  }
  
  /**
   * set the list child node of current Node
   * @param childNodes
   */
  public void setChildNodes(LinkedList<Node> childNodes) {
    this.childNodes = childNodes;
  }
  
  /**
   * add childNode to thisNode
   * @param childNode
   */
  public void addChildNode(Node childNode) {
    this.childNodes.add(childNode);
  }
  /**
   * add Attribute to currentNode.
   * @param key
   * @param value
   */
  public void addAttribute(String key, String value) {
    this.attributes.put(key, value);
  }

  public String toString() {
    StringBuilder xmlString = new StringBuilder("");
    boolean selfClosedTag = false;
    boolean textTag = false;

    if (attributes.size() == 0 && childNodes.size() == 0 && content.equals("")) {
      selfClosedTag = true;
    }
    if (attributes.size() == 0 && childNodes.size() == 0 && !content.equals("")
        && title.equals("")) {
      textTag = true;
    }

    if (textTag) {
      xmlString.append(this.content);
    } else {
      if (this.parentNode != null) {

        xmlString.append("<" + this.title);

        xmlString.append(attributes.xml());

        if (selfClosedTag) {
          xmlString.append(" /");
        }
        xmlString.append(">");
      }
      for (Node childNode : childNodes) {
        xmlString.append(childNode.toString());
      }

      if (this.parentNode != null && !selfClosedTag) {
        xmlString.append("</" + this.title + ">");
      }
    }
    return xmlString.toString();
  }
  /**
   * return the Open Tag of this Node, if this node is textNode so this will equal
   * blank string 
   * @return
   */
  public String toOpenString() {
    StringBuilder xmlString = new StringBuilder("");
    boolean selfClosedTag = false;
    boolean textTag = false;

    if (attributes.size() == 0 && childNodes.size() == 0 && content.equals("")) {
      selfClosedTag = true;
    }
    if (attributes.size() == 0 && childNodes.size() == 0 && !content.equals("")
        && title.equals("")) {
      textTag = true;
    }

    if (textTag) {
      xmlString.append(this.content);
    } else {
      if (this.parentNode != null) {

        xmlString.append("<" + this.title);

        xmlString.append(attributes.xml());

        if (selfClosedTag) {
          xmlString.append(" /");
        }
        xmlString.append(">");
      }
    }
    return xmlString.toString();
  }
  /**
   * return the Close Tag of this Node, if this node is textNode so this will equal
   * blank string 
   * @return
   */  
  public String toCloseString() {
    StringBuilder xmlString = new StringBuilder("");
    boolean selfClosedTag = false;
    boolean textTag = false;

    if (childNodes.size() == 0 && content.equals("")) {
      selfClosedTag = true;
    }
    if (attributes.size() == 0 && childNodes.size() == 0 && !content.equals("")
        && title.equals("")) {
      textTag = true;
    }

    if (textTag) {
      xmlString.append(this.content);
    } else {
      if (this.parentNode != null && !selfClosedTag) {
        xmlString.append("</" + this.title + ">");
      }
    }
    return xmlString.toString();
  }
  /**
   * Convert this Tag Node to Content Node, add Close Tag at the end of child nodes
   * and move all child Nodes of this Node to parent Node.
   */
  public void convertToContent(){
    if(parentNode!=null){
      int thisPostion = parentNode.getChildNodes().indexOf(this);      
      this.content = this.toOpenString();      
      String closeTag = this.toCloseString();
      this.title = "";
      if(!closeTag.equals("")){
        Node closeContentNode = new Node();
        closeContentNode.setContent(closeTag);
        childNodes.addLast(closeContentNode);
        moveAllChildNodesToOtherNode(parentNode, thisPostion);
      }
    }
  }
  /**
   * Move all child Node of this Node to parentNode and insert it after this Node;
   * @param insertPosition
   */
  public void moveAllChildNodesToParentNode(int insertPosition){
    if(parentNode!=null){
      moveAllChildNodesToOtherNode(parentNode, insertPosition);
    }
  }
  /**
   * Move all child Node of this Node to destNode and insert it after insertPosition;
   * @param destNode
   * @param insertPosition
   */
  public void moveAllChildNodesToOtherNode(Node destNode,int insertPosition){
    if(destNode!=null){
      LinkedList<Node> parentChildNodes = destNode.getChildNodes();
      int postShift = insertPosition;
      for(Node childNode : childNodes){
        postShift++;
        childNode.setParentNode(destNode);
        parentChildNodes.add(postShift,childNode);        
      }
      childNodes.clear();
    }
  }
}
