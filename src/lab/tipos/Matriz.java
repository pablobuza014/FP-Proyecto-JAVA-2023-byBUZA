package lab.tipos;

public interface Matriz<E> {

	Integer nf();

	Integer nc();

	E get(Integer f, Integer c);
	
	void change(Integer f, Integer c, E n);

	Boolean esSimetrica();

	Matriz<E> traspuesta();

}