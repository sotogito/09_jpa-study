package com.sotogito.springdatajpa.util;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class PageUtil {

    public Map<String, Object> getPageInfo(Page page, int pagePerBlock) {
        int currentPage = page.getNumber() + 1;
        int beginPage = (currentPage - 1) / pagePerBlock * pagePerBlock + 1;
        int endPage = Math.min(beginPage + pagePerBlock - 1, page.getTotalPages());

        return new HashMap<String, Object>() {{
            put("totalCount", page.getTotalElements());
            put("page", currentPage);
            put("size", page.getSize());
            put("pagePerBlock", pagePerBlock);
            put("totalPage", page.getTotalPages());
            put("beginPage", beginPage);
            put("endPage", endPage);
            put("isFirst", page.isFirst());
            put("isLast", page.isLast());
        }};
    }

}
