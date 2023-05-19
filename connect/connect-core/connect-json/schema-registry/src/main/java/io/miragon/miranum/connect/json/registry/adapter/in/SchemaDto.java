package io.miragon.miranum.connect.json.registry.adapter.in;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SchemaDto {
    // All artefacts of one process are organized in a bundle.
    @NotNull
    @Size(min = 1, max = 255)
    private final String bundle;

    // The name of the schema.
    @NotNull
    @Size(min = 1, max = 255)
    private final String ref;

    // The tag of the schema.
    @NotNull
    @Size(min = 1, max = 255)
    private final String tag;

    // The schema content.
    @NotNull
    private final JsonNode jsonNode;
}
