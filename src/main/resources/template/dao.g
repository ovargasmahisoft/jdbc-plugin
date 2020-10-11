package ${table.basePackage}.domain;

import lombok.Builder;
import lombok.Value;

import java.time.Instant;

@Value
@Builder(toBuilder = true)
public class ${table.daoClassName} {
<% table.columns.each {%>
    ${it.dataType} ${it.fieldName};<%}%>
}