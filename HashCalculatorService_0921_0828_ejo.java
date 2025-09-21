// 代码生成时间: 2025-09-21 08:28:36
import org.springframework.stereotype.Service;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Service
public class HashCalculatorService {

    private String getMd5(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] messageDigest = md.digest(input.getBytes());
        return Base64.getEncoder().encodeToString(messageDigest);
    }

    private String getSha256(String input) throws NoSuchAlgorithmException {
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        byte[] shaHash = sha.digest(input.getBytes());
        return Base64.getEncoder().encodeToString(shaHash);
    }

    /**
     * Calculates the hash for the given input string using the specified algorithm.
     *
     * @param input The input string to be hashed.
     * @param algorithm The desired hashing algorithm (MD5 or SHA-256).
     * @return The hashed string in Base64 encoding.
     * @throws NoSuchAlgorithmException If the algorithm is not available.
     */
    public String calculateHash(String input, String algorithm) throws NoSuchAlgorithmException {
        if (input == null || algorithm == null) {
            throw new IllegalArgumentException("Input and algorithm must not be null.");
        }

        switch (algorithm.toUpperCase()) {
            case "MD5":
                return getMd5(input);
            case "SHA-256":
                return getSha256(input);
            default:
                throw new NoSuchAlgorithmException("Algorithm not supported: " + algorithm);
        }
    }

    // Additional hash algorithms can be added here in the future.
}
