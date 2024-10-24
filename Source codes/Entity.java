public class Entity {
    public String Tag;
    public boolean Destroyable;
    public Entity(String Tag, boolean Destroyable) {
        this.Tag = Tag;
        this.Destroyable = Destroyable;
    }
    public void setTag(String tag) {
        Tag = tag;
    }
    public void setDestroyable(boolean destroyable) {
        Destroyable = destroyable;
    }
    public boolean isDestroyable() {
        return Destroyable;
    }
    @Override
    public String toString() {
        return Tag;
    }
    public Entity() {
    }

}
