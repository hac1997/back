package ifsc.edu.tpj.dto;

import ifsc.edu.tpj.model.enums.ReportType;

public record ReportRequestDTO(
        Long reporterId,
        Long targetUserId,
        Long targetPostId,
        Long targetCommentId,
        ReportType reportType,
        String reason
) {}
