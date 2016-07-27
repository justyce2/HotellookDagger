package com.hotellook.filters.pages;

import com.hotellook.filters.items.BaseLogicItem;
import com.hotellook.filters.task.FilterTask;

public interface FiltersCategory extends BaseLogicItem {
    void addFilterLogic(FilterTask filterTask);
}
