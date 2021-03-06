package org.logbuddy.decorator;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static org.logbuddy.decorator.ComposedDecorator.compose;
import static org.testory.Testory.given;
import static org.testory.Testory.givenTest;
import static org.testory.Testory.thenReturned;
import static org.testory.Testory.thenThrown;
import static org.testory.Testory.when;
import static org.testory.Testory.willReturn;

import org.junit.Before;
import org.junit.Test;
import org.logbuddy.Decorator;
import org.logbuddy.LogBuddyException;

public class TestComposedDecorator {
  private Decorator decoratorA, decoratorB, decoratorC;
  private Object service, serviceA, serviceB, serviceC;
  private Decorator composed;

  @Before
  public void before() {
    givenTest(this);
  }

  @Test
  public void composes_many_decorators_in_array() {
    given(willReturn(serviceA), decoratorA).decorate(service);
    given(willReturn(serviceB), decoratorB).decorate(serviceA);
    given(willReturn(serviceC), decoratorC).decorate(serviceB);
    given(composed = compose(decoratorC, decoratorB, decoratorA));
    when(composed.decorate(service));
    thenReturned(serviceC);
  }

  @Test
  public void composes_many_decorators_in_list() {
    given(willReturn(serviceA), decoratorA).decorate(service);
    given(willReturn(serviceB), decoratorB).decorate(serviceA);
    given(willReturn(serviceC), decoratorC).decorate(serviceB);
    given(composed = compose(asList(decoratorC, decoratorB, decoratorA)));
    when(composed.decorate(service));
    thenReturned(serviceC);
  }

  @Test
  public void no_decorators_return_original_service() {
    given(composed = compose());
    when(composed.decorate(service));
    thenReturned(service);
  }

  @Test
  public void implements_to_string() {
    given(composed = compose(decoratorA, decoratorB, decoratorC));
    when(composed.toString());
    thenReturned(format("compose(%s, %s, %s)", decoratorA, decoratorB, decoratorC));
  }

  @Test
  public void checks_null_array() {
    when(() -> compose((Decorator[]) null));
    thenThrown(LogBuddyException.class);
  }

  @Test
  public void checks_null_decorator() {
    when(() -> compose(decoratorA, null, decoratorC));
    thenThrown(LogBuddyException.class);
  }
}
