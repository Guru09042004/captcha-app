package com.captcha;

import org.springframework.stereotype.Service;
import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CaptchaService {

    private final Map<String, String> captchaStore = new ConcurrentHashMap<>();

    public String generateCaptcha(String sessionId) {
        String pool = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        List<Character> chars = new ArrayList<>();
        chars.add('R');
        Random rand = new Random();
        
        while (chars.size() < 6) {
            char c = pool.charAt(rand.nextInt(pool.length()));
            if (!chars.contains(c)) chars.add(c);
        }
        Collections.shuffle(chars);
        
        StringBuilder sb = new StringBuilder();
        for (char c : chars) sb.append(c);
        String captcha = sb.toString();
        captchaStore.put(sessionId, captcha);
        return captcha;
    }

    public boolean validateCaptcha(String sessionId, String userInput) {
        String stored = captchaStore.get(sessionId);
        return stored != null && stored.equalsIgnoreCase(userInput);
    }

    public byte[] generateAudioFile(String text) throws Exception {
        Map<Character, String> pronunciation = new HashMap<>();
        pronunciation.put('A', "ae"); pronunciation.put('B', "bee");
        pronunciation.put('C', "see"); pronunciation.put('D', "dee");
        pronunciation.put('E', "ee"); pronunciation.put('F', "ef");
        pronunciation.put('G', "jee"); pronunciation.put('H', "ach");
        pronunciation.put('I', "eye"); pronunciation.put('J', "jay");
        pronunciation.put('K', "kay"); pronunciation.put('L', "el");
        pronunciation.put('M', "em"); pronunciation.put('N', "en");
        pronunciation.put('O', "oh"); pronunciation.put('P', "pee");
        pronunciation.put('Q', "kyoo"); pronunciation.put('R', "arr");
        pronunciation.put('S', "ash"); pronunciation.put('T', "tee");
        pronunciation.put('U', "yoo"); pronunciation.put('V', "vee");
        pronunciation.put('W', "double-you"); pronunciation.put('X', "ex");
        pronunciation.put('Y', "wye"); pronunciation.put('Z', "zed");

        StringBuilder pronounceText = new StringBuilder();
        for (char c : text.toCharArray()) {
            pronounceText.append(pronunciation.getOrDefault(c, String.valueOf(c))).append(" ");
        }

        File tempFile = File.createTempFile("captcha_", ".wav");
        ProcessBuilder pb = new ProcessBuilder(
            "espeak", "-w", tempFile.getAbsolutePath(),
            "-v", "en-in", "-s", "70", "-p", "65",
            pronounceText.toString().trim()
        );
        pb.redirectErrorStream(true);
        Process process = pb.start();
        process.waitFor();

        byte[] audioData = new byte[(int) tempFile.length()];
        try (FileInputStream fis = new FileInputStream(tempFile)) {
            fis.read(audioData);
        }
        tempFile.delete();
        return audioData;
    }
}
