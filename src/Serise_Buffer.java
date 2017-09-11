

public class Serise_Buffer {
	
	  private int NMAX=1024;     /* buffer size */
	  private  int iput = 0;  /* buffer input date point */
	  private  int iget = 0;  /* buffer get data point */
	  public  int n = 0; /* buffer have data size*/
	  static byte buffer[]=new byte[1024];    /* buffer */
	  static  boolean buffer_full_state=false;   /* buffer_full_state:false--buffer empty  true--buffer full */
	  static  boolean buffer_lock=false;   /* buffer_lock:false--can read&write  true--can't read&write */
	    
	  /**
	   *     
	   * @param i
	   * @return
	   */
      public int addring (int i)
      {  
           return (i+1) == NMAX ? 0 : i+1;  
      }  
	        
	  /**
	   *       
	   * @return
	   */
      public byte get() 
      {  
          int pos;  
          
          if(buffer_lock==false)
          {
        	  buffer_lock=true;
	          if (n>0)
	          {  
	              pos = iget;  
	              iget = addring(iget);  
	              n--; 
	              buffer_lock=false;
	              
	              /*System.out.println("get-->"+Integer.toHexString(buffer[pos])+"--size:"+(check_buffer_size())); */
	              return buffer[pos];  
	          }
	          else
	          {
		          buffer_lock=false;  
		          return (-1);
	          }
          }
          else 
          {  
              /*System.out.println("Buffer is Empty");  */
              buffer_full_state=false;
              return (-1);  
          }  
      }  
      
	  /**
	   *       
	   * @param i
	   */
      public void put(int i)
      {  
    	  if(buffer_lock==false)
    	  {
    		  buffer_lock=true;
	          if (n<NMAX)
	          {  
	              buffer[iput]=(byte)i;  
	              /*System.out.println("put<--"+Integer.toHexString(buffer[iput])+"size:"+(check_buffer_size())); */
	              /*System.out.println("put<--"+buffer[iput]+"--size:"+(check_buffer_size()));  */
	              
	              iput = addring(iput);  
	              n++;  
	              buffer_lock=false;
	          }
	          else
	          {
		          buffer_lock=false;
		          buffer_full_state=true;
	          }
    	  }
          else  
        	  buffer_full_state=true;
           /*System.out.println("Buffer is full");*/  
      }  
      
      /**
       * 
       * @return
       */
     public int check_buffer_size()
     {
      	return(n);
     }

}
