package software.tnb.jms.amq.resource.local;

import org.testcontainers.containers.output.Slf4jLogConsumer;

import software.tnb.common.deployment.Deployable;
import software.tnb.common.deployment.WithDockerImage;
import software.tnb.jms.amq.service.AMQBroker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.auto.service.AutoService;

@AutoService(AMQBroker.class)
public class LocalAMQBroker extends AMQBroker implements Deployable, WithDockerImage {
    private static final Logger LOG = LoggerFactory.getLogger(LocalAMQBroker.class);
    private AMQBrokerContainer container;

    @Override
    public void deploy() {
        LOG.info("Checking Podman socket before container start...");
        LOG.info("DOCKER_HOST: {}", System.getenv("DOCKER_HOST"));
        LOG.info("HOME: {}", System.getenv("HOME"));
        LOG.info("User: {}", System.getProperty("user.name"));
        LOG.info("Starting AMQ container");
        container = new AMQBrokerContainer(image(), containerEnvironment(), containerPorts());
        container.withNetworkMode("bridge");
        container.withLogConsumer(new Slf4jLogConsumer(LoggerFactory.getLogger("TESTCONTAINERS")));
        container.start();
        LOG.info("AMQ broker container started");
        LOG.info("Checking Podman socket after container start...");
        LOG.info("DOCKER_HOST: {}", System.getenv("DOCKER_HOST"));
        LOG.info("HOME: {}", System.getenv("HOME"));
        LOG.info("User: {}", System.getProperty("user.name"));
    }

    @Override
    public void undeploy() {
        if (container != null) {
            LOG.info("Stopping AMQ broker container");
            container.stop();
        }
    }

    @Override
    public String host() {
        return container.getHost();
    }

    @Override
    public int getPortMapping(int port) {
        return container.getMappedPort(port);
    }

    private int[] containerPorts() {
        return new int[] {8161, 61616, 1883, 5672};
    }

    @Override
    public String defaultImage() {
        return "registry.redhat.io/amq7/amq-broker-rhel8:7.12.2";
    }
}
