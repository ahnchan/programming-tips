package com.ahnchan.jsondiff;

import com.ahnchan.jsondiff.vo.Audit;
import com.ahnchan.jsondiff.vo.Base;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JSONDiff {
    /**
     * Diff two Base Object
     * @param first
     * @param second
     * @return
     * @throws JsonProcessingException
     */
    public List<Audit> diffJSON(Base first, Base second) throws JsonProcessingException {

        // Convert Base to Map
        HashMap<String, Object> firstMap = convertMap(first);
        HashMap<String, Object> secondMap = convertMap(second);

        // Difference (Guava)
        MapDifference<String, Object> resultMap = Maps.difference(firstMap, secondMap);

        List<Audit> results = new ArrayList<Audit>();
        for (Map.Entry<String, MapDifference.ValueDifference<Object>> value : resultMap.entriesDiffering().entrySet()){
            MapDifference.ValueDifference<Object> diff = value.getValue();

            // Make audit (key, before, after)
            Audit audit = new Audit(value.getKey(), diff.leftValue(), diff.rightValue());
            results.add(audit);
        }

        return results;
    }

    /**
     * Convert Base to JSON String
     * @param base
     * @return
     */
    private String convertJsonString(Base base) {

        // Exclude strategy
        ExclusionStrategy strategy = new ExclusionStrategy() {
            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                return false;
            }

            @Override
            public boolean shouldSkipField(FieldAttributes field) {
                return field.getAnnotation(ExcludeAudit.class) != null;
            }
        };

        // gson builder with strategy
        Gson gson = new GsonBuilder()
                .setExclusionStrategies(strategy)
                .create();

        String strJson = gson.toJson(base);

        System.out.println("json:" + strJson);

        return strJson;

    }

    /**
     * Convert Base to JSON Node
     * @param vo
     * @return
     * @throws JsonProcessingException
     */
    private JsonNode convertJson(Base vo) throws JsonProcessingException {

        String strJson = convertJsonString(vo);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(strJson);

        return jsonNode;
    }

    /**
     * Convert Base to Map<String, Object>
     * @param vo
     * @return
     * @throws JsonProcessingException
     */
    private HashMap<String, Object> convertMap(Base vo) throws JsonProcessingException {

        String strJson = convertJsonString(vo);

        ObjectMapper mapper = new ObjectMapper();
        TypeReference<HashMap<String, Object>> type = new TypeReference<HashMap<String, Object>>() {};

        HashMap<String, Object> mapNode = mapper.readValue(strJson, type);

        return mapNode;
    }
}
