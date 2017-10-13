package com.hxh.component.basicore.ui.mrecycleview;

/**
 * Created by hxh on 2017/7/18.
 */
public class PaginationBuilder {



    public static String PAGEKEY = "pageIndex";

    public int pageIndex;
    public int pageSize;
    public String pageFieldName;

    public static class Builder
    {
        private int pageIndex=0;
        private int pageSize;
        private String pageFieldName;
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

        public PaginationBuilder create()
        {
            PaginationBuilder builder = new PaginationBuilder();

            builder.pageIndex = pageIndex;
            builder.pageSize = pageSize;
            builder.pageFieldName = pageFieldName;
            PAGEKEY = builder.pageFieldName;
            return builder;
        }
    }



}
