package task.htmldivider.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import task.htmldivider.domain.UrlRequest;
import task.htmldivider.domain.UrlResponse;
import task.htmldivider.feign.UrlFeignClient;

import java.net.URI;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class UrlService {

    private static final String EXCLUDE = "EXCLUDE";
    private final UrlFeignClient urlFeignClient;

    public UrlResponse getHtml(UrlRequest urlRequest) {
        URI baseUri = URI.create(urlRequest.getUrl());
        var html = urlFeignClient.getHtml(baseUri);

        if(urlRequest.getExtractType().equals(EXCLUDE)){
            html = html.replaceAll("<[^>]*>", "").trim();
        }

        String mixedString = separateCharAndNumber(html);
        return calculate(mixedString, urlRequest.getNumber());
    }

    private String separateCharAndNumber(final String html){
        Comparator<String> charComparator = (s1, s2) -> {
            if(s1.compareToIgnoreCase(s2) == 0){
                return s1.compareTo(s2);
            }else{
                return s1.compareToIgnoreCase(s2);
            }
        };

        final var numbers = html.replaceAll("[^0-9]", "");
        final var sortedNumbers = Stream.of(numbers.split(""))
                .sorted()
                .collect(Collectors.joining());

        final var chars = html.replaceAll("[^a-zA-Z]", "");
        final var sortedChars = Stream.of(chars.split(""))
                .sorted(charComparator)
                .collect(Collectors.joining());

        return mixCharAndNumber(sortedNumbers, sortedChars);
    }

    private String mixCharAndNumber(final String numbers, final String chars){
        boolean isNumberLonger = numbers.length() - chars.length() > 0;
        final int count = isNumberLonger ? chars.length() : numbers.length();
        final String rest = isNumberLonger ? numbers.substring(count) : chars.substring(count);

        StringBuilder stringBuilder = new StringBuilder();

        for(int i=0; i<count; i++){
            stringBuilder.append(chars.charAt(i));
            stringBuilder.append(numbers.charAt(i));
        }

        return stringBuilder.append(rest).toString();
    }

    private UrlResponse calculate(final String mixed, final int number){
        int quotient = mixed.length() / number;

        String sharedString = mixed.substring(0, number * quotient);
        return new UrlResponse(sharedString, mixed.substring(number * quotient));
    }
}
