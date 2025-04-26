package com.room.ShangTingRoom.web.admin.controller.apartment;


import com.room.ShangTingRoom.common.result.Result;
import com.room.ShangTingRoom.web.admin.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;


@Tag(name = "文件管理")
@RequestMapping("/admin/file")
@RestController
public class FileUploadController {
    private final FileService fileService;
    @Autowired
    public FileUploadController(FileService fileService) {
        this.fileService = fileService;
    }

    @Operation(summary = "上传文件")
    @PostMapping("upload")
    public Result<String> upload(@RequestParam MultipartFile file) throws Exception {
        String url = fileService.upload(file);
        return Result.ok(url);
    }

    @Operation(summary = "删除文件")
    @DeleteMapping("delete")
    public Result<Boolean> delete(@RequestParam String objectName) {
        boolean result = fileService.delete(objectName);
        return Result.ok(result);
    }

    @Operation(summary = "获取文件列表")
    @GetMapping("list")
    public Result<List<Map<String, Object>>> listFiles(@RequestParam(defaultValue = "") String prefix) {
        List<Map<String, Object>> fileList = fileService.listFiles(prefix);
        return Result.ok(fileList);
    }
}
