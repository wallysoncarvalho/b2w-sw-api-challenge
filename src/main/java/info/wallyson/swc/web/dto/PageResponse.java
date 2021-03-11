package info.wallyson.swc.web.dto;

import java.util.List;

public class PageResponse<T> {
  private List<T> items;
  private long total;

  public PageResponse() {
  }

  public PageResponse(List<T> items, long total) {
    this.items = items;
    this.total = total;
  }

  public List<T> getItems() {
    return items;
  }

  public long getTotal() {
    return total;
  }

  public void setItems(List<T> items) {
    this.items = items;
  }

  public void setTotal(long total) {
    this.total = total;
  }
}
