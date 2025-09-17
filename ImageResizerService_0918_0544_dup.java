// 代码生成时间: 2025-09-18 05:44:28
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ImageResizerService {

    // Resizes an image to the specified width and height
    public BufferedImage resizeImage(BufferedImage originalImage, int width, int height) {
        BufferedImage resizedImage = new BufferedImage(width, height, originalImage.getType());
        return resizedImage; // Placeholder for actual resizing logic
    }

    // Saves the resized image to a file
    public void saveResizedImage(BufferedImage image, String fileName) {
        try {
            Files.write(Paths.get(fileName), ImageIO.write(image, "png", new File(fileName)));
        } catch (IOException e) {
            throw new RuntimeException("Failed to save the resized image.", e);
        }
    }

    // Process a list of images, resizing them as needed
    public List<String> processImages(List<MultipartFile> images, int targetWidth, int targetHeight) {
        List<String> resizedImagePaths = new ArrayList<>();
        for (MultipartFile imageFile : images) {
            try {
                BufferedImage originalImage = ImageIO.read(imageFile.getInputStream());
                BufferedImage resizedImage = resizeImage(originalImage, targetWidth, targetHeight);
                String resizedFileName = generateFileName(imageFile.getOriginalFilename(), targetWidth, targetHeight);
                saveResizedImage(resizedImage, resizedFileName);
                resizedImagePaths.add(resizedFileName);
            } catch (IOException e) {
                throw new RuntimeException("Failed to process the image file: " + imageFile.getOriginalFilename(), e);
            }
        }
        return resizedImagePaths;
    }

    // Generates a new filename based on the original filename and new dimensions
    private String generateFileName(String originalFilename, int width, int height) {
        String extension = determineFileExtension(originalFilename);
        return originalFilename.substring(0, originalFilename.lastIndexOf('.')) + "_" + width + "x" + height + "." + extension;
    }

    // Determines the file extension from the original filename
    private String determineFileExtension(String filename) {
        int index = Math.max(filename.lastIndexOf("."), filename.lastIndexOf(":"));
        return filename.substring(index + 1);
    }

    // Main method for testing the ImageResizerService
    public static void main(String[] args) {
        // This is where you would initialize and use the ImageResizerService
        // For example, with a list of MultipartFile representing uploaded images
    }
}
