package com.hotellook.badges.badgeviewgenerator;

public class ViewParams {
    public final int bottom;
    public final int cornerRadius;
    public final int height;
    public final int left;
    public final int right;
    public final int textSize;
    public final int top;

    public static class Builder {
        private int bottom;
        private int cornerRadius;
        private int height;
        private int left;
        private int right;
        private int textSize;
        private int top;

        public Builder height(int height) {
            this.height = height;
            return this;
        }

        public Builder left(int left) {
            this.left = left;
            return this;
        }

        public Builder top(int top) {
            this.top = top;
            return this;
        }

        public Builder right(int right) {
            this.right = right;
            return this;
        }

        public Builder bottom(int bottom) {
            this.bottom = bottom;
            return this;
        }

        public Builder textSize(int textSize) {
            this.textSize = textSize;
            return this;
        }

        public void cornerRadius(int cornerRadius) {
            this.cornerRadius = cornerRadius;
        }

        public ViewParams build() {
            return new ViewParams(this.left, this.top, this.right, this.bottom, this.textSize, this.cornerRadius, null);
        }
    }

    private ViewParams(int height, int left, int top, int right, int bottom, int textSize, int cornerRadius) {
        this.height = height;
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
        this.textSize = textSize;
        this.cornerRadius = cornerRadius;
    }
}
