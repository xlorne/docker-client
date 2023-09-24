package com.codingapi.docker;

import com.codingapi.docker.log.LogContainerResultCallback;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.DeviceRequest;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient;
import com.github.dockerjava.transport.DockerHttpClient;
import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.util.List;

@SpringBootTest
class DockerApplicationTests {

    @Test
    void contextLoads() throws InterruptedException {
        DockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder()
                .withDockerHost("unix:///var/run/docker.sock")
                .withDockerTlsVerify(false)
                .build();

        DockerHttpClient httpClient = new ApacheDockerHttpClient.Builder()
                .dockerHost(config.getDockerHost())
                .sslConfig(config.getSSLConfig())
                .maxConnections(100)
                .connectionTimeout(Duration.ofSeconds(30))
                .responseTimeout(Duration.ofSeconds(45))
                .build();


        DockerClient dockerClient = DockerClientImpl.getInstance(config, httpClient);
        List<Container> list = dockerClient.listContainersCmd().exec();
        for (Container container : list) {
            System.out.println(container);
        }

        HostConfig hostConfig = HostConfig.newHostConfig()
                .withAutoRemove(true)
                .withDeviceRequests(ImmutableList.of(new DeviceRequest()
                                .withDriver("nvidia")
                                .withCapabilities(ImmutableList.of(ImmutableList.of("gpu")))
                                .withDeviceIds(ImmutableList.of("0"))
                        )
                );

        CreateContainerResponse container = dockerClient.createContainerCmd("ubuntu")
                .withCmd("nvidia-smi")
                .withHostConfig(hostConfig)
                .exec();

        System.out.println(container.getId());

        dockerClient.startContainerCmd(container.getId()).exec();

        LogContainerResultCallback resultCallback = new LogContainerResultCallback();


        dockerClient.logContainerCmd(container.getId())
                .withStdOut(true)
                .withStdErr(true)
                .withFollowStream(true)
                .withTailAll()
                .exec(resultCallback).awaitCompletion();


        System.out.println(resultCallback.getLogs());
    }

}
