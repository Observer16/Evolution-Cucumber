package model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import io.cucumber.datatable.DataTable;
import lombok.*;
import org.apache.logging.log4j.LogManager;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class TokenBody{
	private String build;
	private String version;
	private String platform;


	public TokenBody(DataTable dataTable) {
		List<Map<String, String>> productParameters = dataTable.asMaps(String.class, String.class);

		this.build = productParameters.get(0).get("build");
		this.version = productParameters.get(0).get("version");
		this.platform = productParameters.get(0).get("platform");
	}
	public String asJSON() {
		ObjectMapper jsonMapper = new JsonMapper().findAndRegisterModules();
		String jsonAsString;

		try {
			jsonAsString = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(this);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Ошибка при сериализации TokenBody", e);
		}

		return jsonAsString;
	}
}