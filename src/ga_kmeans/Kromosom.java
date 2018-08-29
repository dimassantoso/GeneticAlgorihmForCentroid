package ga_kmeans;
public class Kromosom {
    private int x;
    private int y;
    private int cluster;
    
    public Kromosom() {
        
    }

    public Kromosom(int x, int y, int cluster) {
        this.x = x;
        this.y = y;
        this.cluster = cluster;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getCluster() {
        return cluster;
    }

    public void setCluster(int cluster) {
        this.cluster = cluster;
    }
    
   
}
