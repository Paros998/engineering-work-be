package pg.search.store.domain.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Data
public final class PageResponse<T> {
    private final Integer currentPage;
    private final Integer totalPages;
    private final List<T> content;

    private String cacheMeta;

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (PageResponse) obj;
        return Objects.equals(this.currentPage, that.currentPage) &&
                Objects.equals(this.totalPages, that.totalPages) &&
                Objects.equals(this.content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currentPage, totalPages, content);
    }

    @Override
    public String toString() {
        return "PageResponse[" +
                "currentPage=" + currentPage + ", " +
                "totalPages=" + totalPages + ", " +
                "content=" + content + ']';
    }

}