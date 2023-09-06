package context;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.lang.Thread.currentThread;

public enum RunContext {
    RUN_CONTEXT;

    private final Map<String, Object> context = new ConcurrentHashMap<>();

    RunContext() {
    }

    /**
     * Удаляет указанный ключ из контекста.
     *
     * @param  key ключ для удаления
     */
    public void deleteKey(String key) {
        key = wrapKey(key);
        context.remove(key);
    }

    /**
     * Помещает объект в контекст с указанным ключом.
     *
     * @param  key    ключ, с которым будет ассоциирован объект
     * @param  object объект, который нужно сохранить
     */
    public <T> void put(String key, T object) {
        key = wrapKey(key);
        context.put(key, object);
    }

    /**
     * Получает значение, связанное с указанным ключом из контекста.
     *
     * @param  key  ключ для получения значения
     * @return      значение, связанное с указанным ключом
     */
    public Object get(String key) {
        key = wrapKey(key);
        Object object;
        try {
            object = context.get(key);
        } catch (NullPointerException e) {
            throw new AssertionError(String.format("Object with key %s doesn't exist!", key));
        }
        return object;
    }

    /**
     * Получает значение, связанное с указанным ключом, в виде строки.
     *
     * @param  key  ключ для получения значения
     * @return      значение, связанное с ключом в виде строки, или null, если значение не найдено
     */
    public String str(String key) {
        key = wrapKey(key);
        Object object;
        try {
            object = context.get(key);
        } catch (NullPointerException e) {
            throw new AssertionError(String.format("Object with key %s doesn't exist!", key));
        }
        return object != null ? object.toString() : null;
    }

    /**
     * Извлекает объект из контекста по указанному ключу и приводит его к заданному пользовательскому классу.
     *
     * @param  key        ключ, используемый для извлечения объекта из контекста
     * @param  userClass  класс, к которому следует привести извлеченный объект
     * @return            извлеченный объект, приведенный к пользовательскому классу
     */
    public <T> T get(String key, Class<T> userClass) {
        key = wrapKey(key);
        Object object;
        try {
            object = context.get(key);
        } catch (NullPointerException e) {
            throw new AssertionError(String.format("Object with key %s doesn't exist!", key));
        }
        return userClass.cast(object);
    }

    /**
     * Оборачивает указанный ключ, добавляя к нему идентификатор текущего потока.
     *
     * @param  key  ключ для обертывания
     * @return      обернутый ключ
     */
    private String wrapKey(String key) {
        return currentThread().getId() + ":" + key;
    }
}