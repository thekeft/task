package task.htmldivider.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import task.htmldivider.domain.UrlRequest;
import task.htmldivider.feign.UrlFeignClient;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UrlServiceTest {

    private static final String exampleHtml = "<!DOCTYPE html>\n" +
            "<html>\n" +
            "<head>\n" +
            " <meta charset=\"UTF-8\">\n" +
            " <title>Title-20230302</title>\n" +
            "</head>\n" +
            "<body>\n" +
            " <div>테스트</div>\n" +
            "</form>\n" +
            "</body>\n" +
            "</html>\n";

    @InjectMocks
    private UrlService urlService;
    @Mock
    private UrlFeignClient urlFeignClient;

    @Test
    void shouldSuccessToCallFeignClient(){
        when(urlFeignClient.getHtml(any())).thenReturn(exampleHtml);

        urlService.getHtml(new UrlRequest("http://localhost:8080", "EXCLUDE", 100));

        verify(urlFeignClient, times(1)).getHtml(any());
    }

    @Test
    void shouldSuccessToHtmlDivideByHundred(){
        when(urlFeignClient.getHtml(any())).thenReturn(exampleHtml);

        final var urlResponse = urlService.getHtml(new UrlRequest("http://localhost:8080", "EXCLUDE", 100));

        Assertions.assertThat(urlResponse.getQuotient()).isEmpty();
        Assertions.assertThat(urlResponse.getRemainder()).isEqualTo("e0i0l0T2t2233");
    }

    @Test
    void shouldSuccessToHtmlDivideByFive(){
        when(urlFeignClient.getHtml(any())).thenReturn(exampleHtml);

        final var urlResponse = urlService.getHtml(new UrlRequest("http://localhost:8080", "EXCLUDE", 5));

        Assertions.assertThat(urlResponse.getQuotient()).isEqualTo("e0i0l0T2t2");
        Assertions.assertThat(urlResponse.getRemainder()).isEqualTo("233");
    }
}