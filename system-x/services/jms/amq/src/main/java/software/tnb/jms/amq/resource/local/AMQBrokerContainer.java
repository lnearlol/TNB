package software.tnb.jms.amq.resource.local;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.GenericContainer;

import java.time.Duration;
import java.util.Map;

public class AMQBrokerContainer extends GenericContainer<AMQBrokerContainer> {
    private static final Logger LOG = LoggerFactory.getLogger(AMQBrokerContainer.class);
    public AMQBrokerContainer(String image, Map<String, String> env, int... exposedPorts) {
        super(image);
        this.addExposedPorts(exposedPorts);
        this.withEnv(env);
        LOG.info("AMQ BROKER START TIMEOUT");
        this.withStartupTimeout(Duration.ofSeconds(120));
        LOG.info("AMQ BROKER END TIMEOUT");

    }
}
