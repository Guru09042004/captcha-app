package com.captcha;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/captcha")
public class CaptchaController {

    @Autowired
    private CaptchaService captchaService;

    @GetMapping("/generate")
    public Map<String, String> generate(HttpSession session) {
        String captchaText = captchaService.generateCaptcha(session.getId());
        Map<String, String> response = new HashMap<>();
        response.put("captchaId", session.getId());
        response.put("captchaText", captchaText);
        return response;
    }

    @PostMapping("/validate")
    public Map<String, Object> validate(@RequestBody Map<String, String> request, HttpSession session) {
        String userInput = request.get("answer");
        boolean isValid = captchaService.validateCaptcha(session.getId(), userInput);
        Map<String, Object> response = new HashMap<>();
        response.put("valid", isValid);
        response.put("message", isValid ? "✅ CAPTCHA Verified!" : "❌ Wrong CAPTCHA!");
        return response;
    }

    @GetMapping("/audio/{text}")
    public ResponseEntity<byte[]> getAudio(@PathVariable String text) {
        try {
            byte[] audioData = captchaService.generateAudioFile(text.toUpperCase());
            return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=captcha.wav")
                .contentType(MediaType.parseMediaType("audio/wav"))
                .body(audioData);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
