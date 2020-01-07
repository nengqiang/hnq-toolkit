package com.hnq.toolkit.util.kryo;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Registration;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.hnq.toolkit.util.exception.DeserializationException;
import com.hnq.toolkit.util.exception.SerializationException;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Static utilities relating to {@link Kryo}.
 *
 * @author henengqiang
 * @date 2019/07/01
 */
@Slf4j
public class KryoUtils {

    private KryoUtils() {
    }

    /**
     * execute {@link Consumer}.
     */
    public static void consume(final Consumer<Kryo> consumer) {
        Kryo kryo = getKryo();
        try {
            consumer.accept(kryo);
        } finally {
            release(kryo);
        }
    }

    /**
     * execute {@link Function} and return the function's result.
     */
    public static <T> T call(final Function<Kryo, T> callable) {
        Kryo kryo = getKryo();
        try {
            return callable.apply(kryo);
        } finally {
            release(kryo);
        }
    }

    private static Kryo getKryo() {
        try {
            return KryoPoolFactory.get().borrowObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void release(Kryo kryo) {
        if (kryo != null) {
            try {
                KryoPoolFactory.get().returnObject(kryo);
            } catch (Exception ex) {
                log.warn(ex.getMessage(), ex);
            }
        }
    }

    /**
     * Reads a class and returns its registration.
     *
     * @return May be null.
     * @see Kryo#readClass(Input)
     */
    public static Registration readClass(final InputStream inputStreams) {
        return call(kryo -> kryo.readClass(new Input(inputStreams)));
    }

    /**
     * Reads a class and returns its registration.
     *
     * @return May be null.
     * @see Kryo#readClass(Input)
     */
    public static Registration readClass(final byte[] data) {
        return readClass(new ByteArrayInputStream(data));
    }

    /**
     * Writes a class and returns its registration.
     *
     * @param clazz May be null.
     * @return Will be null if type is null.
     * @see Kryo#writeClass(Output, Class)
     */

    public static Registration writeClass(final OutputStream outputStream, final Class<?> clazz) {
        return call(kryo -> {
            try (Output output = new Output(outputStream)) {
                return kryo.writeClass(output, clazz);
            }
        });
    }

    /**
     * serialize the given object by kryo.
     *
     * @param object the object to serialize
     * @return the byte array serialized from the object
     */
    public static byte[] serialize(final Object object) {
        return call(kryo -> toSerialize(kryo, object));
    }

    /**
     * serialize the given object and class by kryo.
     *
     * @param object the object to serialize
     * @param clazz  the object's class
     * @return the byte array serialized from the object
     */
    public static <T> byte[] serialize(final Object object, final Class<T> clazz) {
        return call(kryo -> toSerialize(kryo, object, clazz));
    }

    /**
     * deserialize the given object by kryo.
     *
     * @param data the data to deserialize
     * @return the object deserialized from the byte array
     */
    public static Object deserialize(final byte[] data) {
        return call(kryo -> toDeserialize(kryo, data));
    }

    /**
     * deserialize the given object and class by kryo.
     *
     * @param data  the object to deserialize
     * @param clazz the deserialized object's class
     * @return the object deserialized from the byte array
     */
    public static <T> T deserialize(final byte[] data, final Class<T> clazz) {
        return call(kryo -> toDeserialize(kryo, data, clazz));
    }

    /**
     * clone the given object deeply by kryo.
     *
     * @param object the object to copy
     * @return a deep copy of the object
     */
    public static <T> T copy(final T object) {
        return call(kryo -> kryo.copy(object));
    }

    /**
     * clone the given object deeply by kryo.
     *
     * @param object the object to copy
     * @return a deep copy of the object
     */
    public static <T> T copy(final T object, final Serializer serializer) {
        return call(kryo -> kryo.copy(object, serializer));
    }

    /**
     * clone the given object shallow by kryo.
     *
     * @param object the object to copy shallow
     * @return a shallow copy of the object
     */
    public static <T> T copyShallow(final T object) {
        return call(kryo -> kryo.copyShallow(object));
    }

    /**
     * clone the given object shallow by kryo.
     *
     * @param object the object to copy shallow
     * @return a shallow copy of the object
     */
    public static <T> T copyShallow(final T object, final Serializer serializer) {
        return call(kryo -> kryo.copyShallow(object, serializer));
    }

    /**
     * clone the given object deeply by kryo.
     *
     * @param object the object to clone
     * @return a clone of the object
     */
    public static Object clone(final Object object) {
        return call(kryo -> {
            byte[] data = toSerialize(kryo, object);

            return toDeserialize(kryo, data);
        });
    }

    /**
     * clone the given object deeply by kryo,and return the bean which is instance of the given
     * class.
     *
     * @param object the object to clone
     * @param clazz  the object's class
     * @param <T>    the return type
     * @return a clone of the object
     */
    public static <T> T clone(final Object object, final Class<T> clazz) {
        return call(kryo -> {
            byte[] data = toSerialize(kryo, object, clazz);

            return toDeserialize(kryo, data, clazz);
        });
    }

    private static byte[] toSerialize(Kryo kryo, Object object) {
        try (ByteArrayOutputStream os = new ByteArrayOutputStream(); Output output = new Output(os)) {
            kryo.writeClassAndObject(output, object);
            output.flush();
            return os.toByteArray();
        } catch (IOException e) {
            throw new SerializationException("Failed to serialize by kryo", e);
        }
    }

    private static <T> byte[] toSerialize(Kryo kryo, Object object, Class<T> clazz) {
        try (ByteArrayOutputStream os = new ByteArrayOutputStream(); Output output = new Output(os)) {
            kryo.writeObjectOrNull(output, object, clazz);
            output.flush();
            return os.toByteArray();
        } catch (IOException e) {
            throw new SerializationException("Failed to serialize by kryo", e);
        }
    }

    private static Object toDeserialize(Kryo kryo, byte[] data) {
        try (ByteArrayInputStream is = new ByteArrayInputStream(data); Input input = new Input(is)) {
            return kryo.readClassAndObject(input);
        } catch (IOException e) {
            throw new DeserializationException("Failed to deserialize by kryo", e);
        }
    }

    private static <T> T toDeserialize(Kryo kryo, byte[] data, Class<T> clazz) {
        try (ByteArrayInputStream is = new ByteArrayInputStream(data); Input input = new Input(is)) {
            return kryo.readObjectOrNull(input, clazz);
        } catch (IOException e) {
            throw new DeserializationException("Failed to deserialize by kryo", e);
        }
    }

}
