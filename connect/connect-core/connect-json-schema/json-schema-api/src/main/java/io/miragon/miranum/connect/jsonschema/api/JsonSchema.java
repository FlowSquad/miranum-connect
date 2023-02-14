package io.miragon.miranum.connect.jsonschema.api;

import java.util.List;

public interface JsonSchema {

    List<ValidationResult> validate(Object data);

    List<ValidationResult> validate(Object data, Object rootData);

}
