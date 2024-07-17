package org.azn.util;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class JSONUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static List<Map<String, Object>> readJSON(String filePath) throws IOException {
        return objectMapper.readValue(new File(filePath), new TypeReference<List<Map<String, Object>>>(){});
    }
}
