import java.util.*;


import java.util.LinkedList;
import java.util.List;


public class DictHash{
    String[] hashTable;
    int tableSize;
    int loadFactor;
    int currentLoad;


    public DictHash(){
	   tableSize = 101;
	   hashTable = new String[101];
	   loadFactor = 50;
	   currentLoad = 0;
    }
    /*
    *  takes a Key String and returns a value int. It implements quadratic probing.
    */
    public int hash(String data){
       int hashValue = 0;
	   for(int i = 0; i < data.length(); ++i)
		  hashValue = hashValue + (int) data.charAt(i) * 37;
	   if(hashValue % tableSize < 0)
		  hashValue = (hashValue % tableSize) + tableSize;
	   else
		  hashValue = hashValue % tableSize;
	   //quadratic probing
	   int pVal = 0;
	   
       while(hashTable[(hashValue + pVal * pVal) % tableSize] != null){
	       ++pVal;
        } 
	   return (hashValue + pVal * pVal) % tableSize;
	} 
    /*
    *returns the location of a key in a hash table, 
    *returns -1 if it isnt in the table.
    */
    public int find(String data){
        
        int hashValue = 0;
        for(int i = 0; i < data.length(); ++i)
            hashValue = hashValue + (int) data.charAt(i)*37;
             //make sure hash is positive
        if(hashValue % tableSize < 0)
            hashValue = (hashValue % tableSize) + tableSize;
        else
            hashValue = hashValue % tableSize;
        
        //quadratic probing
        int pVal = 0;
        //if you land on a null then it is is not there 
        if(hashTable[hashValue % tableSize] == null)
            return -1;//not found
        while(!(hashTable[(hashValue + pVal * pVal) % tableSize].equals(data))) {
            ++pVal;
            if(hashTable[(hashValue + pVal * pVal) % tableSize] == null)
                return -1; //not found
        }

        return ( (hashValue + pVal * pVal) % tableSize);    
    }
    
    //from Mark Allen Weiss 
    private static int nextPrime( int n ) {
        if( n % 2 == 0 )
            n++;

        for( ; !isPrime( n ); n += 2 )
            ;

        return n;
    }
    //from Mark Allen Weiss
    private static boolean isPrime( int n ) {
        if( n == 2 || n == 3 )
            return true;

        if( n == 1 || n % 2 == 0 )
            return false;

        for( int i = 3; i * i <= n; i += 2 )
            if( n % i == 0 )
                return false;
		return true;
    }
    /*inserts a string into the hashtable
     * Calls, rehash if the the current load limit is reached.
     */
    public void insert(String data){
    	if(currentLoad > loadFactor)
    	   rehash();
    	hashTable[hash(data)] = data;
    	++currentLoad;
		//return true;
	}
    
    private void rehash( ) {
        String[] oldTable = this.hashTable;
        hashTable = new String[nextPrime(tableSize * 2)];
        tableSize = hashTable.length;
        for(int i =0; i < oldTable.length; ++i)
            if(oldTable[i] != null)
                hashTable[hash(oldTable[i])] = oldTable[i];
        loadFactor = hashTable.length/2;
    }

}