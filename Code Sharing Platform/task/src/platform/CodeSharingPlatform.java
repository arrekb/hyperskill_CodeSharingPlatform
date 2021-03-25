package platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CodeSharingPlatform {

    public static void main(String[] args) {

        // https://hellokoding.com/spring-boot-hello-world-example-with-freemarker/
        // https://www.javainuse.com/spring/spring-boot-freemarker-hello-world
        // https://www.youtube.com/watch?v=QvYFGqxnT4k
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        SpringApplication.run(CodeSharingPlatform.class, args);
    }
}
