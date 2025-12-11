package dw.model.aggregate.response;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AggregateResultDeserializer extends JsonDeserializer<AggregateResult> {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public AggregateResult deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.getCodec().readTree(p);

        if (!node.isArray() || node.size() != 2) {
            throw new IOException("Expected array with 2 elements [metadata, values]");
        }

        // Parse metadata (first element)
        ColumnMetadata metadata = mapper.treeToValue(node.get(0), ColumnMetadata.class);

        // Parse values array (second element)
        JsonNode valuesNode = node.get(1);
        List<AggregateValue> values = new ArrayList<>();
        if (valuesNode.isArray()) {
            for (JsonNode valueNode : valuesNode) {
                values.add(mapper.treeToValue(valueNode, AggregateValue.class));
            }
        }

        return new AggregateResult(metadata, values);
    }
}
