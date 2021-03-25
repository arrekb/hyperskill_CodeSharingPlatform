package platform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import platform.model.CodeSnippet;
import platform.model.Snippet;
import platform.model.SnippetRepository;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Controller
public class CodeSharingWebController {

    @Autowired
    private SnippetRepository snippetRepository;

    @RequestMapping("/welcome")
    public String hello(Model model, @RequestParam(value = "name", required = false, defaultValue = "World") String name) {
        model.addAttribute("name", name);
        return "welcome";
    }


    @GetMapping({"/code/{id}"})
    public String getCode(Model model, @PathVariable String id) {

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

            model.addAttribute("code_snippet", savedSnippet.getSnippetCode());
            model.addAttribute("load_date", savedSnippet.getSnippetDateFormatted());
            if (savedSnippet.isTimeRestriction()) {
                model.addAttribute("time_restriction", savedSnippet.getTimeLeft());
            }
            if (savedSnippet.isViewsRestriction()) {
                model.addAttribute("views_restriction", savedSnippet.getViews());
            }
            return "snippet";
        }
        throw new ResponseStatusException(NOT_FOUND, "Unable to find snippet " + id);
    }

    @GetMapping({"/code/latest"})
    public String getLatest(Model model) {

        Map<String, CodeSnippet> latestCodeSnippetMap = new LinkedHashMap<>();
        int counter = 0;
        for (Snippet snippet : snippetRepository.findAllByOrderBySnippetDateDesc()) {
            if (!snippet.isTimeRestriction() && !snippet.isViewsRestriction()) {
                latestCodeSnippetMap.put(snippet.getSnippetId(), new CodeSnippet(snippet.getSnippetCode(), snippet.getSnippetDate()));

                counter++;
                if (counter > 9) {
                    break;
                }
            }
        }
        model.addAttribute("codeSnippets", latestCodeSnippetMap);
        return "latest";
    }

    @GetMapping({"/code/new"})
    public String getCodeNew() {
        return "new";
    }
}
