package io.miragon.miranum.connect.elementtemplate.c7;

import io.miragon.miranum.connect.elementtemplate.api.BPMNElementType;
import io.miragon.miranum.connect.elementtemplate.api.PropertyType;
import io.miragon.miranum.connect.elementtemplate.core.*;
import io.miragon.miranum.platform.connect.c7.elementtemplates.gen.Binding;
import io.miragon.miranum.platform.connect.c7.elementtemplates.gen.CamundaC7ElementTemplate;
import io.miragon.miranum.platform.connect.c7.elementtemplates.gen.Constraints;
import io.miragon.miranum.platform.connect.c7.elementtemplates.gen.Property;
import lombok.extern.java.Log;

import java.util.Collections;

@Log
public class Camunda7ElementTemplateGenerator implements ElementTemplateGenerator {

    @Override
    public ElementTemplateGenerationResult generate(ElementTemplateInfo elementTemplateInfo) {
        var elementTemplate = new CamundaC7ElementTemplate()
                .withName(elementTemplateInfo.getName())
                .withId(elementTemplateInfo.getId())
                .withVersion((double) elementTemplateInfo.getVersion())
                .withAppliesTo(Collections.singletonList(BPMNElementType.BPMN_SERVICE_TASK.getValue()));

        // Add external task property
        var implementationProperty = createExternalTaskProperty();
        elementTemplate.getProperties().add(implementationProperty);

        // Add property for the topic of the external task
        var implementationTopicProperty = createExternalTaskTopicProperty(elementTemplateInfo.getType());
        elementTemplate.getProperties().add(implementationTopicProperty);

        // Add properties for input parameters
        for (var inputProperty : elementTemplateInfo.getInputProperties()) {
            var property = createInputParameterProp(inputProperty);
            elementTemplate.getProperties().add(property);
        }

        // Add properties for output parameters
        for (var outputProperties : elementTemplateInfo.getOutputProperties()) {
            var property = createOutputParameterProp(outputProperties);
            elementTemplate.getProperties().add(property);
        }

        var json = CamundaC7ElementTemplateConverter.toJsonString(elementTemplate);
        return new ElementTemplateGenerationResult(elementTemplateInfo.getId(), elementTemplateInfo.getVersion(), json);
    }

    private Property createInputParameterProp(ElementTemplatePropertyInfo info) {
        var property = new Property()
                .withLabel("Input: %s".formatted(info.getLabel()))
                .withValue("${}")
                .withType(info.getType().getType())
                .withChoices(null)
                .withBinding(new Binding()
                        .withType(Binding.Type.CAMUNDA_INPUT_PARAMETER)
                        .withName(info.getName()));

        if (!info.isNotEmpty()) {
            property.setConstraints(new Constraints()
                    .withNotEmpty(info.isNotEmpty()));
        }

        if (!info.isEditable()) {
            property.setEditable(info.isEditable());
        }

        return property;
    }

    private Property createOutputParameterProp(ElementTemplatePropertyInfo info) {
        var property = new Property()
                .withLabel("Output: %s".formatted(info.getLabel()))
                .withValue("%sResult".formatted(info.getName()))
                .withType(info.getType().getType())
                .withChoices(null)
                .withBinding(new Binding()
                        .withType(Binding.Type.CAMUNDA_OUTPUT_PARAMETER)
                        .withSource("${%s}".formatted(info.getName())));

        if (!info.isNotEmpty()) {
            property.setConstraints(new Constraints()
                    .withNotEmpty(info.isNotEmpty()));
        }

        if (!info.isEditable()) {
            property.setEditable(info.isEditable());
        }

        return property;
    }

    private Property createExternalTaskProperty() {
        return new Property()
                .withLabel("Implementation Type")
                .withType(PropertyType.STRING.getType())
                .withValue("external")
                .withEditable(false)
                // We set the choices to null, because we don't want to have an empty
                // choices property in the JSON. This can be fixed by not creating an
                // empty list in the first place, but this is what got generated by the
                // jsonschema2pojo generator.
                .withChoices(null)
                .withBinding(new Binding()
                        .withType(Binding.Type.PROPERTY)
                        .withName("camunda:type"));
    }

    private Property createExternalTaskTopicProperty(String type) {
        return new Property()
                .withLabel("Topic")
                .withType(PropertyType.STRING.getType())
                .withValue(type)
                .withEditable(false)
                .withChoices(null)
                .withBinding(new Binding()
                        .withType(Binding.Type.PROPERTY)
                        .withName("camunda:topic"));
    }
}
