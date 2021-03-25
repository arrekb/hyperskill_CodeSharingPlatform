package platform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import platform.model.CodeSnippet;
import platform.model.Snippet;
import platform.model.SnippetRepository;

import java.util.Optional;

@RestController
public class CodeSharingAPIController {

    @Autowired
    private SnippetRepository snippetRepository;

    @PostMapping(value = "/api/code/new", consumes = "application/json")
    public ResponseEntity<String> replaceCode(@RequestBody CodeSnippet codeSnippet) {

        Snippet savedSnippet = snippetRepository.save(
                new Snippet(
                        codeSnippet.getCode(),
                        codeSnippet.getTime(),
                        codeSnippet.getViews()
                )
        );

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json");
        String result = String.format("{ \"id\" : \"%s\" }", savedSnippet.getSnippetId());
        return ResponseEntity.ok().
                headers(responseHeaders).
                body(result);
    }

    @GetMapping({"/api/code/{id}"})
    public ResponseEntity<String> getCodeAsJson(@PathVariable String id) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json");

        Optional<Snippet> snippetOptional = snippetRepository.findSnippetBySnippetId(id);
        Snippet savedSnippet;
        if (snippetOptional.isPresent() &&
                (!snippetOptional.get().isViewsRestriction() || snippetOptional.get().getViews() > 0) &&
                (!snippetOptional.get().isTimeRestriction() || snippetOptional.get().getTimeLeft() > 0)) {

            if (snippetOptional.get().getViews() > 0) {
                snippetOptional.get().setViews(snippetOptional.get().getViews() - 1);
                savedSnippet = snippetRepository.save(snippetOptional.get());
            } else {
                savedSnippet = snippetOptional.get();
            }


            String content = String.format(
                    "{" +
                            "\"code\": \"%s\"," +
                            "\"date\": \"%s\"," +
                            "\"time\": %d," +
                            "\"views\": %d" +
                            "}",
                    savedSnippet.getSnippetCode(),
                    savedSnippet.getSnippetDateFormatted(),
                    savedSnippet.getTimeLeft(),
                    savedSnippet.getViews());
            return ResponseEntity.ok()
                    .headers(responseHeaders)
                    .body(content);
        }
        //        return ResponseEntity.badRequest()
        //                .headers(responseHeaders)
        //                .body("Snippet with id=" + id + " not found.");
        return ResponseEntity.notFound().
                headers(responseHeaders).
                build();
    }

    @GetMapping({"/api/code/latest"})
    public ResponseEntity<String> getLatestAsJson() {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json");

        StringBuilder result = new StringBuilder("[");

        int counter = 0;
        for (Snippet snippet : snippetRepository.findAllByOrderBySnippetDateDesc()) {
            if (!snippet.isTimeRestriction() && !snippet.isViewsRestriction()) {
                result.append(String.format("{" +
                                "\"code\": \"%s\"," +
                                "\"date\": \"%s\"," +
                                "\"time\": %d," +
                                "\"views\": %d" +
                                "},",
                        snippet.getSnippetCode(),
                        snippet.getSnippetDateFormatted(),
                        snippet.getTimeLeft(),
                        snippet.getViews()
                ));
                counter++;
                if (counter > 9) {
                    break;
                }
            }
        }
        if (result.charAt(result.length() - 1) == ',') {
            result.deleteCharAt(result.length() - 1);
        }
        result.append("]");

        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body(result.toString());
    }
}
