package ifsc.edu.tpj.controller;

import ifsc.edu.tpj.dto.PostRequestDTO;
import ifsc.edu.tpj.dto.PostResponseDTO;
import ifsc.edu.tpj.dto.PostSearchDTO;
import ifsc.edu.tpj.model.Tag;
import ifsc.edu.tpj.service.PostService;
import ifsc.edu.tpj.service.TagService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000",  allowCredentials = "true")
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;
    private final TagService tagService;

    @GetMapping
    public ResponseEntity<List<PostResponseDTO>> findAll() {
        List<PostResponseDTO> posts = postService.findAll().stream()
                .map(PostResponseDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(posts);
    }


    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(PostResponseDTO.fromEntity(postService.findById(id)));
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PostResponseDTO> create(@Valid @RequestBody PostRequestDTO postDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(PostResponseDTO.fromEntity(postService.create(postDTO)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PostResponseDTO> update(@PathVariable Long id, @Valid @RequestBody PostRequestDTO postDTO) {
        return ResponseEntity.ok(PostResponseDTO.fromEntity(postService.update(id, postDTO)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        postService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/{commentId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PostResponseDTO> pinCorrectAnswer(
            @PathVariable Long commentId,
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(PostResponseDTO.fromEntity(
                postService.pinCorrectAnswer(commentId, id))
        );
    }

    @PostMapping("/search/advanced")
    public ResponseEntity<List<PostResponseDTO>> searchAdvanced(
            @RequestBody PostSearchDTO filters
    ) {
        return ResponseEntity.ok(
                postService.searchPostsAdvanced(filters)
                        .stream()
                        .map(PostResponseDTO::fromEntity)
                        .toList()
        );
    }

    @PostMapping("/tags/{id}")
    public ResponseEntity<Tag> addTag(
            @RequestParam String name,
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(tagService.addTag(name, id));
    }
}
