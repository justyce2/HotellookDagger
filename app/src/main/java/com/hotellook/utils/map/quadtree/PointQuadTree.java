package com.hotellook.utils.map.quadtree;

import com.hotellook.utils.map.geometry.Bounds;
import com.hotellook.utils.map.geometry.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PointQuadTree<T extends Item> {
    private static final int MAX_DEPTH = 40;
    private static final int MAX_ELEMENTS = 50;
    private final Bounds mBounds;
    private List<PointQuadTree<T>> mChildren;
    private final int mDepth;
    private List<T> mItems;

    public interface Item {
        Point getPoint();
    }

    public PointQuadTree(double minX, double maxX, double minY, double maxY) {
        this(new Bounds(minX, maxX, minY, maxY));
    }

    public PointQuadTree(Bounds bounds) {
        this(bounds, 0);
    }

    private PointQuadTree(double minX, double maxX, double minY, double maxY, int depth) {
        this(new Bounds(minX, maxX, minY, maxY), depth);
    }

    private PointQuadTree(Bounds bounds, int depth) {
        this.mChildren = null;
        this.mBounds = bounds;
        this.mDepth = depth;
    }

    public void add(T item) {
        Point point = item.getPoint();
        if (this.mBounds.contains(point.f743x, point.f744y)) {
            insert(point.f743x, point.f744y, item);
        }
    }

    private void insert(double x, double y, T item) {
        if (this.mChildren == null) {
            if (this.mItems == null) {
                this.mItems = new ArrayList();
            }
            this.mItems.add(item);
            if (this.mItems.size() > MAX_ELEMENTS && this.mDepth < MAX_DEPTH) {
                split();
            }
        } else if (y < this.mBounds.midY) {
            if (x < this.mBounds.midX) {
                ((PointQuadTree) this.mChildren.get(0)).insert(x, y, item);
            } else {
                ((PointQuadTree) this.mChildren.get(1)).insert(x, y, item);
            }
        } else if (x < this.mBounds.midX) {
            ((PointQuadTree) this.mChildren.get(2)).insert(x, y, item);
        } else {
            ((PointQuadTree) this.mChildren.get(3)).insert(x, y, item);
        }
    }

    private void split() {
        this.mChildren = new ArrayList(4);
        this.mChildren.add(new PointQuadTree(this.mBounds.minX, this.mBounds.midX, this.mBounds.minY, this.mBounds.midY, this.mDepth + 1));
        this.mChildren.add(new PointQuadTree(this.mBounds.midX, this.mBounds.maxX, this.mBounds.minY, this.mBounds.midY, this.mDepth + 1));
        this.mChildren.add(new PointQuadTree(this.mBounds.minX, this.mBounds.midX, this.mBounds.midY, this.mBounds.maxY, this.mDepth + 1));
        this.mChildren.add(new PointQuadTree(this.mBounds.midX, this.mBounds.maxX, this.mBounds.midY, this.mBounds.maxY, this.mDepth + 1));
        List<T> items = this.mItems;
        this.mItems = null;
        for (T item : items) {
            insert(item.getPoint().f743x, item.getPoint().f744y, item);
        }
    }

    public boolean remove(T item) {
        Point point = item.getPoint();
        if (this.mBounds.contains(point.f743x, point.f744y)) {
            return remove(point.f743x, point.f744y, item);
        }
        return false;
    }

    private boolean remove(double x, double y, T item) {
        if (this.mChildren == null) {
            return this.mItems.remove(item);
        }
        if (y < this.mBounds.midY) {
            if (x < this.mBounds.midX) {
                return ((PointQuadTree) this.mChildren.get(0)).remove(x, y, item);
            }
            return ((PointQuadTree) this.mChildren.get(1)).remove(x, y, item);
        } else if (x < this.mBounds.midX) {
            return ((PointQuadTree) this.mChildren.get(2)).remove(x, y, item);
        } else {
            return ((PointQuadTree) this.mChildren.get(3)).remove(x, y, item);
        }
    }

    public void clear() {
        this.mChildren = null;
        if (this.mItems != null) {
            this.mItems.clear();
        }
    }

    public Collection<T> search(Bounds searchBounds) {
        List<T> results = new ArrayList();
        search(searchBounds, results);
        return results;
    }

    private void search(Bounds searchBounds, Collection<T> results) {
        if (!this.mBounds.intersects(searchBounds)) {
            return;
        }
        if (this.mChildren != null) {
            for (PointQuadTree<T> quad : this.mChildren) {
                quad.search(searchBounds, results);
            }
        } else if (this.mItems == null) {
        } else {
            if (searchBounds.contains(this.mBounds)) {
                results.addAll(this.mItems);
                return;
            }
            for (Item item : this.mItems) {
                if (searchBounds.contains(item.getPoint())) {
                    results.add(item);
                }
            }
        }
    }
}
