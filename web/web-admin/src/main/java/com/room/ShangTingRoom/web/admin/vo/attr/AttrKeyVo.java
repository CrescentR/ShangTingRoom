package com.room.ShangTingRoom.web.admin.vo.attr;

import com.room.ShangTingRoom.model.entity.AttrKey;
import com.room.ShangTingRoom.model.entity.AttrValue;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Data
public class AttrKeyVo extends AttrKey {

    @Schema(description = "属性value列表")
    private List<AttrValue> attrValueList;
}
