package model.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ByLoginPost {

	private String login;
	private String password;

	public ByLoginPost(Map<String, String> productParameters) {

		this.login = productParameters.get("login");
		this.password = productParameters.get("password");
	}
	public String asJSON() {
		ObjectMapper jsonMapper = new JsonMapper().findAndRegisterModules();
		String jsonAsString;

		try {
			jsonAsString = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(this);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Ошибка при сериализации ByPhonePost", e);
		}

		return jsonAsString;
	}
}