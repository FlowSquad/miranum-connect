package io.miragon.miranum.connect.adapter.in.c7.elementtemplates;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.miragon.miranum.connect.c7.elementtemplates.gen.CamundaC7ElementTemplate;

public class CamundaC7ElementTemplateConverter {

    private static final String $SCHEMA = "https://unpkg.com/@camunda/element-templates-json-schema@0.1.0/resources/schema.json";

    public static String toJsonString(CamundaC7ElementTemplate elementTemplate) {
        var objectMapper = new ObjectMapper();
        objectMapper.addMixIn(CamundaC7ElementTemplate.class, SchemaMixin.class);
        var objectWriter = objectMapper.writerFor(CamundaC7ElementTemplate.class)
                .withAttribute("$schema", $SCHEMA);
        String json;
        try {
            json = objectWriter.withDefaultPrettyPrinter().writeValueAsString(elementTemplate);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Could not generate json string!", e);
        }
        return json;
    }
}