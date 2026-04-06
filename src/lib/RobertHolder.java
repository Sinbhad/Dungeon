package src;

public class RobertHolder <T>{
    private Object[] bucket;
    private int size;

    public RobertHolder(){
        bucket =  new Object[10];
        size = 0;
    }

    public void addToBucket(T thing){
        if(size == bucket.length){
           doubleBucketSize();
        }
        bucket[size] = thing;
        size++;
    }

    public int size(){
        return size;
    }

    public void getStringAtIndex(int index){
        System.out.println(bucket[index]);
    }

    public void clearBucket(){
        bucket = new Object[10];
        size = 0;
    }

    public void printAll(){
        for(int i = 0; i < size; i++){
            System.out.print("[" + bucket[i] + "]");
        }
    }

    public void addAtIndex(int index, T thing) {
        if (size == bucket.length){
            doubleBucketSize();
        }
        for (int i = size; i > index; i--) {
            bucket[i] = bucket[i - 1];
        }
        bucket[index] = thing;
        size++;
    }

    public void replaceAtIndex(int index, T thing){
        if (size == bucket.length){
            doubleBucketSize();
        }
        bucket[index] = thing;
    }

    public void find(T thing){
        for(int i = 0; i < size; i++){
            if(bucket[i].equals(thing)){
                System.out.println(thing + " is in the array!");
            }
        }
    }

    public void findCount(T thing ){
        int count = 0;
        for(int i = 0; i < size - 1; i++){
            if(bucket[i].equals(thing)){
                count++;
            }
        }
        System.out.println(thing + " was found in the array " + count + " times!");
    }

    public void removeAtIndex(int index){
        bucket[index] = null;
        for(int i = index; i < size - 1; i++){
            bucket[i] = bucket[i + 1];
        }
        bucket[size - 1] = null;
        size--;
    }

    public void doubleBucketSize(){
        Object[] bucket2 = new Object[bucket.length * 2];
        for(int i = 0; i < bucket.length; i++){
            bucket2[i] =  bucket[i];
        }
        bucket = bucket2;
    }

    public void addToEnd(T thing){
        if (size == bucket.length){
            doubleBucketSize();
        }
        bucket[size] = thing;
        size++;
    }

    public RobertHolder<T> cloneClass(){
        RobertHolder<T> robertCopy = new RobertHolder<>();
        robertCopy.bucket = new Object[this.bucket.length];
        for(int i = 0; i < size; i++){
            robertCopy.bucket[i] = this.bucket[i];
        }
        robertCopy.size = this.size;
        return robertCopy;
    }

    @SuppressWarnings("unchecked")
    public T[] getBucket(){
        T[] arrayValues = (T[]) new Object[size];
        for(int i = 0; i < size; i++){
            arrayValues[i] = (T) bucket[i];
        }
        return arrayValues;
    }

    @SuppressWarnings("unchecked")
    public T getAtIndex(int index){
        return (T)bucket[index];
    }
}
