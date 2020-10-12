package ${table.basePackage}.repository.jdbc;

import ${table.basePackage}.domain.${table.daoClassName};

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;

import javax.sql.DataSource;
import java.time.Instant;
import java.util.Random;
<% def alphabet = (('A'..'Z')+('a'..'z')+('0'..'9')).join() %>
public class ${table.repositoryClassName}Test {

    @Autowired
    private DataSource dataSource;

    private ${table.repositoryClassName} repository;

    private ${table.daoClassName} initialDao;

    @BeforeAll
    public void before() {
        repository = new ${table.repositoryClassName}(dataSource);
        initialDao = testCreate();
    }

    @Test
    public void testGet() {

    }

    @Test
    public void testGetMany() {

    }

    @Test
    public void testUpdate() {

    }

    @Test
    public void testUpdateMany() {

    }

    @Test
    public void testDelete() {

    }

    private ${table.daoClassName} testCreate() {
        ${table.daoClassName} dao = buildForCreate();
        ${table.daoClassName} result = repository.create(dao);

        daoAsserts(dao, result);
        return result;
    }

    private void daoAsserts(${table.daoClassName} expected, ${table.daoClassName} actual) {

        if(expected.getId() > 0) {
            Assertions.assertEquals(expected.getId(), actual.getId());
        } else {
            Assertions.assertTrue(actual.getId() > 0);
        }

        <% table.columns.findAll { !it.primaryKey }.each {
        %>
        Assertions.assertEquals(expected.${ it.required && it.dataType == "boolean" ? "is" : "get"}${it.fieldName.capitalize()}(), actual.${ it.required && it.dataType == "boolean" ? "is" : "get"}${it.fieldName.capitalize()}());<% } %>
    }

    private ${table.daoClassName} buildForCreate() {
        return ${table.daoClassName}.builder()<% table.columns.findAll { !it.primaryKey }.each { %>
                .${it.fieldName}(<%
            if(it.dataType.toLowerCase() == "string") {%>RandomStringUtils.randomAlphanumeric(${it.size})<%} else
            if(it.dataType.toLowerCase() == "boolean") {%>true<%} else
            if(it.dataType.toLowerCase() == "instant") {%>Instant.now()<%} else
            if(it.dataType.toLowerCase() == "float") {%>new Random().nextFloat()<%} else
            if(it.dataType.toLowerCase() == "double") {%>new Random().nextDouble()<%} else
            if(it.dataType.toLowerCase() == "long") {%>Instant.now().toEpochMilli()<%} else
            if(it.dataType.toLowerCase() == "integer" || it.dataType.toLowerCase() == "int") {%> new Random().nextInt()<%}
            else {%>null<%}
                %>)<% } %>
                .build();
    }

}