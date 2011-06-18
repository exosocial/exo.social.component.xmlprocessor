package org.exoplatform.social.xmlprocessor.api;


public interface Processor {

  /**
   * Add Filter to Processor
   * 
   * @param filter
   */
  public abstract void addFilter(Filter filter);

  /**
   * Remove a filter which instant of Class from queue.
   * @param className
   */
  public abstract void removeFilter(Class className);

  /**
   * Process an Object though Filter chain.
   * @param input
   * @return
   */
  public abstract Object process(Object input);

}