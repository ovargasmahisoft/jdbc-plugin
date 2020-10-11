package {{basePackage}}.domain;

import lombok.Builder;
import lombok.Value;

import java.time.Instant;

@Value
@Builder(toBuilder = true)
public class {{daoClassName}} {

{{#columns}}
    {{dataType}} {{fieldName}};
{{/columns}}

}