package info.wallyson.swc.mappers;

import info.wallyson.swc.web.dto.PageResponse;
import org.springframework.data.domain.Page;

public class PageResponseMapper {

  public static <T> PageResponse<T> toPageResponse(Page<T> page) {
    return new PageResponse<>(page.getContent(), page.getTotalElements());
  }
}
