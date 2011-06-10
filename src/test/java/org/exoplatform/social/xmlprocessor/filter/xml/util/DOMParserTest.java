package org.exoplatform.social.xmlprocessor.filter.xml.util;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.exoplatform.social.xmlprocessor.filter.xml.model.Node;

import junit.framework.TestCase;

public class DOMParserTest extends TestCase {

  public void testCreateDOMTreeListOfString() {
    List<String> input;
    input = Arrays.asList("");
    Node rootNode;
    rootNode = DOMParser.createDOMTree(input);
    assertEquals(1, rootNode.getChildNodes().size());
    assertEquals("", rootNode.getChildNodes().get(0).getContent());
    
    input = Arrays.asList("<a>"," b ","</a>","<i>","e","<h>","</i>");
    rootNode = DOMParser.createDOMTree(input);
    assertEquals(5, rootNode.getChildNodes().size());
    assertEquals(1, rootNode.getChildNodes().get(0).getChildNodes().size());   
    assertEquals("a", rootNode.getChildNodes().get(0).getTitle());
    assertEquals(" b ", rootNode.getChildNodes().get(0).getChildNodes().get(0).getContent());
    assertEquals("<i>", rootNode.getChildNodes().get(1).getContent());
    assertEquals("e", rootNode.getChildNodes().get(2).getContent());  
    assertEquals("<h>", rootNode.getChildNodes().get(3).getContent());
    assertEquals("</i>", rootNode.getChildNodes().get(4).getContent());      
  }

  public void testCreateDOMTreeNodeListOfString() {
    List<String> input;
    input = Arrays.asList("");
    Node rootNode = new Node();
    
    DOMParser.createDOMTree(rootNode,input);
    assertEquals(1, rootNode.getChildNodes().size());
    assertEquals("", rootNode.getChildNodes().get(0).getContent());
    
    rootNode = new Node();
    
    input = Arrays.asList("<a>"," b ","</a>","<i>","e","<h>","</i>");
    DOMParser.createDOMTree(rootNode,input);
    assertEquals(5, rootNode.getChildNodes().size());
    assertEquals(1, rootNode.getChildNodes().get(0).getChildNodes().size());   
    assertEquals("a", rootNode.getChildNodes().get(0).getTitle());
    assertEquals(" b ", rootNode.getChildNodes().get(0).getChildNodes().get(0).getContent());
    assertEquals("<i>", rootNode.getChildNodes().get(1).getContent());
    assertEquals("e", rootNode.getChildNodes().get(2).getContent());  
    assertEquals("<h>", rootNode.getChildNodes().get(3).getContent());
    assertEquals("</i>", rootNode.getChildNodes().get(4).getContent());    
  }

}
