package model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import io.cucumber.datatable.DataTable;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BasketPatchBody {
	private List<BatchItem> batch;

	public BasketPatchBody(DataTable dataTable) {
		List<Map<String, String>> productParameters = dataTable.asMaps(String.class, String.class);

		this.batch = new ArrayList<>();
		for (Map<String, String> params : productParameters) {
			BatchItem batchItem = new BatchItem(params);
			this.batch.add(batchItem);
		}
	}
	public String asJSON() {
		ObjectMapper jsonMapper = new JsonMapper().findAndRegisterModules();
		String jsonAsString;

		try {
			jsonAsString = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(this);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Ошибка при сериализации BasketPatchBody", e);
		}

		return jsonAsString;
	}
}