package com.room.ShangTingRoom.web.admin.vo.fee;

import com.room.ShangTingRoom.model.entity.FeeKey;
import com.room.ShangTingRoom.model.entity.FeeValue;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;


@Data
public class FeeKeyVo extends FeeKey {

    @Schema(description = "杂费value列表")
    private List<FeeValue> feeValueList;
}
