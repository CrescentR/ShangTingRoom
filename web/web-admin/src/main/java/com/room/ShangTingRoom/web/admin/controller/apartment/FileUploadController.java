package com.room.ShangTingRoom.web.admin.controller.apartment;


import com.room.ShangTingRoom.common.result.Result;
import com.room.ShangTingRoom.web.admin.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


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
    public Result<String> upload(@RequestParam MultipartFile file){
        String url = fileService.upload(file);
        return Result.ok(url);
    }

}
