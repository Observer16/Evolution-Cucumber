package api;

import java.util.List;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Article {
	private String layout;
	private List<Object> categories;
	private String title;
	private String lang;
	private String url;
	private String content;
	private List<Object> tags;
}