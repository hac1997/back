package ifsc.edu.tpj.service;

import ifsc.edu.tpj.model.Tag;
import ifsc.edu.tpj.repository.PostRepository;
import ifsc.edu.tpj.repository.TagRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;
    private final PostRepository postRepository;

    @Transactional
    @PreAuthorize("isAuthenticated()")
    public Tag addTag(String name, Long postId) {
        Tag tag = new Tag();
        tag.setName(name);
        tag.getPosts().add(
                postRepository.findById(postId)
                        .orElseThrow(() -> new EntityNotFoundException("POST NOT FOUND WITH ID :: " + postId))
        );

        return tagRepository.save(tag);
    }

    protected Set<Tag> sanitizeTag(List<String> tags) {
        Set<Tag> result = new HashSet<>();
        for (String raw : tags) {
            String finalRaw = raw.trim().toLowerCase();
            result.add(
                    tagRepository.findByName(finalRaw)
                            .orElseGet(() -> tagRepository.save(Tag.builder()
                                    .name(finalRaw)
                                    .build()))
            );
        }
        return result;
    }
}
