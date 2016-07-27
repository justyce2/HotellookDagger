package com.hotellook.filters.pages;

import com.hotellook.filters.FiltersSnapshot;
import com.hotellook.filters.PersistentFilters;
import com.hotellook.filters.items.PaymentFilterItem;
import com.hotellook.filters.task.FilterTask;
import com.hotellook.search.SearchData;

public class AmenitiesFiltersCategory implements FiltersCategory {
    private final RoomsAmenitiesFiltersCategory optionsPage;
    private final PaymentFilterItem paymentFilterItem;
    private final HotelsAmenitiesFiltersCategory servicesPage;

    public AmenitiesFiltersCategory() {
        this.optionsPage = new RoomsAmenitiesFiltersCategory();
        this.servicesPage = new HotelsAmenitiesFiltersCategory();
        this.paymentFilterItem = new PaymentFilterItem();
    }

    public void setUp(SearchData searchData, PersistentFilters persistentFilters) {
        this.optionsPage.setUp(searchData, persistentFilters);
        this.servicesPage.setUp(searchData, persistentFilters);
        this.paymentFilterItem.setUp(searchData, persistentFilters);
    }

    public void reset() {
        this.optionsPage.reset();
        this.servicesPage.reset();
        this.paymentFilterItem.reset();
    }

    public void addFilterLogic(FilterTask logic) {
        this.optionsPage.addFilterLogic(logic);
        this.servicesPage.addFilterLogic(logic);
        logic.addRoomFilter(this.paymentFilterItem.newCriterion());
    }

    public boolean inDefaultState() {
        return this.optionsPage.inDefaultState() && this.servicesPage.inDefaultState() && this.paymentFilterItem.inDefaultState();
    }

    public int _hashCode() {
        return hashCode();
    }

    public void saveState(FiltersSnapshot snapshot) {
        this.optionsPage.saveState(snapshot);
        this.servicesPage.saveState(snapshot);
        this.paymentFilterItem.saveState(snapshot);
    }

    public void restoreState(FiltersSnapshot snapshot) {
        this.optionsPage.restoreState(snapshot);
        this.servicesPage.restoreState(snapshot);
        this.paymentFilterItem.restoreState(snapshot);
    }

    public void release() {
        this.optionsPage.release();
        this.servicesPage.release();
        this.paymentFilterItem.release();
    }

    public int hashCode() {
        return (((this.optionsPage.hashCode() * 31) + this.servicesPage.hashCode()) * 31) + this.paymentFilterItem.hashCode();
    }

    public RoomsAmenitiesFiltersCategory getRoomsAmenitiesPage() {
        return this.optionsPage;
    }

    public HotelsAmenitiesFiltersCategory getHotelsAmenitiesPage() {
        return this.servicesPage;
    }

    public PaymentFilterItem getPaymentFilterItem() {
        return this.paymentFilterItem;
    }
}
