package com.room.ShangTingRoom.web.admin.custom.converter;

import com.room.ShangTingRoom.model.enums.BaseEnum;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

public class StringToBaseEnumConverterFactory implements ConverterFactory<String, BaseEnum> {
    @Override
    public <T extends BaseEnum> Converter<String, T> getConverter(Class<T> targetType) {
        return new Converter<>() {
            @Override
            public T convert(String source) {
                for (T enumConstant : targetType.getEnumConstants()) {
                    if (enumConstant.getCode().toString().equals(source)) {
                        return enumConstant;
                    }
                }
                throw new IllegalArgumentException("非法枚举值：" + source);
            }
        };
    }
}
