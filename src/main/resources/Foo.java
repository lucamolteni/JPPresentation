public class Foo {

    public NUM_TYPE sum(NUM_TYPE[] numbers) {
        NUM_TYPE acc = 0;
        for (NUM_TYPE i : numbers) {
            acc += i;
        }
        return acc;
    }
}
