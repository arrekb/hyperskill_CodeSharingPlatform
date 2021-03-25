package platform.model;

import org.springframework.data.repository.CrudRepository;
import platform.model.Snippet;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface SnippetRepository extends CrudRepository<Snippet, Long> {
    List<Snippet> findAllByOrderBySnippetDateDesc();
    Optional<Snippet> findSnippetBySnippetId(String snippetId);

}
