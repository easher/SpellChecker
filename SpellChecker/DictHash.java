import java.util.*;
import java.util.LinkedList;
import java.util.List;

public class DictHash {
    
    String[] hashTable;
    int tableSize;
    int loadFactor;
    int currentLoad;

    /**
     * Create a DictHash object
     */
    public DictHash() {

        tableSize = 101;
        hashTable = new String[101];
        loadFactor = 50;
        currentLoad = 0;
    }

    /** 
     *inserts string into hashtable
     *
     *@param word word to be added to table
     */
    public void insert(String word){

        if(currentLoad > loadFactor)
            rehash();
        
        hashTable[hash(word)] = word;
        
        ++currentLoad;
    }
    
   /**
    *searches for word in hashtable
    *
    *@return index of location in hashTable, or -1 if its not there.
    */
    public int find(String word){
        
        int hashValue = 0;
        
        for(int i = 0; i < word.length(); ++i)
            hashValue = hashValue + (int) word.charAt(i)*37;
        
        if(hashValue % tableSize < 0)
            hashValue = (hashValue % tableSize) + tableSize;
        else
            hashValue = hashValue % tableSize;
        
        if(hashTable[hashValue % tableSize] == null)
            return -1;
        
        //preform quadratic probing until we find/dont find the word
        int pVal = 0;
        while(!(hashTable[(hashValue + pVal * pVal) % tableSize].equals(word))) {
            ++pVal;
            
            if(hashTable[(hashValue + pVal * pVal) % tableSize] == null)
                return -1;
        }

        return ((hashValue + pVal * pVal) % tableSize);
    }

    private static int nextPrime(int n) {
    
        if( n % 2 == 0 )
            n++;

        for(; !isPrime( n ); n += 2 ) ;

        return n;
    }

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
    
    
    private void rehash( ) {
    
        String[] oldTable = this.hashTable;
        hashTable = new String[nextPrime(tableSize * 2)];
        tableSize = hashTable.length;
        
        for(int i =0; i < oldTable.length; ++i)
            if(oldTable[i] != null)
                hashTable[hash(oldTable[i])] = oldTable[i];
        
        loadFactor = hashTable.length/2;
    }

    /**
     *hashes a string on ASCII value, implements quadratic probing
     *
     *@param word to be hashed
     *@return hashed falue
     */
     private int hash(String word) {
     
        int hashValue = 0;
        
        for(int i = 0; i < word.length(); ++i)
            hashValue = hashValue + (int) word.charAt(i) * 37;
        
        if(hashValue % tableSize < 0)
            hashValue = (hashValue % tableSize) + tableSize;
        else
            hashValue = hashValue % tableSize;
        
        // probe value
        int pVal = 0;
        while( hashTable[(hashValue + pVal * pVal) % tableSize] != null )
            ++pVal;
        
        return (hashValue + pVal * pVal) % tableSize;
    }
}
