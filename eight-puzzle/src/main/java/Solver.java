import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public final class Solver {

  private Node solution = null;

  public Solver(Board initial) {
    checkNotNull(initial, "initial == null.");
    MinPQ<Node> pq = new MinPQ<>(initial.manhattan());
    MinPQ<Node> twinPq = new MinPQ<>(initial.manhattan());
    pq.insert(new Node(initial, null));
    twinPq.insert(new Node(initial.twin(), null));
    while (true) {
      Node node = pq.delMin();
      if (node.board.isGoal()) {
        solution = node;
        return;
      }
      Node twinNode = twinPq.delMin();
      if (twinNode.board.isGoal()) {
        return;
      }

      for (Board b : node.board.neighbors()) {
        if (node.previous == null || !node.previous.board.equals(b)) {
          pq.insert(new Node(b, node));
        }
      }
      for (Board b : twinNode.board.neighbors()) {
        if (twinNode.previous == null || !twinNode.previous.board.equals(b)) {
          twinPq.insert(new Node(b, twinNode));
        }
      }
    }
  }

  public boolean isSolvable() {
    return solution != null;
  }

  public int moves() {
    return solution == null ? -1 : solution.moves;
  }

  public Iterable<Board> solution() {
    if (solution == null) {
      return null;
    }
    Stack<Board> s = new Stack<>();
    Node node = solution;
    while (node.previous != null) {
      s.push(node.board);
      node = node.previous;
    }
    s.push(node.board);
    return s;
  }

  private static <T> T checkNotNull(T object, String message) {
    if (object == null) {
      throw new NullPointerException(message);
    }
    return object;
  }

  private final class Node implements Comparable<Node> {
    private final Board board;
    private final int moves;
    private final Node previous;

    private Node(Board board, Node previous) {
      this.board = board;
      this.previous = previous;
      this.moves = previous == null ? 0 : previous.moves + 1;
    }

    @Override public int compareTo(Node that) {
      int diff = (this.board.manhattan() + this.moves) - (that.board.manhattan() + that.moves);
      if (diff != 0) {
        return diff;
      }
      return this.board.hamming() - that.board.hamming();
    }
  }

  public static void main(String[] args) {
    // create initial board from file
    In in = new In(args[0]);
    int n = in.readInt();
    int[][] blocks = new int[n][n];
    for (int i = 0; i < n; i++)
      for (int j = 0; j < n; j++)
        blocks[i][j] = in.readInt();
    Board initial = new Board(blocks);

    // solve the puzzle
    Solver solver = new Solver(initial);

    // print solution to standard output
    if (!solver.isSolvable()) {
      StdOut.println("No solution possible");
    } else {
      StdOut.println("Minimum number of moves = " + solver.moves());
      for (Board board : solver.solution())
        StdOut.println(board);
    }
  }
}
