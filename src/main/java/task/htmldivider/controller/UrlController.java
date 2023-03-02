package task.htmldivider.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.URL;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import task.htmldivider.domain.UrlRequest;
import task.htmldivider.service.UrlService;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Slf4j
@Controller
@Validated
@RequiredArgsConstructor
public class UrlController {

    private final UrlService urlService;

    @GetMapping("/url")
    public String getHtml(@RequestParam @NotBlank(message = "URL은 공백일 수 없습니다.") @URL String url,
                                @RequestParam @NotBlank String extractType,
                                @RequestParam @Positive(message = "추출단위는 0보다 커야합니다.") @Max(value = 1000000000, message = "최대 1000000000까지 입력가능합니다.") int number,
                           Model model){
        UrlRequest urlRequest = new UrlRequest(url, extractType, number);

        final var result = urlService.getHtml(urlRequest);

        model.addAttribute("result", result);
        return "/index :: #afterCalculation";
    }
}
