package camunda.event.channel.contracts;

import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@StreamListener
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface EventHandler {

    @AliasFor(annotation = StreamListener.class, attribute = "condition")
    String value() default "";

    @AliasFor(annotation = StreamListener.class, attribute = "target")
    String target() default Sink.INPUT;

    @AliasFor(annotation = StreamListener.class, attribute = "condition")
    String eventType() default "";
}
