package io.miranum.platform.deploymentreceiver.adapter.in.rest;

import io.miranum.platform.deploymentreceiver.application.dto.DeploymentDto;
import io.miranum.platform.deploymentreceiver.application.ports.in.DeployFile;
import io.miranum.platform.deploymentreceiver.domain.DeploymentStatus;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/rest/deployment")
@RequiredArgsConstructor
@Tag(name = "DeploymentReceiverController", description = "API to deploy process artifacts (e.g. bpmn and dmn)")
public class DeploymentReceiverController {

    private final DeployFile deployFile;

    @PostMapping
    public DeploymentStatus deploy(@Valid @RequestBody final DeploymentDto deploymentDto) {
        return this.deployFile.deploy(deploymentDto);
    }

}
