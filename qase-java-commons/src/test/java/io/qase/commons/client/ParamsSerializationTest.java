package io.qase.commons.client;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import io.qase.client.v2.JSON;
import io.qase.client.v2.models.CreateResultsRequestV2;
import io.qase.client.v2.models.ResultCreate;
import io.qase.client.v2.models.ResultExecution;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ParamsSerializationTest {

    @Test
    void numericStringParamsShouldSerializeAsJsonStrings() {
        Map<String, String> params = new HashMap<>();
        params.put("paramA", "0");
        params.put("paramB", "12345");
        params.put("paramC", "-1");
        params.put("paramD", "ABC");
        params.put("paramE", "1772372596569");
        params.put("content", "some value");

        ResultCreate result = new ResultCreate()
                .title("test")
                .execution(new ResultExecution().status("passed").duration(100L))
                .params(params);

        // Test single ResultCreate serialization
        String json = JSON.serialize(result);
        System.out.println("ResultCreate JSON: " + json);

        assertTrue(json.contains("\"paramA\":\"0\""), "paramA '0' should be a JSON string, got: " + json);
        assertTrue(json.contains("\"paramB\":\"12345\""), "paramB '12345' should be a JSON string, got: " + json);
        assertTrue(json.contains("\"paramC\":\"-1\""), "paramC '-1' should be a JSON string, got: " + json);
        assertTrue(json.contains("\"paramD\":\"ABC\""), "paramD 'ABC' should be a JSON string, got: " + json);

        // Test bulk request serialization
        CreateResultsRequestV2 bulkRequest = new CreateResultsRequestV2().addResultsItem(result);
        String bulkJson = JSON.serialize(bulkRequest);
        System.out.println("Bulk request JSON: " + bulkJson);

        assertTrue(bulkJson.contains("\"paramA\":\"0\""), "In bulk: paramA '0' should be JSON string, got: " + bulkJson);
        assertTrue(bulkJson.contains("\"paramB\":\"12345\""), "In bulk: paramB should be JSON string, got: " + bulkJson);
    }

    @Test
    void verifyJsonTreePreservesStringTypes() {
        Map<String, String> params = new HashMap<>();
        params.put("statusCode", "200");
        params.put("count", "0");
        params.put("negative", "-1");
        params.put("big", "1772372596569");
        params.put("text", "hello");

        ResultCreate result = new ResultCreate()
                .title("test")
                .execution(new ResultExecution().status("passed").duration(100L))
                .params(params);

        // Use Gson's toJsonTree to inspect the JsonElement tree
        Gson gson = JSON.getGson();
        JsonElement tree = gson.toJsonTree(result);
        JsonObject obj = tree.getAsJsonObject();
        JsonObject paramsObj = obj.getAsJsonObject("params");

        System.out.println("Params JsonObject: " + paramsObj);

        for (Map.Entry<String, JsonElement> entry : paramsObj.entrySet()) {
            JsonPrimitive prim = entry.getValue().getAsJsonPrimitive();
            assertTrue(prim.isString(),
                    String.format("param '%s' value '%s' should be JsonPrimitive(String) but isNumber=%b isString=%b",
                            entry.getKey(), prim, prim.isNumber(), prim.isString()));
        }
    }

    @Test
    void verifyDeserializeRoundtrip() {
        // Test that if we deserialize JSON with numeric-looking string params, they remain strings
        String inputJson = "{\"title\":\"test\",\"execution\":{\"status\":\"passed\",\"duration\":100}," +
                "\"params\":{\"count\":\"0\",\"code\":\"200\"}}";

        ResultCreate deserialized = JSON.getGson().fromJson(inputJson, ResultCreate.class);
        Map<String, String> params = deserialized.getParams();
        assertEquals("0", params.get("count"));
        assertEquals("200", params.get("code"));

        // Re-serialize and check
        String json = JSON.serialize(deserialized);
        System.out.println("Round-trip JSON: " + json);
        assertTrue(json.contains("\"count\":\"0\""), "count should remain string after round-trip");
        assertTrue(json.contains("\"code\":\"200\""), "code should remain string after round-trip");
    }
}
