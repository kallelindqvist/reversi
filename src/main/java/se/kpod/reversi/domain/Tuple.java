package se.kpod.reversi.domain;

public class Tuple<X, Y> {
	public final X x;
	public final Y y;

	public Tuple(X x, Y y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof Tuple<?, ?>)) {
			return false;
		}
		Tuple<?, ?> objTuple = (Tuple<?, ?>) obj;
		if(this.x.equals(objTuple.x) && this.y.equals(objTuple.y)) {
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "Tuple [x=" + x + ", y=" + y + "]";
	}

}