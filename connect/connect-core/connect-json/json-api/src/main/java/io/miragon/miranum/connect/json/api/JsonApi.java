package io.miragon.miranum.connect.json.api;

import com.fasterxml.jackson.databind.JsonNode;

public interface JsonApi {

    JsonNode merge(Object source, Object update);

    JsonSchema getSchema(String schemaKey);

    JsonSchema createSchema(String schemaContent);


}
