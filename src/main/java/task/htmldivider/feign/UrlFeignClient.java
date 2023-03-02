package task.htmldivider.feign;

import feign.Headers;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.net.URI;

@FeignClient(name = "url-client", url = "http://placeholder-url", configuration = Config.class)
public interface UrlFeignClient {

//    @RequestLine("GET")
//    @Headers("Content-Type: text/html")
//    String getHtml(URI uri);

    @GetMapping()
    @Headers("Accept: text/html")
    String getHtml(URI baseUri);
}
