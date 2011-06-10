package org.exoplatform.social.xmlprocessor.filter.xml.util;

import java.util.List;

import junit.framework.TestCase;

public class TokenizerTest extends TestCase {

  public void testTokenize() {
    List<String> result;
    
    result = Tokenizer.tokenize("");
    assertEquals(0, result.size());
    
    result = Tokenizer.tokenize("hello");
    assertEquals(1, result.size());
    assertEquals("hello", result.get(0));
    
    result = Tokenizer.tokenize("a < asd >");
    assertEquals(2, result.size());
    assertEquals("a ", result.get(0));
    assertEquals("< asd >", result.get(1));
    
    result = Tokenizer.tokenize("<a> a </a>");
    assertEquals(3, result.size());
    assertEquals("<a>", result.get(0));
    assertEquals(" a ", result.get(1));
    assertEquals("</a>", result.get(2));
    
    result = Tokenizer.tokenize("<a href=\"hello\"> a </a>");
    assertEquals(3, result.size());
    assertEquals("<a href=\"hello\">", result.get(0));
    assertEquals(" a ", result.get(1));
    assertEquals("</a>", result.get(2));
    
    result = Tokenizer.tokenize("<a href='hello'> a </a>");
    assertEquals(3, result.size());
    assertEquals("<a href='hello'>", result.get(0));
    assertEquals(" a ", result.get(1));
    assertEquals("</a>", result.get(2));
  }

}
