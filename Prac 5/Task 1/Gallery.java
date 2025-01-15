@SuppressWarnings({ "unchecked", "rawtypes", "generics" })
public class Gallery {
    FineGrain c;

    public Gallery() {
        c = new FineGrain();
    }

    public void enter(int person, long time) {
        while (!c.add(Thread.currentThread(), person, time)){};
    }

    public void exit() {
        while (!c.remove(Thread.currentThread())){};
    }
}