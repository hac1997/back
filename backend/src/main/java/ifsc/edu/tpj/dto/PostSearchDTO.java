package ifsc.edu.tpj.dto;

import ifsc.edu.tpj.model.PostSorting;
import ifsc.edu.tpj.util.ValidTag;

import java.time.LocalDate;
import java.util.List;

public record PostSearchDTO(
        String keyword,
        @ValidTag
        List<String> tags,
        LocalDate createdAfter,
        LocalDate createdBefore,
        Integer minComments,
        Integer maxComments,
        PostSorting sorting
) {}
