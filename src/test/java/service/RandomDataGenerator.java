package service;

import net.datafaker.Faker;

public class RandomDataGenerator {

    public static Faker faker = new Faker();

    public static String getRandomDateFor(RandomDataTypeNames dateTypeNames){
        switch (dateTypeNames){
            case FIRSTNAME:
                return faker.name().firstName();
            case LASTNAME:
                return faker.name().lastName();
            case FULLNAME:
                return faker.name().fullName();
            case COUNTRY:
                return faker.address().country();
            case CITYNAME:
                return faker.address().cityName();
            case PHONE:
                return faker.phoneNumber().phoneNumber();
            case NUMBER:
                return faker.number().digits(9);
            case CREDITCARD:
                return faker.finance().creditCard();
            default:
                return "Date Type Names is not available";
        }
    }
}
