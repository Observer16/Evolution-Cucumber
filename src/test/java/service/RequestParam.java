package service;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestParam {

    private RequestParamType type;
    private String name;
    private String value;

    public void setType(String type) {
        this.type = RequestParamType.valueOf(type.toUpperCase());
    }
}