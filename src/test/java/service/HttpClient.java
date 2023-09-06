package service;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSender;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import java.util.List;
import java.util.function.Function;
import static context.RunContext.RUN_CONTEXT;
import static io.restassured.RestAssured.given;
import static java.lang.String.format;
import org.testng.annotations.BeforeMethod;

public class HttpClient {

    public static final ResponseSpecBuilder respSpec = new ResponseSpecBuilder();

    /**
     * Генерирует комментарий к функции для указанного тела функции в блоке кода на языке Markdown с правильным синтаксисом языка.
     *
     * @param  respSpec  спецификация ответа для проверки соответствия ответа
     * @return           функция, которая проверяет ответ с использованием указанной спецификации ответа
     */
    public static Function<Response, Response> validatedWith(ResponseSpecification respSpec) {
        return response -> response.then().spec(respSpec).extract().response();
    }

    /**
     * Генерирует комментарий к функции для указанного тела функции в блоке кода.
     *
     * @param  respSpec  ResponseSpecBuilder, используемый для создания объекта Response
     * @return           функция, которая принимает объект Response и возвращает объект Response
     */
    public static Function<Response, Response> validatedWith(ResponseSpecBuilder respSpec) {
        return validatedWith(respSpec.build());
    }

    private String authToken;

    @BeforeMethod // Получение токена из файла перед каждым тестом
    public void setup() {
        authToken = TokenManager.readTokenFromFile();
    }

    /**
     * Отправляет запрос по указанному адресу с использованием заданного HTTP-метода.
     *
     * @param  method       HTTP-метод для запроса
     * @param  address      адрес, на который отправляется запрос
     * @param  paramsTable  список параметров запроса
     * @return              ответ, полученный от сервера
     */
    public Response sendRequest(String method, String address,
                                List<RequestParam> paramsTable) {
        RequestSender request = createRequest(paramsTable);
        Response response = request.request(Method.valueOf(method), address);

        return response;
    }

    /**
     * Создает объект отправителя запроса на основе указанного списка параметров запроса.
     *
     * @param  paramsTable   список параметров запроса
     * @return               объект отправителя запроса
     */
    public RequestSender createRequest(List<RequestParam> paramsTable) {
        String dataBody = null;
        RequestSpecification request = given();

        // Добавляем токен в заголовок
        authToken = TokenManager.readTokenFromFile();
        request.header("X-Auth-Token", authToken);

        for (RequestParam requestParam : paramsTable) {
            String name = requestParam.getName();
            String value = requestParam.getValue();
            switch (requestParam.getType()) {
                case PATH:
                    request.pathParam(name, value);
                    break;
                case PARAMETER:
                    request.param(name, value);
                    break;
                case HEADER:
                    request.header(name, value);
                    break;
                case BODY:
                    value = RUN_CONTEXT.get("dataBody", String.class);
                    dataBody = value;
                    request.body(dataBody);
                    break;
                default:
                    throw new IllegalArgumentException(format("Некорректно задан тип %s для параметра запроса %s ", requestParam.getType(), name));
            }
        }
        if (dataBody != null) {
            System.out.println("Тело запроса:\n" + dataBody);
        }
        return request;
    }
}