// 代码生成时间: 2025-09-18 16:40:59
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

@SpringBootApplication
@RestController
@RequestMapping("/api")
public class TextFileAnalyzer {

    private static final String UPLOAD_DIR = "/tmp/uploads/";
    private static final String CHARACTER_ENCODING = "UTF-8";

    public static void main(String[] args) {
        SpringApplication.run(TextFileAnalyzer.class, args);
    }

    @PostMapping("/analyze")
    public ResponseEntity<String> analyzeFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No file selected");
        }
        try {
            String content = fileToString(file);
            // Implement analysis logic here
            // For demonstration, we will just return the file content
            return ResponseEntity.ok("File content analyzed: " + content);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error analyzing file: " + e.getMessage());
        }
    }

    private String fileToString(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        String uploadDirPath = UPLOAD_DIR + fileName;
        Path path = Paths.get(uploadDirPath);
        file.transferTo(path);
        return Files.lines(path, java.nio.charset.StandardCharsets.UTF_8)
                .collect(Collectors.joining("
"));
    }
}
