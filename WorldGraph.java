/* Code by Sherri Goings
For Darwin Final Project in CS201 (Data Structures)
Last Modified for Fall 2011 Term
--------------------------------------------------------------------------------------------------
The WorldMap class handles all of the graphics in the Darwin simluation. It depends on the
Draw class for the actual drawing, and also the Point class and Direction enum for other operations.
--------------------------------------------------------------------------------------------------*/

import java.util.*;

public class WorldGraph extends BaseWorldGraph {
  private Vertex[][] vertices;
  private int height;
  private int width;

  private class Vertex {
    private Point point;
    private LinkedList<Vertex> edges;
    private BaseCreature critter;

    public Vertex(Point p) {
      point = p;
      edges = new LinkedList<Vertex>();
      critter = null;
    }

    public String toString() {
      return "(" + point.x + "," + point.y + ")";
    }
  }

  public WorldGraph(int width, int height) {
    this.width = width;
    this.height = height;
    vertices = (Vertex[][]) new Vertex[width][height];
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        Point p = new Point(x, y);
        vertices[x][y] = new Vertex(p);
      }
    }
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        Point p = new Point(x, y);
        for (Direction dir : Direction.values()) {
          Point p2 = adjPoint(p, dir);
          if (p2 != null) addEdge(p, dir);
        }
      }
    }
  }

  public void printGraph() {
    for (Vertex[] cols : vertices) {
      for (Vertex v : cols) {
        System.out.print(v + ": ");
        for (Vertex e : v.edges) System.out.print(e + ", ");
        System.out.println();
      }
    }
  }

  public void removeEdge(Point curP, Direction dir) {
    Point edgeP = adjPoint(curP, dir);
    removeEdge(vertices[curP.x][curP.y], vertices[edgeP.x][edgeP.y]);
  }

  public void removeEdge(Vertex Va, Vertex Vb) {
    Va.edges.remove(Vb);
    Vb.edges.remove(Va);
  }

  public void addEdge(Vertex Va, Vertex Vb) {
    if (!Va.edges.contains(Vb)) Va.edges.add(Vb);
    if (!Vb.edges.contains(Va)) Vb.edges.add(Va);
  }

  public void addEdge(Point curP, Direction dir) {
    Point edgeP = adjPoint(curP, dir);
    addEdge(vertices[curP.x][curP.y], vertices[edgeP.x][edgeP.y]);
  }

  public boolean getEdge(Vertex Va, Vertex Vb) {
    if (Va.edges.contains(Vb)) return true;
    return false;
  }

  public boolean getEdge(Point a, Point b) {
    return getEdge(vertices[a.x][a.y], vertices[b.x][b.y]);
  }

  public void addCreature(Point p, BaseCreature critter) {
    if (inRange(p)) vertices[p.x][p.y].critter = critter;
  }

  public BaseCreature getCreature(Point p) {
    if (inRange(p)) return vertices[p.x][p.y].critter;
    else return null;
  }

  public void removeCreature(Point p) {
    if (inRange(p)) vertices[p.x][p.y].critter = null;
    else {
      System.out.println("ERROR: tryin to remove creature from point out of range");
      System.exit(1);
    }
  }

  public boolean inRange(Point p) {
    if (p.x < 0 || p.x >= width || p.y < 0 || p.y >= height) return false;
    return true;
  }

  public Point adjPoint(Point p, Direction d) {
    if (!inRange(p)) return null;
    Point point = new Point(p.x, p.y);

    if (d == Direction.NORTH) {
      point.y = p.y - 1;
      if (p.y == 0) return null;
    } else if (d == Direction.SOUTH) {
      point.y = p.y + 1;
      if (p.y == height - 1) return null;

    } else if (d == Direction.EAST) {
      point.x = p.x + 1;
      if (p.x == width - 1) return null;

    } else if (d == Direction.WEST) {
      point.x = p.x - 1;
      if (p.x == 0) return null;
    } else System.out.println("invalid direction passed to adjPoint");

    return point;
  }

  public static void main(String[] args) {
    /*World world = new World(5,5,0);
    WorldGraph wg = new WorldGraph(5,5,world);
    Point p1 = new Point(1,1);
    Point p2 = new Point(1,2);
    System.out.println(wg.getEdge(p1,p2));
    wg.printGraph();*/
  }
}
