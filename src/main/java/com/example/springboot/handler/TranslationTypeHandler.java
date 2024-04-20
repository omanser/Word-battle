//package com.example.springboot.handler;
//
//import com.example.springboot.pojo.Translation;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.JsonMappingException;
//import org.apache.ibatis.type.BaseTypeHandler;
//import org.apache.ibatis.type.JdbcType;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.apache.ibatis.type.MappedJdbcTypes;
//import org.apache.ibatis.type.MappedTypes;
//
//import java.io.IOException;
//import java.sql.CallableStatement;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//@MappedJdbcTypes(JdbcType.VARCHAR)
//@MappedTypes(Translation.class)
//public class TranslationTypeHandler extends BaseTypeHandler<Translation> {
//    private static final ObjectMapper objectMapper = new ObjectMapper();
//    @Override
//    public void setNonNullParameter(PreparedStatement ps, int i, Translation parameter, JdbcType jdbcType) throws SQLException {
//        String json = null;
//        try {
//            json = objectMapper.writeValueAsString(parameter);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//        ps.setString(i, json);
//    }
//
//    @Override
//    public Translation getNullableResult(ResultSet rs, String columnName) throws SQLException {
//        String json = rs.getString(columnName);
//        return parse(json);
//    }
//
//    @Override
//    public Translation getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
//        String json = rs.getString(columnIndex);
//        return parse(json);
//    }
//
//    @Override
//    public Translation getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
//        String json = cs.getString(columnIndex);
//        return parse(json);
//    }
//    private Translation parse(String json) throws SQLException {
//        try {
//            return objectMapper.readValue(json, Translation.class);
//        } catch (IOException e) {
//            throw new SQLException("Failed to parse JSON to Translation", e);
//        }
//    }
//}
