package io.miranum.platform.deploymentserver.adapter.out;

import io.miranum.platform.deploymentreceiver.application.ports.in.DeployFile;
import io.miranum.platform.deploymentserver.application.ports.out.DeployFilePort;
import io.miranum.platform.deploymentserver.domain.Deployment;
import io.miranum.platform.deploymentserver.domain.DeploymentStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;

class MiranumEmbeddedDeploymentTest {

    private DeployFilePort miranumEmbeddedDeployment;
    private final DeployFile deployFile = Mockito.mock(DeployFile.class);

    @BeforeEach
    void setup() {
        miranumEmbeddedDeployment = new MiranumEmbeddedDeployment(this.deployFile);
    }

    @Test
    void deployTest() {
        final Deployment deployment = Deployment.builder()
            .file("foobar")
            .type("bpmn")
            .namespace("sampleNamespace")
            .tags(null)
            .build();
        final DeploymentStatus deploymentStatus = DeploymentStatus.builder()
            .success(true)
            .message("Deployment was successfully")
            .build();

        final io.miranum.platform.deploymentreceiver.application.dto.DeploymentDto mappedDeployment = io.miranum.platform.deploymentreceiver.application.dto.DeploymentDto.builder()
            .file(deployment.getFile())
            .type(deployment.getType())
            .namespace(deployment.getNamespace())
            .tags(deployment.getTags())
            .build();
        final io.miranum.platform.deploymentreceiver.domain.DeploymentStatus mappedDeploymentStatus = io.miranum.platform.deploymentreceiver.domain.DeploymentStatus.builder()
            .success(deploymentStatus.isSuccess())
            .message(deploymentStatus.getMessage())
            .build();

        Mockito.when(deployFile.deploy(mappedDeployment)).thenReturn(mappedDeploymentStatus);


        final DeploymentStatus result = this.miranumEmbeddedDeployment.deploy(deployment, "test");

        assertThat(result.isSuccess()).isTrue();
        assertThat(result.getMessage()).isEqualTo("Deployment was successfully");
    }

}
