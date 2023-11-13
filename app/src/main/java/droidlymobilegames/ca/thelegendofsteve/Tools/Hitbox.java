package droidlymobilegames.ca.thelegendofsteve.Tools;

public class Hitbox {
    public int x;
    public int y;
    public int width;
    public int height;

    public Hitbox() {
        //this(0, 0, 0, 0);
    }
    public Hitbox(Hitbox r) {
        this(r.x, r.y, r.width, r.height);
    }
    public Hitbox(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void set(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public boolean intersects(Hitbox bounds) {
        return this.x >= bounds.x && this.x < bounds.x + bounds.width
                && this.y >= bounds.y && this.y < bounds.y + bounds.height;
    }

    public boolean contains(int x, int y) {
        return x >= this.x && x < this.x + this.width && y >= this.y && y < this.y + this.height;
    }
    public boolean intersecting(Hitbox r) {
        int tw = this.width;
        int th = this.height;
        int rw = r.width;
        int rh = r.height;
        if (rw > 0 && rh > 0 && tw > 0 && th > 0) {
            int tx = this.x;
            int ty = this.y;
            int rx = r.x;
            int ry = r.y;
            rw += rx;
            rh += ry;
            tw += tx;
            th += ty;
            return (rw < rx || rw > tx) &&
                    (rh < ry || rh > ty) &&
                    (tw < tx || tw > rx) &&
                    (th < ty || th > ry);
        } else {
            return false;
        }
    }

    public int getX() {
        return (int)this.x;
    }

    public int getY() {
        return (int)this.y;
    }

    public double getWidth() {
        return (double)this.width;
    }

    public double getHeight() {
        return (double)this.height;
    }
    public boolean isRectangleOverlap(int[] rec1, int[] rec2) {
        return overlap(rec1[0], rec1[2], rec2[0], rec2[2]) > 0 && overlap(rec1[1], rec1[3], rec2[1], rec2[3]) > 0;
    }
    private int overlap(int left1, int right1, int left2, int right2) {
        return Math.min(right1, right2) >= Math.max(left1, left2) ? Math.min(right1, right2) - Math.max(left1, left2) : 0;
    }
}
