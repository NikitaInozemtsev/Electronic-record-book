package server.exception;

import org.apache.logging.log4j.message.MessageFactory;
import org.apache.logging.log4j.message.ParameterizedMessageFactory;

public class NotFoundResourceException extends RuntimeException {

    private static final MessageFactory MSG_FACTORY = new ParameterizedMessageFactory();

    public NotFoundResourceException(Class<?> entityClass, Object entityId) {
        this(entityClass.getSimpleName(), entityId);
    }

    public NotFoundResourceException(String entityName, Object entityId) {
        super(MSG_FACTORY.newMessage("Entity not found: {}[{}]", entityName, entityId).getFormattedMessage());
    }

}
