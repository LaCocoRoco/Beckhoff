package twincat.ads;

public class AdsArray {
    /*************************/
    /*** global attributes ***/
    /*************************/

    private int begin = 0;
    
    private int end = 0;

    /*************************/
    /****** constructor ******/
    /*************************/
        
    public AdsArray(int begin, int end) {
        this.begin = begin;
        this.end = end;
    }
    
    /*************************/
    /**** setter & getter ****/
    /*************************/

    public int getBegin() {
        return begin;
    }

    public void setBegin(int begin) {
        this.begin = begin;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }    
}
