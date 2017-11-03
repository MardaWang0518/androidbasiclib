package com.hxh.component.basicore.ui.mrecycleview;

/**
 * Created by hxh on 2017/7/18.
 */
public class PaginationBuilder {



    public static String PAGEKEY = "pageIndex";

    public int pageIndex;
    public int pageSize;
    public String pageIndexFieldName;
    public String pageSizeFieldName;
    public static class Builder
    {
        private int pageIndex=0;
        private int pageSize=10;
        private String pageFieldName;
        public String pageSizeFieldName;
        public Builder setPageSize(int size )
        {
            this.pageSize =size;
            return this;
        }

        public Builder setPageIndex(int index)
        {
            this.pageIndex = index;
            return this;
        }

        public Builder setPageIndexFieldName(String name)
        {
            this.pageFieldName = name;
            return this;
        }

        public Builder setPageSizeFieldName(String name)
        {
            this.pageSizeFieldName = name;
            return this;
        }

        public PaginationBuilder create()
        {
            PaginationBuilder builder = new PaginationBuilder();

            builder.pageIndex = pageIndex;
            builder.pageSize = pageSize;
            builder.pageIndexFieldName = pageFieldName;
            builder.pageSizeFieldName = pageSizeFieldName;
            PAGEKEY = builder.pageIndexFieldName;
            return builder;
        }
    }

    public static String getPAGEKEY() {
        return PAGEKEY;
    }

    public static void setPAGEKEY(String PAGEKEY) {
        PaginationBuilder.PAGEKEY = PAGEKEY;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getPageIndexFieldName() {
        return pageIndexFieldName;
    }

    public void setPageIndexFieldName(String pageIndexFieldName) {
        this.pageIndexFieldName = pageIndexFieldName;
    }

    public String getPageSizeFieldName() {
        return pageSizeFieldName;
    }

    public void setPageSizeFieldName(String pageSizeFieldName) {
        this.pageSizeFieldName = pageSizeFieldName;
    }
}
