package software.tnb.jms.rabbitmq.resource.local;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.GenericContainer;

import java.time.Duration;
import java.util.Map;

public class RabbitMQBrokerContainer extends GenericContainer<RabbitMQBrokerContainer> {
    private static final Logger LOG = LoggerFactory.getLogger(RabbitMQBrokerContainer.class);
    public RabbitMQBrokerContainer(String image, Map<String, String> env, int... exposedPorts) {
        super(image);
        this.addExposedPorts(exposedPorts);
        this.withEnv(env);
        LOG.info("RABBIT BROKER START TIMEOUT");
        this.withStartupTimeout(Duration.ofSeconds(120));
        LOG.info("RABBIT BROKER END TIMEOUT");

    }
}
