
class S {
	int x = 0;
	int z() { return x; }
}
class T extends S {
	int x = 1;
	int z() { return x; }
}

public class StaticBindingForMethodAccess {
	public static void main(String[] args) {
		T t = new T();
		System.out.println(t.z());
		S s = new S();
		System.out.println(s.z());
		s = t;
		System.out.println(s.z());
		System.out.println("passed");
	}
}
