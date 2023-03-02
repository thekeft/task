package task.htmldivider.feign;

import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.net.URI;

@FeignClient(name = "url-client", url = "http://placeholder", configuration = Config.class)
public interface UrlFeignClient {

    @GetMapping()
    String getHtml(URI baseUri);
}
