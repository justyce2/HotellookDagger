package com.hotellook.ui.adapters;

import android.database.Cursor;
import android.database.DataSetObserver;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import com.j256.ormlite.field.FieldType;

public abstract class CursorRecyclerViewAdapter<VH extends ViewHolder> extends Adapter<VH> {
    private Cursor mCursor;
    private DataSetObserver mDataSetObserver;
    private boolean mDataValid;
    private int mRowIdColumn;

    private class NotifyingDataSetObserver extends DataSetObserver {
        private NotifyingDataSetObserver() {
        }

        public void onChanged() {
            super.onChanged();
            CursorRecyclerViewAdapter.this.mDataValid = true;
            CursorRecyclerViewAdapter.this.notifyDataSetChanged();
        }

        public void onInvalidated() {
            super.onInvalidated();
            CursorRecyclerViewAdapter.this.mDataValid = false;
            CursorRecyclerViewAdapter.this.notifyDataSetChanged();
        }
    }

    public abstract void onBindViewHolder(VH vh, int i, Cursor cursor);

    public CursorRecyclerViewAdapter(Cursor cursor) {
        this.mCursor = cursor;
        this.mDataValid = cursor != null;
        this.mRowIdColumn = this.mDataValid ? this.mCursor.getColumnIndex(FieldType.FOREIGN_ID_FIELD_SUFFIX) : -1;
        this.mDataSetObserver = new NotifyingDataSetObserver();
        if (this.mCursor != null) {
            this.mCursor.registerDataSetObserver(this.mDataSetObserver);
        }
    }

    public Cursor getCursor() {
        return this.mCursor;
    }

    public int getItemCount() {
        if (!this.mDataValid || this.mCursor == null) {
            return 0;
        }
        return this.mCursor.getCount();
    }

    public void setHasStableIds(boolean hasStableIds) {
        super.setHasStableIds(true);
    }

    public void onBindViewHolder(VH viewHolder, int position) {
        if (this.mDataValid) {
            onBindViewHolder(viewHolder, position, this.mCursor);
            return;
        }
        throw new IllegalStateException("this should only be called when the cursor is valid");
    }

    public void changeCursor(Cursor cursor) {
        Cursor old = swapCursor(cursor);
        if (old != null) {
            old.close();
        }
    }

    public Cursor swapCursor(Cursor newCursor) {
        if (newCursor == this.mCursor) {
            return null;
        }
        Cursor oldCursor = this.mCursor;
        if (!(oldCursor == null || this.mDataSetObserver == null)) {
            oldCursor.unregisterDataSetObserver(this.mDataSetObserver);
        }
        this.mCursor = newCursor;
        if (this.mCursor != null) {
            if (this.mDataSetObserver != null) {
                this.mCursor.registerDataSetObserver(this.mDataSetObserver);
            }
            this.mRowIdColumn = newCursor.getColumnIndexOrThrow(FieldType.FOREIGN_ID_FIELD_SUFFIX);
            this.mDataValid = true;
            notifyDataSetChanged();
            return oldCursor;
        }
        this.mRowIdColumn = -1;
        this.mDataValid = false;
        notifyDataSetChanged();
        return oldCursor;
    }

    public void clean() {
        if (this.mDataSetObserver != null && this.mCursor != null) {
            this.mCursor.unregisterDataSetObserver(this.mDataSetObserver);
            this.mCursor.close();
        }
    }
}
