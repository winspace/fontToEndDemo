package com.example.springtest.controller;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import java.io.File;
import java.io.FileOutputStream;

import org.json.JSONObject;
import org.json.JSONArray;


import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api")
public class DatabaseController {

    @Autowired
    private DataSource dataSource; // 注入数据源

    private static final String FILE_UPLOAD_PATH = "C:/uploads/"; // 设置文件上传路径

    @GetMapping("/data")
    public List<String> getData() {
        List<String> result = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT name FROM testtable")) {

            while (resultSet.next()) {
                String data = resultSet.getString("name");
                result.add(data);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return "上传失败，请选择文件";
        }

        try {
            // 生成唯一的文件名
            String fileName = file.getOriginalFilename();
            // 构建目标文件对象
            File destFile = new File(fileName);
            // 将上传文件保存到目标文件
            FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(destFile));

            // 将文件信息插入数据库
            try (Connection connection = dataSource.getConnection();
                 PreparedStatement statement = connection.prepareStatement("INSERT INTO files (filename, file_content) VALUES (?, ?)")) {
                statement.setString(1, fileName);
                statement.setBinaryStream(2, file.getInputStream());
                statement.executeUpdate();
            }

            return "文件上传成功，文件名：" + fileName;
        } catch (IOException | SQLException e) {
            e.printStackTrace();
            return "上传失败：" + e.getMessage();
        }
    }

    @GetMapping("/filelist")
    public String getFileList() {
        JSONArray jsonArray = new JSONArray();

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT filename FROM files")) {

            while (resultSet.next()) {
                String fileName = resultSet.getString("filename");
                JSONObject object1 = new JSONObject();
                object1.put("fileName",fileName);
                jsonArray.put(object1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return jsonArray.toString();
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM files WHERE filename = ?")) {
            statement.setString(1, fileName);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                // Retrieve the file content from the database
                Blob fileContent = resultSet.getBlob("file_content");
                // Convert the file content to a byte array
                byte[] bytes = fileContent.getBytes(1, (int) fileContent.length());
                // Create a ByteArrayResource
                ByteArrayResource resource = new ByteArrayResource(bytes);
                // Set the HTTP headers
                HttpHeaders headers = new HttpHeaders();
                headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"");
                headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);
                headers.add(HttpHeaders.CONTENT_LENGTH, String.valueOf(bytes.length));
                // Return the ResponseEntity
                return ResponseEntity.ok()
                        .headers(headers)
                        .body(resource);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ResponseEntity.notFound().build();
    }
}