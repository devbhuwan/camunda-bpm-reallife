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
    /**
     * The name of the binding target (e.g. channel) that the method subscribes to.
     *
     * @return the name of the binding target.
     */
    @AliasFor(annotation = StreamListener.class, attribute = "condition")
    String value() default "";

    /**
     * The name of the binding target (e.g. channel) that the method subscribes to.
     *
     * @return the name of the binding target.
     */
    @AliasFor(annotation = StreamListener.class, attribute = "target")
    String target() default Sink.INPUT;

    /**
     * A condition that must be met by all items that are dispatched to this method.
     *
     * @return a SpEL expression that must evaluate to a {@code boolean} value.
     */
    @AliasFor(annotation = StreamListener.class, attribute = "condition")
    String eventType() default "";
}
