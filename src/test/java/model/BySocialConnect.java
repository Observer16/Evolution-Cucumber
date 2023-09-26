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

public class BySocialConnect {
	private String provider;
	private String code;

	public BySocialConnect(DataTable dataTable) {
		List<Map<String, String>> productParameters = dataTable.asMaps(String.class, String.class);

		this.provider = productParameters.get(0).get("provider");
		this.code = productParameters.get(0).get("code");

	}

	/**
	 * Генерирует JSON-представление текущего объекта.
	 *
	 * @return             строка, содержащая JSON-представление
	 */
	public String asJSON() {
		ObjectMapper jsonMapper = new JsonMapper().findAndRegisterModules();
		String jsonAsString;

		try {
			jsonAsString = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(this);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Ошибка при сериализации BySocialConnect", e);
		}

		return jsonAsString;
	}
}