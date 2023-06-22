package com.sahno.jdbc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class JdbcService {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @SneakyThrows
    public List<String> executeQuery(String url, String username, String password,String query){
        Connection connection = DriverManager.getConnection(url, username, password);
        connection.beginRequest();

        List<String> results = new ArrayList<>();

        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(query);
        ResultSetMetaData metaData = rs.getMetaData();

        while (rs.next()){
            ObjectNode objectNode = MAPPER.createObjectNode();

            for(String columnName : getColumnNames(metaData)){
                objectNode.put(columnName, rs.getString(columnName));
            }
            results.add(MAPPER.writeValueAsString(objectNode));
        }


        connection.endRequest();
        statement.close();
        connection.close();
        rs.close();

        return results;
    }

    private List<String> getColumnNames(ResultSetMetaData metaData) throws SQLException {
        List<String> columnNames = new ArrayList<>();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            columnNames.add(metaData.getColumnName(i));
        }
        return columnNames;
    }
}
