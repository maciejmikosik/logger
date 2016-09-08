package org.logbuddy.logger;

import static org.logbuddy.logger.StackTraceLogger.stackTrace;
import static org.logbuddy.model.Depth.depth;
import static org.testory.Testory.given;
import static org.testory.Testory.givenTest;
import static org.testory.Testory.thenCalled;
import static org.testory.Testory.thenCalledInOrder;
import static org.testory.Testory.when;

import org.junit.Before;
import org.junit.Test;
import org.logbuddy.Logger;
import org.logbuddy.model.Invocation;
import org.logbuddy.model.Returned;
import org.logbuddy.model.Thrown;

public class TestStackTraceLogger {
  private Logger logger, stackTraceLogger;
  private Object model;
  private Invocation invocation;
  private Returned returned;
  private Thrown thrown;

  @Before
  public void before() {
    givenTest(this);
  }

  @Test
  public void initial_depth_is_zero() {
    given(stackTraceLogger = stackTrace(logger));
    when(() -> stackTraceLogger.log(model));
    thenCalled(logger).log(depth(0, model));
  }

  @Test
  public void increases_depth_during_returning_invocation() {
    given(stackTraceLogger = stackTrace(logger));
    when(() -> {
      stackTraceLogger.log(invocation);
      stackTraceLogger.log(model);
      stackTraceLogger.log(returned);
    });
    thenCalledInOrder(logger).log(depth(0, invocation));
    thenCalledInOrder(logger).log(depth(1, model));
    thenCalledInOrder(logger).log(depth(0, returned));
  }

  @Test
  public void increases_depth_during_throwing_invocation() {
    given(stackTraceLogger = stackTrace(logger));
    when(() -> {
      stackTraceLogger.log(invocation);
      stackTraceLogger.log(model);
      stackTraceLogger.log(thrown);
    });
    thenCalledInOrder(logger).log(depth(0, invocation));
    thenCalledInOrder(logger).log(depth(1, model));
    thenCalledInOrder(logger).log(depth(0, thrown));
  }

  @Test
  public void increases_depth_recursively() {
    given(stackTraceLogger = stackTrace(logger));
    when(() -> {
      stackTraceLogger.log(invocation);
      stackTraceLogger.log(invocation);
      stackTraceLogger.log(invocation);
      stackTraceLogger.log(invocation);
      stackTraceLogger.log(model);
      stackTraceLogger.log(returned);
      stackTraceLogger.log(thrown);
      stackTraceLogger.log(returned);
      stackTraceLogger.log(thrown);
    });
    thenCalledInOrder(logger).log(depth(0, invocation));
    thenCalledInOrder(logger).log(depth(1, invocation));
    thenCalledInOrder(logger).log(depth(2, invocation));
    thenCalledInOrder(logger).log(depth(3, invocation));
    thenCalledInOrder(logger).log(depth(4, model));
    thenCalledInOrder(logger).log(depth(3, returned));
    thenCalledInOrder(logger).log(depth(2, thrown));
    thenCalledInOrder(logger).log(depth(1, returned));
    thenCalledInOrder(logger).log(depth(0, thrown));
  }
}