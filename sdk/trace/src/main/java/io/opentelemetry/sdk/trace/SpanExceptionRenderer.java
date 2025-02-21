package io.opentelemetry.sdk.trace;

import io.opentelemetry.api.common.AttributeKey;
import io.opentelemetry.api.common.Attributes;

@FunctionalInterface
public interface SpanExceptionRenderer {
  AttributeKey<String> EXCEPTION_TYPE =
      AttributeKey.stringKey("exception.type");
  AttributeKey<String> EXCEPTION_MESSAGE =
      AttributeKey.stringKey("exception.message");
  AttributeKey<String> EXCEPTION_STACKTRACE =
      AttributeKey.stringKey("exception.stacktrace");

  Attributes renderAttributes(Throwable exception, Attributes additionalAttributes, SpanLimits spanLimits);

  static SpanExceptionRenderer getDefault() {
    return DefaultSpanExceptionRenderer.INSTANCE;
  }
}
