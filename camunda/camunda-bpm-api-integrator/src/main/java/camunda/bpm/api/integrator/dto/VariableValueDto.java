package camunda.bpm.api.integrator.dto;

import camunda.bpm.api.integrator.exception.RestException;
import lombok.Data;
import org.camunda.bpm.engine.ProcessEngineConfiguration;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.type.ValueType;
import org.camunda.bpm.engine.variable.type.ValueTypeResolver;
import org.camunda.bpm.engine.variable.value.TypedValue;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.Map;

@Data
public class VariableValueDto implements Serializable {

    private String type;
    private Object value;
    private Map<String, Object> valueInfo;

    public static VariableMap toMap(ProcessEngineConfiguration processEngineConfiguration, Map<String, VariableValueDto> variables) {
        if (variables == null) {
            return null;
        }
        VariableMap result = Variables.createVariables();
        for (Map.Entry<String, VariableValueDto> variableEntry : variables.entrySet()) {
            result.put(variableEntry.getKey(), variableEntry.getValue().toTypedValue(processEngineConfiguration));
        }
        return result;
    }

    private TypedValue toTypedValue(ProcessEngineConfiguration processEngineConfiguration) {
        ValueTypeResolver valueTypeResolver = processEngineConfiguration.getValueTypeResolver();

        if (type == null) {
            return Variables.untypedValue(value);
        }

        ValueType valueType = valueTypeResolver.typeForName(type);
        if (valueType == null) {
            throw new RestException(HttpStatus.BAD_REQUEST, String.format("Unsupported value type '%s'", type));
        }
        return valueType.createValue(value, valueInfo);
    }
}