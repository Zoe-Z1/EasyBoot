package cn.easy.boot3.common.Jackson;

import cn.hutool.core.collection.CollUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.WritableTypeId;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;

import java.io.IOException;
import java.util.List;

/**
 * @author zoe
 * @date 2023/10/13
 * @description List<Long>返回值序列化器
 */
public class ToStringListSerializer extends JsonSerializer<List<Long>> {

    /**
     * 重写该方法解决泛型擦除问题，否则无法存入Redis
     */
    @Override
    public void serializeWithType(List<Long> value, JsonGenerator gen, SerializerProvider serializers, TypeSerializer typeSer) throws IOException {
        WritableTypeId writableTypeId = typeSer.writeTypePrefix(gen, typeSer.typeId(value, JsonToken.VALUE_STRING));
        serialize(value, gen, serializers);
        typeSer.writeTypeSuffix(gen, writableTypeId);
    }

    @Override
    public void serialize(List<Long> longs, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartArray();
        if (CollUtil.isNotEmpty(longs)) {
            for (Long item : longs) {
                jsonGenerator.writeString(Long.toString(item));
            }
        }
        jsonGenerator.writeEndArray();
    }
}
