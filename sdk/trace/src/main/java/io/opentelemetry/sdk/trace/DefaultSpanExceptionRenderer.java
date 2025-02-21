package io.opentelemetry.sdk.trace;

import java.io.PrintWriter;
import java.io.StringWriter;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.sdk.internal.AttributesMap;

enum DefaultSpanExceptionRenderer implements SpanExceptionRenderer {
  INSTANCE;

  @Override
  public Attributes renderAttributes(
      Throwable exception,
      Attributes additionalAttributes,
      SpanLimits spanLimits) {
    AttributesMap attributesMap = AttributesMap.create(
        spanLimits.getMaxNumberOfAttributesPerEvent(),
        spanLimits.getMaxAttributeValueLength());

    String exceptionName = exception.getClass().getCanonicalName();
    String exceptionMessage = exception.getMessage();
    String stackTrace = printStackTrace(exception, spanLimits.getMaxAttributeValueLength());

    if (exceptionName != null) {
      attributesMap.put(EXCEPTION_TYPE, exceptionName);
    }
    if (exceptionMessage != null) {
      attributesMap.put(EXCEPTION_MESSAGE, exceptionMessage);
    }
    if (stackTrace != null) {
      attributesMap.put(EXCEPTION_STACKTRACE, stackTrace);
    }
    return attributesMap;
  }

  private String printStackTrace(Throwable exception, int maximumLength) {
    StringWriter stringWriter = new StringWriter();
    try (PrintWriter printWriter = new PrintWriter(stringWriter)) {
      exception.printStackTrace(printWriter);
    }
    return stringWriter.toString();
  }
}
