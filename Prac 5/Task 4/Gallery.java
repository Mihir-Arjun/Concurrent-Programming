@SuppressWarnings({ "unchecked", "rawtypes", "generics" })
public class Gallery {
    NonBlock c;

    public Gallery() {
        c = new NonBlock();
    }

    public void enter(int person, long time) {
        while (!c.add(Thread.currentThread(), person, time)){};
    }

    public void exit() {
        while (!c.remove(Thread.currentThread())){};
    }
}