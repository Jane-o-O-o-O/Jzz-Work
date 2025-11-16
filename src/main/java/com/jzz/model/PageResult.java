package com.jzz.model;

import java.io.Serializable;
import java.util.List;

/**
 * 分页结果封装类
 * Page Result Wrapper Class
 *
 * 用于封装分页查询的结果数据
 * Used to encapsulate paginated query results
 *
 * @author Jzz
 * @version 1.0
 * @param <T> 数据类型 (Data Type)
 */
public class PageResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 当前页码（从1开始）
     * Current Page Number (starts from 1)
     */
    private Integer currentPage;

    /**
     * 每页显示的记录数
     * Number of Records Per Page
     */
    private Integer pageSize;

    /**
     * 总记录数
     * Total Number of Records
     */
    private Long totalCount;

    /**
     * 总页数
     * Total Number of Pages
     */
    private Integer totalPages;

    /**
     * 当前页的数据列表
     * Data List for Current Page
     */
    private List<T> data;

    // ==================== 构造方法 (Constructors) ====================

    /**
     * 无参构造方法
     * No-argument Constructor
     */
    public PageResult() {
    }

    /**
     * 全参构造方法
     * Full-argument Constructor
     *
     * @param currentPage 当前页码
     * @param pageSize 每页记录数
     * @param totalCount 总记录数
     * @param data 数据列表
     */
    public PageResult(Integer currentPage, Integer pageSize, Long totalCount, List<T> data) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
        this.data = data;
        // 自动计算总页数 (Automatically calculate total pages)
        this.totalPages = (int) Math.ceil((double) totalCount / pageSize);
    }

    // ==================== Getter 和 Setter 方法 (Getter and Setter Methods) ====================

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
        // 当设置总记录数时，自动重新计算总页数
        // Automatically recalculate total pages when setting total count
        if (pageSize != null && pageSize > 0) {
            this.totalPages = (int) Math.ceil((double) totalCount / pageSize);
        }
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    // ==================== 辅助方法 (Helper Methods) ====================

    /**
     * 判断是否有上一页
     * Check if there is a previous page
     *
     * @return true 有上一页，false 没有上一页
     */
    public boolean hasPrevious() {
        return currentPage != null && currentPage > 1;
    }

    /**
     * 判断是否有下一页
     * Check if there is a next page
     *
     * @return true 有下一页，false 没有下一页
     */
    public boolean hasNext() {
        return currentPage != null && totalPages != null && currentPage < totalPages;
    }

    /**
     * 获取起始记录索引（用于 SQL LIMIT）
     * Get starting record index (for SQL LIMIT)
     *
     * @return 起始索引
     */
    public int getStartIndex() {
        if (currentPage == null || pageSize == null) {
            return 0;
        }
        return (currentPage - 1) * pageSize;
    }

    @Override
    public String toString() {
        return "PageResult{" +
                "currentPage=" + currentPage +
                ", pageSize=" + pageSize +
                ", totalCount=" + totalCount +
                ", totalPages=" + totalPages +
                ", dataSize=" + (data != null ? data.size() : 0) +
                '}';
    }
}
